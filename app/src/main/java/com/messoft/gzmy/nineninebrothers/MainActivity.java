package com.messoft.gzmy.nineninebrothers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.messoft.gzmy.nineninebrothers.adapter.MyFragmentPagerAdapter;
import com.messoft.gzmy.nineninebrothers.app.MyApplication;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityMainBinding;
import com.messoft.gzmy.nineninebrothers.ui.home.HomeFragment;
import com.messoft.gzmy.nineninebrothers.ui.my.MyFragment;
import com.messoft.gzmy.nineninebrothers.ui.news.NewsFragment;
import com.messoft.gzmy.nineninebrothers.utils.StatusBarUtil;
import com.messoft.gzmy.nineninebrothers.view.BottomNavigationViewHelper;

import java.util.ArrayList;

/**
 * @作者 Administrator
 * MainActivity
 * @创建日期 2017/10/12 0012 16:33
 */

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showContentView();
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
                            case R.id.item_find:
                                viewPager.setCurrentItem(2);
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

                switch (position) {
                    case 0:
                    case 1:
                        break;
                    case 2:

                        break;
                    default:

                        break;
                }
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

        setupViewPager(viewPager);

    }


    @Override
    protected void setStatusBar() {
//        StatusBarUtil.setTranslucent(UseInFragmentActivity.this, 0);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this,10, null);
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    private void setupViewPager(ViewPager viewPager) {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new NewsFragment());
        mFragmentList.add(new MyFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
        //设置ViewPage最大缓存的页面个数（cpu消耗少）
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        adapter.notifyDataSetChanged();
    }

//    @Override
//    protected void setStatusBar() {
//        int mColor = getResources().getColor(R.color.colorPrimary);
//        StatusBarUtil.setColor(this, mColor, 0);
//    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 再按一次退出
     */
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出" + getResources().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyApplication.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
