package com.ytinrete.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ytinrete.mvp.factory.PresenterFactory;
import com.ytinrete.mvp.factory.ReflectionPresenterFactory;
import com.ytinrete.mvp.presenter.Presenter;


/**
 * This class is an tv.danmaku.ijk.media.example of how an activity could controls it's presenter.
 * You can inherit from this class or copy/paste this class's code to
 * create your own view implementation.
 *
 * @param <P> a type of presenter to return with {@link #getPresenter}.
 */
public abstract class MvpActivity<P extends Presenter> extends AppCompatActivity implements ViewWithPresenter<P> {

  private static final String PRESENTER_STATE_KEY = "presenter_state";

  private PresenterLifecycleDelegate<P> presenterDelegate =
      new PresenterLifecycleDelegate<>(ReflectionPresenterFactory.<P>fromViewClass(getClass()));

  /**
   * Returns a current presenter factory.
   */
  public PresenterFactory<P> getPresenterFactory() {
    return presenterDelegate.getPresenterFactory();
  }

  /**
   * Sets a presenter factory.
   * Call this method before onCreate/onFinishInflate to override default {@link ReflectionPresenterFactory} presenter factory.
   * Use this method for presenter dependency injection.
   */
  @Override
  public void setPresenterFactory(PresenterFactory<P> presenterFactory) {
    presenterDelegate.setPresenterFactory(presenterFactory);
  }

  /**
   * Returns a current attached presenter.
   * This method is guaranteed to return a non-null value between
   * onResume/onPause and onAttachedToWindow/onDetachedFromWindow calls
   * if the presenter factory returns a non-null value.
   *
   * @return a currently attached presenter or null.
   */
  public P getPresenter() {
    return presenterDelegate.getPresenter();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      presenterDelegate.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_STATE_KEY));
    }
    presenterDelegate.onTakeView(this);
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    presenterDelegate.onViewCreated();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState());
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenterDelegate.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    presenterDelegate.onPause();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenterDelegate.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenterDelegate.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenterDelegate.onDestroy(!isChangingConfigurations());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    presenterDelegate.onNewIntent(intent);
  }
}
