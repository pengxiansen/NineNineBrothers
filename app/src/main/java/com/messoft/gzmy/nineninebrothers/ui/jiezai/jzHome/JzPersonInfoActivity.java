package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.ZsPersonInfoImgAdapter;
import com.messoft.gzmy.nineninebrothers.app.ConstantsUrl;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.ZsPersonFileInfo;
import com.messoft.gzmy.nineninebrothers.bean.ZsPersonInfo;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzPersonInfoBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.messoft.gzmy.nineninebrothers.view.viewbigimage.ViewBigImageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @作者 Administrator
 * 债事人详情（个人跟企业）
 * @创建日期 2017/11/1 0001 11:28
 */

public class JzPersonInfoActivity extends BaseActivity<ActivityJzPersonInfoBinding> {

    private String mId; //债事人id

    private DisposableObserver mDisposableObserver;
    private ZsPersonInfo mData;
    private List<ZsPersonFileInfo> mList;
    private ZsPersonInfoImgAdapter mImgAdapter;

    private ArrayList<String> mImgList;//图片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_person_info);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("债事人详情");
        initDispoable();
        mImgList = new ArrayList<>();
        mImgAdapter = new ZsPersonInfoImgAdapter(this);
        if (getIntent() != null && null != getIntent().getBundleExtra("b")) {
            mId = getIntent().getBundleExtra("b").getString("id");
            if (StringUtils.isNoEmpty(mId)) {
                loadInfo(mId);
            }
        }

        bindingView.xrcImg.setAdapter(mImgAdapter);
    }

    private void initDispoable() {

        mDisposableObserver = new DisposableObserver() {

            @Override
            public void onNext(@NonNull Object obj) {
                showContentView();
                if (obj instanceof BaseBean) {
                    BaseBean bean = (BaseBean) obj;
                    if (bean.getState() == 0) {
                        if (bean.getData() instanceof ZsPersonInfo) {
                            mData = (ZsPersonInfo) bean.getData();
                            //设置信息
                            setDataInfo(mData);
                        } else if (bean.getData() instanceof List) {
                            //设置图片
                            mList = (List<ZsPersonFileInfo>) bean.getData();
                            setImgs(mList);
                        }
                    } else {
                        ToastUtil.showToast(bean.getMessage());
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                showContentView();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void initListener() {
        bindingView.tvPerPhone.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                callPhone();
            }
        });
        bindingView.tvComPhone.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                callPhone();
            }
        });
        mImgAdapter.setOnItemClickListener(new OnItemClickListener<ZsPersonFileInfo>() {
            @Override
            public void onClick(ZsPersonFileInfo zsPersonFileInfo, int position) {
                if (mList == null || mList.size() <= 0) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", position);//第几张
                bundle.putStringArrayList("imageuri", mImgList);
                Intent intent = new Intent(JzPersonInfoActivity.this, ViewBigImageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void callPhone() {
        if (mData != null && mData.getMobile() != null) {
            //跳转到拨号界面，同时传递电话号码
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mData.getMobile()));
            startActivity(dialIntent);
        }
    }

    private void loadInfo(String id) {
        //信息
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        String urlDataInfo = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.GET_ZS_PERSON_INFO,
                map);
        //文件
        Map<String, String> map1 = new HashMap<>();
        map1.put("debtMatterPersonId", id);
        String urlImgs = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.GET_ZS_PERSON_FILE_INFO,
                map1);
        if (!StringUtils.isNoEmpty(urlDataInfo) && !StringUtils.isNoEmpty(urlImgs)) {
            return;
        }
        Observable<BaseBean<ZsPersonInfo>> newsList = HttpClient.Builder.getNineServer().getZsPersonInfo(urlDataInfo);
        Observable<BaseBean<List<ZsPersonFileInfo>>> homeBanner = HttpClient.Builder.getNineServer().getZsPersonFileInfo(urlImgs);
        Observable.merge(newsList, homeBanner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);
    }


    /**
     * 设置信息
     *
     * @param data
     */
    private void setDataInfo(ZsPersonInfo data) {
        DebugUtil.debug("JzPersonInfoActivity", "个人信息：" + data.toString());
        if (data == null) {
            return;
        }
        if (data.getType().equals("0")) {
            //企业
            bindingView.llCompany.setVisibility(View.VISIBLE);
            bindingView.tvComCode.setText("组织机构代码：" + data.getOrganizationCode());
            bindingView.tvComZsName.setText("债事企业名称：" + data.getName());
            bindingView.tvComLegalName.setText("企业法人姓名：" + data.getLegalperson());
            bindingView.tvComLegalIdCard.setText("法人身份证号码：" + data.getIdCard());
            bindingView.tvComAddress.setText("所属地区：" + data.getProvinceText() + data.getCityText() + data.getDistrictText());
            bindingView.tvComBusiness.setText("所属行业：" + data.getIndustry());
            bindingView.tvComPhone.setText("联系电：话" + data.getMobile());
            bindingView.tvComRegisterCapital.setText("注册资本：" + data.getRegisteredCapital());
            bindingView.tvComEmail.setText("联系邮箱：" + data.getEmail());
            bindingView.tvComWx.setText("微信号码：" + data.getWechat());
            bindingView.tvComQq.setText("QQ号码：" + data.getQq());
        } else if (data.getType().equals("1")) {
            //个人
            bindingView.llPerson.setVisibility(View.VISIBLE);
            bindingView.tvPerIdCard.setText("身份证号：" + data.getIdCard());
            bindingView.tvPerZsName.setText("债事人姓名：" + data.getName());
            bindingView.tvPerAddress.setText("所属地区：" + data.getProvinceText() + data.getCityText() + data.getDistrictText());
            bindingView.tvPerPhone.setText("联系电话：" + data.getMobile());
            bindingView.tvPerEmail.setText("联系邮箱：" + data.getEmail());
            bindingView.tvPerWx.setText("微信号码：" + data.getWechat());
            bindingView.tvPerQq.setText("QQ号码：" + data.getQq());
        }
    }

    /**
     * 设置图片
     *
     * @param list
     */
    private void setImgs(List<ZsPersonFileInfo> list) {
        DebugUtil.debug("JzPersonInfoActivity", "图片信息：" + list.toString());
        bindingView.xrcImg.setPullRefreshEnabled(false);
        bindingView.xrcImg.setLoadingMoreEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        bindingView.xrcImg.setLayoutManager(gridLayoutManager);
        // 需加，不然滑动不流畅
        bindingView.xrcImg.setNestedScrollingEnabled(false);
        bindingView.xrcImg.setHasFixedSize(false);

        mImgAdapter.addAll(list);
        mImgAdapter.notifyDataSetChanged();

        if (mImgList != null) {
            for (int i = 0; i < list.size(); i++) {
                mImgList.add(list.get(i).getUrl());
            }
        }
    }
}
