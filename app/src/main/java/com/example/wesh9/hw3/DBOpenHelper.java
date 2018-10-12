package com.example.wesh9.hw3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wesh9 on 10/17/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movies3.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "movies3";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_POPULARITY = "popularity";
    public static final String COLUMN_NAME_VOTE = "vote";
    public static final String COLUMN_NAME_DATEE = "datee";
    public static final String COLUMN_NAME_IMG = "img";
    public static final String COLUMN_NAME_OVERVIEW = "overview";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_TITLE +      " TEXT," +
                    COLUMN_NAME_POPULARITY + " TEXT," +
                    COLUMN_NAME_VOTE +       " TEXT," +
                    COLUMN_NAME_DATEE +      " TEXT," +
                    COLUMN_NAME_IMG +        " TEXT," +
                    COLUMN_NAME_OVERVIEW + " TEXT)";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
