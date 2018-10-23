package com.github.boybeak.hoverbarlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;

public class HoverBarLayout extends LinearLayoutCompat {

    public HoverBarLayout(Context context) {
        super(context);
        initThis(context);
    }

    public HoverBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initThis(context);
    }

    public HoverBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initThis(context);
    }

    private void initThis(Context context) {
        setOrientation(VERTICAL);
    }

    public static class Behavior extends CoordinatorLayout.Behavior<HoverBarLayout> {
        public Behavior() {
        }
        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull HoverBarLayout child, int layoutDirection) {
            return super.onLayoutChild(parent, child, layoutDirection);
        }
    }

}