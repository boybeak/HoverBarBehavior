package com.github.boybeak.design;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FamItemView extends LinearLayoutCompat {

    private static final String TAG = FamItemView.class.getSimpleName();

    private AppCompatTextView mTitleTv;
    private FloatingActionButton mIconFab;
    private boolean showTitle, fullyIcon;

    FamItemView(Context context, boolean showTitle, boolean fullyIcon) {
        super(context);
        initThis(context, showTitle, fullyIcon);
    }

    private void initThis(Context context, boolean showTitle, boolean fullyIcon) {
        this.showTitle = showTitle;
        this.fullyIcon = fullyIcon;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        initTitle(context);
        initIconFab(context);
    }

    private void initTitle(Context context) {
        mTitleTv = new AppCompatTextView(context);
        mTitleTv.setMaxWidth(Utils.dpToPixel(context, 144));
        mTitleTv.setTextAppearance(context, R.style.TextAppearance_AppCompat_Small);
        mTitleTv.setGravity(Gravity.CENTER);
        mTitleTv.setMaxLines(1);
        mTitleTv.setEllipsize(TextUtils.TruncateAt.END);
        mTitleTv.setBackgroundResource(R.drawable.bg_fam_item_title);
        final int paddingHor = Utils.dpToPixel(context, 12);
        final int paddingVer = Utils.dpToPixel(context, 6);
        mTitleTv.setPadding(paddingHor, paddingVer, paddingHor, paddingVer);
        mTitleTv.setVisibility(showTitle ? View.VISIBLE : View.GONE);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(Utils.dpToPixel(context, 8));
        addView(mTitleTv, params);
    }

    private void initIconFab(Context context) {
        int iconRes;
        if (fullyIcon) {
            iconRes = R.layout.layout_fab_icon_fully;
        } else {
            iconRes = R.layout.layout_fab_icon;
        }
        mIconFab = (FloatingActionButton) LayoutInflater.from(context).inflate(iconRes, null, false);
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
