package com.messoft.gzmy.nineninebrothers.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.bean.Street;
import com.messoft.gzmy.nineninebrothers.databinding.ItemSelectStreetBinding;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 首页头部分类
 */

public class SelectStreetAdapter extends BaseRecyclerViewAdapter<Street> {
    private Activity activity;

    public SelectStreetAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_select_street);
    }

    class ViewHolder extends BaseRecyclerViewHolder<Street, ItemSelectStreetBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final Street object, final int position) {
            if (object != null) {
                /**
                 * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
                 */
                binding.executePendingBindings();
                binding.tvTitle.setText(object.getTitle());
            }
        }
    }
}
