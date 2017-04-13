package com.ytinrete.tools;

import android.util.Log;

/**
 *
 */

public class l {
  public static void d(String str) {
    Log.d("ytinrete_log", str == null ? "null" : str);
  }

  public static void md(String str) {
    str = str == null ? "null" : str;
    Log.d("ytinrete_log", "PID:" + android.os.Process.myPid() + " ->" + str);
  }
}
