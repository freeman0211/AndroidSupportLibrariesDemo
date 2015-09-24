package com.freeman.example.apitest.recyclerview;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freeman.example.apitest.R;
import com.freeman.example.apitest.loader.NewsContentProvider;


/**
 * Created by freeman on 9/23/15.
 */
public class FRecyclerViewAdapter extends RecyclerView.Adapter<FRecyclerViewAdapter.ViewHolder> {


    private DataSetObserver mDataSetObserver;
    private int mRowIdColumn;
    private boolean isDataValid;
    private Cursor mCursor;

    public FRecyclerViewAdapter (Cursor cursor) {
        this.mCursor = cursor;
        isDataValid = cursor != null;
        mRowIdColumn = isDataValid ? mCursor.getColumnIndex("_id") : -1;
        setHasStableIds(true);
        mDataSetObserver = new RecyclerDataSetObserver();
        if (mCursor != null) {
            mCursor.registerDataSetObserver(mDataSetObserver);
        }
    }

    public Cursor getCursor () {
        return mCursor;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_recycler_view_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (isDataValid && mCursor.moveToPosition(i)) {
            int index = mCursor.getColumnIndex(NewsContentProvider.NewsTable.COLUMN_NAME_DATA);
            viewHolder.mDescTv.setText(mCursor.getString(index));
        }
    }

    @Override
    public int getItemCount() {
        if (isDataValid && mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (isDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mRowIdColumn);
        }
        return  0;
    }


    public Cursor swapCursor (Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (mDataSetObserver != null) {
                mCursor.registerDataSetObserver(mDataSetObserver);
            }

            mRowIdColumn = mCursor.getColumnIndexOrThrow("_id");
            isDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            isDataValid = false;
            notifyDataSetChanged();
        }

        return oldCursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView mDescTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mDescTv = (TextView) itemView.findViewById(R.id.fragment_item_tv);
        }

    }

    private  class RecyclerDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            isDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            isDataValid = false;
            notifyItemRangeRemoved(0, getItemCount());
        }
    }
}
