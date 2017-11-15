package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.JzPersonList;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzBeianBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.utils.ActivityAlertDialogManager;
import com.messoft.gzmy.nineninebrothers.utils.KeybordUtils;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.RegexUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.TimeUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 Administrator
 * 解债备案
 * @创建日期 2017/10/27 0027 17:49
 */

public class JzBeianActivity extends BaseActivity<ActivityJzBeianBinding> {

    private JzModel mJzModel;
    private String mZqId;
    private String mZwId;
    private int debtType = -1;//债务类型(0:货币,1:非货币)
    private int debtProperty = -1;//债务性质(0:个人,1:企业)
    private int lawsuitState = -1;//诉讼状态(0:非诉讼,1:已诉讼)
    private int containInterest;//是否包含利息(0:不含利息,1:包含利息)
    private String mTime;
    TimePickerDialog mDialogYearMonthDay;

    ArrayAdapter<String> mSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_beian);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("解债备案");
        showContentView();
        mJzModel = new JzModel();

        //含息下拉框
        initSpinner();

        //格式化债事金额
        StringUtils.formatEditText(bindingView.xetMoney);

        //初始化年月日
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        mTime = TimeUtils.getDateToString(millseconds);
                        bindingView.tvTime.setText(mTime);
                    }
                })
                .build();
    }

    private void initListener() {
        //查询债权人
        bindingView.tvQueryZq.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String idCard = bindingView.xetZqIdCard.getText().toString().trim();
                if (!StringUtils.isNoEmpty(idCard)) {
                    ToastUtil.showToast("请输入债权人身份证号码");
                    return;
                }
                if (!RegexUtil.checkIdCard(idCard)) {
                    ToastUtil.showToast("请输入正确的债权人身份证号码");
                    return;
                }
                queryZqNameByIdCard(0, idCard);
            }
        });

        //查询债务人
        bindingView.tvQueryZw.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String idCard = bindingView.xetZwIdCard.getText().toString().trim();
                if (!StringUtils.isNoEmpty(idCard)) {
                    ToastUtil.showToast("请输入债务人身份证号码");
                    return;
                }
                if (!RegexUtil.checkIdCard(idCard)) {
                    ToastUtil.showToast("请输入正确的债务人身份证号码");
                    return;
                }
                queryZqNameByIdCard(1, idCard);
            }
        });

        //含息
        bindingView.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showToast(mSpinnerAdapter.getItem(position));
                containInterest = (position == 0) ? 1 : 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //债事类型
        bindingView.rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_currency:
                        debtType = 0;
                        break;
                    case R.id.rb_no_currency:
                        debtType = 1;
                        break;
                }
            }
        });

        //债事性质
        bindingView.rgProperty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_personal:
                        debtProperty = 0;
                        break;
                    case R.id.rb_company:
                        debtProperty = 1;
                        break;
                }
            }
        });

        //债事性质
        bindingView.rgLawsuit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_lawsuit:
                        lawsuitState = 0;
                        break;
                    case R.id.rb_no_lawsuit:
                        lawsuitState = 1;
                        break;
                }
            }
        });
        //时间
        bindingView.llTime.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showPopTime();
            }
        });

        //用于点击空白取消软键盘
        bindingView.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeybordUtils.inputClose(bindingView.scrollView, JzBeianActivity.this);
                return onTouchEvent(event);
            }
        });
        //下一步
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                goNext();
//                SysUtils.startActivity(JzBeianActivity.this, JzBeiAnNextOneActivity.class);
            }
        });
    }

    private void showPopTime() {
        mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
    }

    private void goNext() {
        String zsMoney = bindingView.xetMoney.getText().toString().trim();
        if (!StringUtils.isNoEmpty(mZqId)) {
            ToastUtil.showToast("请先查询债事人证件号");
            return;
        }
        if (!StringUtils.isNoEmpty(mZwId)) {
            ToastUtil.showToast("请先查询债务人证件号");
            return;
        }
        if (debtType == -1) {
            ToastUtil.showToast("请选择债事类型");
            return;
        }
        if (debtProperty == -1) {
            ToastUtil.showToast("请选择债事性质");
            return;
        }
        if (lawsuitState == -1) {
            ToastUtil.showToast("请选择诉讼情况");
            return;
        }
        if (!StringUtils.isNoEmpty(zsMoney)) {
            ToastUtil.showToast("请输入债事金额");
            return;
        }
        Double douMoney = Double.parseDouble(zsMoney);
        if (douMoney <= 0) {
            ToastUtil.showToast("请输入正确的债事金额");
            return;
        }
        if (!StringUtils.isNoEmpty(mTime)) {
            ToastUtil.showToast("请选择债事时间");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("creditorId", mZqId);
        bundle.putString("debtorId", mZwId);
        bundle.putString("debtType", debtType + "");
        bundle.putString("debtProperty", debtProperty + "");
        bundle.putString("lawsuitState", lawsuitState + "");
        bundle.putString("debtAmount", zsMoney + "");
        bundle.putString("containInterest", containInterest + "");
        bundle.putString("debtDate", mTime);
        SysUtils.startActivity(JzBeianActivity.this, JzBeiAnNextOneActivity.class, bundle);
    }

    /**
     * 身份证模糊查询债市人列表
     *
     * @param type   0--债权  1--债务
     * @param idCard
     */
    private void queryZqNameByIdCard(final int type, String idCard) {
        mJzModel.getDebtMatterPersonList(JzBeianActivity.this, idCard, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                List<JzPersonList> data = (List<JzPersonList>) object;
                if (data != null && data.size() == 1) {
                    //查到了唯一一个
                    if (type == 0) {
                        bindingView.tvZqName.setText(data.get(0).getName());
                        mZqId = data.get(0).getId();
                    } else {
                        bindingView.tvZwName.setText(data.get(0).getName());
                        mZwId = data.get(0).getId();
                    }
                } else if (data != null && data.size() > 1) {
                    ToastUtil.showToast("查询到了多个债事人，请确定输入的身份证号码是否唯一");
                } else if (data != null && data.isEmpty()) {
                    //提示去创建
                    AlertDialog alertDialog = ActivityAlertDialogManager.displayOneBtnDialog(
                            JzBeianActivity.this,
                            new ActivityAlertDialogManager.TipInfo("提示", "查询暂无（输入证件号码）债事人的相关信息", "去创建"),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SysUtils.startActivity(JzBeianActivity.this, JzPersonBeiAnActivity.class);
                                }
                            });
                    alertDialog.show();
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    private void initSpinner() {
        List<String> listSpinner = new ArrayList<>();
        listSpinner.add("含息");
        listSpinner.add("不含息");
        mSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_item,
                listSpinner);
        //设置样式
        mSpinnerAdapter.setDropDownViewResource(R.layout.spinner_text_item);
        bindingView.spinner.setAdapter(mSpinnerAdapter);
        bindingView.spinner.setSelection(0, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityAlertDialogManager.destory(this);
    }
}
