package com.ytinrete.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 *
 */

public class NotInterceptTouchFrameLayout extends FrameLayout {
  public NotInterceptTouchFrameLayout(@NonNull Context context) {
    super(context);
  }

  public NotInterceptTouchFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public NotInterceptTouchFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }


  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return super.onInterceptTouchEvent(ev);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
//    return super.onTouchEvent(event);

    if(event.getAction()==MotionEvent.ACTION_DOWN){
      performClick();
    }

    return false;
  }

}
