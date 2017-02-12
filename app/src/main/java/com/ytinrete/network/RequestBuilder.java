package com.ytinrete.network;


import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class RequestBuilder {

  public abstract String getPath();

  /**
   * 添加 GET 参数
   *
   * @param urlBuilder
   */
  public void get(HttpUrl.Builder urlBuilder) {

  }

  /**
   * 添加 POST 参数，以结果返回
   */
  public RequestBody post() {
    return null;
  }

  public void last(Request.Builder builder) {

  }

  public boolean isEncrypted() {
    return false;
  }

  public String getEncryptionKey() {
    return "";
  }

  public static Request build(RequestBuilder builder) {

    HttpUrl.Builder urlBuilder = HttpUrl
        .parse(builder.getPath()).newBuilder();

    //添加公共参数

    builder.get(urlBuilder);

    Request.Builder requestBuilder = new Request.Builder()
        .url(urlBuilder.build());

    RequestBody body = builder.post();

    if (body != null) {
      if (builder.isEncrypted()) {


      } else {
        requestBuilder.post(body);
      }
    }

    builder.last(requestBuilder);

    return requestBuilder.build();
  }

}
