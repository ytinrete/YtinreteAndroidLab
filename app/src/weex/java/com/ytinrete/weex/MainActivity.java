package com.ytinrete.weex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;

/**
 *
 */

public class MainActivity extends YtBaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weex_main);
    findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, WeexActivity.class));
      }
    });
    findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, WeexFragmentActivity.class));
      }
    });
  }
}
