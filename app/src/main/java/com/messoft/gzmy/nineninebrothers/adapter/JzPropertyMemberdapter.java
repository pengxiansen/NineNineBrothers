package com.messoft.gzmy.nineninebrothers.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.bean.QueryMyAssetList;
import com.messoft.gzmy.nineninebrothers.databinding.ItemAssetListBinding;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.ImgLoadUtil;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 资产管理
 */

public class JzPropertyMemberdapter extends BaseRecyclerViewAdapter<QueryMyAssetList> {
    private Activity activity;

    public JzPropertyMemberdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_asset_list);
    }

    class ViewHolder extends BaseRecyclerViewHolder<QueryMyAssetList, ItemAssetListBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final QueryMyAssetList object, int position) {
            if (object != null) {
                /**
                 * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
                 */
                binding.executePendingBindings();
                binding.tvType.setText("类型："+BusinessUtils.assetType(object.getAssetType()));
                binding.tvAssessPrice.setText("评估价："+object.getEvaluatedPrice());
                binding.tvAddress.setText("地址："+object.getProvinceText()+object.getCityText());
                ImgLoadUtil.displayEspImage(object.getPath(),binding.ivHead,0);
                ViewHelper.setScaleX(itemView,0.8f);
                ViewHelper.setScaleY(itemView,0.8f);
                ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
                ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

            }
        }
    }
}
