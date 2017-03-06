package com.ytinrete.multiproccess;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class MtpActivity1 extends YtBaseActivity {

  @BindView(R.id.btn1)
  Button btn1;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mtp1);

    CommonTools.getPID();

    ButterKnife.bind(this);

    btn1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        startActivity(new Intent(MtpActivity1.this, MtpActivity2.class));

        (new Handler()).postDelayed(new Runnable() {
          @Override
          public void run() {

            throw new NullPointerException("lalala");
          }
        }, 5000);


      }
    });

    //this will be AAA not BBB
    l.d(SingletonTest.getInstance().getStr());

  }
}
