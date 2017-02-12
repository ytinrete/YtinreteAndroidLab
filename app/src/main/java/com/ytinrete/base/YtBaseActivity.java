package com.ytinrete.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.ytinrete.mvp.presenter.Presenter;
import com.ytinrete.mvp.view.MvpActivity;

/**
 *
 */

public class YtBaseActivity <P extends Presenter> extends MvpActivity<P> {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }


  protected void showShortToast(String s) {
    s = TextUtils.isEmpty(s) ? "" : s;
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
  }

}
