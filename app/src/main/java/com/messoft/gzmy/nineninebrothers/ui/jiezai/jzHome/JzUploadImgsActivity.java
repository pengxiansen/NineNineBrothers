package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.app.Constants;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzUploadImgsBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpUiTips;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.interfae.PermissionCallback;
import com.messoft.gzmy.nineninebrothers.model.JzModel;
import com.messoft.gzmy.nineninebrothers.permission.PermissionRequest;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.bitmap.CompressHelper;
import com.messoft.gzmy.nineninebrothers.utils.bitmap.FileUtil;
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
 * 通用上传证件照
 * @创建日期 2017/10/30 0030 11:18
 */

public class JzUploadImgsActivity extends BaseActivity<ActivityJzUploadImgsBinding> implements BGASortableNinePhotoLayout.Delegate {

    private PermissionRequest mRequest;
    private String mType = "";//标志界面跳转  0--债事人备案  1--解债备案
    private String mId;

    private JzModel mJzModel;
    private String mDebtorId;//解债备案过来传过来的 --（债事备案录入的资产传递债务人的memberId）

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
        mJzModel = new JzModel();

        if (getIntent() != null && null != getIntent().getBundleExtra("b")) {
            mType = getIntent().getBundleExtra("b").getString("type");
            mId = getIntent().getBundleExtra("b").getString("id");

            if (mType.equals("1")) {
                //解债备案
                mDebtorId = getIntent().getBundleExtra("b").getString("debtorId");
            }
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
                if (mType.equals("0")) {
                    //债市人文件上传
                    upLoadImgs();
                }
            }
        });
    }

    private void upLoadImgs() {
        if (!StringUtils.isNoEmpty(mId)) {
            ToastUtil.showToast("ID不能为空");
            return;
        }
        final ArrayList<String> data = bindingView.bagPhoto.getData();
        if (data == null || data.size() <= 0) {
            ToastUtil.showToast("请至少选择一张图片");
            return;
        }
        HttpUiTips.showDialog(JzUploadImgsActivity.this, false, "正在上传图片...");
        //这里一张张的上传
        for (int i = 0; i < data.size(); i++) {
            //改了头像先上传头像
            File file = new File(data.get(0));
            //压缩图片
            File newFile = CompressHelper.getDefault(this).compressToFile(file);
            //后缀名
            String ivSuffix = FileUtil.getExtensionName(newFile.getName());
            //Base64
            String str64 = FileUtil.fileToBase64(newFile);

            final int finalI = i;

            if (mType.equals("0")) {
                //债事人文件上传
                mJzModel.uploadDebtMatterPersonFile(JzUploadImgsActivity.this,
                        mId, str64, ivSuffix, new RequestImpl() {
                            @Override
                            public void loadSuccess(Object object) {
                                ToastUtil.showToast("上传第张" + (finalI+1) + "成功");
                                if (finalI == data.size()-1) {
                                    HttpUiTips.dismissDialog(JzUploadImgsActivity.this);
                                    ToastUtil.showToast("上传图片照完毕");
                                    JzUploadImgsActivity.this.finish();
                                }
                            }

                            @Override
                            public void loadFailed(int errorCode, String errorMessage) {
                                ToastUtil.showToast("上传第张" + finalI+1 + "失败（" + errorMessage + ")");
                                if (finalI == data.size()-1) {
                                    HttpUiTips.dismissDialog(JzUploadImgsActivity.this);
                                }
                            }
                        });
            }else if(mType.equals("1")){
                //债事文件上传
                mJzModel.uploadDebtMatterFile(JzUploadImgsActivity.this,
                        mId, str64, ivSuffix, new RequestImpl() {
                            @Override
                            public void loadSuccess(Object object) {
                                ToastUtil.showToast("上传第张" + (finalI+1) + "成功");
                                if (finalI == data.size()-1) {
                                    HttpUiTips.dismissDialog(JzUploadImgsActivity.this);
                                    ToastUtil.showToast("上传证件照完毕");
                                    //然后去资产备案
                                    Bundle bundle = new Bundle();
                                    bundle.putString("debtorId",mDebtorId);
                                    bundle.putString("type","2"); //标志解债备案
                                    SysUtils.startActivity(JzUploadImgsActivity.this,JzBeiAnNextThreeActivity.class,bundle);
                                    JzUploadImgsActivity.this.finish();
                                }
                            }

                            @Override
                            public void loadFailed(int errorCode, String errorMessage) {
                                ToastUtil.showToast("上传第张" + finalI+1 + "失败（" + errorMessage + ")");
                                if (finalI == data.size()-1) {
                                    HttpUiTips.dismissDialog(JzUploadImgsActivity.this);
                                }
                            }
                        });
            }

        }
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
