package com.freeman.example.apitest.loader;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.database.DatabaseUtilsCompat;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by freeman on 9/23/15.
 */
public class NewsContentProvider extends ContentProvider{

    private static final String TAG = NewsContentProvider.class.getSimpleName();

    public static final String AUTHORITY = "com.freeman.example.apitest";

    private DatabaseHelper mDatabaseHelper;

    private final HashMap<String, String> mProjectionMap;
    private final UriMatcher mUriMatcher;

    private static final int MAIN = 1;
    private static final int  MAIN_ID = 2;

    public NewsContentProvider () {

        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, NewsTable.TABLE_NAME, MAIN);
        mUriMatcher.addURI(AUTHORITY, NewsTable.TABLE_NAME + "/#", MAIN_ID);

        mProjectionMap = new HashMap<>();
        mProjectionMap.put(NewsTable._ID, NewsTable._ID);
        mProjectionMap.put(NewsTable.COLUMN_NAME_DATA, NewsTable.COLUMN_NAME_DATA);
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        Log.d(TAG, "onCreate");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(NewsTable.TABLE_NAME);

        switch (mUriMatcher.match(uri)) {
            case MAIN:
                qb.setProjectionMap(mProjectionMap);
                break;
            case MAIN_ID:
                qb.setProjectionMap(mProjectionMap);
                qb.appendWhere(NewsTable._ID + "=?");
                selectionArgs = DatabaseUtilsCompat.appendSelectionArgs (selectionArgs, new String[] { uri.getLastPathSegment() });
                break;
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }

        if (TextUtils.isEmpty(sortOrder)) {
            sortOrder = NewsTable.DEFAULT_SORT_ORDER;
        }

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case MAIN:
                return NewsTable.CONTENT_TYPE;
            case MAIN_ID:
                return NewsTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        Log.d(TAG, "insert");
        if (mUriMatcher.match(uri) != MAIN) {
            // Can only insert into to main URI.
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        if (!values.containsKey(NewsTable.COLUMN_NAME_DATA)) {
            values.put(NewsTable.COLUMN_NAME_DATA, "");
        }

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        long rowId = db.insert(NewsTable.TABLE_NAME, null, values);
        // If the insert succeeded, the row ID exists.
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(NewsTable.CONTENT_ID_URI_BASE, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String finalWhere;
        int count;
        switch (mUriMatcher.match(uri)) {
            case MAIN:
                // If URI is main table, delete uses incoming where clause and args.
                count = db.delete(NewsTable.TABLE_NAME, selection, selectionArgs);
                break;
            // If the incoming URI matches a single note ID, does the delete based on the
            // incoming data, but modifies the where clause to restrict it to the
            // particular note ID.
            case MAIN_ID:
                // If URI is for a particular row ID, delete is based on incoming
                // data but modified to restrict to the given ID.
                finalWhere = DatabaseUtilsCompat.concatenateWhere(
                        NewsTable._ID + " = " + ContentUris.parseId(uri), selection);
                count = db.delete(NewsTable.TABLE_NAME, finalWhere, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int count;
        String finalWhere;
        switch (mUriMatcher.match(uri)) {
            case MAIN:
                // If URI is main table, update uses incoming where clause and args.
                count = db.update(NewsTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MAIN_ID:
                // If URI is for a particular row ID, update is based on incoming
                // data but modified to restrict to the given ID.
                finalWhere = DatabaseUtilsCompat.concatenateWhere(
                        NewsTable._ID + " = " + ContentUris.parseId(uri), selection);
                count = db.update(NewsTable.TABLE_NAME, values, finalWhere, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    public static final class NewsTable implements BaseColumns {

        private NewsTable () {}

        public static final String TABLE_NAME = "news";

        public static final Uri CONTENT_URI =  Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final Uri CONTENT_ID_URI_BASE
                = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME + "/");

        public static final String CONTENT_TYPE
                = "vnd.android.cursor.dir/vnd.example.freeman.apitest.news";

        public static final String CONTENT_ITEM_TYPE
                = "vnd.android.cursor.item/vnd.example.freeman.apitest.news";

        public static final String DEFAULT_SORT_ORDER = "_id COLLATE LOCALIZED ASC";

        public static final String COLUMN_NAME_DATA = "data";

    }

    static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "api_test.db";
        private static final int DATABASE_VERSION = 1;

        DatabaseHelper (Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table " + NewsTable.TABLE_NAME + " (" + NewsTable._ID + " integer primary key, " +
                    NewsTable.COLUMN_NAME_DATA + " text " + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table if exists " + NewsTable.TABLE_NAME);
            onCreate(db);
        }
    }


}
