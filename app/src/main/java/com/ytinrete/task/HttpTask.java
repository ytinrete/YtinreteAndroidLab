package com.ytinrete.task;


import android.text.TextUtils;
import android.util.Pair;


import com.ytinrete.network.response.BasicResponse;

import java.util.List;

/**
 * 网络请求任务，发起一个网络请求，如果有formParams则传送一串表单，否则是get请求，最后用HttpResponse返回
 */

public class HttpTask extends BaseTask<BasicResponse> {

  private String url;
  private List<Pair<String, String>> formParams;
  private List<Pair<String, String>> urlParams;
  private List<Pair<String, String>> headerParams;

  protected HttpTask(TaskManager tm) {
    super(tm);
  }

  @Override
  protected BasicResponse doTask() {
//    if (TextUtils.isEmpty(url)) {
//      TaskManager.l("error! HttpFromPostTask url is empty!");
//      return null;
//    }
//    HttpRequest.Builder builder = new HttpRequest.Builder();
//    builder.url(getUrl());
//    if (formParams != null && formParams.size() > 0) {
//      builder.post(Tools.createFormBodyBytes(formParams));
//    } else {
//      builder.get();
//    }
//    if (urlParams != null && urlParams.size() > 0) {
//      for (Pair p : urlParams) {
//        if (!TextUtils.isEmpty((String) p.first) && !TextUtils.isEmpty((String) p.second)) {
//          builder.param((String) p.first, (String) p.second);
//        }
//      }
//    }
//    if (headerParams != null && headerParams.size() > 0) {
//      for (Pair p : headerParams) {
//        if (!TextUtils.isEmpty((String) p.first) && !TextUtils.isEmpty((String) p.second)) {
//          builder.header((String) p.first, (String) p.second);
//        }
//      }
//    }
    try {
//      return HttpHelper.getDefaultHttpClient().launchRequest(builder.build());
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      TaskManager.l("error http error in launchRequest!");
    }
    return null;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<Pair<String, String>> getFormParams() {
    return formParams;
  }

  public void setFormParams(List<Pair<String, String>> formParams) {
    this.formParams = formParams;
  }

  public List<Pair<String, String>> getUrlParams() {
    return urlParams;
  }

  public void setUrlParams(List<Pair<String, String>> urlParams) {
    this.urlParams = urlParams;
  }

  public List<Pair<String, String>> getHeaderParams() {
    return headerParams;
  }

  public void setHeaderParams(List<Pair<String, String>> headerParams) {
    this.headerParams = headerParams;
  }
}
