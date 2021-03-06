package com.github.boybeak.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class FloatingActionMenu extends DimPopupWindow {

    private static final String TAG = FloatingActionMenu.class.getSimpleName();

    private ViewGroup parent;
    private View anchorView;
    private LinearLayoutCompat container;
    private int iconSize, offsetY;
    private DesignMenu designMenu;

    private int animDuration = 240;
    private AnimatorSet animatorSet;

    private boolean showTitle, fullyIcon;

    private OnItemSelectedListener onItemSelectedListener;
    private OnCreatedListener onCreatedListener;

    public FloatingActionMenu(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FloatingActionMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingActionMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FloatingActionMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        iconSize = Utils.dpToPixel(context, 48);
        offsetY = Utils.dpToPixel(context, 4);
//        animDuration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);

    }

    public FloatingActionMenu showTitle(boolean showTitle) {
        this.showTitle = showTitle;
        return this;
    }

    public FloatingActionMenu fullyIcon(boolean fullyIcon) {
        this.fullyIcon = fullyIcon;
        return this;
    }

    public FloatingActionMenu anchorView (FloatingActionButton fab) {
        this.anchorView = fab;
        if (parent == null) {
            parent((ViewGroup) fab.getParent());
        }
        return this;
    }

    public FloatingActionMenu parent (ViewGroup parent) {
        this.parent = parent;
        return this;
    }

    public FloatingActionMenu inflate(@MenuRes int menuRes) {
        if (anchorView == null) {
            throw new IllegalStateException("call setAnchorView before setDesignMenuItems");
        }
        designMenu = new DesignMenu(anchorView.getContext());
        new MenuInflater(anchorView.getContext()).inflate(menuRes, designMenu);
        onMenuCreated();
        return this;
    }

    public FloatingActionMenu setItems(List<DesignMenuItem> items) {
        if (anchorView == null) {
            throw new IllegalStateException("call setAnchorView before setDesignMenuItems");
        }
        designMenu = new DesignMenu(anchorView.getContext());
        for (DesignMenuItem item : items) {
            designMenu.add(item);
        }
        onMenuCreated();
        return this;
    }

    private void onMenuCreated() {
        container = new LinearLayoutCompat(anchorView.getContext());
        container.setOrientation(LinearLayoutCompat.VERTICAL);
        container.setGravity(Gravity.END|Gravity.BOTTOM);

        final int size = designMenu.size();
        for (int i = 0; i < size; i++) {
            final DesignMenuItem item = designMenu.getItem(i);
            FamItemView child = new FamItemView(anchorView.getContext(), showTitle, fullyIcon);
            FloatingActionButton icon = child.iconFab();
            AppCompatTextView title = child.titleTextView();
            icon.setImageDrawable(item.getIcon());
            title.setText(item.getTitle());
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
            p.bottomMargin = Utils.dpToPixel(anchorView.getContext(), 8);
            container.addView(child, p);

            child.setAlpha(0);
            child.measure(0, 0);
            icon.setPivotX(icon.getMeasuredWidth() / 2);
            icon.setPivotY(icon.getMeasuredHeight() / 2);
            icon.setScaleX(0);
            icon.setScaleY(0);

        }

        container.measure(0, 0);
        super.setWidth(container.getMeasuredWidth());
        super.setHeight(container.getMeasuredHeight());

        super.setContentView(container);
    }

    public void show() {
        if (container == null) {
            throw new IllegalStateException("call setDesignMenuItems before show");
        }
        if (animatorSet != null && animatorSet.isRunning()) {
            return;
        }

        int x = parent.getWidth() - anchorView.getRight() + (anchorView.getWidth() - iconSize) / 2;
        int y = parent.getHeight() - anchorView.getTop() + anchorView.getHeight() + offsetY;
        showAtLocation(parent, Gravity.END | Gravity.BOTTOM, x, y);

        if (isDimEnable()) {
            applyDim();
        }

        animatorSet = new AnimatorSet();
        ObjectAnimator fabRotate = ObjectAnimator.ofFloat(anchorView, "rotation", 0, 45f);
        AnimatorSet.Builder ab = animatorSet.play(fabRotate);

        final int size = container.getChildCount();
        for (int i = size - 1; i >= 0; i--) {
            FamItemView child = (FamItemView)container.getChildAt(i);
            FloatingActionButton icon = child.iconFab();
            ObjectAnimator oaa = ObjectAnimator.ofFloat(child, "alpha", child.getAlpha(), 1f);
            ObjectAnimator oasx = ObjectAnimator.ofFloat(icon, "scaleX", icon.getScaleX(), 1f);
            ObjectAnimator oasy = ObjectAnimator.ofFloat(icon, "scaleY", icon.getScaleY(), 1f);
            int delay = animDuration * (size - i) / size;
            oaa.setDuration(animDuration).setStartDelay(delay);
            oasx.setDuration(animDuration).setStartDelay(delay);
            oasy.setDuration(animDuration).setStartDelay(delay);
            ab.with(oaa).with(oasx).with(oasy);
        }

        animatorSet.setInterpolator(new DecelerateInterpolator());
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
                    onCreatedListener.onCreated(designMenu, itemViews);
                    itemViews.clear();

                }
            }
        });
        animatorSet.start();
    }

    @Override
    public FloatingActionMenu setDimView(ViewGroup dimView) {
        super.setDimView(dimView);
        return this;
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

        if (isDimEnable()) {
            clearDim();
        }
    }

    private void doDismiss() {
        FloatingActionMenu.super.dismiss();

        container = null;
        anchorView = null;
        parent = null;
        designMenu = null;

        onItemSelectedListener = null;
        onCreatedListener = null;
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setWidth(int width) {
        throw new RuntimeException("Don't call this!!");
//        super.setWidth(width);
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setHeight(int height) {
        throw new RuntimeException("Don't call this!!");
//        super.setHeight(height);
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setContentView(View contentView) {
        throw new RuntimeException("Don't call this!!");
//        super.setContentView(contentView);
    }

    public FloatingActionMenu setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
        return this;
    }

    public FloatingActionMenu setOnCreatedListener(OnCreatedListener onCreatedListener) {
        this.onCreatedListener = onCreatedListener;
        return this;
    }

    public interface OnItemSelectedListener {
        boolean onItemSelected(DesignMenuItem item);
    }

    public interface OnCreatedListener {
        void onCreated(DesignMenu designMenu, List<FamItemView> famItemViews);
    }

}
