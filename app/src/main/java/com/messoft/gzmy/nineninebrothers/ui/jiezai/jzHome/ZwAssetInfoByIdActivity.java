package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.ZwAssetInfoImgAdapter;
import com.messoft.gzmy.nineninebrothers.app.ConstantsUrl;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.AssetInfoById;
import com.messoft.gzmy.nineninebrothers.bean.AssetInfoFile;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityZwAssetInfoByIdBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
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
 * 债务人资产详情
 * @创建日期 2017/11/2 0002 14:54
 */

public class ZwAssetInfoByIdActivity extends BaseActivity<ActivityZwAssetInfoByIdBinding> {

    private String mId; //资产id

    private DisposableObserver mDisposableObserver;
    private AssetInfoById mData;
    private List<AssetInfoFile> mList;
    private ZwAssetInfoImgAdapter mImgAdapter;

    private ArrayList<String> mImgList;//图片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zw_asset_info_by_id);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("资产详情");
        initDispoable();
        mImgList = new ArrayList<>();
        mImgAdapter = new ZwAssetInfoImgAdapter(this);
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
                        if (bean.getData() instanceof AssetInfoById) {
                            mData = (AssetInfoById) bean.getData();
                            //设置信息
                            setDataInfo(mData);
                        } else if (bean.getData() instanceof List) {
                            //设置图片
                            mList = (List<AssetInfoFile>) bean.getData();
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
        mImgAdapter.setOnItemClickListener(new OnItemClickListener<AssetInfoFile>() {
            @Override
            public void onClick(AssetInfoFile zsPersonFileInfo, int position) {
                if (mList == null || mList.size() <= 0) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", position);//第几张
                bundle.putStringArrayList("imageuri", mImgList);
                Intent intent = new Intent(ZwAssetInfoByIdActivity.this, ViewBigImageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void loadInfo(String id) {
        //信息
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        String urlDataInfo = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.GET_ASSET_BY_ID,
                map);
        //文件
        Map<String, String> map1 = new HashMap<>();
        map1.put("assetId", id);
        String urlImgs = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL + ConstantsUrl.GET_ASSET_FILE_BY_ID,
                map1);
        if (!StringUtils.isNoEmpty(urlDataInfo) && !StringUtils.isNoEmpty(urlImgs)) {
            return;
        }
        Observable<BaseBean<AssetInfoById>> newsList = HttpClient.Builder.getNineServer().getAssetById(urlDataInfo);
        Observable<BaseBean<List<AssetInfoFile>>> homeBanner = HttpClient.Builder.getNineServer().getAssetInfo(urlImgs);
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
    private void setDataInfo(AssetInfoById data) {
        DebugUtil.debug("JzPersonInfoActivity", "个人信息：" + data.toString());
        if (data == null) {
            return;
        }
        bindingView.tvAssetType.setText("资产类型："+BusinessUtils.assetType(data.getAssetType()));
        bindingView.tvAssetAddress.setText("资产地址："+data.getProvinceText()+data.getCityText()+data.getDistrictText());
        bindingView.tvAssessPrice.setText("评估价格："+data.getEvaluatedPrice());
        bindingView.tvExpectPrice.setText("评估价格："+data.getExpectedPrice());
    }

    /**
     * 设置图片
     *
     * @param list
     */
    private void setImgs(List<AssetInfoFile> list) {
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
