package com.ytinrete.network.request;


import com.ytinrete.network.RequestBuilder;

import okhttp3.RequestBody;


/**
 *
 */
public class ChooseRoomRequest extends RequestBuilder {

  private int roomId;

  public ChooseRoomRequest(int roomId) {
    this.roomId = roomId;
  }

  @Override
  public String getPath() {
    return "chooseRoom.do";
  }

  @Override
  public RequestBody post() {
    return new LoginFormEncodingBuilder().add("roomId", String.valueOf(roomId)).build();
  }
}
