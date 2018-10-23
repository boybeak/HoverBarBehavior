package com.github.boybeak.hoverbarlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

public class HoverBarBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private static final String TAG = HoverBarBehavior.class.getSimpleName();

    private int mMinPosition = 0, mHoverPosition = 0;
    private WeakReference<LinearLayout> mChildRef = null;

    private ViewDragHelper mDragHelper = null;
    private ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return mChildRef != null && mChildRef.get() != null && view == mChildRef.get();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return 1;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return Math.min(Math.max(top, mMinPosition), mHoverPosition);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            boolean settle;
            if (yvel < 0) {
                settle = mDragHelper.settleCapturedViewAt(0, mMinPosition);
            } else {
                settle = mDragHelper.settleCapturedViewAt(0, mHoverPosition);
            }
            if (settle) {
                ViewCompat.postOnAnimation(releasedChild, new SettleRunnable(releasedChild));
            }
        }
    };

    public HoverBarBehavior() {
    }

    public HoverBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void makeSureViewDragHelper(ViewGroup target) {
        if (mDragHelper == null) {
            mDragHelper = ViewDragHelper.create(target, mDragCallback);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull MotionEvent ev) {
        if (mDragHelper != null) {
            mDragHelper.processTouchEvent(ev);
        }
        return true;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, int layoutDirection) {

        parent.onLayoutChild(child, layoutDirection);

        mChildRef = new WeakReference<>(child);
        mMinPosition = parent.getHeight() - child.getHeight();

        final int grandsonCount = child.getChildCount();
        if (grandsonCount == 0) {
            mHoverPosition = parent.getHeight();
        } else {
            View grandson0 = child.getChildAt(0);
            mHoverPosition = parent.getHeight() - grandson0.getHeight();
        }

        ViewCompat.offsetTopAndBottom(child, mHoverPosition);

        makeSureViewDragHelper(parent);

        return true;
    }

    private class SettleRunnable implements Runnable {

        private final View mView;

        SettleRunnable( View view) {
            mView = view;
        }

        @Override
        public void run() {
            if ( mDragHelper != null  &&  mDragHelper.continueSettling( true ) ) {
                ViewCompat.postOnAnimation( mView, this );
            }
        }
    }

}