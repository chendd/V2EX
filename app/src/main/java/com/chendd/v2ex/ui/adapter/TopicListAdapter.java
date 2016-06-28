package com.chendd.v2ex.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.chendd.v2ex.R;
import com.chendd.v2ex.bean.Topic;
import com.chendd.v2ex.utils.TextUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by laucherish on 16/3/16.
 */
public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.NewsViewHolder> {

    private static final int ITEM_TOPIC = 0;

    private Context mContext;
    private List<Topic> mTopicList;
    private long lastPos = -1;
    private boolean isAnim = true;

    public TopicListAdapter(Context context, List<Topic> mTopicList) {
        this.mContext = context;
        this.mTopicList = mTopicList;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TOPIC;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_list, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final Topic news = mTopicList.get(position);
        if (news == null) {
            return;
        }
        bindViewHolder(holder, position, news);
    }

    private void bindViewHolder(final NewsViewHolder holder, int position, final Topic news) {
        holder.mTvTitle.setText(TextUtil.formatText(news.title));
        holder.mTvNode.setText(news.node.title);
        holder.mTvReads.setText(String.valueOf(news.replies));
        if (news.member.avatarLarge != null) {
            holder.mIvNews.setImageURI(Uri.parse(TextUtil.appendHttp(news.member.avatarLarge)));
        }

        holder.mCvItem.setOnClickListener(getListener(holder, news));

        if (isAnim) {
            startAnimator(holder.mCvItem, position);
        }
    }

    @NonNull
    private View.OnClickListener getListener(final NewsViewHolder holder, final Topic news) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!news.isRead()) {
//                    news.setRead(true);
//                    holder.mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_read));
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            newDao.insertReadNew(news.getId() + "");
//                        }
//                    }).start();
//                }
//                NewsDetailActivity.start(mContext, news);
            }
        };
    }

    @Override
    public int getItemCount() {
        return mTopicList == null ? 0 : mTopicList.size();
    }

    private void startAnimator(View view, long position) {
        if (position > lastPos) {
            view.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.item_bottom_in));
            lastPos = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(NewsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mCvItem.clearAnimation();
    }

    public void changeData(List<Topic> newsList) {
        mTopicList = newsList;
        notifyDataSetChanged();
    }

    public void addData(List<Topic> newsList) {
        if (mTopicList == null) {
            changeData(newsList);
        } else {
            mTopicList.addAll(newsList);
            notifyDataSetChanged();
        }
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    public void setmTopicList(List<Topic> mTopicList) {
        this.mTopicList = mTopicList;
    }

    public List<Topic> getmTopicList() {
        return mTopicList;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cv_item)
        CardView mCvItem;

        @Bind(R.id.iv_news)
        SimpleDraweeView mIvNews;

        @Bind(R.id.tv_title)
        TextView mTvTitle;

        @Bind(R.id.tv_reads)
        TextView mTvReads;

        @Bind(R.id.tv_node)
        TextView mTvNode;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
