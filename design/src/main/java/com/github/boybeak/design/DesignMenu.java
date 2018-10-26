package com.github.boybeak.design;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DesignMenu implements Menu {

    private Context context;
    private List<DesignMenuItem> designMenuItems = new ArrayList<>();

    public DesignMenu(Context context) {
        this.context = context;
    }

    @Override
    public DesignMenuItem add(CharSequence title) {
        DesignMenuItem item = new DesignMenuItem(context);
        designMenuItems.add(item);
        return item.setTitle(title);
    }

    @Override
    public DesignMenuItem add(int titleRes) {
        DesignMenuItem item = new DesignMenuItem(context);
        designMenuItems.add(item);
        return item.setTitle(titleRes);
    }

    @Override
    public DesignMenuItem add(int groupId, int itemId, int order, CharSequence title) {
        DesignMenuItem item = new DesignMenuItem(context, itemId, groupId, order, title, null);
        designMenuItems.add(item);
        return item;
    }

    @Override
    public DesignMenuItem add(int groupId, int itemId, int order, int titleRes) {
        DesignMenuItem item = new DesignMenuItem(context, itemId, groupId, order, titleRes, -1);
        designMenuItems.add(item);
        return item;
    }

    public DesignMenuItem add(DesignMenuItem item) {
        designMenuItems.add(item);
        return item;
    }

    @Override
    public SubMenu addSubMenu(CharSequence title) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(int titleRes) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
        return null;
    }

    @Override
    public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
        return 0;
    }

    @Override
    public void removeItem(int id) {
        Iterator<DesignMenuItem> it = designMenuItems.iterator();
        while (it.hasNext()) {
            DesignMenuItem item = it.next();
            if (item.getItemId() == id) {
                it.remove();
                return;
            }
        }
    }

    @Override
    public void removeGroup(int groupId) {

    }

    @Override
    public void clear() {
        designMenuItems.clear();
    }

    @Override
    public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {

    }

    @Override
    public void setGroupVisible(int group, boolean visible) {

    }

    @Override
    public void setGroupEnabled(int group, boolean enabled) {

    }

    @Override
    public boolean hasVisibleItems() {
        return false;
    }

    @Override
    public DesignMenuItem findItem(int id) {
        for (DesignMenuItem item : designMenuItems) {
            if (item.getItemId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return designMenuItems.size();
    }

    @Override
    public DesignMenuItem getItem(int index) {
        return designMenuItems.get(index);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
        return false;
    }

    @Override
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean performIdentifierAction(int id, int flags) {
        return false;
    }

    @Override
    public void setQwertyMode(boolean isQwerty) {

    }
}
