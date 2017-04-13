package com.ytinrete.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ytinrete.android.lab.BuildConfig;
import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;
import com.ytinrete.exp.YtExpActivity;
import com.ytinrete.main.YtMainActivity;
import com.ytinrete.tools.CommonTools;
import com.ytinrete.tools.l;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class YtSplashActivity extends YtBaseActivity {

  /**
   * // 带有 Button 参数
   *
   * @OnClick(R.id.submit) public void sayHi(Button button) {
   * button.setText("Hello!");
   * }
   * <p>
   * // 不带参数
   * @OnClick(R.id.submit) public void submit() {
   * // TODO submit data to server...
   * }
   * <p>
   * // 同时注入多个 View 事件
   * @OnClick({ R.id.door1, R.id.door2, R.id.door3 })
   * public void pickDoor(DoorView door) {
   * if (door.hasPrizeBehind()) {
   * Toast.makeText(this, "You win!", LENGTH_SHORT).show();
   * } else {
   * Toast.makeText(this, "Try again", LENGTH_SHORT).show();
   * }
   * }
   */


  @BindView(R.id.splash_logo)
  View logo;
  @BindView(R.id.splash_mask)
  View mask;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    Thread.currentThread().setName("ytMainThread");
    l.d("--------YtSplashActivity-------");
    CommonTools.getPID(this);
    l.d("--------YtSplashActivity-------");

    ButterKnife.bind(this);

    if (BuildConfig.DEBUG) {

      startActivity(new Intent(YtSplashActivity.this, YtExpActivity.class));
      finish();

      return;
    }

    AnimatorSet animatorLogo = new AnimatorSet();//组合动画
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(logo, "scaleX", 1.3f, 1f);
    scaleX.setDuration(700);
    ObjectAnimator scaleY = ObjectAnimator.ofFloat(logo, "scaleY", 1.3f, 1f);
    scaleY.setDuration(700);
    ObjectAnimator fadeIn = ObjectAnimator.ofFloat(logo, "alpha", 0.0f, 1.0f);
    fadeIn.setDuration(600).setStartDelay(100);


    ObjectAnimator maskDown = ObjectAnimator.ofFloat(mask, "scaleY", 1.0f, 0.0f);
    maskDown.setDuration(400);
    maskDown.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {
        mask.setPivotY(mask.getHeight());
      }

      @Override
      public void onAnimationEnd(Animator animation) {

      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });

    ValueAnimator delay = ValueAnimator.ofFloat(0f, 1f);
    delay.setDuration(1000);

    animatorLogo.setInterpolator(new DecelerateInterpolator());
    animatorLogo.play(scaleX).with(scaleY).with(fadeIn).before(maskDown).before(delay);

    animatorLogo.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {

        startActivity(new Intent(YtSplashActivity.this, YtMainActivity.class));
        finish();
      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
    animatorLogo.start();

  }

  /**
   *
   * ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 0.0f, 350.0f, 0f);
   animator.setDuration(2500).start();

   ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 1.5f);
   animator.setDuration(2000).start();

   ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotationX", 0.0f, 90.0f,0.0F);
   animator.setDuration(2000).start();

   ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.3f, 1.0F);
   animator.setDuration(2000);//动画时间

   setInterpolator()：设置动画插值
   setDuration()：设置动画执行时间
   setRepeatCount()：设置动画重复次数
   setRepeatMode()：设置动画重复模式
   setStartDelay():设置动画延时操作
   setTarget():设置动画的对象
   setEvaluator()：设置动画过度的评估者。
   详细动画配置如下：

   ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.3f, 1.0F);
   animator.setDuration(2000);//动画时间
   animator.setInterpolator(new BounceInterpolator());//动画插值
   animator.setRepeatCount(-1);//设置动画重复次数
   animator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式
   animator.setStartDelay(1000);//动画延时执行
   animator.start();//启动动画

   开始图片的透明度从不透明到0.2的透明再到不透明,随着整个布局背景的颜色变化的同时ImageView先向右平移200个像素，然后再放大2倍，最后沿着X轴从0到90度再到0度的旋转。

   ObjectAnimator animator = ObjectAnimator.ofInt(container, "backgroundColor", 0xFFFF0000, 0xFFFF00FF);
   ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "translationX", 0.0f, 200.0f, 0f);
   ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 2.0f);
   ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView, "rotationX", 0.0f, 90.0f, 0.0F);
   ObjectAnimator animator4 = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.2f, 1.0F);

   //组合动画方式1
   AnimatorSet set = new AnimatorSet();
   ((set.play(animator).with(animator1).before(animator2)).before(animator3)).after(animator4);
   set.setDuration(5000);
   set.start();
   *
   *
   */


}
