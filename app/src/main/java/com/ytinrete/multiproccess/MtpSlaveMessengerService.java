package com.ytinrete.multiproccess;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import com.ytinrete.dto.AIDLTestClientObj;
import com.ytinrete.dto.AIDLTestServerObj;
import com.ytinrete.tools.l;

/**
 *
 */

public class MtpSlaveMessengerService extends Service {

  @Override
  public void onCreate() {
    l.md("MtpSlaveMessengerService onCreate");
    super.onCreate();
    AIDLTestClientObj aa = new AIDLTestClientObj();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    l.md("MtpSlaveMessengerService onStartCommand");
    return super.onStartCommand(intent, flags, startId);
  }

  private Messenger mMessenger = new Messenger(new Handler() {
    @Override
    public void handleMessage(Message msgFromClient) {
      l.md("MtpSlaveMessengerService");
      Message msgToClient = Message.obtain(msgFromClient);//返回给客户端的消息
      switch (msgFromClient.what) {
        //msg 客户端传来的消息
        case 1001:
          msgToClient.what = 1002;
          try {

//            Object obj = msgFromClient.obj;
//
//            if(obj instanceof String){
//              l.md("MtpSlaveMessengerService the server got from client:"+obj);
//            }

            Bundle bd = msgFromClient.getData();
            //否则会有类查找不到错误
            bd.setClassLoader(AIDLTestClientObj.class.getClassLoader());

            AIDLTestClientObj obj = bd.getParcelable("mes");
            l.md("MtpSlaveMessengerService the server got from client AIDLTestClientObj:"
                + obj.getStr() + " " + obj.getCode());
            AIDLTestServerObj serObj = new AIDLTestServerObj();
            serObj.setCode(-2000);
            serObj.setStr("foo_server_2000");
            //模拟耗时
            Thread.sleep(3000);
//            msgToClient.obj = serObj;
            Bundle bd2 = new Bundle();
            bd2.putParcelable("mes2", serObj);
            msgToClient.setData(bd2);

            msgFromClient.replyTo.send(msgToClient);
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
      }

      super.handleMessage(msgFromClient);
    }
  });


  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return mMessenger.getBinder();
  }
}
