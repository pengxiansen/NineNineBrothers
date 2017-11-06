package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.messoft.gzmy.nineninebrothers.Main2Activity;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.HomeHeadTypeAdapter;
import com.messoft.gzmy.nineninebrothers.adapter.HomeNewsAdapter;
import com.messoft.gzmy.nineninebrothers.app.ConstantsUrl;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.base.BaseFragment;
import com.messoft.gzmy.nineninebrothers.base.baseadapter.OnItemClickListener;
import com.messoft.gzmy.nineninebrothers.bean.HomeBanner;
import com.messoft.gzmy.nineninebrothers.bean.HomeHeadType;
import com.messoft.gzmy.nineninebrothers.bean.NewsList;
import com.messoft.gzmy.nineninebrothers.databinding.FragmentJiezhaiHomeBinding;
import com.messoft.gzmy.nineninebrothers.databinding.HomeHeadBinding;
import com.messoft.gzmy.nineninebrothers.http.HttpUtils;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.model.NewsModel;
import com.messoft.gzmy.nineninebrothers.ui.webview.WebCommonActivity;
import com.messoft.gzmy.nineninebrothers.utils.GlideImageLoader;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.messoft.gzmy.nineninebrothers.ui.webview.WebCommonActivity.TYPE_KEY;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class JzHomeFragment extends BaseFragment<FragmentJiezhaiHomeBinding> {

    private static final String TAG = "HomeFragment";
    private BaseActivity activity;

    // 初始化完成后加载数据
    private boolean mIsPrepared = false;
    // 第一次显示时加载数据，第二次不显示
    private boolean mIsFirst = true;
    private int mPage = 0;

    private HomeNewsAdapter mHomeNewsAdapter;
    private HomeHeadTypeAdapter mHomeHeadTypeAdapter;
    private NewsModel mNewsModel;
    private ArrayList<String> mBannerImages;//轮播图

    private View mHeaderView;
    private HomeHeadBinding mHeadBinding;
//    private DisposableObserver mDisposableObserver;

    @Override
    protected int setContent() {
        return R.layout.fragment_jiezhai_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSetting();
        initListener();

    }

    private void initSetting() {
//        showContentView();
        mNewsModel = new NewsModel();
        mHomeNewsAdapter = new HomeNewsAdapter(getActivity());
        mHomeHeadTypeAdapter = new HomeHeadTypeAdapter(activity, 1);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.xrcHome.setLayoutManager(mLayoutManager);
        bindingView.xrcHome.setAdapter(mHomeNewsAdapter);
        // 需加，不然滑动不流畅
        bindingView.xrcHome.setNestedScrollingEnabled(false);
        bindingView.xrcHome.setHasFixedSize(false);

        bindingView.xrcHome.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        bindingView.xrcHome.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        bindingView.xrcHome.setArrowImageView(R.drawable.iconfont_downgrey);

        //设置头
        mHeadBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.home_head, null, false);
        mHeadBinding.rcHead.setPullRefreshEnabled(false);
        mHeadBinding.rcHead.setLoadingMoreEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mHeadBinding.rcHead.setLayoutManager(gridLayoutManager);
        if (mHeaderView == null) {
            mHeaderView = mHeadBinding.getRoot();
            mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            bindingView.xrcHome.addHeaderView(mHeaderView);
            //加载头部分类
            loadHeadType();
        }

//        mDisposableObserver = new DisposableObserver() {
//            @Override
//            public void onNext(@NonNull Object o) {
//                showContentView();
//                if (o instanceof BaseBean) {
//
//                }
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                showContentView();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initListener() {
        bindingView.xrcHome.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                loadNewsListData();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadNewsListData();
            }
        });

        mHomeNewsAdapter.setOnItemClickListener(new OnItemClickListener<NewsList>() {
            @Override
            public void onClick(NewsList newsList, int position) {
                SysUtils.startActivity(getActivity(), Main2Activity.class);
            }
        });

        //查看更多
        mHeadBinding.rlLookMoreNews.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                startActivity(new Intent(getActivity(), WebCommonActivity.class).putExtra(TYPE_KEY, 13));
            }
        });
    }

    @Override
    protected void loadData() {
        // 显示时轮播图滚动
        if (mHeadBinding != null && mHeadBinding.banner != null) {
            mHeadBinding.banner.startAutoPlay();
            mHeadBinding.banner.setDelayTime(4000);
        }
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        loadBanner();

        loadNewsListData();

        //TODO 这里因为上啦加载分页不好操作所以还是分开写
//        loadHomeData();
    }

    @Override
    protected void onInvisible() {
        // 不可见时轮播图停止滚动
        if (mHeadBinding != null && mHeadBinding.banner != null) {
            mHeadBinding.banner.stopAutoPlay();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mHeadBinding != null && mHeadBinding.banner != null) {
            mHeadBinding.banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mHeadBinding != null && mHeadBinding.banner != null) {
            mHeadBinding.banner.stopAutoPlay();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    /**
     * 加载头部分类
     */
    private void loadHeadType() {
        List<HomeHeadType> listHead = new ArrayList<HomeHeadType>();
        listHead.add(new HomeHeadType("解债服务", R.drawable.default_header));
        listHead.add(new HomeHeadType("资产处理", R.drawable.default_header));
        listHead.add(new HomeHeadType("会员商城", R.drawable.default_header));
        mHomeHeadTypeAdapter.addAll(listHead);
        mHeadBinding.rcHead.setAdapter(mHomeHeadTypeAdapter);
        mHomeHeadTypeAdapter.notifyDataSetChanged();
    }

    private void loadBanner() {
        mNewsModel.getBanner(getActivity(), new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {

                List<HomeBanner> data = (List<HomeBanner>) object;
                if (data != null && data.size() > 0) {
                    if (mBannerImages == null) {
                        mBannerImages = new ArrayList<String>();
                    } else {
                        mBannerImages.clear();
                    }
                    for (int i = 0; i < data.size(); i++) {
                        //加上图片域名
                        mBannerImages.add(ConstantsUrl.MASTER_URL_IMG+data.get(i).getImgName());
                    }
                    //设置轮播图
                    mHeadBinding.banner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
                    //点击事件
                    mHeadBinding.banner.setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            position = position - 1;
                        }
                    });

                    mIsFirst = false;
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                //获取轮播图失败
            }
        });
    }


    /**
     * 加载列表
     */
    private void loadNewsListData() {
        mNewsModel.getNewsList(getActivity(), mPage, HttpUtils.per_page, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                List<NewsList> data = (List<NewsList>) object;
                if (mPage == 0) {
                    if (data != null && data.size() > 0) {
                        setNewListAdapter(data);
                    } else {
                        //TODO 暂无新闻
                        refreshComplete();
                    }
                } else {
                    if (data != null && data.size() > 0) {
                        bindingView.xrcHome.refreshComplete();
                        mHomeNewsAdapter.addAll(data);
                        mHomeNewsAdapter.notifyDataSetChanged();
                    } else {
                        bindingView.xrcHome.loadMoreComplete();
                    }
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                showContentView();
                refreshComplete();
                ToastUtil.showToast(errorMessage);
            }
        });
    }

    private void refreshComplete() {
        if (bindingView.xrcHome != null) {
            bindingView.xrcHome.refreshComplete();
        }
    }

    private void setNewListAdapter(List<NewsList> data) {
        mHomeNewsAdapter.clear();
        mHomeNewsAdapter.addAll(data);
        mHomeNewsAdapter.notifyDataSetChanged();
        bindingView.xrcHome.refreshComplete();

        mIsFirst = false;
    }

    /**
     * 加载轮播图和列表
     */
    private void loadHomeData() {
//        String newsListUrl = BusinessUtils.getUrl(ConstantsUrl.MASTER_URL_COMMON + ConstantsUrl.GET_NEWS_LIST,
//                null, mPage, HttpUtils.per_page);
//        String bannerUrl = BusinessUtils.getUrlNoPage(ConstantsUrl.MASTER_URL_COMMON + ConstantsUrl.GET_BANNER_LIST,
//                null);
//        if (!StringUtils.isNoEmpty(newsListUrl) && !StringUtils.isNoEmpty(bannerUrl)) {
//            return;
//        }
//        Observable<BaseBean<List<NewsList>>> newsList = HttpClient.Builder.getNineServer().getNewsList(newsListUrl);
//        Observable<BaseBean<List<HomeBanner>>> homeBanner = HttpClient.Builder.getNineServer().getHomeBanner(bannerUrl);
//        Observable.merge(newsList,homeBanner)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(mDisposableObserver);
    }
}
