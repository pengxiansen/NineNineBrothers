package com.messoft.gzmy.nineninebrothers.utils;

import android.text.TextUtils;

import com.messoft.gzmy.nineninebrothers.bean.LoginPersonInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26 0026.
 * 本项目业务工具类
 */

public class BusinessUtils {

    public static String getToken() {
        return SPUtils.getString("accessToken", "");
    }

    public static String getSecret() {

        return SPUtils.getString("secret", "");
    }

    public static LoginPersonInfo getLoginPersonInfo() {
        Object object = SPUtils.getObject("loginPersonInfo");
        if (object != null && object instanceof LoginPersonInfo) {
            LoginPersonInfo login = (LoginPersonInfo) object;
            if (login != null) {
                return login;
            }
        }
        return null;
    }

    /**
     * 带分页
     *
     * @param url
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static String getUrl(String url, Map<String, String> map, int pageNo, int pageSize) {
//        if (TextUtils.isEmpty(Constants.ACCOUNT_ID)) {
//            return null;
//        }
        JSONObject jsonObject = new JSONObject();
        String sUrl;
        try {
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
            }
            sUrl = url + toURLEncoderUTF8(jsonObject.toString()) + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
            return sUrl;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 不带分页
     *
     * @param url
     * @return
     */
    public static String getUrlNoPage(String url, Map<String, String> map) {
//        if (TextUtils.isEmpty(Constants.ACCOUNT_ID)) {
//            return null;
//        }
        JSONObject jsonObject = new JSONObject();
        String sUrl;
        try {
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
            }
            sUrl = url + toURLEncoderUTF8(jsonObject.toString());
            return sUrl;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 资产类型
     */
    public static String assetType(String assetType) {
        if (!StringUtils.isNoEmpty(assetType)) {
            return "";
        }
        String str = "";
        if (assetType.equals("0")) {
            str = "房产";
        } else if (assetType.equals("1")) {
            str = "专利";
        }
        if (assetType.equals("2")) {
            str = "股权";
        }
        if (assetType.equals("4")) {
            str = "货物";
        }
        return str;
    }

    /**
     * 债务性质(0:个人,1:企业)
     *
     * @param debtorType
     * @return
     */
    public static String debtProperty(String debtorType) {
        if (!StringUtils.isNoEmpty(debtorType)) {
            return "";
        }
        if (debtorType.equals("0")) {
            return "个人";
        } else if (debtorType.equals("1")) {
            return "企业";
        }
        return "";
    }

    /**
     * 债务类型(0:货币,1:非货币)
     *
     * @param debtorType
     * @return
     */
    public static String debtorType(String debtorType) {
        if (!StringUtils.isNoEmpty(debtorType)) {
            return "";
        }
        if (debtorType.equals("0")) {
            return "货币";
        } else if (debtorType.equals("1")) {
            return "非货币";
        }
        return "";
    }

    /**
     * lawsuitState：诉讼状态(0:非诉讼,1:已诉讼)
     *
     * @param debtorType
     * @return
     */
    public static String lawsuitState(String debtorType) {
        if (!StringUtils.isNoEmpty(debtorType)) {
            return "";
        }
        if (debtorType.equals("0")) {
            return "非诉讼";
        } else if (debtorType.equals("1")) {
            return "已诉讼";
        }
        return "";
    }

    /**
     * 请求参数包含中文时，设置UTF-8格式
     *
     * @param s
     * @return
     */
    public static String toURLEncoderUTF8(String s) {
        String result = "";
        try {
            result = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将2017-07-05 17:11:09切割 2017-07-05
     */
    public static String subyymmddd(String strTime) {
        if (strTime == null || TextUtils.isEmpty(strTime)) {
            return "";
        }
        if (strTime.length() < 10) {
            return "";
        }
        return strTime.substring(0, 10);
    }

    /**
     * 将2017-07-05 17:11:09切割 17:11
     *
     * @return
     */
    public static String subhm(String time) {
        if (time == null || TextUtils.isEmpty(time)) {
            return "";
        }
        if (time.length() < 16) {
            return "";
        }
        return time.substring(11, 16);
    }
}
