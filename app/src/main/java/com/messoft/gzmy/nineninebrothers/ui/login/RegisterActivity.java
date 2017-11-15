package com.messoft.gzmy.nineninebrothers.ui.login;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.SelectStreetAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.Login;
import com.messoft.gzmy.nineninebrothers.bean.PersonInfo;
import com.messoft.gzmy.nineninebrothers.bean.RxBusMessage;
import com.messoft.gzmy.nineninebrothers.bean.Street;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityRegisterBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.rx.RxBus;
import com.messoft.gzmy.nineninebrothers.interfae.TimeListener;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.RegexUtil;
import com.messoft.gzmy.nineninebrothers.utils.SPUtils;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.TimeCount;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.messoft.gzmy.nineninebrothers.view.cityselectpop.CitySelectPopWindow;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import java.util.List;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private static final String TAG = "RegisterActivity";

    //省市区选择
    private CitySelectPopWindow cityPop;
    private Handler mHandler;
    private String mCurrentProviceName, mCurrentProviceID, mCurrentCityName, mCurrentCityID,
            mCurrentDistrictName, mCurrentDistrictCode;

    private LoginModel mLoginModel;
    private XRecyclerView mXrcStreet; //街道选择
    private int selectStreetFlag = 0;//街道选择第一进来--0
    private SelectStreetAdapter mSelectStreetAdapter;
    private String mStreetId;
    private String mStreetName;

    private TimeCount mTimeCount;
    private BaseNiceDialog mStreetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initSetting();
        initListener();
    }

    private void initSetting() {
        showContentView();
        setTitle("注册");

        mLoginModel = new LoginModel();
        mSelectStreetAdapter = new SelectStreetAdapter(RegisterActivity.this);
        //计时器
        mTimeCount = new TimeCount(60000, 1000, new TimeListener() {
            @Override
            public void timeFinish() {
                if (!isFinishing()) {
                    bindingView.tvGetCode.setText("重新获取");
                    bindingView.tvGetCode.setClickable(true);
                    mTimeCount.cancel();
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                //计时过程显示
                if (!isFinishing()) {
                    bindingView.tvGetCode.setClickable(false);
                    bindingView.tvGetCode.setText(millisUntilFinished / 1000 + "秒");
                }
                if (isFinishing()) {
                    mTimeCount.cancel();
                }
            }
        });

        //初始化handler
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    //收到pop的通知
                    mCurrentProviceName = cityPop.getCurrentProviceName();
                    mCurrentProviceID = cityPop.getCurrentProviceID();
                    mCurrentCityName = cityPop.getCurrentCityName();
                    mCurrentCityID = cityPop.getCurrentCityID();
                    mCurrentDistrictName = cityPop.getCurrentDistrictName();
                    mCurrentDistrictCode = cityPop.getCurrentDistrictCode();
                    //// REFACTOR: 2017/10/25 0025 待重构 因为选择南山区有两个南山区会选到第二个上，所以南山区加个"区"
                    if (mCurrentDistrictName.equals("南山")) {
                        mCurrentDistrictName = mCurrentDistrictName + "区";
                    }
                    bindingView.tvAddress.setText(mCurrentProviceName + " " + mCurrentCityName + " " + mCurrentDistrictName);
                    DebugUtil.debug(TAG, "选择获取的id数组" + mCurrentProviceID + " " + mCurrentCityID + " " + mCurrentDistrictCode);
                    if (cityPop != null) {
                        cityPop.dismiss();
                    }
                }
            }
        };

        cityPop = new CitySelectPopWindow(RegisterActivity.this, mHandler);
        // 设置弹出窗体的背景
        cityPop.setBackgroundDrawable(new BitmapDrawable());

    }

    private void initListener() {
        //获取验证码
        bindingView.tvGetCode.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                getCode();
            }
        });
        //注册
        bindingView.tvRegister.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                clickRegister();
            }
        });
        //选择省市区
        bindingView.tvAddress.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (cityPop.isShowing()) {
                    cityPop.dismiss();
                } else {
                    cityPop.showAtLocation(bindingView.tvAddress, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
        //选择街道
        bindingView.tvStreet.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                clickStreet();
            }
        });

        //点击街道
        mSelectStreetAdapter.setOnItemClickListener(new OnItemClickListener<Street>() {
            @Override
            public void onClick(Street street, int position) {
                if (street != null) {
                    mStreetId = street.getId()+"";
                    mStreetName = street.getTitle();
                    bindingView.tvStreet.setText(mStreetName);
                    if (mStreetDialog != null) {
                        mStreetDialog.dismiss();
                    }
                }
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String etPhone = bindingView.etPhone.getText().toString().trim();
        if (!StringUtils.isNoEmpty(etPhone)) {
            ToastUtil.showToast("手机号码不能为空");
            return;
        }
        if (!RegexUtil.checkMobile(etPhone)) {
            ToastUtil.showToast("手机号码格式不正确");
            return;
        }
        mLoginModel.getCode(RegisterActivity.this, etPhone, "0", new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                //// REFACTOR: 2017/10/25 0025 待重构  这里获取的object为null 不知为什么
                ToastUtil.showToast("验证码已发送到您的手机");
                mTimeCount.start();
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    private void clickStreet() {
        if (!StringUtils.isNoEmpty(mCurrentDistrictCode)) {
            ToastUtil.showToast("请先选择所属地区");
            return;
        }
        loadStreetList();
    }

    /**
     * 请求街道信息
     */
    private void loadStreetList() {
        mLoginModel.searchStreetById(RegisterActivity.this, mCurrentDistrictCode, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                List<Street> streetList = (List<Street>) object;
                showPopStreet(streetList);
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    /**
     * 展示街道弹窗
     *
     * @param streetList
     */
    private void showPopStreet(final List<Street> streetList) {
        if (streetList == null && streetList.size() < 1) {
            ToastUtil.showToast("暂无街道可以选择");
            return;
        }
//        ToastUtil.showToast(streetList.size()+"");
        // 需加，不然滑动不流畅
//                .setDimAmount(0.3f)
        mStreetDialog = NiceDialog.init()
                .setLayoutId(R.layout.dialog_select_street)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        mXrcStreet = (XRecyclerView) holder.getConvertView().findViewById(R.id.xrc_street);
                        if (selectStreetFlag == 0) {
                            mXrcStreet.setLoadingMoreEnabled(false);
                            mXrcStreet.setPullRefreshEnabled(false);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(RegisterActivity.this);
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            mXrcStreet.setLayoutManager(mLayoutManager);
                            mXrcStreet.setAdapter(mSelectStreetAdapter);
                            // 需加，不然滑动不流畅
                            mXrcStreet.setNestedScrollingEnabled(false);
                            mXrcStreet.setHasFixedSize(false);

                            selectStreetFlag = 1;
                        }
                        mSelectStreetAdapter.clear();
                        mSelectStreetAdapter.addAll(streetList);
                        mSelectStreetAdapter.notifyDataSetChanged();
                    }
                })
//                .setDimAmount(0.3f)
                .setShowBottom(true)
                .setOutCancel(true);
        mStreetDialog.show(getSupportFragmentManager());
    }

    private void clickRegister() {
        final String etPhone = bindingView.etPhone.getText().toString().trim();
        String etCode = bindingView.etCode.getText().toString().trim();
        final String etPsw = bindingView.etPsw.getText().toString().trim();
        String etFatherReferralCode = bindingView.etRecommendCode.getText().toString().trim();

        if (!StringUtils.isNoEmpty(etPhone)) {
            ToastUtil.showToast("请输入手机号");
            return;
        }
        if (!RegexUtil.checkMobile(etPhone)) {
            ToastUtil.showToast("手机号不符合规范");
            return;
        }
        if (!StringUtils.isNoEmpty(etCode)) {
            ToastUtil.showToast("请输入验证码");
            return;
        }
        if (!StringUtils.isNoEmpty(etPsw)) {
            ToastUtil.showToast("请输入密码");
            return;
        }
        if (!RegexUtil.isPassword(etPsw)) {
            ToastUtil.showToast("请设置规范的密码（6-18位包含字母和数字）");
            return;
        }
        if (!StringUtils.isNoEmpty(mCurrentProviceID) || !StringUtils.isNoEmpty(mCurrentProviceName)
                || !StringUtils.isNoEmpty(mCurrentCityID) || !StringUtils.isNoEmpty(mCurrentCityName)
                || !StringUtils.isNoEmpty(mCurrentDistrictCode) || !StringUtils.isNoEmpty(mCurrentDistrictName)) {
            ToastUtil.showToast("请选择省市区");
            return;
        }
        mLoginModel.register(RegisterActivity.this,
                etPhone,
                etCode,
                etPsw,
                etFatherReferralCode,
                mCurrentProviceID,
                mCurrentProviceName,
                mCurrentCityID,
                mCurrentCityName,
                mCurrentDistrictCode,
                mCurrentDistrictName,
                mStreetId,
                mStreetName, new RequestImpl() {
                    @Override
                    public void loadSuccess(Object object) {
                        ToastUtil.showToast("注册成功");
                        if (object != null) {
//                            Login login = (Login) object;
//                            saveUserData(login);
//                            SysUtils.startActivity(RegisterActivity.this, MainActivity.class);
                            PersonInfo personInfo = new PersonInfo(etPhone, etPsw);
                            RxBus.getInstanceBus().post(new RxBusMessage<PersonInfo>(personInfo));
                            finish();
                        }
                    }

                    @Override
                    public void loadFailed(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }

    /**
     * 保存用户信息
     *
     * @param login
     */
    private void saveUserData(Login login) {
        SPUtils.putObject("loginObject", login);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mTimeCount.cancel();
        }
    }

}
