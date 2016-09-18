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
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.helen.dayjoke.BuildConfig;
import com.helen.dayjoke.R;
import com.helen.dayjoke.api.APIManager;
import com.helen.dayjoke.api.APIService;
import com.helen.dayjoke.entity.ConstellationEn;
import com.helen.dayjoke.entity.VersionInfo;
import com.helen.dayjoke.entity.constellation.BombResponseEn;
import com.helen.dayjoke.entity.constellation.Constellation;
import com.helen.dayjoke.ui.adapter.JokePagerAdapter;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.ui.fragment.JokePicFragment;
import com.helen.dayjoke.ui.fragment.JokeTextFragment;
import com.helen.dayjoke.ui.fragment.QiuTuFragment;
import com.helen.dayjoke.ui.fragment.VideoFragment;
import com.helen.dayjoke.ui.fragment.WelfareContentFragment;
import com.helen.dayjoke.ui.service.DownloadService;
import com.helen.dayjoke.ui.view.MaterialDialog;
import com.helen.dayjoke.utils.SPUtil;
import com.helen.dayjoke.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
    APIService APIService ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        updateCheck();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        APIService = APIManager.getInstance().getAPIService();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        boolean isOpenWelfare = SPUtil.getInstance().getBoolean(Constant.KEY_OPEN_WELFARE);
        if(isOpenWelfare){
            initJokeView(true);
        }else {
            initJokeView(false);
            requestConfig();
        }

        initConstellationView();
    }

    private void requestConfig() {
        APIManager.getInstance().getWelfareConfig(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Boolean isOpenWelfare) {
                addWelfare(isOpenWelfare);
                SPUtil.getInstance().putBoolean(Constant.KEY_OPEN_WELFARE,isOpenWelfare).commit();
            }
        });
    }

    /**
     * 初始化首页笑话
     */
    JokePagerAdapter mPagerAdapter;
    private void initJokeView(boolean hasWelfare){
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new JokePagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new JokeTextFragment(),getString(R.string.joke_text));
        mPagerAdapter.addFragment(new VideoFragment(),getString(R.string.video));
        mPagerAdapter.addFragment(new JokePicFragment(),getString(R.string.joke_pic));
        mPagerAdapter.addFragment(new QiuTuFragment(),getString(R.string.qiu_tu));
        if(hasWelfare) {
            mPagerAdapter.addFragment(new WelfareContentFragment(), getString(R.string.action_welfare));
        }
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        MobclickAgent.onEvent(MainActivity.this, Constant.Event.EVENT_ID_TAB_TEXT);
                        break;
                    case 1:
                        MobclickAgent.onEvent(MainActivity.this, Constant.Event.EVENT_ID_TAB_VIDEO);
                        break;
                    case 2:
                        MobclickAgent.onEvent(MainActivity.this, Constant.Event.EVENT_ID_TAB_PIC);
                        break;
                    case 3:
                        MobclickAgent.onEvent(MainActivity.this, Constant.Event.EVENT_ID_TAB_QIU);
                        break;
                    case 4:
                        MobclickAgent.onEvent(MainActivity.this, Constant.Event.EVENT_ID_TAB_WELFARE);
                        break;
                    default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addWelfare(boolean isOpenWelfare){
        if(isOpenWelfare) {
            mPagerAdapter.addFragment(new WelfareContentFragment(), getString(R.string.action_welfare));
            mPagerAdapter.notifyDataSetChanged();
        }
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
        APIService.getConstellation(constellation.getName(), ConstellationEn.TYPE_TODAY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    @Override
    protected void onDestroy() {
        if(mSubscriber != null && !mSubscriber.isUnsubscribed()){
            mSubscriber.unsubscribe();
        }
        if(mVersionInfoSubscriber != null && !mVersionInfoSubscriber.isUnsubscribed()){
            mVersionInfoSubscriber.unsubscribe();
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
            ToastUtil.showToast(this, R.string.quit);
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_about){
            AboutActivity.launcher(this);
        }else if(id == R.id.nav_setting){
            SettingActivity.launcher(this, Constant.REQ_CODE_CONS_CHANGE);
        }else if(id == R.id.nav_feedback){
            FeedBackActivity.launcher(this);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_welfare) {
            openWelfare();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openWelfare() {
        WelfareActivity.launcher(this);
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


    private Subscriber<VersionInfo> mVersionInfoSubscriber;
    private void updateCheck(){
        if(mVersionInfoSubscriber == null || mVersionInfoSubscriber.isUnsubscribed()){
            mVersionInfoSubscriber = new Subscriber<VersionInfo>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(final VersionInfo info) {
                    if(info != null) {
                        String version = BuildConfig.VERSION_NAME;
                        final String newVersion = info.getVersion();
                        if (newVersion.compareTo(version) > 0 && !newVersion.equals(SPUtil.getInstance().getString(Constant.KEY_IGNORE_VERSION))) {
                            final MaterialDialog materialDialog = new MaterialDialog(MainActivity.this);
                            materialDialog.setTitle(getString(R.string.alert));
                            materialDialog.setPositiveButton(getString(R.string.update), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this, DownloadService.class);
                                    intent.putExtra(DownloadService.KEY_URL,info.getApkFile().getUrl());
                                    startService(intent);
                                    materialDialog.dismiss();
                                }
                            });
                            materialDialog.setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    materialDialog.dismiss();
                                }
                            });
                            materialDialog.setNeutralButton(getString(R.string.ignore), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    materialDialog.dismiss();
                                    SPUtil.getInstance().putString(Constant.KEY_IGNORE_VERSION,newVersion).commit();
                                }
                            });
                            materialDialog.setMessage(Html.fromHtml(info.getUpdateContent()));
                            materialDialog.show();
                        }
                    }
                }
            };
        }
        APIService.getVersionInfo(1,"-version")
                .map(new Func1<BombResponseEn<VersionInfo>, List<VersionInfo>>() {
                    @Override
                    public List<VersionInfo> call(BombResponseEn<VersionInfo> responseEn) {
                        return responseEn.results;
                    }
                })
                .map(new Func1<List<VersionInfo>, VersionInfo>() {
                    @Override
                    public VersionInfo call(List<VersionInfo> versionInfos) {
                        if(versionInfos != null && !versionInfos.isEmpty()){
                            return versionInfos.get(0);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mVersionInfoSubscriber);
    }
}
