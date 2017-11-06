package com.messoft.gzmy.nineninebrothers.ui.jiezai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.adapter.MyFragmentPagerAdapter;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.LoginPersonInfo;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJieZhaiBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.JzPropertyManage.JzPropertyFragment;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome.JzHomeFragment;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzManage.JzKuJzsFragment;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzManage.JzManageFragment;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzMy.JzMyFragment;
import com.messoft.gzmy.nineninebrothers.utils.SPUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;
import com.messoft.gzmy.nineninebrothers.view.BottomNavigationViewHelper;

import java.util.ArrayList;

/**
 * @作者 Administrator
 * 解债模块
 * @创建日期 2017/10/23 0023 17:40
 */

public class JzActivity extends BaseActivity<ActivityJieZhaiBinding> {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private LoginModel mLoginModel;
    private String mRoleId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_zhai);
        setTitle("资产解债");
        mLoginModel = new LoginModel();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_news:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_lib:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_manage:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_find:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
        //查询登录人信息
        checkLoginPersonInfo();
    }

    /**
     * 查询登录人信息
     */
    private void checkLoginPersonInfo() {
        mLoginModel.checkLoginPersonInfo(JzActivity.this, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                LoginPersonInfo data = (LoginPersonInfo) object;
                if (data != null) {
                    mRoleId = data.getRoleId();
                    SPUtils.putObject("loginPersonInfo", data);
                    SPUtils.putString("roleId", mRoleId);
                }
                showContentView();
                setupViewPager(viewPager);
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
                setupViewPager(viewPager);
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new JzHomeFragment());
        //0.普通会员 1.高级合伙人 2.解债师
        if (mRoleId.equals("0") || mRoleId.equals("1")) {
            mFragmentList.add(new JzManageFragment());
        }else if(mRoleId.equals("2")) {
            mFragmentList.add(JzKuJzsFragment.newInstance("0",""));
        }
        mFragmentList.add(new JzPropertyFragment());
        mFragmentList.add(new JzMyFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
        //设置ViewPage最大缓存的页面个数（cpu消耗少）
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);
        adapter.notifyDataSetChanged();
    }
}
