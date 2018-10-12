package com.example.wesh9.hw3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesh9 on 10/17/2017.
 */

public class DatabaseManager {

    private SQLiteOpenHelper dbOpenHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void open() {
        database = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        dbOpenHelper.close();
    }

    public void insertMovieInfo(String title, String release, String vote, String datee, String img, String overview) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_NAME_TITLE, title);
        values.put(DBOpenHelper.COLUMN_NAME_POPULARITY, release);
        values.put(DBOpenHelper.COLUMN_NAME_VOTE, vote);
        values.put(DBOpenHelper.COLUMN_NAME_DATEE, datee);
        values.put(DBOpenHelper.COLUMN_NAME_IMG, img);
        values.put(DBOpenHelper.COLUMN_NAME_OVERVIEW, overview);

        database.insert(DBOpenHelper.TABLE_NAME, null, values);
    }

    public List<String[]> getAllRecordsOrderedBy(String key) {
        Cursor cursor = database.query(DBOpenHelper.TABLE_NAME,
                new String[]{
                        DBOpenHelper.COLUMN_ID,
                        DBOpenHelper.COLUMN_NAME_TITLE,
                        DBOpenHelper.COLUMN_NAME_VOTE,
                        DBOpenHelper.COLUMN_NAME_POPULARITY,
                        DBOpenHelper.COLUMN_NAME_DATEE,
                        DBOpenHelper.COLUMN_NAME_IMG,
                        DBOpenHelper.COLUMN_NAME_OVERVIEW
                }, null, null, null, null, key, null);
        cursor.moveToFirst();
        List<String[]> result = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            result.add(new String[]{cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),

            });

            cursor.moveToNext();
        }
        //return the list ordered by the key
        //The key should be one of the column keys defined in DBOpenHelper.
        return result;
    }

    public List<Movie> getAllRecords(String key) {
        Cursor cursor = database.query(DBOpenHelper.TABLE_NAME,
                new String[]{
                        DBOpenHelper.COLUMN_ID,
                        DBOpenHelper.COLUMN_NAME_TITLE,
                        DBOpenHelper.COLUMN_NAME_POPULARITY,
                        DBOpenHelper.COLUMN_NAME_VOTE,
                        DBOpenHelper.COLUMN_NAME_DATEE,
                        DBOpenHelper.COLUMN_NAME_IMG,
                        DBOpenHelper.COLUMN_NAME_OVERVIEW
                }, null, null, null, null, key, null );
        cursor.moveToFirst();
        Movie movie;
        List<Movie> result =new ArrayList<Movie>();
        while (!cursor.isAfterLast()) {
            movie = new Movie();
            movie.setTitle(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_TITLE)));

            movie.setDate(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_POPULARITY)));
            movie.setRating(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_VOTE)));
            movie.setDatee(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_DATEE)));
            movie.setImg(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_IMG)));
            movie.setOverview(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_OVERVIEW)));
            cursor.moveToNext();
            result.add(movie);
        }
        return result;
    }

    public void deleteAll() {
        if (database.isOpen()) {
            database.execSQL("delete from " + DBOpenHelper.TABLE_NAME);
        }
    }
}
