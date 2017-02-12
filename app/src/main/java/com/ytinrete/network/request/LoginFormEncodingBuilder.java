package com.ytinrete.network.request;

import android.text.TextUtils;


import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 *
 */
public class LoginFormEncodingBuilder {
  private FormBody.Builder formEncodingBuilder;

  public LoginFormEncodingBuilder() {
    formEncodingBuilder = new FormBody.Builder();

  }

  public FormBody.Builder add(String name, String value) {
    return formEncodingBuilder.add(name, value);
  }

  public FormBody.Builder addEncoded(String name, String value) {
    return formEncodingBuilder.addEncoded(name, value);
  }

  public RequestBody build() {
    return formEncodingBuilder.build();
  }

}
