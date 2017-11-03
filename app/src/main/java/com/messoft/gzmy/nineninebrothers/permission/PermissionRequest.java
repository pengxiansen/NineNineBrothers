package com.messoft.gzmy.nineninebrothers.permission;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.interfae.PermissionCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30 0030.
 * 权限请求
 */

public class PermissionRequest {
     private Context mContext;
     private PermissionCallback mCallback;

    public PermissionRequest(Context context, PermissionCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    /**
     * 这里申请读写和相机，需要其他再封装
     */
    public void requestWriteAndCamera(){
        AndPermission.with(mContext)
                .requestCode(110)
                .permission(Permission.STORAGE,Permission.CAMERA)
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(mContext, rationale).show();
                    }
                })
                .start();
    }

    @PermissionYes(110)
    public void yes(List<String> permissions) {
        this.mCallback.onSuccessful(permissions);
    }

    @PermissionNo(110)
    public void no(List<String> permissions) {
        System.out.println(permissions.toString());
        this.mCallback.onFailure(permissions);
    }
}
