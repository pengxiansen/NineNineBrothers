package com.messoft.gzmy.nineninebrothers.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.GetDebtMatterProgressFileList;
import com.messoft.gzmy.nineninebrothers.bean.JzProgress;
import com.messoft.gzmy.nineninebrothers.databinding.ItemJzProgressListBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.view.viewbigimage.ViewBigImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 解债进度列表
 */

public class JzProgressAdapter extends BaseRecyclerViewAdapter<JzProgress> {
    private Activity activity;
    private JzModel mJzModel;

    public JzProgressAdapter(Activity activity) {
        this.activity = activity;
        mJzModel = new JzModel();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_jz_progress_list);
    }

    class ViewHolder extends BaseRecyclerViewHolder<JzProgress, ItemJzProgressListBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final JzProgress object, int position) {
            if (object != null) {
                binding.tvTitle.setText(BusinessUtils.progressStage(object.getDebtStage()+""));
                binding.tvTime.setText(object.getCreateTime());
                binding.tvDesc.setText(object.getRemarks());
                //图片
                setImgs(object.getId(), binding.xrcImg);
                binding.executePendingBindings();
            }
        }
    }

    private void setImgs(String id, final RecyclerView xrcImg) {
        final ArrayList<String> mImgList  = new ArrayList<>();
        final JzProgressItemImgImgAdapter mImagAdapter = new JzProgressItemImgImgAdapter(activity);
        xrcImg.setAdapter(mImagAdapter);
        mJzModel.getDebtMatterProgressFileList(activity, id, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                List<GetDebtMatterProgressFileList> list = (List<GetDebtMatterProgressFileList>) object;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
                xrcImg.setLayoutManager(gridLayoutManager);
                // 需加，不然滑动不流畅
                xrcImg.setNestedScrollingEnabled(false);
                xrcImg.setHasFixedSize(false);

                if (mImgList != null && list.size() > 0) {
                    mImgList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        mImgList.add(list.get(i).getUrl());
                    }
                }
                if (mImagAdapter != null) {
                    mImagAdapter.addAll(mImgList);
                    mImagAdapter.notifyDataSetChanged();
                }

                mImagAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
                    @Override
                    public void onClick(String s, int position) {
                        if (mImgList == null || mImgList.size() <= 0) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                        bundle.putInt("code", position);//第几张
                        bundle.putStringArrayList("imageuri", mImgList);
                        Intent intent = new Intent(activity, ViewBigImageActivity.class);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                xrcImg.setVisibility(View.GONE);
            }
        });
    }

}
