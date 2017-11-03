package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.app.Constants;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzUploadImgsBinding;
import com.messoft.gzmy.nineninebrothers.interfae.PermissionCallback;
import com.messoft.gzmy.nineninebrothers.permission.PermissionRequest;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
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
 * 通用上传证件照
 * @创建日期 2017/10/30 0030 11:18
 */

public class JzUploadImgsActivity extends BaseActivity<ActivityJzUploadImgsBinding> implements BGASortableNinePhotoLayout.Delegate {

    private PermissionRequest mRequest;
    private String mType = "";//标志界面跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_upload_imgs);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("上传证件照");
        showContentView();

        if (getIntent() != null && null != getIntent().getBundleExtra("b")) {
            mType = getIntent().getBundleExtra("b").getString("type");
        }

        mRequest = new PermissionRequest(this, new PermissionCallback() {

            @Override
            public void onSuccessful(List<String> permissions) {
//                ToastUtil.showToast("申请权限成功");
                // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "NineNineBrothersPhoto");
                //第二个参数表示是否有拍照功能，没有传null  （mTakePhotoCb.isChecked() ? takePhotoDir : null）
                startActivityForResult(BGAPhotoPickerActivity.newIntent(JzUploadImgsActivity.this, takePhotoDir,
                        bindingView.bagPhoto.getMaxItemCount() - bindingView.bagPhoto.getItemCount(),
                        null, false), Constants.REQUEST_CODE_CHOOSE_PHOTO);
            }

            @Override
            public void onFailure(List<String> permissions) {
                ToastUtil.showToast("申请权限失败，请在设置中打开读写和相机权限");
                // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                if (AndPermission.hasAlwaysDeniedPermission(JzUploadImgsActivity.this, permissions)) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(JzUploadImgsActivity.this, 110).show();
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

    private void initListener() {
        //上传
        bindingView.tvLogin.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ArrayList<String> data = bindingView.bagPhoto.getData();
                if (data != null && data.size() > 0) {
                    DebugUtil.debug("JzUploadImgsActivity" + data.toString());

                    if (mType.equals("JzBeiAnNextOneActivity")) {
                        //解债备案下一步跳过来的
                        Bundle bundle = new Bundle();
                        bundle.putString("type",mType);
                        SysUtils.startActivity(JzUploadImgsActivity.this,JzBeiAnNextThreeActivity.class,bundle);
                    }
                }
            }
        });
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
//            if (mSingleChoiceCb.isChecked()) {
//                bindingView.bagPhoto.setData(BGAPhotoPickerActivity.getSelectedImages(data));
//            } else {
//                bindingView.bagPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
//            }
            bindingView.bagPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == Constants.REQUEST_CODE_PHOTO_PREVIEW) {
            bindingView.bagPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

}
