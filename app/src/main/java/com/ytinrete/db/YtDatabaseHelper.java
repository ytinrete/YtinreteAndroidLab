package com.ytinrete.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 *
 */

public class YtDatabaseHelper {

  public static void initRealm(Context context) {
    Realm.init(context);
    RealmConfiguration configuration = new RealmConfiguration.Builder()
        .name("ytinrete")
        .schemaVersion(1L)
        .deleteRealmIfMigrationNeeded()//数据库升级直接删除原有数据
        .build();
    Realm.setDefaultConfiguration(configuration);
  }
}
