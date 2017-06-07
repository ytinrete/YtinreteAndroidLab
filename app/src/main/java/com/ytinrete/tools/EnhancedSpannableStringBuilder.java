package com.ytinrete.tools;


import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;

/**
 *
 */

public class EnhancedSpannableStringBuilder extends SpannableStringBuilder {
  public EnhancedSpannableStringBuilder() {
  }

  public EnhancedSpannableStringBuilder(CharSequence text) {
    super(text);
  }

  public EnhancedSpannableStringBuilder append(CharSequence text) {
    if (TextUtils.isEmpty(text)) {
      return this;
    } else {
      super.append(text);
      return this;
    }
  }

  public EnhancedSpannableStringBuilder append(Object object) {
    return this.append((CharSequence) object.toString());
  }

  public EnhancedSpannableStringBuilder appendWithSize(CharSequence text, int size) {
    if (TextUtils.isEmpty(text)) {
      return this;
    } else {
      int start = this.length();
      this.append(text);
      this.setSpan(new AbsoluteSizeSpan(size), start, this.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      return this;
    }
  }

  public EnhancedSpannableStringBuilder appendWithColor(CharSequence text, int color) {
    if (TextUtils.isEmpty(text)) {
      return this;
    } else {
      int start = this.length();
      this.append(text);
      this.setSpan(new ForegroundColorSpan(color), start, this.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      return this;
    }
  }

  public EnhancedSpannableStringBuilder appendWithColorAndSize(CharSequence text, int color, int proportion) {
    if (TextUtils.isEmpty(text)) {
      return this;
    } else {
      int start = this.length();
      this.append(text);
      this.setSpan(new ForegroundColorSpan(color), start, this.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      this.setSpan(new AbsoluteSizeSpan(proportion), start, this.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      return this;
    }
  }

  public EnhancedSpannableStringBuilder appendWithStrikethrough(CharSequence text) {
    if (TextUtils.isEmpty(text)) {
      return this;
    } else {
      int start = this.length();
      this.append(text);
      this.setSpan(new StrikethroughSpan(), start, this.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      return this;
    }
  }

  public EnhancedSpannableStringBuilder appendWithStrikethroughAndColor(CharSequence text, int color) {
    if (TextUtils.isEmpty(text)) {
      return this;
    } else {
      int start = this.length();
      this.append(text);
      this.setSpan(new StrikethroughSpan(), start, this.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      this.setSpan(new ForegroundColorSpan(color), start, this.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      return this;
    }
  }
}

