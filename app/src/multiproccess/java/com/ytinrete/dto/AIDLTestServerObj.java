package com.ytinrete.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */

public class AIDLTestServerObj implements Parcelable {

  private int code;
  private String str;

  public AIDLTestServerObj() {

  }

  protected AIDLTestServerObj(Parcel in) {
    code = in.readInt();
    str = in.readString();
  }

  public static final Creator<AIDLTestServerObj> CREATOR = new Creator<AIDLTestServerObj>() {
    @Override
    public AIDLTestServerObj createFromParcel(Parcel in) {
      return new AIDLTestServerObj(in);
    }

    @Override
    public AIDLTestServerObj[] newArray(int size) {
      return new AIDLTestServerObj[size];
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
