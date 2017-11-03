package com.messoft.gzmy.nineninebrothers.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.bean.JzKuList;
import com.messoft.gzmy.nineninebrothers.databinding.ItemJzKuListBinding;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 解债库
 */

public class JzKuAdapter extends BaseRecyclerViewAdapter<JzKuList> {
    private Activity activity;

    public JzKuAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_jz_ku_list);
    }

    class ViewHolder extends BaseRecyclerViewHolder<JzKuList, ItemJzKuListBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final JzKuList object, int position) {
            if (object != null) {
                binding.setDataBean(object);
                /**
                 * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
                 */
//                binding.executePendingBindings();

                ViewHelper.setScaleX(itemView,0.8f);
                ViewHelper.setScaleY(itemView,0.8f);
                ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
                ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

            }
        }
    }
}
