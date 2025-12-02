package com.code.chatboat.utils;

import android.content.Context;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewUtils {
//    public static void setupRecyclerViewGridEndlessScroll(RecyclerView recyclerView, Context context, int spanCount, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, int progressView, int itemView) {
//        GridLayoutManager mLayoutManager = new GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false);
//        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                switch (adapter.getItemViewType(position)) {
//                    case progressView:
//                        return 1;
//                    case itemView:
//                        return spanCount;
//                    default:
//                        return -1;
//                }
//            }
//        });
//
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(mLayoutManager);
//    }

    public static void setupRecyclerViewGrid(RecyclerView recyclerView, Context context, int spanCount) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false));
    }

    public static void setupRecyclerViewLinear(RecyclerView recyclerView, Context context, int orientation) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(context, orientation, false));
    }

    // Add other utility functions as needed
}