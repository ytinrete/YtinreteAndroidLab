package com.ytinrete.base;


import android.support.multidex.MultiDexApplication;

import com.ytinrete.contract.YtApp;
import com.ytinrete.tools.CommonTools;
import com.ytinrete.tools.l;

/**
 *
 */

public class YtApplication extends MultiDexApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    l.d("--------YtApplication onCreate-------");
    CommonTools.getPID(this);
    l.d("--------YtApplication-------");

    if (!CommonTools.getCurProcessName(this).equals(getPackageName())) {
      l.d("Ytinrete Other Process:" + CommonTools.getCurProcessName(this));
//      if(CommonTools.getCurProcessName(this).contains("test")){
//       //模拟延时
//        try {
//          Thread.sleep(3000);
//          l.d("wake up!");
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//
//      }
//      return;
    } else {
      l.d("Ytinrete Master Process");
      //设置静态上下文
      YtApp.getInstance().setAppContext(this);

      //播放音乐用的service
//    startService(new Intent(this, YtMusicService.class));

    }

  }
}
