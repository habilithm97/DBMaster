 package com.example.dbmaster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper2 extends SQLiteOpenHelper {

    private static final String DB_NAME  = "dbtest.db";
    private static final int DB_VERSION = 1;

    public static final String TB_NAME = "user";
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "name";
    public static final String USER_AGE = "age";
    public static final String USER_SEX = "sex";
    public static final String[] All_COLUMNS = {TB_NAME, USER_ID, USER_NAME, USER_AGE, USER_SEX};

    private static final String CREATE_TABLE = "CREATE TABLE " + TB_NAME + " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " +
            USER_AGE + " INTEGER, " +
            USER_SEX + " TEXT" + ")";

    public DBHelper2(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) { // 이전 버전을 삭제하고 새 버전으로 업그레이드
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(sqLiteDatabase);
    }
}
