package com.github.boybeak.hoverbarlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

@CoordinatorLayout.DefaultBehavior(FloatingActionMenuBehavior.class)
public class FloatingActionMenu extends FrameLayout  {

    private static final String TAG = FloatingActionMenu.class.getSimpleName();

    public FloatingActionMenu(@NonNull Context context) {
        super(context);
    }

    public FloatingActionMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingActionMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FloatingActionMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setActionPosition(int l, int t, int r, int b) {
        ViewCompat.offsetTopAndBottom(getChildAt(0), getHeight() - t);
        Log.v(TAG, "setActionPosition l=" + l + " t=" + t + " r=" + r + " b=" + b);
    }

}
