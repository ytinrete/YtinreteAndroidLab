package com.ytinrete.tools;

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


}
