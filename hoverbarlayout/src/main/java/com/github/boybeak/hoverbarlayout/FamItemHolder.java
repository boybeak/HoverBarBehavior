package com.github.boybeak.hoverbarlayout;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class FamItemHolder extends RecyclerView.ViewHolder {

    AppCompatTextView title;
    FloatingActionButton icon;

    public FamItemHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.fam_item_title);
        icon = itemView.findViewById(R.id.fam_item_icon);
    }
}
