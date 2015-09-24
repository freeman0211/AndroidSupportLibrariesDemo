package com.freeman.example.apitest.animator;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.os.Bundle;

import com.freeman.example.apitest.R;

/**
 * Created by freeman on 15/8/20.
 */
public class ActivityAnimatorTest extends Activity {

    private AnimatorView animatorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator_test_layout);


        animatorView = (AnimatorView) findViewById(R.id.animator_test_view);

        /*ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setTarget(animatorView);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorView.setRotationX((Float) animation.getAnimatedValue());
            }
        });
//        valueAnimator.start();

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(animatorView, "imageAlpha", 0x00, 0xff);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
//        objectAnimator.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator, objectAnimator);
//        animatorSet.playSequentially(valueAnimator, objectAnimator);
//        animatorSet.play(objectAnimator).before(valueAnimator);
        animatorSet.start();


        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 400f, 50f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 400f, 100f);
        ObjectAnimator.ofPropertyValuesHolder(animatorView, pvhX, pvhY).setDuration(2000).start();

        animatorView.animate().scaleX(2.0f).scaleY(2.0f);*/

        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_test_anim);
        animator.setTarget(animatorView);
        animator.start();

    }
}