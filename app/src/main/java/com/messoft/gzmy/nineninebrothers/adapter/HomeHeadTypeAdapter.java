package com.messoft.gzmy.nineninebrothers.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewAdapter;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.BaseRecyclerViewHolder;
import com.messoft.gzmy.nineninebrothers.bean.HomeHeadType;
import com.messoft.gzmy.nineninebrothers.databinding.ItemHomeHeaderTypeBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.ui.home.ApplyPartnerActivity;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.JzActivity;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome.AssetDisposeActivity;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome.JzServeActivity;
import com.messoft.gzmy.nineninebrothers.ui.webview.WebCommonActivity;
import com.messoft.gzmy.nineninebrothers.utils.ImgLoadUtil;
import com.messoft.gzmy.nineninebrothers.utils.SPUtils;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

import static com.messoft.gzmy.nineninebrothers.ui.webview.WebCommonActivity.TYPE_KEY;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 首页头部分类
 */

public class HomeHeadTypeAdapter extends BaseRecyclerViewAdapter<HomeHeadType> {
    private BaseActivity activity;
    private int type;

    public HomeHeadTypeAdapter(BaseActivity activity, int type) {
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
                                //解债服务
                                if (type == 0) {
                                    //首页点进来才跳解债
                                    SysUtils.startActivity(activity, JzActivity.class);
                                } else if (type == 1) {
                                    //已经在解债界面中
                                    //会员不能查看解债服务模块--提示跳转申请合伙人界面
                                    String roleId = SPUtils.getString("roleId","");
                                    if (StringUtils.isNoEmpty(roleId)) {
                                        //0.普通会员 1.高级合伙人 2.解债师
                                        if (roleId.equals("0")) {
                                            showGotoApply();
                                        }else if(roleId.equals("1")){
                                            SysUtils.startActivity(activity, JzServeActivity.class);
                                        }else if(roleId.equals("2")){
                                            SysUtils.startActivity(activity, JzServeActivity.class);
                                        }
                                    }
                                }
                                break;
                            case 1:
                                //资产处理
                                if (type == 0) {
                                    //首页点进来
                                }else if(type==1){
                                    SysUtils.startActivity(activity, AssetDisposeActivity.class);
                                }
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

    /**
     * 调到申请合伙人提示
     */
    private void showGotoApply() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("温馨提示");
        builder.setMessage("普通会员不能查看解债模块，需要申请成为合伙人才能查看");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            SysUtils.startActivity(activity, ApplyPartnerActivity.class);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
