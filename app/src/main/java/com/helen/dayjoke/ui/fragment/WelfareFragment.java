package com.helen.dayjoke.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helen.dayjoke.R;
import com.helen.dayjoke.api.APIManager;
import com.helen.dayjoke.api.APIService;
import com.helen.dayjoke.entity.Mito;
import com.helen.dayjoke.ui.adapter.WelfareAdapter;
import com.helen.dayjoke.ui.view.EmptyEmbeddedContainer;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Helen on 2016/5/24.
 *
 */
public class WelfareFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,EmptyEmbeddedContainer.EmptyInterface{
    private List<Mito> mMitos = new ArrayList<>();
    private WelfareAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private EmptyEmbeddedContainer mEmptyContainer;
    private int page = 1;
    private int type = 0;

    public static WelfareFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        WelfareFragment fragment = new WelfareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main,container,false);
        initView(view);
        initAPI();
        type = getArguments().getInt("type",0);
        page = 1;
        requestData();
        return view;
    }

    private void initView(View view){
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new WelfareAdapter(mMitos,type);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem = -1;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1 == mAdapter.getItemCount()){
                    page += 1;
                    requestData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] lastItem = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
                lastVisibleItem = getMaxPosition(lastItem);
            }
        });

        mEmptyContainer = (EmptyEmbeddedContainer) view.findViewById(R.id.empty_container);
        mEmptyContainer.setEmptyInterface(this);
        mEmptyContainer.setType(EmptyEmbeddedContainer.EmptyStyle.EmptyStyle_LOADING_WITH_VIEW);

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        //转圈颜色
        mRefreshLayout.setColorSchemeResources(R.color.color_refresh_loading);
        //背景
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_refresh_progress);
        mRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }


    private APIService apiService;
    private Subscriber<List<Mito>> subscriber;

    private void initAPI() {
        apiService = APIManager.getInstance().getAPIService();
    }

    private void requestData() {
        if(subscriber == null || subscriber.isUnsubscribed()) {
            subscriber = new Subscriber<List<Mito>>() {
                @Override
                public void onCompleted() {
                    mRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(Throwable e) {
                    mRefreshLayout.setRefreshing(false);
                    if (mMitos.isEmpty()) {
                        mRefreshLayout.setEnabled(false);
                        mEmptyContainer.setType(EmptyEmbeddedContainer.EmptyStyle.EmptyStyle_RETRY);
                    }
                    if (page > 1) {
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onNext(final List<Mito> mitos) {
                    mRefreshLayout.setEnabled(true);
                    mEmptyContainer.setType(EmptyEmbeddedContainer.EmptyStyle.EmptyStyle_NORMAL);
                    if (mitos != null) {
                        if (page == 1) {
                            mMitos.clear();
                        }
                        mMitos.addAll(mitos);
                        mAdapter.notifyDataSetChanged();
                    }
                    if (mMitos.isEmpty()) {
                        mEmptyContainer.setType(EmptyEmbeddedContainer.EmptyStyle.EmptyStyle_NODATA);
                    }
                }
            };
        }
        if(type != 0) {
            apiService.getMito("Content", "Index", "gengduolist", page, type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(subscriber);
        }else {
            apiService.getMitoRandom("Content","Index","suiji",page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(subscriber);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscriber != null && !subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter.getGalleryDialog() != null){
            mAdapter.getGalleryDialog().startTimer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mAdapter.getGalleryDialog() != null){
            mAdapter.getGalleryDialog().stopTimer();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        requestData();
    }

    @Override
    public void doRetry() {
        mEmptyContainer.setType(EmptyEmbeddedContainer.EmptyStyle.EmptyStyle_LOADING);
        requestData();
    }
}
