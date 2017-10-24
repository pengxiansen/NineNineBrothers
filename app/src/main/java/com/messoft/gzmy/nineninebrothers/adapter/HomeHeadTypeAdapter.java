package com.messoft.gzmy.nineninebrothers.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.bean.HomeHeadType;
import com.messoft.gzmy.nineninebrothers.databinding.ItemHomeHeaderTypeBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.JieZhaiActivity;
import com.messoft.gzmy.nineninebrothers.ui.webview.WebCommonActivity;
import com.messoft.gzmy.nineninebrothers.utils.ImgLoadUtil;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

import static com.messoft.gzmy.nineninebrothers.ui.webview.WebCommonActivity.TYPE_KEY;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 首页头部分类
 */

public class HomeHeadTypeAdapter extends BaseRecyclerViewAdapter<HomeHeadType> {
    private Activity activity;
    private int type;

    public HomeHeadTypeAdapter(Activity activity, int type) {
        this.activity = activity;
        this.type = type;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_home_header_type);
    }

    class ViewHolder extends BaseRecyclerViewHolder<HomeHeadType, ItemHomeHeaderTypeBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final HomeHeadType object, final int position) {
            if (object != null) {
                /**
                 * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
                 */
                binding.executePendingBindings();
                ImgLoadUtil.displayCircleById(binding.ivHeader, object.getImgId());
                binding.tvTitle.setText(object.getTitle());

                binding.llItem.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        switch (position) {
                            case 0:
                                if (type==0) {
                                    //首页点进来才跳
                                    SysUtils.startActivity(activity, JieZhaiActivity.class);
                                }
                                break;
                            case 1:

                                break;
                            case 2:
                                //会员商城
                                activity.startActivity(new Intent(activity, WebCommonActivity.class).putExtra(TYPE_KEY, 12));
                                break;
                            default:

                                break;
                        }
                    }
                });
            }
        }
    }
}
