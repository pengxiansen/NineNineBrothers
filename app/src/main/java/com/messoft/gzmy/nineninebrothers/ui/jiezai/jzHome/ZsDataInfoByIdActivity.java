package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.ZsDataInfoImgAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.ZsDataInfoById;
import com.messoft.gzmy.nineninebrothers.bean.ZsDataInfoFileById;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityZsDataInfoByIdBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.messoft.gzmy.nineninebrothers.view.viewbigimage.ViewBigImageActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @作者 Administrator
 * 2.2.5  根据id查询债事信息
 * @创建日期 2017/11/2 0002 14:04
 */

public class ZsDataInfoByIdActivity extends BaseActivity<ActivityZsDataInfoByIdBinding> {

    private String mId; //债事人id

    private DisposableObserver mDisposableObserver;
    private ZsDataInfoById mData;
    private List<ZsDataInfoFileById> mList;
    private ZsDataInfoImgAdapter mImgAdapter;

    private ArrayList<String> mImgList;//图片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zs_data_info_by_id);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("债事人详情");
        initDispoable();
        mImgList = new ArrayList<>();
        mImgAdapter = new ZsDataInfoImgAdapter(this);
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
                        if (bean.getData() instanceof ZsDataInfoById) {
                            mData = (ZsDataInfoById) bean.getData();
                            //设置信息
                            setDataInfo(mData);
                        } else if (bean.getData() instanceof List) {
                            //设置图片
                            mList = (List<ZsDataInfoFileById>) bean.getData();
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
        mImgAdapter.setOnItemClickListener(new OnItemClickListener<ZsDataInfoFileById>() {
            @Override
            public void onClick(ZsDataInfoFileById zsPersonFileInfo, int position) {
                if (mList == null || mList.size() <= 0) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", position);//第几张
                bundle.putStringArrayList("imageuri", mImgList);
                Intent intent = new Intent(ZsDataInfoByIdActivity.this, ViewBigImageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadInfo(String id) {
        //信息
        JSONObject map = new JSONObject();
        try {
            map.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //文件
        JSONObject map1 = new JSONObject();
        try {
            map1.put("debtMatterId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Observable<BaseBean<ZsDataInfoById>> newsList = HttpClient.Builder.getNineServerQz().getZsInfoById(StringUtils.toURLEncoderUTF8(map.toString()));
        Observable<BaseBean<List<ZsDataInfoFileById>>> homeBanner = HttpClient.Builder.getNineServerQz().getZsInfoFileById(StringUtils.toURLEncoderUTF8(map1.toString()));
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
    private void setDataInfo(ZsDataInfoById data) {
        DebugUtil.debug("JzPersonInfoActivity", "个人信息：" + data.toString());
        if (data == null) {
            return;
        }
        if (data.getQuestion1().equals("0")) {
            bindingView.tvQ1.setVisibility(View.VISIBLE);
        }
        if (data.getQuestion2().equals("0")) {
            bindingView.tvQ2.setVisibility(View.VISIBLE);
        }
        if (data.getQuestion3().equals("0")) {
            bindingView.tvQ3.setVisibility(View.VISIBLE);
        }
        if (data.getQuestion4().equals("0")) {
            bindingView.tvQ4.setVisibility(View.VISIBLE);
        }
        bindingView.tvQ5.setText("身份证明："+data.getQuestion5());
        bindingView.tvQ6.setText("票据合同："+data.getQuestion6());
        bindingView.tvQ7.setText("电子证明："+data.getQuestion7());
    }

    /**
     * 设置图片
     *
     * @param list
     */
    private void setImgs(List<ZsDataInfoFileById> list) {
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
