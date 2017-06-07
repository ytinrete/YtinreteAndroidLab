package com.ytinrete.normal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;
import com.ytinrete.tools.EnhancedSpannableStringBuilder;

/**
 *
 */

public class NormalTest1Activity extends YtBaseActivity {

  private TextView tv1;
  private TextView tv2;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_normal_text1);

    tv1 = (TextView)findViewById(R.id.normal_text1_tv1);
    tv2 = (TextView)findViewById(R.id.normal_text1_tv2);

    String a = "abc";
    String b = "456";

    String c = "def";
    String d = "789";
    String e = "kkk";

    int colorBlack = getResources().getColor(R.color.md_black_1000);
    int colorGray = getResources().getColor(R.color.md_blue_grey_200);

    EnhancedSpannableStringBuilder b1 = new EnhancedSpannableStringBuilder();
    b1.appendWithColor(a+" "+b, colorGray);

    EnhancedSpannableStringBuilder b2 = new EnhancedSpannableStringBuilder();
    b2.appendWithColor(c+" ", colorBlack);
    b2.appendWithStrikethroughAndColor(d, colorGray);
    b2.appendWithColor(" "+e, colorBlack);

    tv1.setText(b1);
    tv2.setText(b2);

  }



}
