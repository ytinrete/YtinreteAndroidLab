package com.ytinrete.contract;

import android.content.Context;

/**
 *
 */

public class YtApp {

  private static Context appContext;

  private static YtApp app;
  
  public static YtApp getInstance() {
    if (app == null) {
      app = new YtApp();
    }
    return app;
  }

  public Context getAppContext() {
    return appContext;
  }

  public void setAppContext(Context appContext) {
    YtApp.appContext = appContext;
  }
}
