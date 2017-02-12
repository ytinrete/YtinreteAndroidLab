package com.ytinrete.network.response;

public class BasicResponse {

  private Object ret;
  private int retCode;
  private String retDesc;

  public String getRetDesc() {
    return retDesc;
  }

  public void setRetDesc(String retDesc) {
    this.retDesc = retDesc;
  }

  public Object getRet() {
    return ret;
  }

  public void setRet(Object ret) {
    this.ret = ret;
  }

  public int getRetCode() {
    return retCode;
  }

  public void setRetCode(int retCode) {
    this.retCode = retCode;
  }
}
