package com.ytinrete.mvp.presenter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This is a base class for all presenters. Subclasses can override
 * {@link #onCreate}, {@link #onDestroy}, {@link #onSave},
 * {@link #onTakeView}, {@link #onDropView}.
 * <p/>
 * {@link Presenter.OnDestroyListener} can also be used by external classes
 * to be notified about the need of freeing resources.
 *
 * @param <View> a type of view to return with {@link #getView()}.
 */
public class Presenter<View> {

  @Nullable
  private View view;
  private CopyOnWriteArrayList<OnDestroyListener> onDestroyListeners = new CopyOnWriteArrayList<>();

  /**
   * This method is called after presenter construction.
   * <p/>
   * This method is intended for overriding.
   *
   * @param savedState If the presenter is being re-instantiated after a process restart then this Bundle
   *                   contains the data it supplied in {@link #onSave}.
   */
  protected void onCreate(@Nullable Bundle savedState) {
  }

  /**
   * This method is being called when a user leaves view.
   * <p/>
   * This method is intended for overriding.
   */
  protected void onDestroy() {
  }

  protected void onActivityNewIntent(Intent intent) {
  }

  protected void onActivityStart() {
  }

  protected void onActivityStop() {
  }

  /**
   * This method is being called when a activity/frament resume
   * <p/>
   * This method is intended for overriding.
   */
  protected void onResume() {
  }

  /**
   * This method is being called when a activity/frament pause
   * <p/>
   * This method is intended for overriding.
   */
  protected void onPause() {
  }

  /**
   * A returned state is the state that will be passed to {@link #onCreate} for a new presenter instance after a process restart.
   * <p/>
   * This method is intended for overriding.
   *
   * @param state a non-null bundle which should be used to put presenter's state into.
   */
  protected void onSave(Bundle state) {
  }

  /**
   * This method is being called when a view gets attached to it.
   * Normally this happens during {@link Activity#onResume()}, {@link android.app.Fragment#onResume()}
   * and {@link android.view.View#onAttachedToWindow()}.
   * <p/>
   * This method is intended for overriding.
   *
   * @param view a view that should be taken
   */
  protected void onTakeView(View view) {
  }

  /**
   * This method is being called after a view created.
   * Normally this happens during {@link Activity#onStart()} }, {@link android.app.Fragment#onActivityCreated(Bundle)}
   * <p/>
   * This method is intended for overriding.
   */
  protected void onViewCreated() {
  }

  /**
   * This method is being called when a view gets detached from the presenter.
   * Normally this happens during {@link Activity#onPause()} ()}, {@link Fragment#onPause()} ()}
   * and {@link android.view.View#onDetachedFromWindow()}.
   * <p/>
   * This method is intended for overriding.
   */
  protected void onDropView() {
  }

  /**
   * A callback to be invoked when a presenter is about to be destroyed.
   */
  public interface OnDestroyListener {
    /**
     * Called before {@link Presenter#onDestroy()}.
     */
    void onDestroy();
  }

  /**
   * Adds a listener observing {@link #onDestroy}.
   *
   * @param listener a listener to add.
   */
  public void addOnDestroyListener(OnDestroyListener listener) {
    onDestroyListeners.add(listener);
  }

  /**
   * Removed a listener observing {@link #onDestroy}.
   *
   * @param listener a listener to remove.
   */
  public void removeOnDestroyListener(OnDestroyListener listener) {
    onDestroyListeners.remove(listener);
  }

  /**
   * Returns a current view attached to the presenter or null.
   * <p/>
   * View is normally available between
   * {@link Activity#onResume()} and {@link Activity#onPause()},
   * {@link Fragment#onResume()} and {@link Fragment#onPause()},
   * {@link android.view.View#onAttachedToWindow()} and {@link android.view.View#onDetachedFromWindow()}.
   * <p/>
   * Calls outside of these ranges will return null.
   * Notice here that {@link Activity#onActivityResult(int, int, Intent)} is called *before* {@link Activity#onResume()}
   * so you can't use this method as a callback.
   *
   * @return a current attached view.
   */
  @Nullable
  public View getView() {
    return view;
  }

  /**
   * Initializes the presenter.
   */
  public void create(Bundle bundle) {
    onCreate(bundle);
  }

  /**
   * Destroys the presenter, calling all {@link Presenter.OnDestroyListener} callbacks.
   */
  public void destroy() {
    for (OnDestroyListener listener : onDestroyListeners) {
      listener.onDestroy();
    }
    onDestroy();
  }

  /**
   * when * {@link android.app.Activity#onResume()}, {@link android.app.Fragment#onResume()}
   */
  public void resume() {
    onResume();
  }


  /**
   * when * {@link Activity#onPause()}, {@link Fragment#onPause()}
   */
  public void pause() {
    onPause();
  }

  /**
   * when * {@link Activity#onStart()}
   */
  public void start(){
    onActivityStart();
  }

  /**
   * when * {@link Activity#onStop()}
   */
  public void stop(){
    onActivityStop();
  }

  public void newIntent(Intent intent) {
    onActivityNewIntent(intent);
  }


  /**
   * Saves the presenter.
   */
  public void save(Bundle state) {
    onSave(state);
  }

  /**
   * Attaches a view to the presenter.
   *
   * @param view a view to attach.
   */
  public void takeView(View view) {
    this.view = view;
    onTakeView(view);
  }

  /**
   * after view created
   */
  public void viewCreated() {
    onViewCreated();
  }

  /**
   * Detaches the presenter from a view.
   */
  public void dropView() {
    onDropView();
    this.view = null;
  }

  public void onVisible() {

  }

  public void onInVisible() {

  }

}
