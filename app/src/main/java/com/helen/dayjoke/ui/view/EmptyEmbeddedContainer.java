package com.helen.dayjoke.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 数据加载界面
 * 将EmptyEmbeddedContainer作为一个ViewGroup，然后嵌套一个子View作为想刷新的数据
 * <将EmptyEmbeddedContainer作为一个ViewGroup
 *  	android:layout_width=""
 *  	android:layout_height=""
 *  	>
 *  	<View />  子View(有且只能有一个)
 *  </EmptyEmbeddedContainer>
 *
 *
 * 根据要显示的状态去调用EmptyEmbeddedContainer的setType方法
 * EmptyStyle_LOADING 数据正在加载
 * EmptyStyle_RETRY   重新加载数据
 * EmptyStyle_NODATA  加载结束没有数据
 * EmptyStyle_NORMAL  加载结束数据正常
 * EmptyStyle_LOADING_WITH_VIEW 继续加载新数据
 */
public class EmptyEmbeddedContainer extends FrameLayout {

	public static final int CHILD_COUNT = 2;

	private EmptyLayout mEmptyLayout;

	private Context mContext;

	private EmptyStyle type;

	public EmptyEmbeddedContainer(Context context) {
		this(context, null);
	}

	public EmptyEmbeddedContainer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EmptyEmbeddedContainer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		initLayout();
	}

	private void initLayout() {
		if (mEmptyLayout == null) {
			mEmptyLayout = new EmptyLayout(mContext);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			addView(mEmptyLayout, params);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int childCount = getChildCount();
		if(childCount != CHILD_COUNT){
			throw new IllegalStateException("EmptyEmbeddedContainer can host only one direct child ");
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		int childCount = getChildCount();
		if(childCount == CHILD_COUNT){
			View view = getChildAt(0);
			//判断EmptyLayout是否是第一个子view，若是，移动到第二个子view，在使用EmptyStyle_LOADING_WITH_VIEW时，保证进度条在最顶端不会被覆盖
			if(view == mEmptyLayout){
				removeViewAt(0);
				addView(mEmptyLayout);
			}
		}
		super.onLayout(changed, left, top, right, bottom);
	}

	public void setType(EmptyStyle type) {
		this.type = type;
		if(null != mEmptyLayout){
			mEmptyLayout.setType(type);
		}
	}

	public EmptyStyle getType(){
		return type;
	}


	public void setNoDataDefault(String string) {
		if(null != mEmptyLayout){
			mEmptyLayout.setNoDataDefault(string);
		}
	}

	public void setNoDataDefault(int drawable) {
		if(null != mEmptyLayout){
			mEmptyLayout.setNoDataDefault(drawable);
		}
	}

	public void setEmptyLayoutVisibility(int visibility){
		if(null != mEmptyLayout){
			mEmptyLayout.setVisibility(visibility);
		}
	}

	public void setEmptyInterface(EmptyInterface emptyInterface) {
		if(null != mEmptyLayout){
			mEmptyLayout.setEmptyInterface(emptyInterface);
		}
	}

	public interface EmptyInterface {
		void doRetry();
	}

	public enum EmptyStyle {
		EmptyStyle_LOADING, EmptyStyle_RETRY, EmptyStyle_NODATA, EmptyStyle_NORMAL, EmptyStyle_LOADING_WITH_VIEW
	}
}
