package com.messoft.gzmy.nineninebrothers.model;

import android.content.Context;

import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.Login;
import com.messoft.gzmy.nineninebrothers.bean.LoginPersonInfo;
import com.messoft.gzmy.nineninebrothers.bean.PointsInfo;
import com.messoft.gzmy.nineninebrothers.bean.PointsList;
import com.messoft.gzmy.nineninebrothers.bean.Street;
import com.messoft.gzmy.nineninebrothers.bean.UploadFile;
import com.messoft.gzmy.nineninebrothers.http.BaseObserver;
import com.messoft.gzmy.nineninebrothers.http.HttpClient;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.http.RxSchedulers;
import com.messoft.gzmy.nineninebrothers.utils.DebugUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/24 0024.
 * 登录注册模块
 */

public class LoginModel {

    /**
     * 登录
     */
    public void login(Context context, String account, String password, final RequestImpl listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", account);
            jsonObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerMember().login(StringUtils.toURLEncoderUTF8(jsonObject.toString()))
                .compose(RxSchedulers.<BaseBean<Login>>compose())
                .subscribe(new BaseObserver<Login>(context, true) {
                    @Override
                    protected void onSuccess(Login login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 注册
     */
    public void register(Context context,
                         String mobile,
                         String code,
                         String password,
                         String fatherReferralCode,
                         String province,
                         String provinceText,
                         String city,
                         String cityText,
                         String district,
                         String districtText,
                         String street, //街道非必填
                         String streetText,
                         final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("mobile", mobile);
            map.put("code", code);
            map.put("password", password);
            map.put("fatherReferralCode", fatherReferralCode);
            map.put("province", province);
            map.put("provinceText", provinceText);
            map.put("city", city);
            map.put("cityText", cityText);
            map.put("district", district);
            map.put("districtText", districtText);
            if (StringUtils.isNoEmpty(street) && StringUtils.isNoEmpty(streetText)) {
                map.put("street", street);
                map.put("streetText", streetText);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerMember().register(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<Login>>compose())
                .subscribe(new BaseObserver<Login>(context, true) {
                    @Override
                    protected void onSuccess(Login login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 根据区请求街道
     */
    public void searchStreetById(Context context, String parentId, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("parentId", parentId + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerCommon().searchStreetById(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<List<Street>>>compose())
                .subscribe(new BaseObserver<List<Street>>(context, true) {
                    @Override
                    protected void onSuccess(List<Street> login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 获取验证码 0.注册、1.修改密码2.微信授权登录验证
     */
    public void getCode(Context context, String account, String type, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("mobile", account);
            map.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerMember().getCode(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserver(context, true) {
                    @Override
                    protected void onSuccess(Object login) {
                        listener.loadSuccess(login);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.1.1 账号手机修改
     */
    public void modifyMobile(Context context, String mobile, String code, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("mobile", mobile);
            map.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerMember().modifyMobile(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserver(context, true) {
                    @Override
                    protected void onSuccess(Object login) {
                        listener.loadSuccess(login);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 验证验证码 0.注册、1.修改密码2.微信授权登录验证
     */
    public void checkCode(Context context, String account, String type, String code, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("mobile", account);
            map.put("type", type);
            map.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerMember().checkCode(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserver(context, true) {
                    @Override
                    protected void onSuccess(Object login) {
                        listener.loadSuccess(login);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 修改密码
     */
    public void changePsw(Context context, String account, String code, String password, String rePassword, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("mobile", account);
            map.put("code", code);
            map.put("password", password);
            map.put("rePassword", rePassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerMember().changePsw(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserver(context, true) {
                    @Override
                    protected void onSuccess(Object login) {
                        listener.loadSuccess(login);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 查看登录人信息
     */
    public void checkLoginPersonInfo(Context context, final RequestImpl listener) {
        HttpClient.Builder.getNineServerQz().checkLoginPersonInfo("")
                .compose(RxSchedulers.<BaseBean<LoginPersonInfo>>compose())
                .subscribe(new BaseObserver<LoginPersonInfo>(context, false) {
                    @Override
                    protected void onSuccess(LoginPersonInfo login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.4.1 修改登录人信息
     */
    public void updateLoginMemberInfo(Context context,
                                      String name,
                                      String idCard,
                                      String imgName,
                                      final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            if (StringUtils.isNoEmpty(name)) {
                map.put("name", name);
            }
            if (StringUtils.isNoEmpty(idCard)) {
                map.put("idCard", idCard);
            }
            if (StringUtils.isNoEmpty(imgName)) {
                map.put("imgName", imgName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerQz().updateLoginMemberInfo(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean>compose())
                .subscribe(new BaseObserver(context, true, "上传个人信息") {
                    @Override
                    protected void onSuccess(Object login) {
                        listener.loadSuccess(login);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.3.1 获取账号积分信息
     */
    public void getPointByAccount(Context context, String accountId, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("accountId", accountId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerMember().getPointByAccount(StringUtils.toURLEncoderUTF8(map.toString()))
                .compose(RxSchedulers.<BaseBean<PointsInfo>>compose())
                .subscribe(new BaseObserver<PointsInfo>(context, false) {
                    @Override
                    protected void onSuccess(PointsInfo login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 2.3.1 查询账户积分流水列表
     */
    public void getPointLogList(Context context, String accountId, int pageNo, int pageSize, final RequestImpl listener) {
        JSONObject map = new JSONObject();
        try {
            map.put("accountId", accountId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient.Builder.getNineServerMember().getPointLogList(StringUtils.toURLEncoderUTF8(map.toString()),pageNo,pageSize)
                .compose(RxSchedulers.<BaseBean<List<PointsList>>>compose())
                .subscribe(new BaseObserver<List<PointsList>>(context, true) {
                    @Override
                    protected void onSuccess(List<PointsList> login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }

    /**
     * 上传文件
     */
    public void uploadFile(Context context, String moduleName, String extName, String fileStr, final RequestImpl listener) {
        Map<String, String> map = new HashMap<String, String>();
//        map.put("moduleName", "userImg");
        map.put("moduleName", moduleName);
        map.put("extName", extName);
        map.put("fileStr", fileStr);
        DebugUtil.debug("uploadFile","上传文件信息："+StringUtils.toURLEncoderUTF8(map.toString()));
        HttpClient.Builder.getNineServerCommon().uploadFile(moduleName,extName,fileStr)
                .compose(RxSchedulers.<BaseBean<UploadFile>>compose())
                .subscribe(new BaseObserver<UploadFile>(context, true, "上传图片中...") {
                    @Override
                    protected void onSuccess(UploadFile login) {
                        if (login != null) {
                            listener.loadSuccess(login);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.loadFailed(errorCode, msg);
                    }
                });
    }
}
