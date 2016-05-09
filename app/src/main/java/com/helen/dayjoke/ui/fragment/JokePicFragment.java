package com.helen.dayjoke.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helen.dayjoke.R;
import com.helen.dayjoke.api.APIManager;
import com.helen.dayjoke.api.APIService;
import com.helen.dayjoke.entity.JokeEn;
import com.helen.dayjoke.entity.ResponseBodyEn;
import com.helen.dayjoke.entity.ResponseEn;
import com.helen.dayjoke.ui.adapter.JokePicAdapter;
import com.helen.dayjoke.ui.adapter.JokeTextAdapter;
import com.helen.dayjoke.ui.view.DividerItemDecoration;
import com.helen.dayjoke.ui.view.EmptyEmbeddedContainer;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 李晓伟 on 2016/4/29.
 *
 */
public class JokePicFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,EmptyEmbeddedContainer.EmptyInterface{
    private List<JokeEn> mJokeEnList = new ArrayList<>();
    private JokePicAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private EmptyEmbeddedContainer mEmptyContainer;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main,container,false);
        initView(view);
        initAPI();
        page = 1;
        requestData();
        return view;
    }

    private void initView(View view){
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mAdapter = new JokePicAdapter(mJokeEnList);
        mAdapter.setEmptyInterface(this);
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
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
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


    private APIService apiService;
    private Subscriber<List<JokeEn>> subscriber;

    private void initAPI() {
        apiService = APIManager.getInstance().getAPIService();
    }

    private void requestData() {
        subscriber = new Subscriber<List<JokeEn>>() {
            @Override
            public void onCompleted() {
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mRefreshLayout.setRefreshing(false);
                if(mJokeEnList.isEmpty()){
                    mRefreshLayout.setEnabled(false);
                    mEmptyContainer.setType(EmptyEmbeddedContainer.EmptyStyle.EmptyStyle_RETRY);
                }
                if(page > 1){
                    mAdapter.notifyDataSetChanged(JokeTextAdapter.STATUS_LOAD_FAIL);
                }
            }

            @Override
            public void onNext(final List<JokeEn> jokeEns) {
                mRefreshLayout.setEnabled(true);
                mEmptyContainer.setType(EmptyEmbeddedContainer.EmptyStyle.EmptyStyle_NORMAL);
                if(jokeEns != null){
                    if(page == 1){
                        mJokeEnList.clear();
                    }
                    mJokeEnList.addAll(jokeEns);
                    mAdapter.notifyDataSetChanged(JokeTextAdapter.STATUS_NORMAL);
                }
                if(mJokeEnList.isEmpty()){
                    mEmptyContainer.setType(EmptyEmbeddedContainer.EmptyStyle.EmptyStyle_NODATA);
                }
            }
        };
        apiService.getPicJoke(String.valueOf(page))
                .map(new Func1<ResponseEn, List<JokeEn>>() {
                    @Override
                    public List<JokeEn> call(ResponseEn responseEn) {
                        if(responseEn != null){
                            ResponseBodyEn bodyEn = responseEn.getData();
                            if(bodyEn != null){
                                return bodyEn.getJokeEnList();
                            }
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscriber != null && !subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
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
