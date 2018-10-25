package com.github.boybeak.hoverbarlayout;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;

public class FloatingActionMenuBehavior extends CoordinatorLayout.Behavior<FloatingActionMenu> {

    private static final String TAG = FloatingActionMenuBehavior.class.getSimpleName();

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull FloatingActionMenu child, int layoutDirection) {

        return false;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FloatingActionMenu child, @NonNull View dependency) {
//        Log.v(TAG, "layoutDependsOn dependency=" + dependency.getClass().getName());
//        child.setActionPosition(dependency.getLeft(), dependency.getTop(), dependency.getRight(), dependency.getBottom());
        return true;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull FloatingActionMenu child, @NonNull View dependency) {
        Log.v(TAG, "onDependentViewChanged dependency=" + dependency.getClass().getName());
        Log.v(TAG, "setActionPosition l=" + dependency.getLeft() + " t=" + dependency.getTop()
                + " r=" + dependency.getRight() + " b=" + dependency.getBottom());
        child.setActionPosition(dependency.getLeft(), dependency.getTop(), dependency.getRight(), dependency.getBottom());
        return false;
    }

    @Override
    public void onDependentViewRemoved(@NonNull CoordinatorLayout parent, @NonNull FloatingActionMenu child, @NonNull View dependency) {
//        Log.v(TAG, "onDependentViewRemoved dependency=" + dependency.getClass().getName());
        super.onDependentViewRemoved(parent, child, dependency);
    }


}