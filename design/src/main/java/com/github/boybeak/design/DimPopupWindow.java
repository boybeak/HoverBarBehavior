package com.github.boybeak.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.PopupWindow;

public class DimPopupWindow extends PopupWindow {

    private Drawable dimDrawable;
    private ViewGroup dimView;

    private int animDuration;

    public DimPopupWindow(Context context) {
        super(context);
        initThis(context);
    }

    public DimPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initThis(context);
    }

    public DimPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initThis(context);
    }

    public DimPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initThis(context);
    }

    private void initThis(Context context) {
        animDuration = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    public DimPopupWindow setDimView(ViewGroup dimView) {
        this.dimView = dimView;
        return this;
    }

    public boolean isDimable() {
        return dimView != null;
    }

    public void applyDim(){
        if(dimView == null) {
            return;
        }
        int alpha = (int) (255 * 0.6f);
        dimDrawable = new ColorDrawable(Color.BLACK);
        dimDrawable.setAlpha(0);
        dimDrawable.setBounds(0, 0, dimView.getWidth(), dimView.getHeight());
        ViewGroupOverlay overlay = dimView.getOverlay();
        overlay.add(dimDrawable);
        ObjectAnimator.ofInt(dimDrawable, "alpha", dimDrawable.getAlpha(), alpha)
                .setDuration(animDuration).start();
    }

    public void clearDim() {
        if(dimView == null) {
            return;
        }
        ObjectAnimator oa = ObjectAnimator.ofInt(dimDrawable, "alpha", dimDrawable.getAlpha(), 0);
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroupOverlay overlay = dimView.getOverlay();
                overlay.clear();
                animation.removeAllListeners();
                dimView = null;
                dimDrawable = null;
            }
        });
        oa.setDuration(animDuration).start();

    }
}
