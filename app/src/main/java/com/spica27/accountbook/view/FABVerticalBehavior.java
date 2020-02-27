package com.spica27.accountbook.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FABVerticalBehavior extends FloatingActionButton.Behavior {

  private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
  private boolean mIsAnimatingOut = false;

  public FABVerticalBehavior(Context context, AttributeSet attrs) {
    super();
  }


  @Override
  public boolean onStartNestedScroll(
      @NonNull CoordinatorLayout coordinatorLayout,
      @NonNull FloatingActionButton child,
      @NonNull View directTargetChild,
      @NonNull View target,
      int axes,
      int type) {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL
        || super.onStartNestedScroll(
            coordinatorLayout, child, directTargetChild, target, axes, type);
  }

  @Override
  public void onNestedScroll(
      @NonNull CoordinatorLayout coordinatorLayout,
      @NonNull FloatingActionButton child,
      @NonNull View target,
      int dxConsumed,
      int dyConsumed,
      int dxUnconsumed,
      int dyUnconsumed,
      int type,
      @NonNull int[] consumed) {
    super.onNestedScroll(
        coordinatorLayout,
        child,
        target,
        dxConsumed,
        dyConsumed,
        dxUnconsumed,
        dyUnconsumed,
        type,
        consumed);
    if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
      //隐藏Fab
      animateOut(child);
    } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
      //显示Fab
      animateIn(child);
    }
  }

  private void animateOut(final FloatingActionButton button) {
    if (Build.VERSION.SDK_INT >= 14) {
      ViewCompat.animate(button)
          .translationY(button.getHeight() + getMarginBottom(button))
          .setInterpolator(INTERPOLATOR)
          .withLayer()
          .setListener(
              new ViewPropertyAnimatorListener() {
                public void onAnimationStart(View view) {
                  FABVerticalBehavior.this.mIsAnimatingOut = true;
                }

                public void onAnimationCancel(View view) {
                  FABVerticalBehavior.this.mIsAnimatingOut = false;
                }

                public void onAnimationEnd(View view) {
                  FABVerticalBehavior.this.mIsAnimatingOut = false;
                  view.setVisibility(View.GONE);
                }
              })
          .start();
    } else {

    }
  }

  private void animateIn(FloatingActionButton button) {
    button.setVisibility(View.VISIBLE);
    if (Build.VERSION.SDK_INT >= 14) {
      ViewCompat.animate(button)
          .translationY(0)
          .setInterpolator(INTERPOLATOR)
          .withLayer()
          .setListener(null)
          .start();
    } else {

    }
  }

  private int getMarginBottom(View v) {
    int marginBottom = 0;
    final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
      marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
    }
    return marginBottom;
  }
}
