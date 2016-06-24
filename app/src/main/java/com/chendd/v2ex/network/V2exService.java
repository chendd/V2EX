package com.chendd.v2ex.network;

import com.chendd.v2ex.bean.Member;
import com.chendd.v2ex.bean.Node;
import com.chendd.v2ex.bean.NodeList;
import com.chendd.v2ex.bean.ReplyList;
import com.chendd.v2ex.bean.SiteInfo;
import com.chendd.v2ex.bean.SiteStats;
import com.chendd.v2ex.bean.Topic;
import com.chendd.v2ex.bean.TopicList;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author admin
 * @Time 2016/6/21.
 */
public interface V2exService {

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("site/info.json")
    Observable<SiteInfo> getSiteInfo();

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("site/stats.json")
    Observable<SiteStats> getSiteStats();

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("nodes/all.json")
    Observable<NodeList> getNodesAll();

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("nodes/show.json")
    Observable<Node> getNodeById(@Query("id")int id);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("nodes/show.json")
    Observable<Node> getNodeByName(@Query("name")String name);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("topics/latest.json")
    Observable<TopicList> getLatestTopics();

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("topics/hot.json")
    Observable<TopicList> getHotTopics();

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("topics/show.json")
    Observable<Topic> getTopicById(@Query("id")int id);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("topics/show.json")
    Observable<TopicList> getTopicsByUsername(@Query("username")String username);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("topics/show.json")
    Observable<TopicList> getTopicsByNodeId(@Query("node_id")int nodeId);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("topics/show.json")
    Observable<TopicList> getTopicsByNodeName(@Query("node_name")String nodeName);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("replies/show.json")
    Observable<ReplyList> getRepliesByTopicId(@Query("topic_id")int topicId, @Query("page") String page, @Query("page_size") String pageSize);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("members/show.json")
    Observable<Member> getMemberByUsername(@Query("username")String username);

}
