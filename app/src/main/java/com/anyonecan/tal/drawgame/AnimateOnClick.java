package com.anyonecan.tal.drawgame;

import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

public abstract class AnimateOnClick {
    View layout = null;
    int duration = 1000;
    int repeat = 1;
    Animation anim;

    protected AnimateOnClick(View layout) {
        this.layout = layout;
    }

    protected AnimateOnClick(View layout, int repeat) {
        this(layout);
        this.repeat = repeat;
    }

    protected abstract Animation createAnimation();

    private void run(Animation.AnimationListener animListener) {
        layout.setClickable(false);
        anim = createAnimation();
        anim.setDuration(duration); // 1000 ms = 1second
        anim.setRepeatCount(repeat);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setAnimationListener(animListener);
        layout.startAnimation(anim);
    }

    public void click() {
        Navigation navigate = new Navigation() {
            @Override
            public void run() {
                layout.setClickable(true);
            }
        };
        run(new AnimListener(navigate));
    }

    public void click(Animation.AnimationListener animListener) {
        run(animListener);
        //layout.setClickable(true);
    }

    public void click(final Navigation navigate) {
        Navigation newNavigate = new Navigation() {
            @Override
            public void run() {
                navigate.run();
                layout.setClickable(true);
            }
        };
        run(new AnimListener(newNavigate));
    }
}

