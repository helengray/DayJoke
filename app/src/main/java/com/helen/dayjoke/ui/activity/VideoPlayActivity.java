package com.helen.dayjoke.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.helen.dayjoke.R;
import com.helen.dayjoke.ui.application.DJApplication;
import com.helen.dayjoke.utils.EnvironmentUtil;
import com.helen.dayjoke.utils.HLog;
import com.helen.dayjoke.utils.TimeUtil;

import th.ds.wa.normal.banner.AdViewListener;
import th.ds.wa.normal.banner.BannerManager;


/**
 * 视频播放
 */
public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener,
		MediaPlayer.OnInfoListener,MediaPlayer.OnBufferingUpdateListener,View.OnClickListener{
	public static final String TAG_STARY = "start";
	public static final String TAG_PAUSE = "pause";
	private static final String TAG = "GalleryDialog.java";
	private Activity mContext;
	private RelativeLayout mAdLayout;
	private VideoView mVideoView;
	private SimpleDraweeView mDraweeView;
	private ImageView mImageViewVideoPlay;
	private ProgressBar mProgressBar;
	private TextView mTextViewCurrentProgress;
	private TextView mTextViewTotal;
	private ImageView mBtnMenu;
	private SeekBar mSeekBar;
	private Handler mHandlerRefresh;
	private RefreshRunnable mRefreshRunnable;

	private String videoUrl;
	private String picUrl;

	public static void launcher(Context context,String highUrl,String lowUrl,String picUrl){
		Intent intent = new Intent(context,VideoPlayActivity.class);
		intent.putExtra("videoHigh",highUrl);
		intent.putExtra("videoLow",lowUrl);
		intent.putExtra("pic",picUrl);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_dialog);
		init();
	}

	private void init() {
		mContext = this;
		Intent intent = getIntent();
		picUrl = intent.getStringExtra("pic");

		boolean isWifi = EnvironmentUtil.isWIFIConnected();
		String highUrl = intent.getStringExtra("videoHigh");
		String lowUrl = intent.getStringExtra("videoLow");
		if(isWifi){
			videoUrl = TextUtils.isEmpty(highUrl)?lowUrl:highUrl;
		}else {
			videoUrl = TextUtils.isEmpty(lowUrl)?highUrl:lowUrl;
		}

		mHandlerRefresh = new Handler();
		mRefreshRunnable = new RefreshRunnable();
		mAdLayout = (RelativeLayout) findViewById(R.id.layout_ad);
		mVideoView = (VideoView) findViewById(R.id.video_view);
		mTextViewCurrentProgress = (TextView) findViewById(R.id.tv_progress);
		mTextViewTotal = (TextView) findViewById(R.id.tv_total);
		mBtnMenu = (ImageView) findViewById(R.id.iv_menu);
		mBtnMenu.setOnClickListener(this);
		mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
		mSeekBar.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

		//mVideoView.setZOrderOnTop(true);
		mVideoView.setOnPreparedListener(this);
		mVideoView.setOnErrorListener(this);
		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){
					mBtnMenu.performClick();
				}
				return true;
			}
		});
		mDraweeView = (SimpleDraweeView) findViewById(R.id.pic);
		mImageViewVideoPlay = (ImageView) findViewById(R.id.iv_video);

		show();
	}

	private void show(){
		initAD();
		mDraweeView.setImageURI(picUrl);
		mDraweeView.setVisibility(View.VISIBLE);
		mImageViewVideoPlay.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
		setMenuStartStatus();
		mVideoView.setVideoURI(Uri.parse(videoUrl));
		mVideoView.start();
	}

	@Override
	protected void onDestroy() {
		try {
			mVideoView.stopPlayback();
			mAdLayout.removeAllViews();
			if(mRefreshRunnable != null){
				mHandlerRefresh.removeCallbacks(mRefreshRunnable);
			}
		} catch (Exception e) {
			FLog.e(TAG, e, ">>>>>>>>>> mDismissListener -- onDismiss() <<<<<<<<<<");
		}
		super.onDestroy();
	}

	class RefreshRunnable implements Runnable{

		@Override
		public void run() {
			changeProgress();
			mHandlerRefresh.postDelayed(this,1000);
		}
	}

	private void initAD() {
		View adView = BannerManager.getInstance(DJApplication.getInstance()).getBanner(this);
		if(adView != null) {
			BannerManager.getInstance(DJApplication.getInstance()).setAdListener(new AdViewListener() {
				@Override
				public void onReceivedAd() {
					HLog.d(TAG,"onReceivedAd");
				}

				@Override
				public void onSwitchedAd() {
					HLog.d(TAG,"onSwitchedAd");
				}

				@Override
				public void onFailedToReceivedAd() {
					HLog.d(TAG,"onFailedToReceivedAd");
				}
			});
			RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			mAdLayout.addView(adView, rllp);
		}
	}


	@Override
	public void onPrepared(MediaPlayer mp) {
		HLog.d(TAG,"onPrepared");
		mp.setOnInfoListener(this);
		mp.setOnBufferingUpdateListener(this);
		onStartPlay();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		HLog.d(TAG,"onError---what="+what+",extra="+extra);
		mDraweeView.setVisibility(View.VISIBLE);
		mImageViewVideoPlay.setVisibility(View.VISIBLE);
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		HLog.d(TAG,"onCompletion");
		onStopPlay();
	}

	/**
	 * 暂停播放
	 */
	private void onPausePlay(){
		mVideoView.pause();
		mImageViewVideoPlay.setVisibility(View.VISIBLE);
		mHandlerRefresh.removeCallbacks(mRefreshRunnable);
	}

	/**
	 * 重新开始播放
	 */
	private void onResumePlay(){
		mVideoView.start();
		mImageViewVideoPlay.setVisibility(View.GONE);
		mHandlerRefresh.post(mRefreshRunnable);
	}

	/**
	 * 停止播放
	 */
	private void onStopPlay(){
		mImageViewVideoPlay.setVisibility(View.VISIBLE);
		int max = mVideoView.getDuration();
		mTextViewCurrentProgress.setText(TimeUtil.formatHHMMSS(max));
		setMenuPauseStatus();
		mHandlerRefresh.removeCallbacks(mRefreshRunnable);
	}

	/**
	 * 第一次开始播放
	 */
	private void onStartPlay(){
		mDraweeView.setVisibility(View.GONE);
		mImageViewVideoPlay.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		int max = mVideoView.getDuration();
		mTextViewTotal.setText(TimeUtil.formatHHMMSS(max));
		mSeekBar.setMax(max);
		//changeProgress();
		mHandlerRefresh.post(mRefreshRunnable);
	}

	/**
	 * 更改进度条
	 */
	private void changeProgress(){
		int progress = mVideoView.getCurrentPosition();
		mTextViewCurrentProgress.setText(TimeUtil.formatHHMMSS(progress));
		mSeekBar.setProgress(progress);
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		HLog.d(TAG,"onInfo---what="+what+",extra="+extra);
		if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
			//
		}
		return false;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		mSeekBar.setSecondaryProgress(percent);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.iv_menu){
			String tag = (String) v.getTag();
			if(tag.equals(TAG_STARY)){
				setMenuPauseStatus();
				onPausePlay();
			}else if(tag.equals(TAG_PAUSE)){
				setMenuStartStatus();
				onResumePlay();
			}
		}
	}

	private void setMenuPauseStatus(){
		mBtnMenu.setTag(TAG_PAUSE);
		mBtnMenu.setImageResource(android.R.drawable.ic_media_play);
	}

	private void setMenuStartStatus(){
		mBtnMenu.setTag(TAG_STARY);
		mBtnMenu.setImageResource(android.R.drawable.ic_media_pause);
	}
}

