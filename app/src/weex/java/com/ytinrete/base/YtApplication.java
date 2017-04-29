package com.ytinrete.base;


import android.support.multidex.MultiDexApplication;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.ytinrete.contract.YtApp;
import com.ytinrete.tools.CommonTools;
import com.ytinrete.tools.l;
import com.ytinrete.weex.ImageAdapter;

/**
 *
 */

public class YtApplication extends MultiDexApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    YtApp.getInstance().setAppContext(this);
    l.d("application onCreate");
    InitConfig config=new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
    WXSDKEngine.initialize(this,config);
  }
}
