package com.messoft.gzmy.nineninebrothers.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.bean.PointsList;
import com.messoft.gzmy.nineninebrothers.databinding.ItemPointsListBinding;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 积分明细
 */

public class PointsListdapter extends BaseRecyclerViewAdapter<PointsList> {
    private Activity activity;

    public PointsListdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_points_list);
    }

    class ViewHolder extends BaseRecyclerViewHolder<PointsList, ItemPointsListBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final PointsList object, int position) {
            if (object != null) {
                /**
                 * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
                 */
                binding.executePendingBindings();
                String type = object.getType().equals("0") ? "（商城消费）" : "（推荐合伙人）";
                binding.tvTitle.setText(object.getRemarks() + type);
                binding.tvTime.setText(object.getCreateTime());
                String payType = object.getPayType().equals("0") ? "+" : "-";
                binding.tvPoint.setText(payType + object.getAmount()+"积分");
                ViewHelper.setScaleX(itemView, 0.8f);
                ViewHelper.setScaleY(itemView, 0.8f);
                ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
                ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

            }
        }
    }
}
