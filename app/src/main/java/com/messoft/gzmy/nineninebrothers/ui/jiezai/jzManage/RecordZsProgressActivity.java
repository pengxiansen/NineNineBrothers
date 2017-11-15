package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzManage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.app.Constants;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.JzProgress;
import com.messoft.gzmy.nineninebrothers.bean.RxBusMessage;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityRecordZsProgressBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpUiTips;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.rx.RxBus;
import com.messoft.gzmy.nineninebrothers.http.rx.RxCodeConstants;
import com.messoft.gzmy.nineninebrothers.interfae.PermissionCallback;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.permission.PermissionRequest;
import com.messoft.gzmy.nineninebrothers.utils.bitmap.CompressHelper;
import com.messoft.gzmy.nineninebrothers.utils.bitmap.FileUtil;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * @作者 Administrator
 * 录制债事进度
 * //解债师可以在首页的进度列表录制资产或者债事的进度 会员或高级合伙人只能在个人中心已通过的订单里面去录制
 * 判断1：解债师和（会员和高级会员）
 * 判断2：从哪个界面过来的
 * @创建日期 2017/11/7 0007 10:16
 */

public class RecordZsProgressActivity extends BaseActivity<ActivityRecordZsProgressBinding> implements BGASortableNinePhotoLayout.Delegate {

    private PermissionRequest mRequest;
    private String mId;
    private JzModel mJzModel;
    private JzProgress mJzProgress; //最上面的进度信息
    private int mStept;//表示需要录制的进度
    private String mRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_zs_progress);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("录入债事进度");
        mJzModel = new JzModel();
        //TODO 判断
        if (getIntent() != null && getIntent().getBundleExtra("b") != null) {
            //type  0:解债师 1:高级合伙人
            mId = getIntent().getBundleExtra("b").getString("id");
            if (StringUtils.isNoEmpty(mId)) {
                //查信息
                loadInfo();
            }
        }
        showContentView();
        //图片选择
        initPickView();
    }

    private void initListener() {
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        if (mStept < 1 || mStept > 4) {
            return;
        }
        mRemarks = bindingView.etRemark.getText().toString().trim();
        DebugUtil.error("RecordZsProgressActivity", "remarks:" + mStept + "----" + bindingView.bagPhoto.getData().toString());

        doRecord();
    }

    private void doRecord() {
        mJzModel.inputDebtMatterProgress(RecordZsProgressActivity.this, mId, mStept, mRemarks, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                BaseBean bean = (BaseBean) object;
                //获取债事交易进度id
                String id = bean.getId();
                if (bindingView.bagPhoto.getData() != null && bindingView.bagPhoto.getData().size() > 0) {
                    //有图片就上传图片
                    uploadImgs(id, bindingView.bagPhoto.getData());
                } else {
                    //直接申请了
                    ToastUtil.showToast(bean.getMessage());
                    //提示相应页面刷新
                    RxBus.getInstanceBus().post(new RxBusMessage(RxCodeConstants.REFRESH_JZ_PROGRESS_LIST, 0));
                    finish();
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    /**
     * 上传图片
     *
     * @param id
     * @param data
     */
    private void uploadImgs(String id, ArrayList<String> data) {
        if (!StringUtils.isNoEmpty(id)) {
            return;
        }
        HttpUiTips.showDialog(RecordZsProgressActivity.this, false, "正在上传图片...");
        for (int i = 0; i < data.size(); i++) {
            File file = new File(data.get(i));
            //压缩图片
            File newFile = CompressHelper.getDefault(this).compressToFile(file);
            //后缀名
            String ivSuffix = FileUtil.getExtensionName(data.get(i));
            //Base64
            String str64 = FileUtil.fileToBase64(newFile);
            final int finalI = i;
            mJzModel.uploadDebtMatterProgressFile(RecordZsProgressActivity.this,
                    id, str64, ivSuffix, new RequestImpl() {
                        @Override
                        public void loadSuccess(Object object) {
                            if (finalI == 2) {
                                ToastUtil.showToast("上传成功");
                                //提示相应页面刷新
                                RxBus.getInstanceBus().post(new RxBusMessage(RxCodeConstants.REFRESH_JZ_PROGRESS_LIST, 0));
                                finish();
                            }
                        }

                        @Override
                        public void loadFailed(int errorCode, String errorMessage) {
                            HttpUiTips.dismissDialog(RecordZsProgressActivity.this);
                            ToastUtil.showToast(errorMessage);
                        }
                    });
        }
    }

    private void loadInfo() {
        //拿到最新的一个进度（已完成这个进度）
        mJzModel.queryDebtMatterProgressInfoList(RecordZsProgressActivity.this, mId, 0, 1, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                List<JzProgress> list = (List<JzProgress>) object;
                if (list.size() > 0) {
                    mJzProgress = list.get(0);
                    if (mJzProgress.getDebtStage() == 4) {
                        //已完成了，不用录了
                        mStept = 5;
                        ToastUtil.showToast("进度已完成，无须录制");
                        finish();
                    } else {
                        mStept = mJzProgress.getDebtStage() + 1;
                        bindingView.tvStage.setText(BusinessUtils.progressStage(mStept + ""));
                    }
                } else {
                    //没查到就从1阶段录起
                    mStept = 1;
                    bindingView.tvStage.setText(BusinessUtils.progressStage(mStept + ""));
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                showError();
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        loadInfo();
    }

    private void initPickView() {
        mRequest = new PermissionRequest(this, new PermissionCallback() {

            @Override
            public void onSuccessful(List<String> permissions) {
//                ToastUtil.showToast("申请权限成功");
                // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "NineNineBrothersPhoto");
                //第二个参数表示是否有拍照功能，没有传null  （mTakePhotoCb.isChecked() ? takePhotoDir : null）
                startActivityForResult(BGAPhotoPickerActivity.newIntent(RecordZsProgressActivity.this, takePhotoDir,
                        bindingView.bagPhoto.getMaxItemCount() - bindingView.bagPhoto.getItemCount(),
                        null, false), Constants.REQUEST_CODE_CHOOSE_PHOTO);
            }

            @Override
            public void onFailure(List<String> permissions) {
                ToastUtil.showToast("申请权限失败，请在设置中打开读写和相机权限");
                // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                if (AndPermission.hasAlwaysDeniedPermission(RecordZsProgressActivity.this, permissions)) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(RecordZsProgressActivity.this, 110).show();
                }
            }
        });

        //设置九宫格图片的属性
        //不设置单选
        bindingView.bagPhoto.setMaxItemCount(9);
        //是否编辑
        bindingView.bagPhoto.setEditable(true);
        //加号
        bindingView.bagPhoto.setPlusEnable(true);
        //开启拖拽排序
        bindingView.bagPhoto.setSortable(true);
        // 设置拖拽排序控件的代理
        bindingView.bagPhoto.setDelegate(this);
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {

        mRequest.requestWriteAndCamera();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        bindingView.bagPhoto.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, bindingView.bagPhoto.getMaxItemCount(), models, models, position, false), Constants.REQUEST_CODE_PHOTO_PREVIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE_CHOOSE_PHOTO) {
            bindingView.bagPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == Constants.REQUEST_CODE_PHOTO_PREVIEW) {
            bindingView.bagPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }
}
