package com.ytinrete.multiproccess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ytinrete.android.lab.IAIDLTestInterface;
import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;
import com.ytinrete.dto.AIDLTestClientObj;
import com.ytinrete.dto.AIDLTestServerObj;
import com.ytinrete.tools.l;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */

public class MtpMasterActivity extends YtBaseActivity {

  @BindView(R.id.btn1)
  Button btn1;
  @BindView(R.id.btn2)
  Button btn2;
  @BindView(R.id.btn3)
  Button btn3;
  @BindView(R.id.btn4)
  Button btn4;
  @BindView(R.id.btn5)
  Button btn5;
  @BindView(R.id.btn6)
  Button btn6;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mtp_master);
    ButterKnife.bind(this);

    SingletonTest.getInstance().setStr("BBB");
    SingletonTest.getInstance().setAnInt(2);
    l.md("MtpMasterActivity:" + SingletonTest.getInstance().getAnInt() + " "
        + SingletonTest.getInstance().getStr());

    Intent intentAidl = new Intent("com.ytinrete.android.lab.aidl");
    intentAidl.setPackage("com.ytinrete.android.lab");// must
    bindService(intentAidl, serviceConnection, Context.BIND_AUTO_CREATE);


    Intent intentMessenger = new Intent();
    intentMessenger.setClass(this, MtpSlaveMessengerService.class);
    intentMessenger.setPackage("com.ytinrete.android.lab");// must
    bindService(intentMessenger, mConn, Context.BIND_AUTO_CREATE);
  }

  IAIDLTestInterface aidl;// 服务

  private ServiceConnection serviceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      aidl = IAIDLTestInterface.Stub.asInterface(service);// 获取服务对象
      l.md("connect ok");
    }// 连接服务

    @Override
    public void onServiceDisconnected(ComponentName name) {
      l.md("disconnect");
    }
  };


  private Messenger mService;

  private ServiceConnection mConn = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mService = new Messenger(service);
      l.md("Messenger connect ok");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      l.md("Messenger disconnect");
      mService = null;

    }
  };

  private Messenger mMessenger = new Messenger(new Handler() {
    @Override
    public void handleMessage(Message msgFromServer) {

      l.md("Messenger server replay:");
      switch (msgFromServer.what) {
        case 1002:

          Bundle bd = msgFromServer.getData();
          //否则会有类查找不到错误
          bd.setClassLoader(AIDLTestServerObj.class.getClassLoader());

          AIDLTestServerObj obj = bd.getParcelable("mes2");
          if (obj != null) {
            l.md("Messenger server replay:" + obj.getCode() + " " + obj.getStr());
          }
          break;
      }
      super.handleMessage(msgFromServer);
    }
  });


  @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn1:
        Intent inA = new Intent(this, MtpSlaveActivity.class);
        inA.putExtra("str", "foo");
        startActivity(inA);
        break;

      case R.id.btn2:
        Intent inS = new Intent(this, MtpSlaveService.class);
        inS.setAction("bar");
        inS.putExtra("str", "foo");
        startService(inS);
        break;

      case R.id.btn4:
        Intent inP = new Intent(this, MtpSlaveService.class);
        inP.setAction("Provider");
        inP.putExtra("str", "foo");
        startService(inP);
        break;

      case R.id.btn5:
        Intent inB = new Intent("slave_broadcast");
        inB.putExtra("str", "foo_broadcast");
        sendBroadcast(inB);
        break;

      case R.id.btn6:
        if (aidl != null) {
          try {
            AIDLTestClientObj clientObj = new AIDLTestClientObj();
            clientObj.setCode(100);
            clientObj.setStr("bar_client");
            AIDLTestServerObj obj = aidl.testObj(clientObj);
            if (obj != null) {
//              l.md("server replay:" + obj);
              l.md("server replay:" + obj.getCode() + " " + obj.getStr());
            }
            //这里会被阻塞
            l.md("server replay:" + aidl.clientToServer("aidl_foo"));
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        }
        break;

      case R.id.btn3:

        if (mService != null) {

          l.md("btn3");

          AIDLTestClientObj clientObj = new AIDLTestClientObj();
          clientObj.setCode(-1000);
          clientObj.setStr("bar_client_1000");

          Message msgFromClient = Message.obtain(null, 1001);
//          msgFromClient = Message.obtain(null, 1001, 1, 2);

          Bundle bd = new Bundle();
          bd.putParcelable("mes", clientObj);
          msgFromClient.setData(bd);

          msgFromClient.replyTo = mMessenger;

          //往服务端发送消息
          try {
            //这里并不会阻塞
            mService.send(msgFromClient);
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        }

        break;

      default:
        break;
    }
  }

  @Override
  protected void onDestroy() {
    if (aidl != null) {
      aidl = null;
      unbindService(serviceConnection);
    }

    super.onDestroy();

  }
}
