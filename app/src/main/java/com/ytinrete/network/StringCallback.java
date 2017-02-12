package com.ytinrete.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class StringCallback implements Callback {

  private final String mCharset;

  public StringCallback() {
    mCharset = null;
  }

  public StringCallback(String charset) {
    mCharset = charset;
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
    final String s;
    try {
      if (mCharset == null) {
        s = response.body().string();
      } else {
        s = new String(response.body().bytes(), mCharset);
      }
      response.body().close();
    } catch (IOException e) {
      handleException(e, call);
      return;
    }
    beforeResponse(s);
    HttpHelper.getMainThreadHandler().post(new Runnable() {

      @Override
      public void run() {
        if (!call.isCanceled()) {
          onSuccess(s, response, call);
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
   * 成功回调之前执行的方法，可以做额外的工作，此方法在后台线程被调用。
   *
   * @param s 保证不为null
   */
  public void beforeResponse(String s) {

  }

  /**
   * @param s        保证不为null
   * @param response 保证不为null，可以调用除body()方法之外的任意方法，是immutable
   * @param call
   */
  public abstract void onSuccess(String s, Response response, Call call);


  /**
   * @param e       可能是IOException
   * @param call
   */
  public abstract void onFailure(Exception e, Call call);

}