package com.hznu.kaoqin.net.retrofit;

/**
 * Created by 代码咖啡 on 17/3/28
 * <p>
 * Email: wjnovember@icloud.com
 */

import android.content.Context;

import com.hznu.kaoqin.net.Net;
import com.hznu.kaoqin.pojo.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 单例模式
 */
public class RetrofitUtil {

    private static RetrofitUtil mInstance;

    private static HttpLoggingInterceptor mInterceptor;
    private static OkHttpClient.Builder mClientBuilder;
    private static Retrofit.Builder mRetrofitBuilder;

    private static RetrofitAPI mApi;

    private static Observable mObservable;

    private RetrofitUtil() {

    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil();
                    mInterceptor = new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY);
                    mClientBuilder = new OkHttpClient.Builder()
                            .addInterceptor(mInterceptor);
                    mRetrofitBuilder = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                }
            }
        }
        mClientBuilder.readTimeout(Constant.Net.READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(Constant.Net.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        return mInstance;
    }

    /**
     * 设置基本Url
     *
     * @param url
     * @return
     */
    private static RetrofitUtil baseUrl(String url) {
        mRetrofitBuilder.baseUrl(url);
        return mInstance;
    }

    /**
     * 设置基本url
     *
     * @param url
     * @return
     */
    private static RetrofitUtil baseUrl(HttpUrl url) {
        mRetrofitBuilder.baseUrl(url);
        return mInstance;
    }

    /**
     * 设置读取超时
     *
     * @param seconds
     * @return
     */
    public static RetrofitUtil readTimeOut(int seconds) {
        mClientBuilder.readTimeout(seconds, TimeUnit.SECONDS);
        return mInstance;
    }

    /**
     * 设置连接超时
     *
     * @param seconds
     * @return
     */
    public static RetrofitUtil connectTimeOut(int seconds) {
        mClientBuilder.connectTimeout(seconds, TimeUnit.SECONDS);
        return mInstance;
    }

    /**
     * 构建
     *
     * @return
     */
//    private static RetrofitUtil build() {
//        mRetrofitBuilder.client(mClientBuilder.build())
//                .build();
//        return mInstance;
//    }

    /**
     * 创建请求
     *
     * @return
     */
    private static RetrofitUtil create(Context context) {
        baseUrl(Net.getBaseUrl(context));
        if (mApi == null) {
            mApi = mRetrofitBuilder.client(mClientBuilder.build())
                    .build()
                    .create(RetrofitAPI.class);
        }
        return mInstance;
    }

    /**
     * 登录
     *
     * @param context
     * @param account
     * @param pwd
     * @param subscriber
     * @return
     */
    public static RetrofitUtil login(Context context, String account, String pwd, Subscriber subscriber) {
//        if (mApi != null) {
        create(context);
        mObservable = mApi.login(account, pwd);
        config(subscriber);
//        }
        return mInstance;
    }

    /**
     * 退出登录
     *
     * @param context
     * @param id
     * @param token
     * @return
     */
    public static RetrofitUtil logout(Context context, String id, int token, Subscriber subscriber) {
//        if (mApi != null) {
        create(context);
        mObservable = mApi.logout(id, token);
        config(subscriber);
//        }
        return mInstance;
    }

    /**
     * 判断用户是否在线
     * @param context
     * @param id
     * @param token
     * @param subscriber
     * @return
     */
    public static RetrofitUtil isOnline(Context context, String id, int token, Subscriber subscriber) {
        create(context);
        mObservable = mApi.isOnLine(id, token);
        config(subscriber);
        return mInstance;
    }

    /**
     * 获取教师的课程
     * @param context
     * @param teacherId
     * @param token
     * @param subscriber
     * @return
     */
    public static RetrofitUtil getCourses(Context context, String teacherId, int token, Subscriber subscriber) {
        create(context);
        mObservable = mApi.getCourses(teacherId, token);
        config(subscriber);
        return mInstance;
    }

    /**
     * 通过课程id获取学生列表和课程信息
     * @param context
     * @param courseId
     * @param teacherId
     * @param token
     * @param subscriber
     * @return
     */
    public static RetrofitUtil getStudentsByCourse(Context context, String courseId, String teacherId, int token, Subscriber subscriber) {
        create(context);
        mObservable = mApi.getStudentsByCourse(courseId, teacherId, token);
        config(subscriber);
        return mInstance;
    }

    /**
     * 获取教师所有课程的名字
     * @param context
     * @param teacherId
     * @param token
     * @param subscriber
     * @return
     */
    public static RetrofitUtil getCourseNames(Context context, String teacherId, int token, Subscriber subscriber) {
        create(context);
        mObservable = mApi.getCourseNames(teacherId, token);
        config(subscriber);
        return mInstance;
    }

    /**
     * 获取当前课程有到勤记录的日期
     * @param context
     * @param teacherId
     * @param courseName
     * @param token
     * @return
     */
    public static RetrofitUtil getArrivalDate(Context context, String teacherId, String courseName,
                                              int token, Subscriber subscriber) {
        create(context);
        mObservable = mApi.getArrivalDate(teacherId, courseName, token);
        config(subscriber);
        return mInstance;
    }

    public static RetrofitUtil getArrival(Context context, String teacherId, String courseName,
                                          String arrivalDate, int token,Subscriber subscriber) {
        create(context);
        mObservable = mApi.getArrival(teacherId, courseName, arrivalDate, token);
        config(subscriber);
        return mInstance;
    }

    /**
     * 配置被观察者
     *
     * @param subscriber
     */
    private static void config(Subscriber subscriber) {
        if (mObservable != null) {
            mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }

}
