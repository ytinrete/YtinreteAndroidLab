package com.ytinrete.network.request;

import com.ytinrete.network.RequestBuilder;

/**
 *
 */

public class MessageBoardGetListRequest extends RequestBuilder {
  @Override
  public String getPath() {
    return "http://www.ytinrete.com/go/MessageBoard/getList";
  }
}
