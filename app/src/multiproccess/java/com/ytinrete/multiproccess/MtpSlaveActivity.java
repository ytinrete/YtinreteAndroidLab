package com.ytinrete.multiproccess;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;
import com.ytinrete.tools.l;

/**
 *
 */

public class MtpSlaveActivity extends YtBaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mtp_slave);

    Intent in = getIntent();
    if (in != null) {
      l.md("get from intent:" + in.getStringExtra("str"));
    }
    l.md("MtpMasterActivity:" + SingletonTest.getInstance().getAnInt() + " "
        + SingletonTest.getInstance().getStr());
  }
}
