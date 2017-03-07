package com.ytinrete.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;

import com.ytinrete.contract.YtApp;

/**
 *
 */

public class CommonTools {

  public static int dp2Px(int dp) {
    DisplayMetrics displayMetrics = YtApp.getInstance().getAppContext().getResources().getDisplayMetrics();
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  public static int pxToDp(int px) {
    DisplayMetrics displayMetrics = YtApp.getInstance().getAppContext().getResources().getDisplayMetrics();
    return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  public static int getPID(){
    AppLog.d("PID:" + android.os.Process.myPid());
    l.d("PID:" + android.os.Process.myPid()+" Thread:"+Thread.currentThread().getName());
    return android.os.Process.myPid();
  }

  public static int getPID(Context context){
    l.d(getCurProcessName(context));
    return getPID();
  }

  private static String getCurProcessName(Context context) {
    int pid = android.os.Process.myPid();
    String processNameString = "";
    ActivityManager mActivityManager = (ActivityManager) context.getSystemService(
        Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
        .getRunningAppProcesses()) {
      if (appProcess.pid == pid) {
        processNameString = appProcess.processName;
      }
    }
    return processNameString;
  }


}
