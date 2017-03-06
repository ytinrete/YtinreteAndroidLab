package com.ytinrete.multiproccess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;
import com.ytinrete.tools.CommonTools;
import com.ytinrete.tools.l;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class MtpActivity2 extends YtBaseActivity {

  @BindView(R.id.btn1)
  Button btn1;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mtp2);

    ButterKnife.bind(this);

    CommonTools.getPID();

    btn1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    //This is still BBB
    l.d(SingletonTest.getInstance().getStr());


  }
}
