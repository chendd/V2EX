package com.chendd.v2ex.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chendd.v2ex.R;
import com.chendd.v2ex.base.BaseFragment;
import com.chendd.v2ex.bean.Topic;
import com.chendd.v2ex.bean.TopicList;
import com.chendd.v2ex.network.RetrofitManager;
import com.chendd.v2ex.ui.adapter.AutoLoadOnScrollListener;
import com.chendd.v2ex.ui.adapter.TopicListAdapter;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by laucherish on 16/3/16.
 */
public class TopicListFragment extends BaseFragment implements PullToRefreshView.OnRefreshListener {

    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_SCROLL = "scroll";
    public static final String EXTRA_CURDATE = "curdate";

    @Bind(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @Bind(R.id.tv_load_error)
    TextView mTvLoadError;
    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;
    @Bind(R.id.rcv_news_list)
    RecyclerView mRcvNewsList;
    @Bind(R.id.ptr_news_list)
    PullToRefreshView mPtrNewsList;

    public TopicListAdapter mExtraAdapter;
    private TopicListAdapter mTopicListAdapter;
    private String curDate;
    private AutoLoadOnScrollListener mAutoLoadListener;
    private Snackbar mLoadLatestSnackbar;
    private Snackbar mLoadBeforeSnackbar;
    private OnRecyclerViewCreated mOnRecyclerViewCreated;
    private LinearLayoutManager mLinearLayoutManager;
    private List<Topic> mNewsList;

    private boolean move = false;
    //记录顶部显示的项
    private int position = 0;
    //记录顶部项的偏移
    private int scroll = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        position = getArguments().getInt(EXTRA_POSITION);
        scroll = getArguments().getInt(EXTRA_SCROLL);
        curDate = getArguments().getString(EXTRA_CURDATE);
        init();
        if (mTopicListAdapter.getmTopicList().size() == 0) {
            loadLatestNews();
        }
    }

    public static TopicListFragment newInstance(int position, int scroll, TopicListAdapter adapter, String curDate) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        bundle.putInt(EXTRA_SCROLL, scroll);
        bundle.putString(EXTRA_CURDATE,curDate);
        TopicListFragment fragment = new TopicListFragment();
        fragment.setArguments(bundle);
        fragment.mExtraAdapter = adapter;
        return fragment;
    }

    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        mPtrNewsList.setOnRefreshListener(this);

        //配置RecyclerView
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRcvNewsList.setLayoutManager(mLinearLayoutManager);
        mRcvNewsList.setHasFixedSize(true);
        mRcvNewsList.setItemAnimator(new DefaultItemAnimator());
        mTopicListAdapter = new TopicListAdapter(getActivity(), new ArrayList<Topic>());
        mRcvNewsList.setAdapter(mTopicListAdapter);
        mAutoLoadListener = new AutoLoadOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadBeforeNews(curDate);
            }
        };
        mRcvNewsList.addOnScrollListener(mAutoLoadListener);
        mRcvNewsList.addOnScrollListener(new RecyclerViewListener());

        if (mExtraAdapter != null) {
            mTopicListAdapter.setAnim(false);
            mTopicListAdapter.setmTopicList(mExtraAdapter.getmTopicList());
            mTopicListAdapter.notifyDataSetChanged();
            move();
        }

        mLoadLatestSnackbar = Snackbar.make(mRcvNewsList, R.string.load_fail, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadLatestNews();
                    }
                });
        mLoadBeforeSnackbar = Snackbar.make(mRcvNewsList, R.string.load_more_fail, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadBeforeNews(curDate);
                    }
                });
    }

    private void move() {
        if (position < 0 || position >= mRcvNewsList.getAdapter().getItemCount()) {
            return;
        }
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();

        if (position <= firstItem) {
            mRcvNewsList.scrollToPosition(position);
            move = true;
        } else if (position <= lastItem) {
            int top = mRcvNewsList.getChildAt(position - firstItem).getTop() - scroll;
            mRcvNewsList.scrollBy(0, top);
            if (mOnRecyclerViewCreated != null) {
                mOnRecyclerViewCreated.recyclerViewCreated();
            }
        } else {
            mRcvNewsList.scrollToPosition(position);
            move = true;
        }
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (move) {
                move = false;
                int n = position - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRcvNewsList.getChildCount()) {
                    int top = mRcvNewsList.getChildAt(n).getTop() - scroll;
                    mRcvNewsList.smoothScrollBy(0, top);
                }
                if (mOnRecyclerViewCreated != null) {
                    mOnRecyclerViewCreated.recyclerViewCreated();
                }
            }
        }
    }

    private void loadLatestNews() {
        RetrofitManager.builder().getLatestTopics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                        showProgress();
                    }
                })
                .subscribe(new Action1<TopicList>() {
                    @Override
                    public void call(TopicList newsList) {
                        mPtrNewsList.setRefreshing(false);
                        hideProgress();
                        if (newsList.size() == 0) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mTvLoadEmpty.setVisibility(View.GONE);
                            mTopicListAdapter.addData(newsList);
                        }
                        mTvLoadError.setVisibility(View.GONE);
                        mLoadLatestSnackbar.dismiss();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPtrNewsList.setRefreshing(false);
                        hideProgress();
                        mLoadLatestSnackbar.show();
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);
                    }
                });

    }

    private void loadBeforeNews(String date) {

    }


    private void cacheAllDetail(List<Topic> newsList) {

    }

    private void cacheNewsDetail(int newsId) {

    }


    public void setmOnRecyclerViewCreated(OnRecyclerViewCreated mOnRecyclerViewCreated) {
        this.mOnRecyclerViewCreated = mOnRecyclerViewCreated;
    }

    public String getCurDate(){
        return curDate;
    }

    public void setmTopicListAdapter(TopicListAdapter mTopicListAdapter) {
        this.mTopicListAdapter = mTopicListAdapter;
    }

    public TopicListAdapter getmTopicListAdapter() {
        return mTopicListAdapter;
    }

    @Override
    public void onRefresh() {
        loadLatestNews();
    }

    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }

    public RecyclerView getRecyclerView() {
        return mRcvNewsList;
    }

    public interface OnRecyclerViewCreated {
        void recyclerViewCreated();
    }

}
