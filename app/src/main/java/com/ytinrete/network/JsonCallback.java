package com.ytinrete.network;


import com.ytinrete.tools.JsonSerializer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * JSON响应回调基类，子类需实现onSuccess、onFailure两个方法
 *
 * @param <T>
 */
public abstract class JsonCallback<T> implements Callback {

  private final Class<T> mClazz;

  public JsonCallback(Class<T> clazz) {
    mClazz = clazz;
  }

  protected Class<T> getResponseClass() {
    return mClazz;
  }

  @Override
  public void onFailure(final Call call, final IOException e) {
    HttpHelper.getMainThreadHandler().post(new Runnable() {
      @Override
      public void run() {
        if (!call.isCanceled()) {
          onFailure(e, call);
        }
      }
    });
  }

  @Override
  public void onResponse(final Call call, final Response response) throws IOException {
    final T t;
    try {
      t = JsonSerializer.getInstance().deserializeFrankly(response.body().charStream(),
          getResponseClass());
      if (t == null) {
        throw new NullPointerException("Json deserialize Frankly returned null");
      }
    } catch (Exception e) {
      handleException(e, call);
      return;
    } finally {
      if (response != null && response.body() != null) {
        response.body().close();
      }
    }
    beforeResponse(t);
    HttpHelper.getMainThreadHandler().post(new Runnable() {

      @Override
      public void run() {
        if (!call.isCanceled()) {
          onSuccess(t, response, call);
        }
      }
    });
  }

  private void handleException(final Exception e, final Call call) {
    HttpHelper.getMainThreadHandler().post(new Runnable() {

      @Override
      public void run() {
        if (!call.isCanceled()) {
          onFailure(e, call);
        }
      }
    });
  }

  /**
   * JSON解析完毕后，成功回调之前执行的方法，可以做额外的工作，此方法在后台线程被调用。
   *
   * @param t 保证不为null
   */
  public void beforeResponse(T t) {

  }

  /**
   * 网络请求成功并解析出非null的模型类对象时进入的回调，在主线程被调用。
   *
   * @param t        保证不为null
   * @param response 保证不为null，可以调用除body()方法之外的任意方法，是immutable
   * @param call
   */
  public abstract void onSuccess(T t, Response response, Call call);


  /**
   * 失败时进入的回调，在主线程被调用。网络IO错误、JSON解析错误都会导致进入此回调。
   *
   * @param e    可能是NullPointerException, JsonParseException, JsonMappingException, IOException其中之一
   * @param call
   */
  public abstract void onFailure(Exception e, Call call);

}
