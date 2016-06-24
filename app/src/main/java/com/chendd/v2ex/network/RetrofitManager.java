package com.chendd.v2ex.network;

import com.chendd.v2ex.App;
import com.chendd.v2ex.bean.Member;
import com.chendd.v2ex.bean.Node;
import com.chendd.v2ex.bean.NodeList;
import com.chendd.v2ex.bean.ReplyList;
import com.chendd.v2ex.bean.SiteInfo;
import com.chendd.v2ex.bean.SiteStats;
import com.chendd.v2ex.bean.Topic;
import com.chendd.v2ex.bean.TopicList;
import com.chendd.v2ex.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author admin
 * @Time 2016/6/21.
 */
public class RetrofitManager {

    public static final String BASE_V2EX_URL = "https://www.v2ex.com/api/";

    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private static OkHttpClient mOkHttpClient;
    private final V2exService mV2exService;

    public static RetrofitManager builder(){
        return new RetrofitManager();
    }

    private RetrofitManager() {

        initOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_V2EX_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mV2exService = retrofit.create(V2exService.class);
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {

                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };

    public Observable<SiteInfo> getSiteInfo(){
        return mV2exService.getSiteInfo();
    }

    public Observable<SiteStats> getSiteStats(){
        return mV2exService.getSiteStats();
    }

    public Observable<NodeList> getNodesAll(){
        return mV2exService.getNodesAll();
    }

    public Observable<Node> getNodeById(@Query("id")int id){
        return mV2exService.getNodeById(id);
    }

    public Observable<Node> getNodeByName(@Query("name")String name){
        return mV2exService.getNodeByName(name);
    }

    public Observable<TopicList> getLatestTopics() {
        return mV2exService.getLatestTopics();
    }

    public Observable<TopicList> getHotTopics(){
        return mV2exService.getHotTopics();
    }

    public Observable<Topic> getTopicById(@Query("id")int id) {
        return mV2exService.getTopicById(id);
    }

    public Observable<TopicList> getTopicsByUsername(@Query("username")String username) {
        return mV2exService.getTopicsByUsername(username);
    }

    public Observable<TopicList> getTopicsByNodeId(@Query("node_id")int nodeId) {
        return mV2exService.getTopicsByNodeId(nodeId);
    }

    public Observable<TopicList> getTopicsByNodeName(@Query("node_name")String nodeName) {
        return mV2exService.getTopicsByNodeName(nodeName);
    }

    public Observable<ReplyList> getRepliesByTopicId(@Query("topic_id")int topicId, @Query("page") String page, @Query("page_size") String pageSize){
        return mV2exService.getRepliesByTopicId(topicId,page,pageSize);
    }

    public Observable<Member> getMemberByUsername(@Query("username")String username) {
        return mV2exService.getMemberByUsername(username);
    }

}
