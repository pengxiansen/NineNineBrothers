package com.messoft.gzmy.nineninebrothers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.messoft.gzmy.nineninebrothers.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30 0030.
 * 流失标签
 */

public class FlowTagAdapter extends TagAdapter {

    private Context mContext;
    private TagFlowLayout mView;

    public FlowTagAdapter(Context context, TagFlowLayout flowLayout, List datas) {
        super(datas);
        this.mContext = context;
        this.mView = flowLayout;
    }

    @Override
    public View getView(FlowLayout parent, int position, Object o) {
        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_item,
                mView, false);
        tv.setText(o.toString());
        return tv;
    }
}
