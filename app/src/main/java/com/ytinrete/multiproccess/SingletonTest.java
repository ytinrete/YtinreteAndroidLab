package com.ytinrete.multiproccess;

/**
 *
 */

public class SingletonTest {

  private static SingletonTest instance;

  private String str = "AAA";

  private int anInt = 1;

  private SingletonTest() {

  }

  public static SingletonTest getInstance() {
    if (instance == null) {
      instance = new SingletonTest();
    }
    return instance;
  }

  public static void setInstance(SingletonTest instance) {
    SingletonTest.instance = instance;
  }

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  public int getAnInt() {
    return anInt;
  }

  public void setAnInt(int anInt) {
    this.anInt = anInt;
  }
}
