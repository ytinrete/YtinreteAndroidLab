package com.ytinrete.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */

public class AIDLTestClientObj implements Parcelable {

  private int code;
  private String str;

  public AIDLTestClientObj() {

  }

  protected AIDLTestClientObj(Parcel in) {
    code = in.readInt();
    str = in.readString();
  }

  public static final Creator<AIDLTestClientObj> CREATOR = new Creator<AIDLTestClientObj>() {
    @Override
    public AIDLTestClientObj createFromParcel(Parcel in) {
      return new AIDLTestClientObj(in);
    }

    @Override
    public AIDLTestClientObj[] newArray(int size) {
      return new AIDLTestClientObj[size];
    }
  };

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    // 1.必须按成员变量声明的顺序封装数据
    dest.writeInt(this.code);
    dest.writeString(this.str);

  }
}
