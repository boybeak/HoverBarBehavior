package com.github.boybeak.design;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.*;

public class DesignMenuItem implements MenuItem {

    private Context context;

    private int id, groupId, order;
    private CharSequence title, titleCondensed;
    private Drawable icon;

    public DesignMenuItem(Context context) {
        this.context = context;
    }

    public DesignMenuItem(Context context, int id, int groupId, int order, CharSequence title, Drawable icon) {
        this.context = context;
        this.id = id;
        this.groupId = groupId;
        this.order = order;
        this.title = title;
        this.icon = icon;
    }

    public DesignMenuItem(Context context, int id, int groupId, int order, int title, int icon) {
        this.context = context;
        this.id = id;
        this.groupId = groupId;
        this.order = order;
        this.title = context.getString(title);
        if (icon != -1) {
            this.icon = context.getDrawable(icon);
        }
    }

    @Override
    public int getItemId() {
        return id;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    DesignMenuItem setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public DesignMenuItem setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public DesignMenuItem setTitle(int title) {
        return setTitle(context.getString(title));
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public DesignMenuItem setTitleCondensed(CharSequence titleCondensed) {
        this.titleCondensed = titleCondensed;
        return this;
    }

    @Override
    public CharSequence getTitleCondensed() {
        if (TextUtils.isEmpty(titleCondensed)) {
            return title;
        }
        return titleCondensed;
    }

    @Override
    public DesignMenuItem setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public DesignMenuItem setIcon(int iconRes) {
        return setIcon(context.getDrawable(iconRes));
    }

    @Override
    public Drawable getIcon() {
        return icon;
    }

    @Override
    public DesignMenuItem setIntent(Intent intent) {
        return this;
    }

    @Override
    public Intent getIntent() {
        return null;
    }

    @Override
    public DesignMenuItem setShortcut(char numericChar, char alphaChar) {
        return this;
    }

    @Override
    public DesignMenuItem setNumericShortcut(char numericChar) {
        return this;
    }

    @Override
    public char getNumericShortcut() {
        return 0;
    }

    @Override
    public DesignMenuItem setAlphabeticShortcut(char alphaChar) {
        return this;
    }

    @Override
    public char getAlphabeticShortcut() {
        return 0;
    }

    @Override
    public DesignMenuItem setCheckable(boolean checkable) {
        return this;
    }

    @Override
    public boolean isCheckable() {
        return false;
    }

    @Override
    public DesignMenuItem setChecked(boolean checked) {
        return this;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public DesignMenuItem setVisible(boolean visible) {
        return this;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public DesignMenuItem setEnabled(boolean enabled) {
        return this;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean hasSubMenu() {
        return false;
    }

    @Override
    public SubMenu getSubMenu() {
        return null;
    }

    @Override
    public DesignMenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        return this;
    }

    @Override
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override
    public void setShowAsAction(int actionEnum) {

    }

    @Override
    public DesignMenuItem setShowAsActionFlags(int actionEnum) {
        return this;
    }

    @Override
    public DesignMenuItem setActionView(View view) {
        return this;
    }

    @Override
    public DesignMenuItem setActionView(int resId) {
        return this;
    }

    @Override
    public View getActionView() {
        return null;
    }

    @Override
    public DesignMenuItem setActionProvider(ActionProvider actionProvider) {
        return this;
    }

    @Override
    public ActionProvider getActionProvider() {
        return null;
    }

    @Override
    public boolean expandActionView() {
        return false;
    }

    @Override
    public boolean collapseActionView() {
        return false;
    }

    @Override
    public boolean isActionViewExpanded() {
        return false;
    }

    @Override
    public DesignMenuItem setOnActionExpandListener(OnActionExpandListener listener) {
        return this;
    }
}
