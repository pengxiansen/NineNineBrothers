package com.messoft.gzmy.nineninebrothers.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class InterfaceAuthenUtils {
    /**
     * 定义加密方式
     * MAC算法可选以下多种算法
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    private final static String KEY_MAC = "HmacMD5";

    /**
     * 全局数组
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 构造函数
     */
    public InterfaceAuthenUtils() {

    }

    /**
     * BASE64 加密
     *
     * @param key 需要加密的字节数组
     * @return 字符串
     * @throws Exception
     */
    public static String encryptBase64(byte[] key) throws Exception {
        return Base64.encodeToString(key, Base64.DEFAULT);
//        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * BASE64 解密
     *
     * @param key 需要解密的字符串
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] decryptBase64(String key) throws Exception {

//        return (new BASE64Decoder()).decodeBuffer(key);
        return Base64.decode(key, Base64.DEFAULT);
    }

    /**
     * 初始化HMAC密钥
     *
     * @return
     */
    public static String init() {
        SecretKey key;
        String str = "";
        try {
            KeyGenerator generator = KeyGenerator.getInstance(KEY_MAC);
            key = generator.generateKey();
            str = encryptBase64(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * HMAC加密
     *
     * @param data 需要加密的字节数组
     * @param key  密钥
     * @return 字节数组
     */
    public static byte[] encryptHMAC(byte[] data, String key) {
        SecretKey secretKey;
        byte[] bytes = null;
        try {
            secretKey = new SecretKeySpec(decryptBase64(key), KEY_MAC);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * HMAC加密
     *
     * @param data 需要加密的字符串
     * @param key  密钥
     * @return 字符串
     */
    public static String encryptHMAC(String data, String key) {
        if (data == null || data.equals("")) {
            return null;
        }
        byte[] bytes = encryptHMAC(data.getBytes(), key);
        return byteArrayToHexString(bytes);
    }


    /**
     * 将一个字节转化成十六进制形式的字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteToHexString(byte b) {
        int ret = b;
        //System.out.println("ret = " + ret);
        if (ret < 0) {
            ret += 256;
        }
        int m = ret / 16;
        int n = ret % 16;
        return hexDigits[m] + hexDigits[n];
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }


    /**
     * 生成一个基于HMAC(秘钥base64加密)加密算法的请求头签名
     *
     * @param secret 签名秘钥
     * @return 签名内容
     * @throws IOException
     */
    public static String signTopRequestNew(String action, String data,
                                           String timestamp, String token, String secret) throws IOException {
        if (secret != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(secret);
            secret = m.replaceAll("");
        }
        Map<String, String> params = new HashMap<String, String>();

        params.put("action", action);
        params.put("timestamp", timestamp);
        if (!TextUtils.isEmpty(data)) {
//            data = URLEncoder.encode(data, "UTF-8");
            params.put("data", data);
        }
        params.put("token", token);
        DebugUtil.debug("signTopRequestNew","params:"+params);
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (areNotEmpty(new String[]{key, value})) {
                query.append(key).append(value);
            }
        }
        return encryptHMAC(query.toString(), secret);
    }

    /**
     * 数组是否非空
     *
     * @param values
     * @return
     */
    public static boolean areNotEmpty(String[] values) {
        boolean result = true;
        if ((values == null) || (values.length == 0))
            result = false;
        else {
            for (String value : values) {/**对值查询*/
                result &= !isEmpty(value);/**初始化true &非空数据，如果是空，则result为false*/
            }
        }
        return result;
    }

    /**
     * 字符串为空
     *
     * @param value 字符串内容
     * @return <code>true</code>为空<code>false</code>不为空
     */
    public static boolean isEmpty(String value) {
        if ((value == null) || (value.length() == 0)) {
            return true;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

//    /**
//     * 验证签名
//     * @param params 签名参数
//     * @param secret 签名秘钥
//     * @param topSign 签名字符串
//     * @return <code>true</code>验证成功<code>false<code>请求失败
//     * @throws IOException
//     */
//    public static boolean verifySing(Map<String, String> params,String secret,String topSign) throws IOException {
//        params.remove("sign");
//        return signTopRequestNew(params, secret).equals(topSign);
//    }

    /**
     * 获取日期与当前日期相差多少秒
     *
     * @param dateStr
     * @return 秒数
     */
    public static long differDateSecond(String dateStr) throws Exception {
        if (dateStr == null || dateStr.equals("")) {
            return 0l;
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse(dateStr);
        c.setTime(date1);
        long day1 = c.getTimeInMillis();
        Date currentDate = new Date();
        c.setTime(currentDate);
        long day2 = c.getTimeInMillis();
        Double differDate2 = (day1 - day2) * 1.0 / 1000 / 60 / 60 / 24;
        return (long) (differDate2 * 60 * 60 * 24);
    }

    /**
     * 测试方法
     * 接口类型(1.需要走签名认证的信息,2不需要走签名认证的信息)可以在过滤其中都走签名认证,后台配置一个不需要走签名认证的地址列表,逗号分隔
     * 登陆成功返回当前用户的秘钥信息给接口调用方.token信息(日期加时间)。。。。1.用于用户身份认证. 2.如果登陆后的秘钥被破解,token失效,会重新获取,增加安全性
     * 接口调用方每次对数据做签名.
     * 接口提供方后台对签名,时间 做认证.
     * （1）判断是否包含timestamp，token，sign参数，如果不含有返回错误码。
     * （2）判断服务器接到请求的时间和参数中的时间戳是否相差很长一段时间（时间自定义如半个小时），如果超过则说明该 url已经过期（如果url被盗，他改变了时间戳，但是会导致sign签名不相等）。
     * （3）判断token是否有效，根据请求过来的token，查询redis缓存中的uid，如果获取不到这说明该token已过期。
     * （4）根据用户请求的url参数，服务器端按照同样的规则生成sign签名，对比签名看是否相等，相等则放行。（自然url签名 也无法100%保证其安全，也可以通过公钥AES对数据和url加密，但这样如果无法确保公钥丢失，所以签名只是很大程 度上保证安全）。
     * （5）此url拦截只需对获取身份认证的url放行（如登陆url），剩余所有的url都需拦截。
     * <p>
     * 用户名,密码泄露,秘钥泄露 问题
     */
    public static void main(String[] args) throws Exception {
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//		String s1=format.format(new Date());
//            //客户端签名
//            String secret="vtO9QqIoDIjOKZLlaQUYJ1DOP1KOSSlm7IJfdcSwzCSSkElK/RDrSgq4GwuzTF6SrnwneNjydNaKwK2fmkNk/A==";
//            System.out.println(secret.length());
//            Map<String, String> params=new HashMap<String, String>();
//            params.put("action","getTestResultRemarks");
//            //需要增加
//            params.put("timestamp","2016-12-22 17:00:00");
//            params.put("data","{model:1,accountId:4,roleId:1}");
//            //需要增加（登录之后会给）
//            params.put("token","5AB14AD637364876CD46E0C755E13EB0");
//            String sign=signTopRequestNew(params, secret);
//            params.put("sign",sign);
//
//            //服务端参数校验
//            long second=differDateSecond(params.get("timestamp"));
//            if(second<-1800){
//                System.out.println("请求地址已过期");
//                System.out.println(second);
//            }
//            if(verifySing(params, secret, sign)){
//                System.out.println("签名验证成功!");
//            }
//
//            System.out.println(sign);
    }
}
