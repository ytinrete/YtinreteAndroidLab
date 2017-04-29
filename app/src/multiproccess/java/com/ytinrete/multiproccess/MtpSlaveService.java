package com.ytinrete.multiproccess;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.ytinrete.android.lab.IAIDLTestInterface;
import com.ytinrete.dto.AIDLTestClientObj;
import com.ytinrete.dto.AIDLTestServerObj;
import com.ytinrete.tools.l;

/**
 *
 */

public class MtpSlaveService extends Service {


  private final IAIDLTestInterface.Stub mBinder = new IAIDLTestInterface.Stub() {

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
      //pass
    }

    @Override
    public int clientToServer(String str) throws RemoteException {
      l.md("the server got from client:" + str);

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      l.md("server send back");
      return 200;
    }

    @Override
    public AIDLTestServerObj testObj(AIDLTestClientObj obj) throws RemoteException {
      l.md("the server got from client AIDLTestClientObj:" + obj.getStr()+" "+obj.getCode());
      AIDLTestServerObj serObj = new AIDLTestServerObj();
      serObj.setCode(100);
      serObj.setStr("foo_server");
      return serObj;
    }

  };

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
//    if ("com.ytinrete.android.lab.aidl".equals(intent.getAction())) {
//      return mBinder;
//    }
    return mBinder;
  }

  BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      l.md(intent.getStringExtra("str"));
    }
  };


  @Override
  public void onCreate() {
    super.onCreate();
    l.md("MtpSlaveService onCreate");
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("slave_broadcast");
    this.registerReceiver(receiver, intentFilter);
  }

  @Override
  public void onDestroy() {
    this.unregisterReceiver(receiver);
    super.onDestroy();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    l.md("MtpSlaveService onStartCommand:" + intent.getAction());

    if ("Provider".equals(intent.getAction())) {
      try {
        Cursor info = this.getContentResolver().query(
            Uri.parse("content://com.ytinrete.content/info1"), null, null, null, null);
        if (info != null && info.moveToLast()) {
//          //跟定义那边对应就行
          l.md("MtpSlaveService:" + info.getString(info.getColumnIndex("str")));
          info.close();
        }
      } catch (Exception e) {
      }
    }

    return super.onStartCommand(intent, flags, startId);
  }
}
