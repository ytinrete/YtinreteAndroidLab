package com.ytinrete.base;


import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.ytinrete.contract.YtApp;
import com.ytinrete.db.YtDatabaseHelper;
import com.ytinrete.music.service.YtMusicService;
import com.ytinrete.tools.AppLog;
import com.ytinrete.tools.CommonTools;
import com.ytinrete.tools.l;

/**
 *
 */

public class YtApplication extends MultiDexApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    AppLog.d("YtApplication");
    //设置静态上下文
    YtApp.getInstance().setAppContext(this);

    //播放音乐用的service
//    startService(new Intent(this, YtMusicService.class));

    //初始化数据库
    YtDatabaseHelper.initRealm(this);

    AppLog.d("PID:" + android.os.Process.myPid());

    l.d("--------YtApplication-------");
    CommonTools.getPID(this);
    l.d("--------YtApplication-------");


  }
}
