package com.ytinrete.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytinrete.mvp.factory.PresenterFactory;
import com.ytinrete.mvp.factory.ReflectionPresenterFactory;
import com.ytinrete.mvp.presenter.Presenter;


/**
 * This view is an example of how a view should control it's presenter.
 * You can inherit from this class or copy/paste this class's code to
 * create your own view implementation.
 *
 * @param <P> a type of presenter to return with {@link #getPresenter}.
 */
public abstract class MvpFragment<P extends Presenter> extends Fragment implements ViewWithPresenter<P> {

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
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    if (bundle != null) {
      presenterDelegate.onRestoreInstanceState(bundle.getBundle(PRESENTER_STATE_KEY));
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    presenterDelegate.onTakeView(this);
    return super.onCreateView(inflater, container, savedInstanceState);

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenterDelegate.onViewCreated();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (getActivity() != null) {
      presenterDelegate.onDestroy(!getActivity().isChangingConfigurations());
    } else {
      presenterDelegate.onDestroy(true);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle bundle) {
    super.onSaveInstanceState(bundle);
    bundle.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState());
  }

  @Override
  public void onResume() {
    super.onResume();
    presenterDelegate.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenterDelegate.onPause();
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      presenterDelegate.onVisible();
    } else {
      presenterDelegate.onInVisible();
    }
  }
}
