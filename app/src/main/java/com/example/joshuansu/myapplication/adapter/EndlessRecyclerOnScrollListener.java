package com.example.joshuansu.myapplication.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.joshuansu.myapplication.MainActivity;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int TOTAL_ITEMS = MainActivity.TOTAL_ITEMS;
    private boolean Loading = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (Loading & totalItemCount <= TOTAL_ITEMS) {
            Loading = false;
        }

        int visibleThreshold = 7;
        if (!Loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount && totalItemCount < TOTAL_ITEMS) {
            onLoadMore(totalItemCount);
            Loading = true;
        }

    }

    public abstract void onLoadMore(int totalItemCount);
}