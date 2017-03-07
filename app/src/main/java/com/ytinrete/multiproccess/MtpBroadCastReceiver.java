package com.ytinrete.multiproccess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ytinrete.tools.CommonTools;
import com.ytinrete.tools.l;

/**
 *
 */

public class MtpBroadCastReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    l.d("----------MtpBroadCastReceiver onReceive---------");
    CommonTools.getPID(context);
    l.d("----------MtpBroadCastReceiver onReceive---------");
  }
}
