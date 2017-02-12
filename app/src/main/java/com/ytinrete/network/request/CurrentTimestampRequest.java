package com.ytinrete.network.request;


import com.ytinrete.network.RequestBuilder;

/**
 *
 */

public class CurrentTimestampRequest extends RequestBuilder {
  @Override
  public String getPath() {
      return "http://ytinrete.com";
  }
}
