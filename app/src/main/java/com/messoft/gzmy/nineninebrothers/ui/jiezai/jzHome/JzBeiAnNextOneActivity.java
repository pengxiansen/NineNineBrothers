package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.FlowTagAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzBeiAnNextOneBinding;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @作者 Administrator
 * 解债备案下一步
 * @创建日期 2017/10/30 0030 14:52
 */

public class JzBeiAnNextOneActivity extends BaseActivity<ActivityJzBeiAnNextOneBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_bei_an_next_one);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("解债备案");
        showContentView();

        initFlow();
    }

    private void initListener() {
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //下一步
                Set<Integer> selectedList = bindingView.flowId.getSelectedList();
                ToastUtil.showToast(selectedList.toString());

                Bundle bundle = new Bundle();
                bundle.putString("type","JzBeiAnNextOneActivity");
                SysUtils.startActivity(JzBeiAnNextOneActivity.this,JzUploadImgsActivity.class,bundle);
            }
        });
    }

    private void initFlow() {

        //身份证明
        List<String> listId = new ArrayList<>();
        listId.add("营业执照副本复印件");
        listId.add("组织机构代码复印件");
        listId.add("法人身份证复印件");
        FlowTagAdapter flowIdlAdapter = new FlowTagAdapter(JzBeiAnNextOneActivity.this, bindingView.flowId,
                listId);
        bindingView.flowId.setAdapter(flowIdlAdapter);

        //票据证明
        List<String> listBill = new ArrayList<>();
        listBill.add("合同");
        listBill.add("收据");
        listBill.add("借据");
        listBill.add("欠据");
        listBill.add("协议");
        listBill.add("电报");
        listBill.add("提货单");
        listBill.add("仓单发票");
        listBill.add("其他");
        FlowTagAdapter flowBillAdapter = new FlowTagAdapter(JzBeiAnNextOneActivity.this, bindingView.flowBill,
                listBill);
        bindingView.flowBill.setAdapter(flowBillAdapter);

        //电子发票证明
        List<String> listData = new ArrayList<>();
        listData.add("微信");
        listData.add("QQ");
        listData.add("短信");
        listData.add("微博");
        listData.add("其他");
        FlowTagAdapter flowDataAdapter = new FlowTagAdapter(JzBeiAnNextOneActivity.this, bindingView.flowData,
                listData);
        bindingView.flowData.setAdapter(flowDataAdapter);
    }
}
