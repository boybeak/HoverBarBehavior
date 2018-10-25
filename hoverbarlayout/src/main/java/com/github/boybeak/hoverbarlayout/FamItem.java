package com.github.boybeak.hoverbarlayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class FamItem {

    private int id;
    private CharSequence title;
    private Drawable icon;

    public FamItem(int id, CharSequence title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public FamItem(Context context, int id, @StringRes int titleRes, @DrawableRes int iconRes) {
        this(id, context.getString(titleRes), context.getDrawable(iconRes));
    }

    public int getId() {
        return id;
    }

    public CharSequence getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }
}
