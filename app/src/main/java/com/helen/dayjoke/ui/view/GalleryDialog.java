package com.helen.dayjoke.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.helen.dayjoke.ui.view.photodraweeview.OnViewTapListener;
import com.helen.dayjoke.ui.view.photodraweeview.PhotoDraweeView;
import com.helen.dayjoke.utils.EnvironmentUtil;
import com.helen.dayjoke.utils.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 自定义的画廊，用于对图片的预览的操作
 */
public class GalleryDialog extends Dialog {

	private static final String TAG = "GalleryDialog.java";

	private TextView mSaveButton;
	private ViewPager mViewPager;
	private List<Uri> mImageUris = new ArrayList<Uri>();
	private ViewPagerAdapter mAdapter ;

	public GalleryDialog(Context context) {
		this(context, R.style.gallery_dialog);
		mContext = context;
	}

	Context mContext;
	public GalleryDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		
		this.setOnDismissListener(mDismissListener);

		OnCancelListener mCancelListener = new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				mImageUris.clear();
				mImageUris = null;
			}
		};
		this.setOnCancelListener(mCancelListener);
		setContentView(R.layout.gallery_dialog);
		

		mViewPager = (ViewPager)findViewById(R.id.gallery_dialog_view_pager);
		mSaveButton = (TextView) findViewById(R.id.btn_save);
		mSaveButton.setOnClickListener(mClickListener);

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


	public void showGallery(Uri currentUri){
		try {
			int position = 0;
			//int tempPosition = 0;
			if(mImageUris == null){
				mImageUris = new ArrayList<Uri>();
			}
			mImageUris.clear();
			mImageUris.add(currentUri);
			/*for(Uri uri : imageUris){
				mImageUris.add(uri);	
				if(uri.toString().equals(currentUri.toString())){
					position = tempPosition;
				}
				tempPosition++;
			}*/
			mAdapter = new ViewPagerAdapter(getContext(),mImageUris);
			mViewPager.setAdapter(mAdapter);
			mViewPager.setEnabled(false);		
			mAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(position>0?position:0);
			super.show();
		} catch (Exception e) {
			FLog.e(TAG, e, ">>>>>>>>>> showGallery() <<<<<<<<<<");
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
			Toast.makeText(getContext(), "SD卡不可用",Toast.LENGTH_SHORT).show();
		} else {
			Uri uri = mImageUris.get(mViewPager.getCurrentItem());
			String scheme = uri.toString();//getScheme();
			if(!TextUtils.isEmpty(scheme)){
				File file = null;
				if (scheme.startsWith("http")) {
					FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(scheme));
					if(resource != null) {
						file = resource.getFile();
					}
				} else {
					String pathString = uri.toString().replace("file://", "");
					file = new File(pathString);
				}
				if(file==null) {
					Toast.makeText(getContext(), "文件不存在，保存失败",Toast.LENGTH_SHORT).show();
					return;
				}
				String savePathString = getAlbumStorageDir("dayjoke").getAbsolutePath();
				String md5 = MD5.toMd5(scheme);
				String type=".jpg";
				copySdcardFile(file.toString(), savePathString + File.separator + md5 + type);
				notifyImageLibraryUpdate(getContext(), savePathString+ File.separator+md5+type);
			}
		}
	}

	//文件拷贝
	//要复制的目录下的所有非子目录(文件夹)文件拷贝
	private int copySdcardFile(String fromFile, String toFile) {
		try {
			File file2File = new File(toFile);
			if (file2File.exists()) {
				Toast.makeText(getContext(), "保存成功",Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getContext(), "保存成功",Toast.LENGTH_SHORT).show();
			return 0;

		} catch (Exception ex) {
			Toast.makeText(getContext(), "保存失败",Toast.LENGTH_SHORT).show();
			return -1;
		}
	}

	
	private File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
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
							/*********************适配图片宽度到屏幕宽度方便用户查看图片****************************/
							float fromRealToScreenW = 1.0f * sWidth / width;
							float fromRealToScreenH = 1.0f * sHeight / height;
							//获取freso将图片缩放到一屏显示的缩放比例
							float fromRealToScreen = fromRealToScreenH < fromRealToScreenW?fromRealToScreenH:fromRealToScreenW;
							//1.0f/fromRealToScreen 用来扩大到原始图片大小，再乘以fromRealToScreenW用来适配屏幕宽度
							final float scale = 1.0f/fromRealToScreen * fromRealToScreenW;
							photoView.setMinimumScale(scale);
							photoView.setMaximumScale(scale * 2);
							photoView.postDelayed(new Runnable() {
								@Override
								public void run() {
									photoView.setScale(scale, false);
								}
							}, 200);
							/*************************************************/
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
