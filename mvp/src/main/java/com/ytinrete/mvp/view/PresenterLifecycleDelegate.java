package com.ytinrete.mvp.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ytinrete.mvp.factory.PresenterFactory;
import com.ytinrete.mvp.factory.PresenterStorage;
import com.ytinrete.mvp.presenter.Presenter;


/**
 * This class adopts a View lifecycle to the Presenter`s lifecycle.
 *
 * @param <P> a type of the presenter.
 */
public final class PresenterLifecycleDelegate<P extends Presenter> {

  private static final String PRESENTER_KEY = "presenter";
  private static final String PRESENTER_ID_KEY = "presenter_id";

  @Nullable
  private PresenterFactory<P> presenterFactory;
  @Nullable
  private P presenter;
  @Nullable
  private Bundle bundle;

  public PresenterLifecycleDelegate(@Nullable PresenterFactory<P> presenterFactory) {
    this.presenterFactory = presenterFactory;
  }

  /**
   * {@link ViewWithPresenter#getPresenterFactory()}
   */
  @Nullable
  public PresenterFactory<P> getPresenterFactory() {
    return presenterFactory;
  }

  /**
   * {@link ViewWithPresenter#setPresenterFactory(PresenterFactory)}
   */
  public void setPresenterFactory(@Nullable PresenterFactory<P> presenterFactory) {
    if (presenter != null)
      throw new IllegalArgumentException("setPresenterFactory() should be called before onResume()");
    this.presenterFactory = presenterFactory;
  }

  /**
   * {@link ViewWithPresenter#getPresenter()}
   */
  public P getPresenter() {
    if (presenterFactory != null) {
      if (presenter == null && bundle != null) {
        presenter = PresenterStorage.INSTANCE.getPresenter(bundle.getString(PRESENTER_ID_KEY));
      }

      if (presenter == null) {
        presenter = presenterFactory.createPresenter();
        PresenterStorage.INSTANCE.add(presenter);
        presenter.create(bundle == null ? null : bundle.getBundle(PRESENTER_KEY));
      }
      bundle = null;
    }
    return presenter;
  }

  /**
   * {@link android.app.Activity#onSaveInstanceState(Bundle)}, {@link android.app.Fragment#onSaveInstanceState(Bundle)}, {@link android.view.View#onSaveInstanceState()}.
   */
  public Bundle onSaveInstanceState() {
    Bundle bundle = new Bundle();
    getPresenter();
    if (presenter != null) {
      Bundle presenterBundle = new Bundle();
      presenter.save(presenterBundle);
      bundle.putBundle(PRESENTER_KEY, presenterBundle);
      bundle.putString(PRESENTER_ID_KEY, PresenterStorage.INSTANCE.getId(presenter));
    }
    return bundle;
  }

  /**
   * {@link android.app.Activity#onCreate(Bundle)}, {@link android.app.Fragment#onCreate(Bundle)}, {@link android.view.View#onRestoreInstanceState(Parcelable)}.
   */
  public void onRestoreInstanceState(Bundle presenterState) {
    this.bundle = ParcelFn.unmarshall(ParcelFn.marshall(presenterState));
  }

  /**
   * {@link android.app.Activity#onCreate(Bundle)}, {@link android.app.Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}, {@link android.view.View#onAttachedToWindow()}
   */
  public void onTakeView(Object view) {
    getPresenter();
    if (presenter != null) {
      presenter.takeView(view);
    }
  }

  /**
   * {@link Activity#onDestroy()}, {@link Fragment#onDestroy()}, {@link android.view.View#onAttachedToWindow()}
   */
  public void onDropView() {
    getPresenter();
    if (presenter != null) {
      presenter.dropView();
    }
  }

  /**
   * {@link android.app.Activity#onResume()}, {@link android.app.Fragment#onResume()}
   */
  public void onResume() {
    getPresenter();
    if (presenter != null) {
      presenter.resume();
    }
  }

  /**
   * {@link android.app.Activity#onPause()}, {@link android.app.Fragment#onPause()}, {@link android.view.View#onDetachedFromWindow()}
   */
  public void onPause() {
    if (presenter != null) {
      presenter.pause();
    }
  }

  /**
   * {@link android.app.Activity#onPause()}, {@link android.app.Fragment#onPause()}, {@link android.view.View#onDetachedFromWindow()}
   */
  public void onDestroy(boolean isFinish) {
    if (presenter != null) {
      onDropView();
      if (isFinish) {
        presenter.destroy();
        presenter = null;
      }
    }
  }

  public void onNewIntent(Intent intent) {
    if (presenter != null) {
      presenter.newIntent(intent);
    }
  }


  public void onVisible() {
    getPresenter();
    if (presenter != null) {
      presenter.onVisible();
    }
  }

  public void onInVisible() {
    getPresenter();
    if (presenter != null) {
      presenter.onInVisible();
    }
  }

}
