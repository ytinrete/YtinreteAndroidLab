package com.ytinrete.network;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;


public class HttpHelper {

  static {
    instance = new OkHttpClient.Builder()
        .retryOnConnectionFailure(false)
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
//        .addInterceptor(new Interceptor() {
//          @Override
//          public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            long startTime = SystemClock.elapsedRealtime();
//            Response response = chain.proceed(request);
//            String url = request.url().toString();
//            int index = url.indexOf("?");
//            if(index >= 0) {
//              url = url.substring(0, index);
//            }
////            MobileAnalysis.getInstance().addUrlEvent(url, startTime, SystemClock.elapsedRealtime(), response.code(), null);
//            return response;
//          }
//        })
        .build();
  }

  private static OkHttpClient instance;

  private static final Handler mainThreadHandler = new Handler(
      Looper.getMainLooper());

  public static Call newCall(RequestBuilder requestBuilder) {
    return instance.newCall(RequestBuilder.build(requestBuilder));
  }

  public static Handler getMainThreadHandler() {
    return mainThreadHandler;
  }

}
