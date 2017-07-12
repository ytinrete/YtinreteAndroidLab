package com.ytinrete.base;


import android.support.multidex.MultiDexApplication;


import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.ytinrete.android.lab.BuildConfig;
import com.ytinrete.contract.YtApp;
import com.ytinrete.tools.CommonTools;
import com.ytinrete.tools.l;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;
import java.util.List;

/**
 *
 */

public class YtApplication extends MultiDexApplication implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage()
      );
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    YtApp.getInstance().setAppContext(this);
    l.d("application onCreate");

    SoLoader.init(this, /* native exopackage */ false);
  }
}
