package com.iamrajendra.analogclock.animation;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class CardViewAnimation {
    public static void collapseView(final View itemView, final View view,int minHeight) {

        ValueAnimator anim = ValueAnimator.ofInt(itemView.getMeasuredHeightAndState(),
                minHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                layoutParams.height = val;
                itemView.setLayoutParams(layoutParams);
                view.setVisibility(View.GONE);

            }
        });
        anim.start();
    }
    public static void expandView(final View itemView, final View view,int height) {

        ValueAnimator anim = ValueAnimator.ofInt(itemView.getMeasuredHeightAndState(),
                height);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                layoutParams.height = val;
                itemView.setLayoutParams(layoutParams);
                view.setVisibility(View.VISIBLE);
            }
        });
        anim.start();

    }
}
