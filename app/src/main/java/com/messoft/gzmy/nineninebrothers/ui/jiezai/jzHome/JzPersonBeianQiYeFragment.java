package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.SelectStreetAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.Street;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentJzPersonBeianQiyeBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.utils.KeybordUtils;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.RegexUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.DataProvider;
import chihane.jdaddressselector.ISelectAble;
import chihane.jdaddressselector.SelectedListener;
import chihane.jdaddressselector.Selector;

/**
 * Created by Administrator on 2017/10/27 0027.
 * 债事人备案-企业
 */

public class JzPersonBeianQiYeFragment extends BaseFragment<FragmentJzPersonBeianQiyeBinding> {

    private JzModel mJzModel;
    private LoginModel mLoginModel;
    private Selector mSelector;
    private BottomDialog mDialog;
    private String mProvince;
    private String mProvinceText;
    private String mCity;
    private String mCityText;
    private String mDistrict;
    private String mDistrictText;
    private String mStreet;
    private String mStreetText;

    private BaseNiceDialog mStreetDialog;
    private XRecyclerView mXrcStreet; //街道选择
    private SelectStreetAdapter mSelectStreetAdapter;


    @Override
    protected int setContent() {
        return R.layout.fragment_jz_person_beian_qiye;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSetting();
        initListener();

    }

    private void initSetting() {
        showContentView();
        mJzModel = new JzModel();
        mLoginModel = new LoginModel();
        mSelectStreetAdapter = new SelectStreetAdapter(getActivity());
    }

    private void initListener() {
        //用于点击空白取消软键盘
        bindingView.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeybordUtils.inputClose(bindingView.scrollView, getActivity());
                return getActivity().onTouchEvent(event);
            }
        });
        //下一步
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                goNext();
//                Bundle bundle = new Bundle();
//                bundle.putString("id", "113");
//                bundle.putString("type", "0");
//                SysUtils.startActivity(getActivity(), JzUploadImgsActivity.class, bundle);
            }
        });
        //选地址
        bindingView.xetAddress.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showAddressDialog();
            }
        });
        //选街道
        bindingView.xetStreet.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                loadStreetList();
            }
        });
        //点击街道
        mSelectStreetAdapter.setOnItemClickListener(new OnItemClickListener<Street>() {
            @Override
            public void onClick(Street street, int position) {
                if (street != null) {
                    mStreet = street.getId() + "";
                    mStreetText = street.getTitle();
                    bindingView.xetStreet.setText(mStreetText);
                    if (mStreetDialog != null) {
                        mStreetDialog.dismiss();
                    }
                }
            }
        });
    }

    /**
     * 选择地址
     */
    private void showAddressDialog() {
        mSelector = new Selector(getActivity(), 3);
        setAddressData();
        mDialog = new BottomDialog(getActivity());
        mDialog.init(getActivity(), mSelector);
        mDialog.show();
    }

    private void setAddressData() {

        mSelector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, final DataReceiver receiver) {
                //根据tab的深度和前一项选择的id，获取下一级菜单项
//                Log.i(TAG, "provideData: currentDeep >>> " + currentDeep + " preId >>> " + preId);
                if (currentDeep == 0) {
                    preId = 0;
                }
                mLoginModel.searchStreetById(getActivity(), preId + "", new RequestImpl() {
                    @Override
                    public void loadSuccess(Object object) {
                        List<Street> streetList = (List<Street>) object;
                        receiver.send(getDatas(streetList));
                    }

                    @Override
                    public void loadFailed(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
            }
        });
        mSelector.setSelectedListener(new SelectedListener() {
            @Override
            public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
                if (selectAbles != null && selectAbles.size() == 3) {
                    if (null != selectAbles.get(0)) {
                        mProvince = selectAbles.get(0).getId() + "";
                        mProvinceText = selectAbles.get(0).getName() + "";
                    }
                    if (null != selectAbles.get(1)) {
                        mCity = selectAbles.get(1).getId() + "";
                        mCityText = selectAbles.get(1).getName() + "";
                    }
                    if (null != selectAbles.get(2)) {
                        mDistrict = selectAbles.get(2).getId() + "";
                        mDistrictText = selectAbles.get(2).getName() + "";
                    } else {
                        mDistrict = null;
                        mDistrictText = null;
                    }
                    bindingView.xetAddress.setText(mProvinceText + mCityText + mDistrictText);
                    //选完省市区清空街道
                    bindingView.xetStreet.setText(null);
                    mStreet = null;
                    mStreetText = null;

                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            }
        });
    }

    private ArrayList<ISelectAble> getDatas(final List<Street> list) {

        ArrayList<ISelectAble> data = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            final int finalJ = j;
            data.add(new ISelectAble() {
                @Override
                public String getName() {
                    return list.get(finalJ).getTitle();
                }

                @Override
                public int getId() {
                    return list.get(finalJ).getId();
                }

                @Override
                public Object getArg() {
                    return this;
                }
            });
        }
        return data;
    }

    /**
     * 选街道
     */
    private void loadStreetList() {
        if (!StringUtils.isNoEmpty(mDistrict) || !StringUtils.isNoEmpty(mDistrictText)) {
            ToastUtil.showToast("没有选择地区，无法选择街道");
            return;
        }
        mLoginModel.searchStreetById(getActivity(), mDistrict, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                List<Street> streetList = (List<Street>) object;
                if (streetList == null || streetList.size() <= 0) {
                    ToastUtil.showToast("暂无可选街道");
                    return;
                }
                showPopStreet(streetList);
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    private void showPopStreet(final List<Street> streetList) {
        if (streetList == null && streetList.size() < 1) {
            ToastUtil.showToast("暂无街道可以选择");
            return;
        }
        mStreetDialog = NiceDialog.init()
                .setLayoutId(R.layout.dialog_select_street)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        mXrcStreet = (XRecyclerView) holder.getConvertView().findViewById(R.id.xrc_street);
                        mXrcStreet.setLoadingMoreEnabled(false);
                        mXrcStreet.setPullRefreshEnabled(false);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mXrcStreet.setLayoutManager(mLayoutManager);
                        mXrcStreet.setAdapter(mSelectStreetAdapter);
                        // 需加，不然滑动不流畅
                        mXrcStreet.setNestedScrollingEnabled(false);
                        mXrcStreet.setHasFixedSize(false);

                        mSelectStreetAdapter.clear();
                        mSelectStreetAdapter.addAll(streetList);
                        mSelectStreetAdapter.notifyDataSetChanged();
                    }
                })
//                .setDimAmount(0.3f)
                .setShowBottom(true)
                .setOutCancel(true);
        mStreetDialog.show(getActivity().getSupportFragmentManager());
    }

    private void goNext() {
        String etCode = bindingView.xetCode.getText().toString().trim();
        String etZsName = bindingView.xetZsName.getText().toString().trim();
        String etlegalName = bindingView.xetLegalName.getText().toString().trim();
        String etIdCard = bindingView.xetIdCard.getText().toString().trim();
        String etAddress = bindingView.xetAddress.getText().toString().trim();
        String etStreet = bindingView.xetStreet.getText().toString().trim();
        String etIndustry = bindingView.xetIndustry.getText().toString().trim();
        String etPhone = bindingView.xetPhone.getText().toString().trim();
        String etRegisterCapital = bindingView.xetRegisterCapital.getText().toString().trim();
        String etEmail = bindingView.xetEmail.getText().toString().trim();
        String etWx = bindingView.xetWx.getText().toString().trim();
        String etQq = bindingView.xetQq.getText().toString().trim();

        if (!StringUtils.isNoEmpty(etCode)) {
            ToastUtil.showToast("请填写组织机构代码");
            return;
        }
        if (!StringUtils.isNoEmpty(etZsName)) {
            ToastUtil.showToast("请填写企业债事名称");
            return;
        }
        if (!StringUtils.isNoEmpty(etlegalName)) {
            ToastUtil.showToast("请填写企业法人姓名");
            return;
        }
        if (!StringUtils.isNoEmpty(etIdCard)) {
            ToastUtil.showToast("请填写法人身份证号码");
            return;
        }
        if (!RegexUtil.checkIdCard(etIdCard)) {
            ToastUtil.showToast("请填写正确的法人身份证号码");
            return;
        }
        if (!StringUtils.isNoEmpty(etAddress)) {
            ToastUtil.showToast("请选择所属地区");
            return;
        }
        if (!StringUtils.isNoEmpty(etStreet)) {
            ToastUtil.showToast("请选择所属街道");
            return;
        }
        if (!StringUtils.isNoEmpty(etPhone)) {
            ToastUtil.showToast("请填写联系电话");
            return;
        }
        if (!RegexUtil.checkMobileAndTel(etPhone)) {
            ToastUtil.showToast("请填写正确的手机号或固定电话号码");
            return;
        }
        if (!StringUtils.isNoEmpty(etRegisterCapital)) {
            ToastUtil.showToast("请填写注册资本");
            return;
        }
        Double douCapital = Double.parseDouble(etRegisterCapital);
        if (douCapital <= 0) {
            ToastUtil.showToast("注册资本不能小于0元");
            return;
        }
        if (!StringUtils.isNoEmpty(etEmail)) {
            ToastUtil.showToast("请填写邮箱");
            return;
        }
        if (!RegexUtil.checkEmail(etEmail)) {
            ToastUtil.showToast("请输入正确的邮箱");
            return;
        }

        mJzModel.debtMatterPersonRecord(getActivity(),
                0,
                etCode,
                etZsName,
                etlegalName,
                etIdCard,
                mProvince,
                mProvinceText,
                mCity,
                mCityText,
                mDistrict,
                mDistrictText,
                mStreet,
                mStreetText,
                etIndustry,
                etPhone,
                etRegisterCapital,
                etEmail,
                etWx,
                etQq, new RequestImpl() {
                    @Override
                    public void loadSuccess(Object object) {
                        BaseBean bean = (BaseBean) object;
                        String id = bean.getId();
                        if (StringUtils.isNoEmpty(id)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            bundle.putString("type", "0");
                            SysUtils.startActivity(getActivity(), JzUploadImgsActivity.class, bundle);

                            getActivity().finish();
                        }
                    }

                    @Override
                    public void loadFailed(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });

//        Bundle bundle = new Bundle();
//        bundle.putString("code",etCode);
//        bundle.putString("zs_name",etZsName);
//        bundle.putString("legal_name",etlegalName);
//        bundle.putString("id_card",etIdCard);
//        bundle.putString("address",etAddress);
//        bundle.putString("street",etStreet);
//        bundle.putString("industry",etIndustry);
//        bundle.putString("phone",etPhone);
//        bundle.putString("register_capital",etRegisterCapital);
//        bundle.putString("email",etEmail);
//        bundle.putString("wx",etWx);
//        bundle.putString("qq",etQq);
//        SysUtils.startActivity(getActivity(), JzUploadImgsActivity.class,bundle);
    }

    /**
     * 是否修改了信息
     *
     * @return
     */
    public boolean isChangeInfo() {
        String etCode = bindingView.xetCode.getText().toString().trim();
        String etZsName = bindingView.xetZsName.getText().toString().trim();
        String etlegalName = bindingView.xetLegalName.getText().toString().trim();
        String etIdCard = bindingView.xetIdCard.getText().toString().trim();
        String etAddress = bindingView.xetAddress.getText().toString().trim();
        String etStreet = bindingView.xetStreet.getText().toString().trim();
        String etIndustry = bindingView.xetIndustry.getText().toString().trim();
        String etPhone = bindingView.xetPhone.getText().toString().trim();
        String etRegisterCapital = bindingView.xetRegisterCapital.getText().toString().trim();
        String etEmail = bindingView.xetEmail.getText().toString().trim();
        String etWx = bindingView.xetWx.getText().toString().trim();
        String etQq = bindingView.xetQq.getText().toString().trim();
        if (TextUtils.isEmpty(etCode) && TextUtils.isEmpty(etZsName) && TextUtils.isEmpty(etlegalName) &&
                TextUtils.isEmpty(etIdCard) && TextUtils.isEmpty(etAddress) && TextUtils.isEmpty(etStreet) &&
                TextUtils.isEmpty(etIndustry) && TextUtils.isEmpty(etPhone) &&
                TextUtils.isEmpty(etRegisterCapital) && TextUtils.isEmpty(etEmail) &&
                TextUtils.isEmpty(etWx) && TextUtils.isEmpty(etQq)) {
            return false;
        }
        return true;
    }
}
