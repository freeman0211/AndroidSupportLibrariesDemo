package com.freeman.example.apitest.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by freeman on 9/24/15.
 */
public class FRecyclerViewOnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    public interface OnItemClickListener {
        void onItemClick (View v, int position);
    }

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public FRecyclerViewOnItemClickListener (Context context, OnItemClickListener listener) {
        this.mListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(child, rv.getChildAdapterPosition(child));
            return true;
        }
        return false;
    }

}
