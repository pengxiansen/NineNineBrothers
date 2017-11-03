package com.messoft.gzmy.nineninebrothers.adapter;

import android.app.Activity;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;
import android.widget.TextView;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.databinding.ItemAssetKuLeftBinding;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 资产库左边
 */

public class AssetKuLeftAdapter extends BaseRecyclerViewAdapter<String> {

    private int indexSelected = -1;
    private Activity activity;
    // 最后是把选中的position赋值给old
    int old = -1;
    // 用来保存选中项
    private SparseBooleanArray selected;

    public AssetKuLeftAdapter(Activity activity) {
        this.activity = activity;
        selected = new SparseBooleanArray();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_asset_ku_left);
    }

    class ViewHolder extends BaseRecyclerViewHolder<String, ItemAssetKuLeftBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final String object, final int position) {
            if (object != null) {
//                binding.setDataBean(object);
                /**
                 * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
                 */
                binding.executePendingBindings();
                TextView tv = binding.tvTitle;
                tv.setText(object);
                if(selected.get(position)){
                    tv.setTextColor(activity.getResources().getColor(R.color.red));
                    tv.setBackgroundColor(activity.getResources().getColor(R.color.colorPageBg));
                }else{
                    tv.setTextColor(activity.getResources().getColor(R.color.title1));
                    tv.setBackgroundColor(activity.getResources().getColor(R.color.white));
                }
            }
        }
    }

    /**
     * 此方法的逻辑为当选中某个item时，由于最开始old=-1,因此if里面的语句不执行,select集合里这个item的标签为true。
     * 此时这个old就不-1了，然后再点击别的item时，此时就会执行if里的语句，此时这个item的标签就为false了
     */
    public void setSelectedItem(int item) {
        if (old != -1) {
            this.selected.put(old, false);
        }
        this.selected.put(item, true);
        old = item;
    }
}
