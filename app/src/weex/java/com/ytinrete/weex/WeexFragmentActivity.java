package com.ytinrete.weex;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;

/**
 *
 */

public class WeexFragmentActivity extends YtBaseActivity {
  private static final String EXAMPLE_URL="http://dotwe.org/raw/dist/b22f2a3b087c7fd02471c76e066f0f23.bundle.js";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    WeexFragment weexFragment = WeexFragment.newInstance(EXAMPLE_URL);
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.add(R.id.content_fragment, weexFragment);
    transaction.commit();
  }
}
