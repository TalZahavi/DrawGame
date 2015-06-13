package com.anyonecan.tal.drawgame;

import android.view.animation.Animation;

public class AnimListener implements Animation.AnimationListener {
    private Navigation navigate = null;

    public AnimListener(Navigation navigate) {
        this.navigate = navigate;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        navigate.run();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
