package com.messoft.gzmy.nineninebrothers.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.bean.AssetKuList;
import com.messoft.gzmy.nineninebrothers.databinding.ItemAssetKuRightBinding;
import com.messoft.gzmy.nineninebrothers.utils.ImgLoadUtil;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 首页头部分类
 */

public class AssetKuRightAdapter extends BaseRecyclerViewAdapter<AssetKuList> {
    private BaseActivity activity;

    public AssetKuRightAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_asset_ku_right);
    }

    class ViewHolder extends BaseRecyclerViewHolder<AssetKuList, ItemAssetKuRightBinding> {


        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final AssetKuList object, final int position) {
            if (object != null) {
                /**
                 * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
                 */
                binding.executePendingBindings();
                ImgLoadUtil.displayEspImageHasHead(object.getPath(), binding.ivHeader, 0);
                binding.tvTitle.setText("评估价格：" + object.getEvaluatedPrice());
                binding.tvAddress.setText(object.getProvinceText() + object.getCityText());

            }
        }
    }

    /**
     * 普通会员提示
     */
    private void showTip() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_select_person_type)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        //取消
                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        //普通会员
                        holder.setOnClickListener(R.id.share, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                ToastUtil.showToast("普通会员");
                            }
                        });
                        //高级合伙人
                        holder.setOnClickListener(R.id.delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                ToastUtil.showToast("高级合伙人");

                            }
                        });
                    }
                })
//                .setDimAmount(0.3f)
                .setShowBottom(true)
                .setOutCancel(true)
                .show(activity.getSupportFragmentManager());
    }
}
