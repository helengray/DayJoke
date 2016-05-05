package com.helen.dayjoke.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.helen.dayjoke.R;
import com.helen.dayjoke.api.APIManager;
import com.helen.dayjoke.api.APIService;
import com.helen.dayjoke.entity.ConstellationEn;
import com.helen.dayjoke.entity.constellation.Constellation;
import com.helen.dayjoke.ui.adapter.JokePagerAdapter;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.ui.fragment.JokePicFragment;
import com.helen.dayjoke.ui.fragment.JokeTextFragment;
import com.helen.dayjoke.utils.SPUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private TextView mTextConsName;
    private TextView mTextConsDate;
    private ImageView mImgCons;


    private TextView mTextViewSummary;
    private RatingBar mRatingBarComposite;
    private TextView mTextViewHealth;
    private RatingBar mRatingBarLove;
    private TextView mTextViewLuckyColor;
    private RatingBar mRatingBarWork;
    private TextView mTextViewLuckyNum;
    private RatingBar mRatingBarWealth;
    private TextView mTextViewMatchFriends;

    private int mConsIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        initJokeView();
        initConstellationView();
    }

    /**
     * 初始化首页笑话
     */
    private void initJokeView(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.joke_view_pager);
        JokePagerAdapter pagerAdapter = new JokePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new JokeTextFragment(),getString(R.string.joke_text));
        pagerAdapter.addFragment(new JokePicFragment(),getString(R.string.joke_pic));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    /**
     * 初始化抽屉栏-星座
     */
    private void initConstellationView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        mTextConsName = (TextView) view.findViewById(R.id.text_cons_name);
        mTextConsDate = (TextView) view.findViewById(R.id.text_cons_date);
        mImgCons = (ImageView) view.findViewById(R.id.img_cons);

        mTextViewSummary = (TextView) view.findViewById(R.id.text_cons_summary);
        mRatingBarComposite = (RatingBar) view.findViewById(R.id.rating_composite_index);
        mTextViewHealth = (TextView) view.findViewById(R.id.text_health_index);
        mRatingBarLove = (RatingBar) view.findViewById(R.id.rating_love_index);
        mTextViewLuckyColor = (TextView) view.findViewById(R.id.text_lucky_color);
        mRatingBarWork = (RatingBar) view.findViewById(R.id.rating_work_index);
        mTextViewLuckyNum = (TextView) view.findViewById(R.id.text_lucky_num);
        mRatingBarWealth = (RatingBar) view.findViewById(R.id.rating_wealth_index);
        mTextViewMatchFriends = (TextView) view.findViewById(R.id.text_match_friends);

        mConsIndex = SPUtil.getInstance().getInt(Constant.KEY_CONSTELLATION_INDEX,0);
        initConsData(Constellation.CONSTELLATIONS[mConsIndex]);
    }

    private APIService mAPIService;
    private Subscriber<ConstellationEn> mSubscriber;

    private void initConsData(final Constellation constellation) {
        mTextConsName.setText(constellation.getName());
        mTextConsDate.setText(constellation.getDate());
        mImgCons.setImageResource(constellation.getResId());
        if(mSubscriber == null || mSubscriber.isUnsubscribed()){
            mSubscriber = new Subscriber<ConstellationEn>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ConstellationEn constellationEn) {
                    mTextViewSummary.setText(constellationEn.getSummary());
                    mRatingBarComposite.setRating(constellationEn.getAllFormat());
                    mTextViewHealth.setText(constellationEn.getHealth());
                    mRatingBarLove.setRating(constellationEn.getLoveFormat());
                    mTextViewLuckyColor.setText(constellationEn.getColor());
                    mRatingBarWork.setRating(constellationEn.getWorkFormat());
                    mTextViewLuckyNum.setText(constellationEn.getNumber());
                    mRatingBarWealth.setRating(constellationEn.getMoneyFormat());
                    mTextViewMatchFriends.setText(constellationEn.getQFriend());
                }
            };
        }
        mAPIService = APIManager.getInstance().getAPIService();
        mAPIService.getConstellation(constellation.getName(), ConstellationEn.TYPE_TODAY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    @Override
    protected void onDestroy() {
        if(mSubscriber != null && !mSubscriber.isUnsubscribed()){
            mSubscriber.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            quit();
        }
    }

    /**
     * 退出应用
     */
    private long exitTime = 0;
    public void quit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), R.string.quit, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_about){
            //
        }else if(id == R.id.nav_setting){
            SettingActivity.launcher(this, Constant.REQ_CODE_CONS_CHANGE);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == Constant.REQ_CODE_CONS_CHANGE){
                int index = SPUtil.getInstance().getInt(Constant.KEY_CONSTELLATION_INDEX,0);
                if(index != mConsIndex){
                    mConsIndex = index;
                    initConsData(Constellation.CONSTELLATIONS[index]);
                }
            }
        }
    }
}
