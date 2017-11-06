package com.messoft.gzmy.nineninebrothers.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.databinding.ItemZsPersonInfoImgBinding;
import com.messoft.gzmy.nineninebrothers.utils.ImgLoadUtil;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 债事进度item里面的图片
 */

public class JzProgressItemImgImgAdapter extends BaseRecyclerViewAdapter<String> {
    private Activity activity;

    public JzProgressItemImgImgAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_zs_person_info_img);
    }

    class ViewHolder extends BaseRecyclerViewHolder<String, ItemZsPersonInfoImgBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final String object, final int position) {
            if (object != null) {
                /**
                 * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
                 */
                binding.executePendingBindings();
                ImgLoadUtil.displayEspImage(object, binding.iv, 0);

//                binding.iv.setOnClickListener(new PerfectClickListener() {
//                    @Override
//                    protected void onNoDoubleClick(View v) {
//                        ToastUtil.showToast(position+"");
//                    }
//                });
//                ViewHelper.setScaleX(itemView,0.8f);
//                ViewHelper.setScaleY(itemView,0.8f);
//                ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
//                ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

            }
        }
    }
}
