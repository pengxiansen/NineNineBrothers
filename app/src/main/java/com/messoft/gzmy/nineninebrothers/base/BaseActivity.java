package com.messoft.gzmy.nineninebrothers.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityBaseBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.StatusBarUtil;
import com.messoft.gzmy.nineninebrothers.utils.dialog.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * @作者 Administrator
 * BaseActivity
 * @创建日期 2017/10/12 0012 17:41
 */

public abstract class BaseActivity<SV extends ViewDataBinding> extends RxAppCompatActivity implements BGASwipeBackHelper.Delegate {

    //布局view
    protected SV bindingView;
    private LinearLayout llProgressbar;
    private View refresh; //加载失败，重新加载
    private TextView tvError;//加载失败或者 加载数为空的提示
    private ActivityBaseBinding mBaseBinding;
    private Animation mRotate;
    private LinearInterpolator interpolator;
    //rxjava2.0 CompositeSubscription修改为CompositeDisposable
    //CompositeSubscription.unsubscribe修改为 CompositeDisposable.dispose();
//    private CompositeDisposable mCompositeDisposable;

    protected BGASwipeBackHelper mSwipeBackHelper;
    private ImageView mImg;

    //获取view
    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);

        //content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());

        //设置透明状态栏
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),38);

        llProgressbar = getView(R.id.ll_progress_bar);
        refresh = getView(R.id.ll_error_refresh);
        tvError = getView(R.id.tv_error);
        mImg = getView(R.id.img_progress);

        //加载动画
        mRotate = AnimationUtils.loadAnimation(this, R.anim.common_progress_cirle);
        //设置匀速旋转，在xml文件中设置会出现卡顿
        interpolator = new LinearInterpolator();
        mRotate.setInterpolator(interpolator);
        //默认进入页面就开启动画
        if (mImg != null && mRotate != null) {
            mImg.startAnimation(mRotate);
        }

        //设置toolbar
        if (hasToolBar()) {
            setToolBar();
        } else {
            mBaseBinding.toolBar.setVisibility(View.GONE);
        }

        //点击加载失败布局
        refresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        bindingView.getRoot().setVisibility(View.GONE);
    }

    /**
     * 设置titleBar
     */
    protected void setToolBar() {
        setSupportActionBar(mBaseBinding.toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        //手动设置才有效果（子类确定要不要右边的菜单栏）
        mBaseBinding.toolBar.setTitleTextAppearance(this, R.style.ToolBar_Title);
        mBaseBinding.toolBar.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
        //返回键
        mBaseBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 设置标题栏
     *
     * @param text
     */
    public void setTitle(CharSequence text) {
        mBaseBinding.tvTitle.setText(text);
    }

    /**
     * 获取右边textView
     */
    public TextView getRightTv() {
        return mBaseBinding.tvRight;
    }

    /**
     * 设置toolbar右边文字
     *
     * @param text
     */
    public void setRightText(CharSequence text) {
        mBaseBinding.tvRight.setText(text);
    }

    /**
     * 设置toolbar右边文字图片
     *
     * @param
     */
    public void setRightImg(int imgId) {
        Drawable img = getResources().getDrawable(imgId);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        mBaseBinding.tvRight.setCompoundDrawables(img, null, null, null); //设置左图标
    }

    /**
     * 子类是否需要继承父类的toolbar
     *
     * @return
     */
    protected boolean hasToolBar() {
        return true;
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }

    protected void showLoading() {
        if (llProgressbar.getVisibility() != View.VISIBLE) {
            llProgressbar.setVisibility(View.VISIBLE);
        }
        //开始动画
        if (mImg != null && mRotate != null) {
            mImg.startAnimation(mRotate);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (llProgressbar.getVisibility() != View.GONE) {
            llProgressbar.setVisibility(View.GONE);
        }
        //停止动画
        if (mImg != null && mRotate != null) {
            mImg.clearAnimation();
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
        if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
            bindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showError() {
        if (llProgressbar.getVisibility() != View.GONE) {
            llProgressbar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mImg != null && mRotate != null) {
            mImg.clearAnimation();
        }
        if (refresh.getVisibility() != View.VISIBLE) {
            refresh.setVisibility(View.VISIBLE);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
    }

    protected void showNoData(String strTip) {
        if (llProgressbar.getVisibility() != View.GONE) {
            llProgressbar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mImg != null && mRotate != null) {
            mImg.clearAnimation();
        }
        if (refresh.getVisibility() != View.VISIBLE) {
            refresh.setVisibility(View.VISIBLE);
            tvError.setText(strTip);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

//    public void addDisposable(Disposable s) {
//        if (this.mCompositeDisposable == null) {
//            this.mCompositeDisposable = new CompositeDisposable();
//        }
//        this.mCompositeDisposable.add(s);
//    }
//
//    public void removeDisposable() {
//        if (this.mCompositeDisposable != null) {
//            this.mCompositeDisposable.dispose();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (this.mCompositeDisposable != null) {
//            this.mCompositeDisposable.dispose();
//        }
    }
}
