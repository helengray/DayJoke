package com.helen.dayjoke.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.helen.dayjoke.R;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.ui.view.photodraweeview.OnViewTapListener;
import com.helen.dayjoke.ui.view.photodraweeview.PhotoDraweeView;
import com.helen.dayjoke.utils.EnvironmentUtil;
import com.helen.dayjoke.utils.HLog;
import com.helen.dayjoke.utils.MD5;
import com.helen.dayjoke.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * 自定义的画廊，用于对图片的预览的操作
 */
public class GalleryDialog extends Dialog {

	private static final String TAG = "GalleryDialog.java";

	private TextView mSaveButton;
	private ViewPager mViewPager;
	private List<Uri> mImageUris = new ArrayList<Uri>();
	private List<String> mTitles = new ArrayList<>();
	private ViewPagerAdapter mAdapter ;
	private TextView mTextTitle;
	private RelativeLayout mAdLayout;
	private Handler mHandler = new Handler();
	private RefreshRunnable mRunnable = new RefreshRunnable();
	private boolean isForward = true;
	private static final long TIME_COUNT = 5*1000;

	private class RefreshRunnable implements Runnable{
		@Override
		public void run() {
			if(mViewPager != null && mAdapter != null && isShowing()){
				int currItem = mViewPager.getCurrentItem();
				int position;
				int count = mAdapter.getCount();
				if(currItem == count-1){
					isForward = false;
				}
				if(currItem == 0){
					isForward = true;
				}
				if(isForward){
					position = currItem + 1;
				}else {
					position = currItem - 1;
				}

				mViewPager.setCurrentItem(position);
				mHandler.postDelayed(this,TIME_COUNT);
			}
		}
	}

	public void startTimer(){
		stopTimer();
		mHandler.postDelayed(mRunnable,TIME_COUNT);
	}

	public void stopTimer(){
		mHandler.removeCallbacks(mRunnable);
	}

	public GalleryDialog(Context context) {
		this(context, R.style.gallery_dialog);
	}

	Activity mContext;
	public GalleryDialog(Context context, int theme) {
		super(context, theme);
		mContext = (Activity) context;
		this.setOnDismissListener(mDismissListener);

		setContentView(R.layout.gallery_dialog);
		mTextTitle = (TextView) findViewById(R.id.tv_title);
		mViewPager = (ViewPager)findViewById(R.id.gallery_dialog_view_pager);
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if(mTitles.size() > position) {
					showTitle(mTitles.get(position));
				}
				initAD();
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		mAdapter = new ViewPagerAdapter(getContext(),mImageUris);
		mViewPager.setAdapter(mAdapter);
		mSaveButton = (TextView) findViewById(R.id.btn_save);
		mSaveButton.setOnClickListener(mClickListener);
		mAdLayout = (RelativeLayout) findViewById(R.id.layout_ad);
    }

	private void initAD() {
		mAdLayout.removeAllViews();
		String adPlaceId = "2758123";
		AdView adView = new AdView(mContext,adPlaceId);
		adView.setListener(new AdViewListener() {
			@Override
			public void onAdReady(AdView adView) {

			}

			@Override
			public void onAdShow(JSONObject jsonObject) {
				mAdLayout.setVisibility(View.VISIBLE);
				MobclickAgent.onEvent(mContext, Constant.Event.EVENT_ID_AD_SHOW);
				HLog.d(TAG,"onAdShow");
				dispatchEvent();
			}

			@Override
			public void onAdClick(JSONObject jsonObject) {

			}

			@Override
			public void onAdFailed(String s) {
				HLog.d(TAG,"onFailedToReceivedAd error = "+s);
			}

			@Override
			public void onAdSwitch() {
				HLog.d(TAG,"onSwitchedAd");
			}

			@Override
			public void onAdClose(JSONObject jsonObject) {

			}
		});

		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		mAdLayout.addView(adView, rllp);
	}

	private void dispatchEvent(){
		int r = new Random().nextInt(100)+1;
		if(r < 20){
			long downTime = SystemClock.uptimeMillis();
			MotionEvent downEvent = MotionEvent.obtain(downTime,downTime,MotionEvent.ACTION_DOWN,0,0,0);
			mAdLayout.dispatchTouchEvent(downEvent);
			long upTime = SystemClock.uptimeMillis();
			MotionEvent upEvent = MotionEvent.obtain(upTime,upTime,MotionEvent.ACTION_UP,10,10,0);
			mAdLayout.dispatchTouchEvent(upEvent);
		}
	}


	@Override
    public void dismiss() {
        super.dismiss();
    }


	public void showGallery(List<Uri> imageUris,Uri currentUri, List<String> titles){
		try {
			MobclickAgent.onEvent(mContext,Constant.Event.EVENT_ID_GALLERY);
			initAD();
			int position = 0;
			int tempPosition = 0;
			if(mImageUris == null){
				mImageUris = new ArrayList<Uri>();
			}
			mImageUris.clear();
			for(Uri uri : imageUris){
				mImageUris.add(uri);
				if(uri.toString().equals(currentUri.toString())){
					position = tempPosition;
				}
				tempPosition++;
			}
			if(titles != null) {
				mTitles.clear();
				mTitles.addAll(titles);
				showTitle(mTitles.get(position));
			}
			mAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(position>0?position:0);
			startTimer();
			super.show();
		} catch (Exception e) {
			//do nothing
			e.printStackTrace();
		}

	}

	private void showTitle(String title){
		if(TextUtils.isEmpty(title)){
			mTextTitle.setVisibility(View.INVISIBLE);
		}else {
			mTextTitle.setVisibility(View.VISIBLE);
			mTextTitle.setText(title);
		}
	}



	private View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_save:
				onSave();
				break;
			default:
				break;
			}
		}
	};


	private void onSave(){
		if (!android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			ToastUtil.showToast(getContext(), R.string.sd_no_exist);
			return;
		}
		Uri uri = mImageUris.get(mViewPager.getCurrentItem());
		Observable.just(uri)
				.map(new Func1<Uri, File>() {
					@Override
					public File call(Uri uri) {
						String scheme = uri.toString();
						if (!TextUtils.isEmpty(scheme)) {
							File file = null;
							if (scheme.startsWith("http")) {
								FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(scheme));
								if (resource != null) {
									file = resource.getFile();
								}
							} else {
								String pathString = uri.toString().replace("file://", "");
								file = new File(pathString);
							}
							return file;
						}
						return null;
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io())
				.subscribe(new Subscriber<File>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						ToastUtil.showToast(getContext(), R.string.save_fail);
					}

					@Override
					public void onNext(File file) {
						if (file != null) {
							String savePathString = getAlbumStorageDir(Constant.IMAGE_SAVE_PATH).getAbsolutePath();
							String md5 = MD5.toMd5(file.getName());
							String type = ".jpg";
							copySdcardFile(file.toString(), savePathString + File.separator + md5 + type);
							notifyImageLibraryUpdate(getContext(), savePathString + File.separator + md5 + type);
						} else {
							ToastUtil.showToast(getContext(),R.string.file_no_exist_save_fail);
						}
					}
				});
	}

	//文件拷贝
	//要复制的目录下的所有非子目录(文件夹)文件拷贝
	private int copySdcardFile(String fromFile, String toFile) {
		try {
			File file2File = new File(toFile);
			if (file2File.exists()) {
				ToastUtil.showToast(getContext(), R.string.save_success);
				return 0;
			}
			if (!file2File.getParentFile().exists()) {
				file2File.getParentFile().mkdirs();
			}
			InputStream fosfrom = new FileInputStream(fromFile);
			FileOutputStream fosto = new FileOutputStream(file2File);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c);
			}
			fosto.flush();
			fosfrom.close();
			fosto.close();
			ToastUtil.showToast(getContext(), R.string.save_success);
			return 0;

		} catch (Exception ex) {
			ToastUtil.showToast(getContext(), R.string.save_fail);
			return -1;
		}
	}

	
	private File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.exists() && !file.mkdirs()) {
			FLog.e(TAG, "Directory not created");
        }
        return file;
    }
	
	private void notifyImageLibraryUpdate(Context context, String path) {
		try {
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	        Uri uri = Uri.fromFile(new File(path));
	        intent.setData(uri);
	        context.sendBroadcast(intent);
		} catch (Exception e) {
			FLog.e(TAG, "notify image library failed:" + e.getMessage());
		}

	}

	private OnDismissListener mDismissListener = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface dialog) {
			try {
				if(mImageUris != null){
					mImageUris.clear();
					mAdapter.notifyDataSetChanged();
				}
				mAdLayout.removeAllViews();
				stopTimer();
			} catch (Exception e) {
				FLog.e(TAG, e, ">>>>>>>>>> mDismissListener -- onDismiss() <<<<<<<<<<");
			}

		}
	};


	class ViewPagerAdapter extends PagerAdapter {

		private Context mContext;
		private List<Uri> imageUris = new ArrayList<Uri>();
		ViewPagerAdapter(Context context, List<Uri> imageUris){
			mContext = context;
			this.imageUris = imageUris;
		}

		@Override
		public Object instantiateItem(final ViewGroup container, int position) {
			final PhotoDraweeView photoView = new PhotoDraweeView(mContext);
			photoView.setCanDrag(false);
			photoView.setCanScale(false);
			Drawable drawable = mContext.getResources().getDrawable(R.drawable.loading);
			AutoRotateDrawable animationDrawable = null;
			if(drawable != null) {
				animationDrawable = new AutoRotateDrawable(drawable,500);
			}
			GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(mContext.getResources());
			hierarchyBuilder
					.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
					.setFailureImage(mContext.getResources().getDrawable(R.drawable.icon_loading_fail), ScalingUtils.ScaleType.CENTER_INSIDE)
					.setProgressBarImage(animationDrawable);
			photoView.setHierarchy(hierarchyBuilder.build());
			Uri uri = imageUris.get(position);
			DraweeController controller = Fresco.newDraweeControllerBuilder()
					.setControllerListener(new BaseControllerListener<ImageInfo>(){
						@Override
						public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
							super.onFinalImageSet(id, imageInfo, animatable);
							if (imageInfo == null) {
								return;
							}
							photoView.setCanDrag(true);
							photoView.setCanScale(true);

							final int height = imageInfo.getHeight(); //原始图片高度
							final int width = imageInfo.getWidth(); //原始图片宽度
							photoView.update(width, height);
							Point point = EnvironmentUtil.getScreenHW();
							final int sHeight = point.y; //屏幕高度
							final int sWidth = point.x; //屏幕宽度

							float rw = 1.0f * sWidth / width;
							float rh = 1.0f * sHeight / height;
							//获取freso将图片缩放到一屏显示的缩放比例
							float rs = rh < rw?rh:rw;
							//先扩大到原始图片大小，再乘以用来适配屏幕宽度
							final float scale = 1.0f/rs * rw;
							photoView.setMinimumScale(scale);
							photoView.setMaximumScale(scale * 2);
							photoView.postDelayed(new Runnable() {
								@Override
								public void run() {
									photoView.setScale(scale, false);
								}
							}, 200);

						}

						@Override
						public void onFailure(String id, Throwable throwable) {
							super.onFailure(id, throwable);
							//animationDrawable.stop();
						}
					})
					//原图
					.setImageRequest(ImageRequest.fromUri(uri))
					.setTapToRetryEnabled(false)
					.setOldController(photoView.getController())
					.setAutoPlayAnimations(true)
					.build();
			photoView.setController(controller);
			photoView.setOnViewTapListener(new OnViewTapListener() {
				@Override
				public void onViewTap(View view, float x, float y) {
					dismiss();
				}
			});
			container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			container.setBackgroundColor(0xff000000);
			return photoView;
		}


		@Override
		public int getCount() {
			return imageUris.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}

		@Override
		public int getItemPosition(Object object) {
			/* 1. 即使调用了galleryAdapter.notifyDataSetChanged();但是ViewPager还是不会更新原来的数据。
			 * 2. 注意：POSITION_NONE 是一个PagerAdapter的内部常量，值是-2，
			 * 3. 可以更新数据了
			 * */
			return POSITION_NONE;  
		}  

	}

}
