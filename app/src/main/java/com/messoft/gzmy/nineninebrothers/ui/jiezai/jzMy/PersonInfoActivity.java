package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzMy;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.app.Constants;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.LoginPersonInfo;
import com.messoft.gzmy.nineninebrothers.bean.UploadFile;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityPersonInfoBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.interfae.PermissionCallback;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.permission.PermissionRequest;
import com.messoft.gzmy.nineninebrothers.ui.login.ForgetPswActivity;
import com.messoft.gzmy.nineninebrothers.utils.bitmap.CompressHelper;
import com.messoft.gzmy.nineninebrothers.utils.bitmap.FileUtil;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.RegexUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.messoft.gzmy.nineninebrothers.view.viewbigimage.ViewBigImageActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;

/**
 * @作者 Administrator
 * 个人资料
 * @创建日期 2017/11/9 0009 16:22
 */

public class PersonInfoActivity extends BaseActivity<ActivityPersonInfoBinding> {

    private PermissionRequest mRequest;
    private LoginModel mLoginModel;
    private int mEditFlag = 0;//默认进来为0不能编辑，1可以编辑
    private boolean mChangeFlag = false;//用户是否修改过了信息
    private boolean mChangeImgFlag = false;//用户是否修改过了头像
    private LoginPersonInfo mData;
    private ArrayList<String> imgOne;//用来保存头像图片方便点击到预览大图

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        if (mChangeFlag) {
            //提示有信心尚未保存
            showExitPop();
            return;
        }
        mSwipeBackHelper.backward();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("个人信息");
        setRightText("编辑");
        showContentView();

        mLoginModel = new LoginModel();
        imgOne = new ArrayList<>();
        loadInfo();

        mRequest = new PermissionRequest(this, new PermissionCallback() {

            @Override
            public void onSuccessful(List<String> permissions) {
//                ToastUtil.showToast("申请权限成功");
                // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "pictures");
                //第二个参数表示是否有拍照功能，没有传null  （mTakePhotoCb.isChecked() ? takePhotoDir : null）
                startActivityForResult(BGAPhotoPickerActivity.newIntent(PersonInfoActivity.this, takePhotoDir,
                        1,
                        null, false), Constants.REQUEST_CODE_CHOOSE_PHOTO);
            }

            @Override
            public void onFailure(List<String> permissions) {
                ToastUtil.showToast("申请权限失败，请在设置中打开读写和相机权限");
                // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                if (AndPermission.hasAlwaysDeniedPermission(PersonInfoActivity.this, permissions)) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(PersonInfoActivity.this, 110).show();
                }
            }
        });
    }

    private void initListener() {
        //编辑
        getRightTv().setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                clickEdit();
            }
        });
        //头像item
        bindingView.rlIvHead.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mEditFlag == 0) {
                    ToastUtil.showToast("请在编辑模式下修改个人信息");
                    return;
                }
                mRequest.requestWriteAndCamera();
            }
        });
        //点击头像
        bindingView.ivHead.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (imgOne != null && imgOne.size() > 0) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("selet", 1);// 2,大图显示当前页数，1,头像，不显示页数
                    bundle.putInt("code", 1);//第几张
                    bundle.putStringArrayList("imageuri", imgOne);
                    Intent intent = new Intent(PersonInfoActivity.this, ViewBigImageActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        //姓名
        bindingView.rlName.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mEditFlag == 0) {
                    ToastUtil.showToast("请在编辑模式下修改个人信息");
                    return;
                }
                showDialog("修改姓名", bindingView.tvName, mData.getName());
            }
        });
        //手机号
        bindingView.rlPhone.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "PersonInfoActivity");
                SysUtils.startActivity(PersonInfoActivity.this, ForgetPswActivity.class, bundle);
            }
        });
        //身份证
        bindingView.rlIdCard.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mEditFlag == 0) {
                    ToastUtil.showToast("请在编辑模式下修改个人信息");
                    return;
                }
                showDialog("修改身份证号码", bindingView.tvIdCard, mData.getIdCard());
            }
        });
        bindingView.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadInfo();
                        bindingView.refreshLayout.finishRefresh();
                    }
                }, 1000);
            }

        });
    }

    /**
     * 点击编辑
     */
    private void clickEdit() {
        if (mEditFlag == 0) {
            //点击完为可编辑状态
            mEditFlag = 1;
            getRightTv().setText("完成");
            bindingView.tvName.setTextColor(getResources().getColor(R.color.title1));
            bindingView.tvPhone.setTextColor(getResources().getColor(R.color.title1));
            bindingView.tvIdCard.setTextColor(getResources().getColor(R.color.title1));
        } else if (mEditFlag == 1) {
            //点击完提交
            submitInfo();
        }
    }


    /**
     * 修改个人信息
     */
    private void submitInfo() {
        String name = bindingView.tvName.getText().toString().trim();
        String idCard = bindingView.tvIdCard.getText().toString().trim();
        if ((!name.equals(mData.getName())) ||
                (!idCard.equals(mData.getIdCard()))) {
            mChangeFlag = true;
        }
        //先判断空
        if (!StringUtils.isNoEmpty(name)) {
            ToastUtil.showToast("姓名不能空");
            return;
        }
        if (!StringUtils.isNoEmpty(idCard)) {
            ToastUtil.showToast("身份证号码不能空");
            return;
        }
        //判断用户是否修改了信息，没有就不用提交
        if (!mChangeFlag) {
            //未修改
            completeChange();
            return;
        }

        //最后提交修改
        submit(name, idCard);
    }

    /**
     * 最后提交修改
     *
     * @param name
     * @param idCard
     */
    private void submit(final String name, final String idCard) {
        if (mChangeImgFlag) {
            //改了头像先上传头像
            File file = new File(imgOne.get(0));
            //压缩图片
            File newFile = CompressHelper.getDefault(this).compressToFile(file);
            //后缀名
            String ivSuffix = FileUtil.getExtensionName(newFile.getName());
            //Base64
            String str64 = FileUtil.fileToBase64(newFile);
            mLoginModel.uploadFile(PersonInfoActivity.this,
                    "userImg",
                    ivSuffix,
                    str64,
                    new RequestImpl() {
                        @Override
                        public void loadSuccess(Object object) {
                            ToastUtil.showToast("上传文件成功");
                            UploadFile uploadFile = (UploadFile) object;
                            if (uploadFile != null && uploadFile.getDbFileName() != null) {
                                uploadInfo(uploadFile.getDbFileName(), name, idCard);
                            }
                        }

                        @Override
                        public void loadFailed(int errorCode, String errorMessage) {
                            ToastUtil.showToast(errorMessage);
                            //失败了继续上传
                            uploadInfo("", name, idCard);
                        }
                    });
        } else {
            //不上传图片
            uploadInfo("", name, idCard);
        }
    }

    /**
     * 最后上传个人信息
     *
     * @param dbFileName
     * @param
     * @param name
     */
    private void uploadInfo(String dbFileName, String name, String idCard) {
        mLoginModel.updateLoginMemberInfo(PersonInfoActivity.this,
                name, idCard, dbFileName, new RequestImpl() {
                    @Override
                    public void loadSuccess(Object object) {
                        //上传成功
                        ToastUtil.showToast("修改成功");
                        //刷新数据
                        loadInfo();
                        //完成
                        completeChange();
                        mChangeFlag = false;

                    }

                    @Override
                    public void loadFailed(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }


    /**
     * 完成修改
     */
    private void completeChange() {
        mEditFlag = 0;
        getRightTv().setText("编辑");
        bindingView.tvName.setTextColor(getResources().getColor(R.color.itemBackground));
        bindingView.tvPhone.setTextColor(getResources().getColor(R.color.itemBackground));
        bindingView.tvIdCard.setTextColor(getResources().getColor(R.color.itemBackground));
    }

    private void loadInfo() {
        mLoginModel.checkLoginPersonInfo(PersonInfoActivity.this, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                mData = (LoginPersonInfo) object;
                String img = Constants.MASTER_URL_IMG + mData.getImgName();
                imgOne.add(img);
                Glide.with(bindingView.ivHead.getContext())
                        .load(img)
                        .crossFade(500)
                        .into(bindingView.ivHead);
                bindingView.tvName.setText(mData.getName());
                bindingView.tvPhone.setText(mData.getMobile());
                bindingView.tvIdCard.setText(mData.getIdCard());
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage + "，请下拉刷新重试");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE_CHOOSE_PHOTO) {
            ArrayList<String> selectedImages = BGAPhotoPickerActivity.getSelectedImages(data);
            if (selectedImages.size() > 0) {
                DebugUtil.error("PersonInfoActivity", "图片路径：" + selectedImages.get(0).toString());
                if (imgOne != null && imgOne.size() > 0) {
                    imgOne.clear();
                }
                imgOne.add(selectedImages.get(0).toString());
                Glide.with(bindingView.ivHead.getContext())
                        .load(selectedImages.get(0).toString())
                        .crossFade(500)
                        .into(bindingView.ivHead);
                mChangeFlag = true;
                mChangeImgFlag = true;
            }
        } else if (requestCode == Constants.REQUEST_CODE_PHOTO_PREVIEW) {
            ArrayList<String> selectedImages = BGAPhotoPickerActivity.getSelectedImages(data);
            if (selectedImages.size() > 0) {
                DebugUtil.error("PersonInfoActivity", "图片路径：" + selectedImages.get(0).toString());
                if (imgOne != null && imgOne.size() > 0) {
                    imgOne.clear();
                }
                imgOne.add(selectedImages.get(0).toString());
                Glide.with(bindingView.ivHead.getContext())
                        .load(selectedImages.get(0).toString())
                        .crossFade(500)
                        .into(bindingView.ivHead);
                mChangeFlag = true;
                mChangeImgFlag = true;
            }
        }
    }

    private void showExitPop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfoActivity.this);
        builder.setTitle("温馨提示");
        builder.setMessage("您有未保存的编辑信息，确定离开吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSwipeBackHelper.backward();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 这是兼容的 AlertDialog
     */
    @SuppressLint("RestrictedApi")
    private void showDialog(final String title, final TextView textView, final String oldContent) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
//        builder.setMessage("这是 android.support.v7.app.AlertDialog 中的样式");
        final EditText editText = new EditText(this);
        builder.setView(editText, 20, 50, 20, 20);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("修改", null);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etStr = editText.getText().toString().trim();
                if (TextUtils.isEmpty(etStr)) {
                    ToastUtil.showToast("请输入内容");
                    return;
                }
                if (title.equals("修改身份证号码") && (!RegexUtil.checkIdCard(etStr))) {
                    ToastUtil.showToast("请输入正确的身份证号码");
                    return;
                }
                textView.setText(etStr);
                if (null != oldContent && !oldContent.equals(etStr)) {
                    mChangeFlag = true;
                }
                dialog.dismiss();
            }
        });
    }
}
