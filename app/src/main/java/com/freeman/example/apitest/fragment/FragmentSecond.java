package com.freeman.example.apitest.fragment;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.freeman.example.apitest.FLog;
import com.freeman.example.apitest.R;
import com.freeman.example.apitest.loader.NewsContentProvider;
import com.freeman.example.apitest.model.NewsInfo;
import com.freeman.example.apitest.notification.NotificationHelper;
import com.freeman.example.apitest.recyclerview.FRecyclerViewAdapter;
import com.freeman.example.apitest.recyclerview.FRecyclerViewOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freeman on 9/19/15.
 */
public class FragmentSecond extends FragmentBase implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentSecond.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private FRecyclerViewAdapter mRecyclerAdapter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        FLog.d(TAG, "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FLog.d(TAG, "onDestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        FLog.d(TAG, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        FLog.d(TAG, "onPause");
        pauseInsert();
    }

    @Override
    public void onStart() {
        super.onStart();
        FLog.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        FLog.d(TAG, "onResume");
        startInsert();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FLog.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.activity_fragment_second_layout, null);

        initRecyclerView(view);

        return view;
    }

    private boolean isInserting = false;

    private void initRecyclerView (View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_second_recycler_view);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new FRecyclerViewOnItemClickListener(getActivity(), new FRecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(v.getContext(), "Clicked: " + position, Toast.LENGTH_SHORT).show();
                if (isInserting) {
                    pauseInsert();
                    isInserting = false;
                } else {
                    startInsert();
                    isInserting = true;
                    sendNotification();
                }
            }
        }));
    }

    private void initRecyclerViewAdapter (Cursor cursor) {
        if (mRecyclerAdapter == null) {
            mRecyclerAdapter = new FRecyclerViewAdapter(cursor);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        } else {
            mRecyclerAdapter.swapCursor(cursor);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FLog.d(TAG, "onViewCreated");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FLog.d(TAG, "onCreate");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FLog.d(TAG, "onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FLog.d(TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FLog.d(TAG, "onActivityCreated");

        deleteAllDataBeforeInsert();

        initNotificationManager();

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FLog.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
    }

    @Override
    protected String getTipString() {
        return TAG;
    }


    // These are the rows that we will retrieve.
    static final String[] PROJECTION = new String[] {
            NewsContentProvider.NewsTable._ID,
            NewsContentProvider.NewsTable.COLUMN_NAME_DATA,
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(getActivity(), NewsContentProvider.NewsTable.CONTENT_URI,
                PROJECTION, null, null, null);
        //两秒更新一次
        loader.setUpdateThrottle(2000);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        initRecyclerViewAdapter(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        initRecyclerViewAdapter(null);
    }

    private static int mIndex = 0;


    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private final Runnable mInsertRunnable = new Runnable() {
        @Override
        public void run() {
            ContentValues cv = new ContentValues();
            NewsInfo newsInfo = new NewsInfo();
            newsInfo.setDesc(String.format("Insert : %d", mIndex++));
            newsInfo.setTime(System.currentTimeMillis());
            cv.put("data", newsInfo.toString());
            if (getContext() != null) {
                getContext().getContentResolver().insert(NewsContentProvider.NewsTable.CONTENT_URI, cv);
                mHandler.postDelayed(this, 3000);
            }

        }
    };

    private void startInsert () {
        isInserting = true;
        mHandler.postDelayed(mInsertRunnable, 3000);
    }

    private void pauseInsert () {
        isInserting = false;
        mHandler.removeCallbacks(mInsertRunnable);
    }

    private void deleteAllDataBeforeInsert () {
        if (getContext() != null) {
            getContext().getContentResolver().delete(NewsContentProvider.NewsTable.CONTENT_URI, null, null);
        }
    }

    private final Runnable mDeleteRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };


    private NotificationManager mNotificationManager;
    private void initNotificationManager () {
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void sendNotification () {
        NotificationHelper.sendNotification(mNotificationManager, NotificationHelper.createNotificationByCompat(getActivity()));
    }


}
