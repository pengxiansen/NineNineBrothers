package com.messoft.gzmy.nineninebrothers.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by Administrator on 2017/10/26 0026.
 * http://blog.csdn.net/w18756901575/article/details/51373693
 * 密码保护工具类
 */

// REFACTOR: 2017/10/26 0026 待重构这里使用这个加密打成正式包会出问题，暂无改成从sp获取和存取
public class PasswordHelp {
    /**
     * 保存账号密码
     * @param context
     * @param number
     * @param password
     * @param isRemember 是否记住密码
     */
    public static void savePassword(Context context, String number, String password, boolean isRemember) {
        //对数据进行加密
        //得到key
        SecretKey key = PasswordHelp.readKey(PasswordHelp.getPath("a.a"));
        if (key == null) {
            key = get3DESKey();
            //保存key
            PasswordHelp.saveKey(key, PasswordHelp.getPath("a.a"));
        }
        //对得到number和password进行加密
        byte[] numberByte = encrypt3DES(number, key);
        byte[] passwordByte = encrypt3DES(password, key);
        number = Base64.encodeToString(numberByte, Base64.DEFAULT);
        password = Base64.encodeToString(passwordByte, Base64.DEFAULT);

//        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("number", number);
//        editor.putString("password", password);
//        editor.putBoolean("isRemember", isRemember);
//        editor.commit();

        SPUtils.putString("number", number);
        SPUtils.putString("password", password);
        SPUtils.putBoolean("isRemember", isRemember);
    }

    //读取账号密码
    public static String[] readPassword(Context context) {
        String str[] = new String[]{SPUtils.getString("number", ""), SPUtils.getString("password", ""), String.valueOf(SPUtils.getBoolean("isRemember", false))};
        str[0] = d(str[0]);
        str[1] = d(str[1]);
        return str;
    }

    private static String d(String str) {
        if (!TextUtils.isEmpty(str)) {
            //对数据进行解密
            SecretKey key = readKey(PasswordHelp.getPath("a.a"));
            if (key != null) {
                str = decoder3DES(Base64.decode(str.getBytes(), Base64.DEFAULT), key);
            }
        }
        return str;
    }

    //保存key
    public static boolean saveKey(SecretKey key, String path) {
        try {
            FileOutputStream fileOutputStream1 = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    fileOutputStream1);
            objectOutputStream.writeObject(key);
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            DebugUtil.debug("测试", e.toString());
        }
        return false;
    }

    //读取key
    public static SecretKey readKey(String path) {
        SecretKey key = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(path));
            key = (SecretKey) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            DebugUtil.debug("测试:读取key:", e.toString());
        }
        return key;
    }

    //获取路径
    public static String getPath(String FileName) {
        if (TextUtils.isEmpty(FileName)) {

            return null;
        }
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/pengxiansen");
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        File file1 = new File(file, FileName);
        if (!file1.exists() || !file1.isFile()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                DebugUtil.debug("测试:文件创建失败：", e.toString());
                return null;
            }
        }
        return file1.getPath();
    }

    /**
     * 数据加解密3DES所需要的Key
     */
    public static SecretKey get3DESKey() {
        try {
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(168);// can 168 or 112/new SecureRandom()
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            // 转化key
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            SecretKey generateSecret = factory.generateSecret(deSedeKeySpec);

            return generateSecret;
        } catch (Exception e) {
            e.printStackTrace();
            DebugUtil.debug("测试", e.toString());
        }
        return null;
    }

    /**
     * 数据加密3DES
     */
    private static byte[] encrypt3DES(String str, SecretKey generateSecret) {
        try {
            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, generateSecret);
            byte[] result = cipher.doFinal(str.getBytes("utf-8"));

            return result;
        } catch (Exception e) {
            System.out.println("加密出错：" + e.getMessage());
        }
        return null;
    }

    /**
     * 数据解密3DES
     */
    private static String decoder3DES(byte[] str, SecretKey generateSecret) {
        try {
            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, generateSecret);
            System.out.println(2);
            byte[] result = cipher.doFinal(str);
            System.out.println(3);

            return new String(result, "utf-8");
        } catch (Exception e) {
            System.out.println("解密出错:" + e.getMessage());
        }
        return null;
    }
}
