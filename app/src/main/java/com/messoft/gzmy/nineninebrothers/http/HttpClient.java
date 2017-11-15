package com.messoft.gzmy.nineninebrothers.http;

import com.messoft.gzmy.nineninebrothers.bean.AssetInfoById;
import com.messoft.gzmy.nineninebrothers.bean.AssetInfoFile;
import com.messoft.gzmy.nineninebrothers.bean.AssetKuList;
import com.messoft.gzmy.nineninebrothers.bean.AssetProgress;
import com.messoft.gzmy.nineninebrothers.bean.BaseBean;
import com.messoft.gzmy.nineninebrothers.bean.GetAssetProgressFileList;
import com.messoft.gzmy.nineninebrothers.bean.GetDebtMatterProgressFileList;
import com.messoft.gzmy.nineninebrothers.bean.HomeBanner;
import com.messoft.gzmy.nineninebrothers.bean.JzInfo;
import com.messoft.gzmy.nineninebrothers.bean.JzKuList;
import com.messoft.gzmy.nineninebrothers.bean.JzPersonList;
import com.messoft.gzmy.nineninebrothers.bean.JzProgress;
import com.messoft.gzmy.nineninebrothers.bean.Login;
import com.messoft.gzmy.nineninebrothers.bean.LoginPersonInfo;
import com.messoft.gzmy.nineninebrothers.bean.NewsList;
import com.messoft.gzmy.nineninebrothers.bean.PointsInfo;
import com.messoft.gzmy.nineninebrothers.bean.PointsList;
import com.messoft.gzmy.nineninebrothers.bean.QueryAssetDiscussApplyListWithAssetInfo;
import com.messoft.gzmy.nineninebrothers.bean.QueryDebtOrderTradeList;
import com.messoft.gzmy.nineninebrothers.bean.QueryMyAssetList;
import com.messoft.gzmy.nineninebrothers.bean.Street;
import com.messoft.gzmy.nineninebrothers.bean.UploadDebtMatterProgressFile;
import com.messoft.gzmy.nineninebrothers.bean.UploadFile;
import com.messoft.gzmy.nineninebrothers.bean.UploadFileHasData;
import com.messoft.gzmy.nineninebrothers.bean.ZsDataInfoById;
import com.messoft.gzmy.nineninebrothers.bean.ZsDataInfoFileById;
import com.messoft.gzmy.nineninebrothers.bean.ZsPersonFileInfo;
import com.messoft.gzmy.nineninebrothers.bean.ZsPersonInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 网络请求类，一个接口对应一个方法
 */

public interface HttpClient {

    class Builder {
        public static HttpClient getNineServerCommon() {
            return HttpUtils.getInstance().getNineServerCommon(HttpClient.class);
        }

        public static HttpClient getNineServerQz() {
            return HttpUtils.getInstance().getNineServerQz(HttpClient.class);
        }

        public static HttpClient getNineServerMember() {
            return HttpUtils.getInstance().getNineServerMember(HttpClient.class);
        }

    }

    /**
     * 首页资讯
     */
    @FormUrlEncoded
    @POST("/news/news.do?action=getNewsList")
    Observable<BaseBean<List<NewsList>>> getNewsList(@Field("pageNo") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 首页轮播图
     */
    @FormUrlEncoded
    @POST("/image/image.do?action=getImageList")
    Observable<BaseBean<List<HomeBanner>>> getHomeBanner(@Field("data") String phone);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/account/account.do?action=login")
    Observable<BaseBean<Login>> login(@Field("data") String phone);

    /**
     * 注册 (结果同登录一样)
     */
    @FormUrlEncoded
    @POST("/account/account.do?action=reg")
    Observable<BaseBean<Login>> register(@Field("data") String phone);

    /**
     * 根据区请求街道
     */
    @FormUrlEncoded
    @POST("/area/area.do?action=getAreaList")
    Observable<BaseBean<List<Street>>> searchStreetById(@Field("data") String phone);

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("/verificationCode/verificationCode.do?action=getVerificationCode")
    Observable<BaseBean> getCode(@Field("data") String phone);

    /**
     * 校验验证码
     */
    @FormUrlEncoded
    @POST("/verificationCode/verificationCode.do?action=validateVerificationCode")
    Observable<BaseBean> checkCode(@Field("data") String phone);

    /**
     * 2.1.1 账号手机修改
     */
    @FormUrlEncoded
    @POST("/account/account.do?action=modifyMobile")
    Observable<BaseBean> modifyMobile(@Field("data") String phone);

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("/account/account.do?action=modifyPassword")
    Observable<BaseBean> changePsw(@Field("data") String phone);

    /**
     * 查询登录人信息
     */
    @FormUrlEncoded
    @POST("/member/member.do?action=getLoginMemberInfo")
    Observable<BaseBean<LoginPersonInfo>> checkLoginPersonInfo(@Field("data") String phone);

    /**
     * 2.4.1 修改登录人信息
     */
    @FormUrlEncoded
    @POST("/member/member.do?action=updateLoginMemberInfo")
    Observable<BaseBean> updateLoginMemberInfo(@Field("data") String phone);

    /**
     * 2.3.1 获取账号积分信息
     */
    @FormUrlEncoded
    @POST("/point/point.do?action=getPointByAccount")
    Observable<BaseBean<PointsInfo>> getPointByAccount(@Field("data") String phone);

    /**
     * 2.5.2  查询资产订单列表
     */
    @FormUrlEncoded
    @POST("/pointLog/pointLog.do?action=getPointLogList")
    Observable<BaseBean<List<PointsList>>> getPointLogList(@Field("data") String phone,
                                                           @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 解债库，解债师才能看
     */
    @FormUrlEncoded
    @POST("/debtMatter/debtMatter.do?action=queryDebtMatterListWithAsset")
    Observable<BaseBean<List<JzKuList>>> getJzKuList(@Field("data") String phone, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 2.1 文件上传模块API接口
     */
//    @FormUrlEncoded
//    @POST("upload/upload.do?action=uploadFile")
//    Observable<BaseBean<UploadFile>> uploadFile(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("upload/upload.do?action=uploadFile")
    Observable<BaseBean<UploadFile>> uploadFile(@Field("moduleName") String moduleName,
                                                @Field("extName") String extName,
                                                @Field("fileStr") String fileStr);


    /**
     * 2.2.6  查询债事详细资料
     */
    @FormUrlEncoded
    @POST("/debtMatter/debtMatter.do?action=queryDebtMatterInfo")
    Observable<BaseBean<JzInfo>> getJzInfo(@Field("data") String phone);

    /**
     * 2.1.4  查询债事人详情
     */
    @FormUrlEncoded
    @POST("/debtMatterPerson/debtMatterPerson.do?action=queryDebtMatterPersonInfoById")
    Observable<BaseBean<ZsPersonInfo>> getZsPersonInfo(@Field("data") String phone);

    /**
     * 2.4.3  查询债事单交易列表
     */
    @FormUrlEncoded
    @POST("/debtMatterOrder/debtMatterOrder.do?action=queryDebtOrderTradeList")
    Observable<BaseBean<List<QueryDebtOrderTradeList>>> queryDebtOrderTradeList(@Field("data") String phone);

    /**
     * 2.4.1  债事接单申请
     */
    @FormUrlEncoded
    @POST("/debtMatterOrder/debtMatterOrder.do?action=applyDebtMatterOrder")
    Observable<BaseBean> applyDebtMatterOrder(@Field("data") String phone);

    /**
     * 2.3.4  查询资产库列表
     */
    @FormUrlEncoded
    @POST("/asset/asset.do?action=getAssetList")
    Observable<BaseBean<List<AssetKuList>>> getAssetList(@Field("data") String phone);

    /**
     * 2.7.1  查询债事交易进度列表
     */
    @FormUrlEncoded
    @POST("/debtMatterProgress/debtMatterProgress.do?action=queryDebtMatterProgressInfoList")
    Observable<BaseBean<List<JzProgress>>> queryDebtMatterProgressInfoList(@Field("data") String phone);

    /**
     * 2.7.1  查询债事交易进度列表
     */
    @FormUrlEncoded
    @POST("/debtMatterProgress/debtMatterProgress.do?action=queryDebtMatterProgressInfoList")
    Observable<BaseBean<List<JzProgress>>> queryDebtMatterProgressInfoList(@Field("data") String phone,@Field("pageNo") int pageNo,@Field("pageSize") int pageSize);

    /**
     * 2.7.2  查询债事交易进度文件列表
     */
    @FormUrlEncoded
    @POST("/debtMatterProgressFile/debtMatterProgressFile.do?action=getDebtMatterProgressFileList")
    Observable<BaseBean<List<GetDebtMatterProgressFileList>>> getDebtMatterProgressFileList(@Field("data") String phone);

    /**
     * 2.7.3  录入债事交易进度
     */
    @FormUrlEncoded
    @POST("/debtMatterProgress/debtMatterProgress.do?action=inputDebtMatterProgress")
    Observable<BaseBean> inputDebtMatterProgress(@Field("data") String phone);

    /**
     * 2.7.4  债事交易进度文件上传
     */
    @FormUrlEncoded
    @POST("/debtMatterProgressFile/debtMatterProgressFile.do?action=uploadDebtMatterProgressFile")
    Observable<BaseBean<UploadDebtMatterProgressFile>> uploadDebtMatterProgressFile(@Field("data") String phone);

    /**
     * 2.1.1  债事人备案
     */
    @FormUrlEncoded
    @POST("/debtMatterPerson/debtMatterPerson.do?action=debtMatterPersonRecord")
    Observable<BaseBean> debtMatterPersonRecord(@Field("data") String phone);

    /**
     * 2.1.2  债事人文件上传
     */
    @FormUrlEncoded
    @POST("/debtMatterPersonFile/debtMatterPersonFile.do?action=uploadDebtMatterPersonFile")
    Observable<BaseBean<UploadFileHasData>> uploadDebtMatterPersonFile(@Field("data") String phone);

    /**
     * 2.2.2  债事文件上传
     */
    @FormUrlEncoded
    @POST("/debtMatterFile/debtMatterFile.do?action=uploadDebtMatterFile")
    Observable<BaseBean<UploadFileHasData>> uploadDebtMatterFile(@Field("data") String phone);

    /**
     * 2.1.3  查询债事人列表
     */
    @FormUrlEncoded
    @POST("/debtMatterPerson/debtMatterPerson.do?action=getDebtMatterPersonList")
    Observable<BaseBean<List<JzPersonList>>> getDebtMatterPersonList(@Field("data") String phone);

    /**
     * 2.5.1  资产洽谈申请
     */
    @FormUrlEncoded
    @POST("/assetDiscussApply/assetDiscussApply.do?action=applyAssetDiscuss")
    Observable<BaseBean> assetDiscussApply(@Field("data") String phone);

    /**
     * 2.6.1  查询资产交易进度列表
     */
    @FormUrlEncoded
    @POST("/assetProgress/assetProgress.do?action=queryAssetProgressListByAssetId")
    Observable<BaseBean<List<AssetProgress>>> queryAssetProgressListByAssetId(@Field("data") String phone,
                                                                              @Field("pageNo") int pageNo,
                                                                              @Field("pageSize") int pageSize);
    /**
     * 2.6.2  查询资产交易进度文件列表
     */
    @FormUrlEncoded
    @POST("/assetProgressFile/assetProgressFile.do?action=getAssetProgressFileList")
    Observable<BaseBean<List<GetAssetProgressFileList>>> getAssetProgressFileList(@Field("data") String phone);

    /**
     * 2.6.3  录入资产交易进度
     */
    @FormUrlEncoded
    @POST("/assetProgress/assetProgress.do?action=inputAssetProgress")
    Observable<BaseBean> inputAssetProgress(@Field("data") String phone);

    /**
     * 2.6.4  资产交易进度文件上传
     */
    @FormUrlEncoded
    @POST("/assetProgressFile/assetProgressFile.do?action=uploadAssetProgressFile")
    Observable<BaseBean<UploadDebtMatterProgressFile>> uploadAssetProgressFile(@Field("data") String phone);


    /**
     * 2.5.2  查询资产订单列表
     */
    @FormUrlEncoded
    @POST("/assetDiscussApply/assetDiscussApply.do?action=queryAssetDiscussApplyListWithAssetInfo")
    Observable<BaseBean<List<QueryAssetDiscussApplyListWithAssetInfo>>> queryAssetDiscussApplyListWithAssetInfo(@Field("data") String phone);

    /**
     * 2.3.6  查询资产管理列表
     */
    @FormUrlEncoded
    @POST("/asset/asset.do?action=queryMyAssetList")
    Observable<BaseBean<List<QueryMyAssetList>>> queryMyAssetList(@Field("data") String phone,
                                                                  @Field("pageNo") int pageNo,
                                                                  @Field("pageSize") int pageSize);

    /**
     * 2.1.5  查询债事人文件列表
     */
    @FormUrlEncoded
    @POST("/debtMatterPersonFile/debtMatterPersonFile.do?action=getDebtMatterPersonFileList")
    Observable<BaseBean<List<ZsPersonFileInfo>>> getZsPersonFileInfo(@Field("data") String phone);

    /**
     * 2.2.5  根据id查询债事信息
     */
    @FormUrlEncoded
    @POST("/debtMatter/debtMatter.do?action=getDebtMatterById")
    Observable<BaseBean<ZsDataInfoById>> getZsInfoById(@Field("data") String phone);

    /**
     * 2.2.7  查询债事文件列表
     */
    @FormUrlEncoded
    @POST("/debtMatterFile/debtMatterFile.do?action=getDebtMatterFileList")
    Observable<BaseBean<List<ZsDataInfoFileById>>> getZsInfoFileById(@Field("data") String phone);

    /**
     * 2.3.3  查询资产详情
     */
    @FormUrlEncoded
    @POST("/asset/asset.do?action=getAssetById")
    Observable<BaseBean<AssetInfoById>> getAssetById(@Field("data") String phone);

    /**
     * 2.3.5  查询资产文件列表
     */
    @FormUrlEncoded
    @POST("/assetFile/assetFile.do?action=getAssetFileList")
    Observable<BaseBean<List<AssetInfoFile>>> getAssetInfo(@Field("data") String phone);

    /**
     * 2.2.1  债事备案
     */
    @FormUrlEncoded
    @POST("/debtMatter/debtMatter.do?action=debtMatterRecord")
    Observable<BaseBean> debtMatterRecord(@Field("data") String phone);

    /**
     * 2.3.1  资产备案
     */
    @FormUrlEncoded
    @POST("/asset/asset.do?action=assetRecord")
    Observable<BaseBean> assetRecord(@Field("data") String phone);

}
