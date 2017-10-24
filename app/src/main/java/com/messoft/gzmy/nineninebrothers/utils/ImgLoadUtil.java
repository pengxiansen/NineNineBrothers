package com.messoft.gzmy.nineninebrothers.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.messoft.gzmy.nineninebrothers.R;

/**
 * Created by Administrator on 2017/10/23 0023.
 * 图片加载工具类
 */

public class ImgLoadUtil {

    private static ImgLoadUtil instance;

    private ImgLoadUtil(){

    }

    private static ImgLoadUtil getInstance(){
        if (instance == null) {
            instance = new ImgLoadUtil();
        }
        return instance;
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    public static void displayCircle(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .crossFade(500)
                .error(R.drawable.default_header)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    public static void displayCircleById(ImageView imageView, int imageId) {
        Glide.with(imageView.getContext())
                .load(imageId)
                .crossFade(500)
                .error(R.drawable.default_header)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    public static void displayEspImage(String url, ImageView imageView, int type) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .placeholder(getDefaultPic(type))
                .error(getDefaultPic(type))
                .into(imageView);
    }

    private static int getDefaultPic(int type) {
        switch (type) {
            case 0:// 资讯
                return R.drawable.img_load_error;
        }
        return R.drawable.img_load_error;
    }

    /**
     * 资讯列表图标
     */
    @BindingAdapter("android:showNewsImg")
    public static void showNewsImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .override((int) SysUtils.getDimens(R.dimen.news_list_width), (int) SysUtils.getDimens(R.dimen.news_list_height))
                .placeholder(getDefaultPic(0))
                .error(getDefaultPic(0))
                .into(imageView);
    }
}
