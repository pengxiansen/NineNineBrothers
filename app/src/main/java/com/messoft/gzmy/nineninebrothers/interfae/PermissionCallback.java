package com.messoft.gzmy.nineninebrothers.interfae;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30 0030.
 * 权限回调接口
 */

public interface PermissionCallback {
    void onSuccessful(List<String> permissions);

    void onFailure(List<String> permissions);
}
