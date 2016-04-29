package com.helen.dayjoke.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.helen.dayjoke.R;

public class EmptyLayout extends LinearLayout {

    /**
     * 和EmptyLayout同级的Layout
     */
    private View mPeerView = null;
    private View mEmptyLayout = null;


    private ProgressBar mProgressBar = null;

    private TextView mNoDataTextView = null;


    private EmptyEmbeddedContainer.EmptyInterface mEmptyInterface = null;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEmptyLayout();
    }

    private void getPeerView(){
        EmptyEmbeddedContainer parent = (EmptyEmbeddedContainer) this.getParent();
        if(parent.getChildCount() != EmptyEmbeddedContainer.CHILD_COUNT){
            throw new IllegalStateException("EmptyEmbeddedContainer can host only one direct child ");
        }
        mPeerView = parent.getChildAt(1);
    }

    private void initEmptyLayout(){
        mEmptyLayout = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, this,false);
        mEmptyLayout.setVisibility(GONE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mEmptyLayout, params);
        mProgressBar = (ProgressBar) mEmptyLayout.findViewById(R.id.empty_loading_pb);
        mNoDataTextView = (TextView) mEmptyLayout.findViewById(R.id.img_error);
        mEmptyLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mEmptyInterface) {
                    mEmptyInterface.doRetry();
                }
            }
        });
    }

    public void setEmptyInterface(EmptyEmbeddedContainer.EmptyInterface emptyInterface) {
        this.mEmptyInterface = emptyInterface;
    }

    public void setType(EmptyEmbeddedContainer.EmptyStyle type) {
        if(null == mPeerView){
            getPeerView();
        }
        if (mEmptyLayout.getVisibility() != VISIBLE){
            mEmptyLayout.setVisibility(VISIBLE);
        }
        mPeerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mNoDataTextView.setVisibility(View.GONE);
        switch (type) {
            case EmptyStyle_LOADING:
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case EmptyStyle_RETRY:
                mNoDataTextView.setVisibility(View.VISIBLE);
                mNoDataTextView.setText(R.string.error);
                AnimationDrawable anim = (AnimationDrawable) mNoDataTextView.getCompoundDrawables()[1];
                if(anim != null){
                    anim.start();
                }
                break;
            case EmptyStyle_NODATA:
                mNoDataTextView.setVisibility(View.VISIBLE);
                mNoDataTextView.setText(R.string.no_data);
                anim = (AnimationDrawable) mNoDataTextView.getCompoundDrawables()[1];
                if(anim != null){
                    anim.start();
                }
                break;
            case EmptyStyle_NORMAL:
                if (mPeerView != null) {
                    mPeerView.setVisibility(View.VISIBLE);
                }
                mEmptyLayout.setVisibility(GONE);
                break;
            case EmptyStyle_LOADING_WITH_VIEW:
                mProgressBar.setVisibility(View.VISIBLE);
                if (mPeerView != null) {
                    mPeerView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    public void setNoDataDefault(String string) {
        mNoDataTextView.setText(string);
    }

    public void setNoDataDefault(int drawable) {
        mNoDataTextView.setText("");
        mNoDataTextView.setCompoundDrawablesWithIntrinsicBounds(0, drawable, 0, 0);
    }
}
