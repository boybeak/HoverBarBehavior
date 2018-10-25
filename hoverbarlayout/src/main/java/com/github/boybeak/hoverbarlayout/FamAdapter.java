package com.github.boybeak.hoverbarlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

class FamAdapter extends RecyclerView.Adapter<FamItemHolder> {

    private LayoutInflater mInflater;
    private List<FamItem> mItems = null;

    public FamAdapter(Context context, List<FamItem> items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @NonNull
    @Override
    public FamItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FamItemHolder(mInflater.inflate(R.layout.layout_fam_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FamItemHolder famItemHolder, int i) {
        FamItem item = mItems.get(i);
        famItemHolder.title.setText(item.getTitle());
        famItemHolder.icon.setImageDrawable(item.getIcon());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
