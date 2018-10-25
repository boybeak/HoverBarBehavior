package com.github.boybeak.hoverbarlayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

public class FamItemView extends LinearLayoutCompat {

    private static final String TAG = FamItemView.class.getSimpleName();

    private AppCompatTextView mTitleTv;
    private FloatingActionButton mIconFab;

    public FamItemView(Context context) {
        this(context, null);
    }

    public FamItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FamItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initThis(context, attrs, defStyleAttr);
    }

    private void initThis(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        initTitle(context, attrs, defStyleAttr);
        initIconFab(context, attrs, defStyleAttr);
    }

    private void initTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        mTitleTv = new AppCompatTextView(context, attrs, defStyleAttr);
        mTitleTv.setMaxWidth(Utils.dpToPixel(context, 144));
        mTitleTv.setTextAppearance(context, R.style.TextAppearance_AppCompat_Small);
        mTitleTv.setGravity(Gravity.CENTER);
        mTitleTv.setMaxLines(1);
        mTitleTv.setEllipsize(TextUtils.TruncateAt.END);
        mTitleTv.setBackgroundResource(R.drawable.bg_fam_item_title);
        final int paddingHor = Utils.dpToPixel(context, 12);
        final int paddingVer = Utils.dpToPixel(context, 6);
        mTitleTv.setPadding(paddingHor, paddingVer, paddingHor, paddingVer);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(Utils.dpToPixel(context, 8));
        addView(mTitleTv, params);
    }

    private void initIconFab(Context context, AttributeSet attrs, int defStyleAttr) {
        mIconFab = new FloatingActionButton(context, attrs, defStyleAttr);
        mIconFab.setElevation(Utils.dpToPixel(context, 2));
        mIconFab.setSize(FloatingActionButton.SIZE_MINI);
        mIconFab.setSupportBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int margin = Utils.dpToPixel(context, 4);
        params.setMargins(margin, margin, margin, margin);
        addView(mIconFab, params);
    }

    public AppCompatTextView titleTextView() {
        return mTitleTv;
    }

    public FloatingActionButton iconFab() {
        return mIconFab;
    }

}
