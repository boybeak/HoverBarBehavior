package com.github.boybeak.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.*;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.*;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class FamPopupWindow extends DimPopupWindow {

    private static final String TAG = FamPopupWindow.class.getSimpleName();

    private View anchorView;
    private LinearLayoutCompat container;
    private int iconSize, offsetY;
    private List<FamItem> famItems;

    private int animDuration = 240;
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
        iconSize = Utils.dpToPixel(context, 48);
        offsetY = Utils.dpToPixel(context, 0);
//        animDuration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);

    }

    public FamPopupWindow setAnchorView (FloatingActionButton fab) {
        this.anchorView = fab;
        return this;
    }

    public FamPopupWindow setFamItems(List<FamItem> items) {
        if (anchorView == null) {
            throw new IllegalStateException("call setAnchorView before setFamItems");
        }
        this.famItems = items;
        container = new LinearLayoutCompat(anchorView.getContext());
        container.setOrientation(LinearLayoutCompat.VERTICAL);
        container.setGravity(Gravity.END|Gravity.BOTTOM);

        final int size = famItems.size();
        for (int i = 0; i < size; i++) {
            final FamItem item = famItems.get(i);
            FamItemView child = new FamItemView(anchorView.getContext());
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
            p.bottomMargin = Utils.dpToPixel(anchorView.getContext(), 12);
            container.addView(child, p);

            child.setAlpha(0);
            child.measure(0, 0);
            icon.setPivotX(icon.getMeasuredWidth() / 2);
            icon.setPivotY(icon.getMeasuredHeight() / 2);
            icon.setScaleX(0);
            icon.setScaleY(0);

        }

        container.measure(0, 0);
        setWidth(container.getMeasuredWidth());
        setHeight(container.getMeasuredHeight());

        setContentView(container);
        return this;
    }

    public void show() {
        if (container == null) {
            throw new IllegalStateException("call setFamItems before show");
        }
        if (animatorSet != null && animatorSet.isRunning()) {
            return;
        }

        showAsDropDown(anchorView, (iconSize - anchorView.getWidth()) / 2, offsetY, Gravity.END | Gravity.TOP);
        if (isDimable()) {
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
                    onCreatedListener.onCreated(famItems, itemViews);
                    itemViews.clear();

                }
            }
        });
        animatorSet.start();
    }

    @Override
    public FamPopupWindow setDimView(ViewGroup dimView) {
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

        if (isDimable()) {
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
