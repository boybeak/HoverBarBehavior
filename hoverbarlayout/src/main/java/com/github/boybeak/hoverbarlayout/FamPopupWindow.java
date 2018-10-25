package com.github.boybeak.hoverbarlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.*;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.*;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

public class FamPopupWindow extends PopupWindow {

    private static final String TAG = FamPopupWindow.class.getSimpleName();

    private Drawable dimDrawable;
    private ViewGroup dimView;
    private View anchorView;
    private LinearLayoutCompat container;
    private int iconSize, offsetY;
    private List<FamItem> famItems;

    private int animDuration = 0;
    private AnimatorSet animatorSet;

    private OnItemSelectedListener onItemSelectedListener;
    private OnCreatedListener onCreatedListener;

    public FamPopupWindow(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FamPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FamPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FamPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        iconSize = Utils.dpToPixel(context, 48);
        offsetY = -Utils.dpToPixel(context, 8);
        animDuration = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);

    }

    public FamPopupWindow setDimView(ViewGroup dimView) {
        this.dimView = dimView;
        return this;
    }

    public void show(FloatingActionButton fab, List<FamItem> items) {
        if (animatorSet != null && animatorSet.isRunning()) {
            return;
        }

        container = new LinearLayoutCompat(fab.getContext());
        container.setOrientation(LinearLayoutCompat.VERTICAL);
        container.setGravity(Gravity.END|Gravity.BOTTOM);
        setContentView(container);

        this.anchorView = fab;
        this.famItems = items;
        container.removeAllViews();

        animatorSet = new AnimatorSet();
        ObjectAnimator fabRotate = ObjectAnimator.ofFloat(fab, "rotation", 0, 45f);
        AnimatorSet.Builder ab = animatorSet.play(fabRotate);

        final int size = items.size();
        for (int i = 0; i < size; i++) {
            final FamItem item = items.get(i);
            FamItemView child = new FamItemView(fab.getContext());
            FloatingActionButton icon = child.iconFab();
            AppCompatTextView title = child.titleTextView();
            icon.setImageDrawable(item.getIcon());
            title.setText(item.getTitle());
            if (TextUtils.isEmpty(item.getTitle())) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(item.getTitle());
            }
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemSelectedListener != null) {
                        if (onItemSelectedListener.onItemSelected(item)) {
                            dismiss();
                        }
                    }
                }
            });

            LinearLayoutCompat.LayoutParams p = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            p.bottomMargin = Utils.dpToPixel(fab.getContext(), 12);
            container.addView(child, p);

            child.setAlpha(0);
            child.measure(0, 0);
            icon.setPivotX(icon.getMeasuredWidth() / 2);
            icon.setPivotY(icon.getMeasuredHeight() / 2);
            icon.setScaleX(0);
            icon.setScaleY(0);

            ObjectAnimator oaa = ObjectAnimator.ofFloat(child, "alpha", child.getAlpha(), 1f);
            ObjectAnimator oasx = ObjectAnimator.ofFloat(icon, "scaleX", icon.getScaleX(), 1f);
            ObjectAnimator oasy = ObjectAnimator.ofFloat(icon, "scaleY", icon.getScaleY(), 1f);
            int delay = animDuration * (size - i) / size;
            oaa.setDuration(animDuration).setStartDelay(delay);
            oasx.setDuration(animDuration).setStartDelay(delay);
            oasy.setDuration(animDuration).setStartDelay(delay);
            ab.with(oaa).with(oasx).with(oasy);
        }

        container.measure(0, 0);
        setWidth(container.getMeasuredWidth());
        setHeight(container.getMeasuredHeight());
        showAsDropDown(fab, (iconSize - fab.getWidth()) / 2, offsetY, Gravity.END | Gravity.TOP);
        if (dimView != null) {
            applyDim();
        }
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animation.removeAllListeners();
                if (onCreatedListener != null) {
                    List<FamItemView> itemViews = new ArrayList<>();
                    for (int i = 0; i < container.getChildCount(); i++) {
                        FamItemView itemView = (FamItemView)container.getChildAt(i);
                        itemViews.add(itemView);
                    }
                    onCreatedListener.onCreated(famItems, itemViews);
                    itemViews.clear();
                }
            }
        });
        animatorSet.start();
    }

    @Override
    public void dismiss() {
        if (animatorSet != null && animatorSet.isRunning()) {
            return;
        }
        animatorSet = new AnimatorSet();
        ObjectAnimator fabRotate = ObjectAnimator.ofFloat(anchorView, "rotation", 45f, 0);
        AnimatorSet.Builder ab = animatorSet.play(fabRotate);
        final int size = container.getChildCount();
        for (int i = 0; i < size; i++) {
            FamItemView child = (FamItemView)container.getChildAt(i);
            FloatingActionButton icon = child.iconFab();
            ObjectAnimator oaa = ObjectAnimator.ofFloat(child, "alpha", child.getAlpha(), 0f);
            ObjectAnimator oasx = ObjectAnimator.ofFloat(icon, "scaleX", icon.getScaleX(), 0f);
            ObjectAnimator oasy = ObjectAnimator.ofFloat(icon, "scaleY", icon.getScaleY(), 0f);
            int delay = animDuration * i / size;
            oaa.setDuration(animDuration).setStartDelay(delay);
            oasx.setDuration(animDuration).setStartDelay(delay);
            oasy.setDuration(animDuration).setStartDelay(delay);
            ab.with(oaa).with(oasx).with(oasy);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animation.removeAllListeners();
                doDismiss();
            }
        });
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();

        if (dimView != null) {
            clearDim();
        }
    }

    private void doDismiss() {
        FamPopupWindow.super.dismiss();

        container = null;
        anchorView = null;
        famItems = null;

        onItemSelectedListener = null;
        onCreatedListener = null;
    }


    private void applyDim(){
        int alpha = (int) (255 * 0.6f);
        dimDrawable = new ColorDrawable(Color.BLACK);
        dimDrawable.setAlpha(0);
        dimDrawable.setBounds(0, 0, dimView.getWidth(), dimView.getHeight());
        ViewGroupOverlay overlay = dimView.getOverlay();
        overlay.add(dimDrawable);
        ObjectAnimator.ofInt(dimDrawable, "alpha", dimDrawable.getAlpha(), alpha)
                .setDuration(animDuration).start();
    }

    private void clearDim() {
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

    public FamPopupWindow setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
        return this;
    }

    public FamPopupWindow setOnCreatedListener(OnCreatedListener onCreatedListener) {
        this.onCreatedListener = onCreatedListener;
        return this;
    }

    public interface OnItemSelectedListener {
        boolean onItemSelected(FamItem item);
    }

    public interface OnCreatedListener {
        void onCreated(List<FamItem> famItems, List<FamItemView> famItemViews);
    }

}