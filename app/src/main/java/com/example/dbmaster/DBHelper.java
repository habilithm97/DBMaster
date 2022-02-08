package com.example.dbmaster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static String FILE_NAME = "dbtest.db";
    public static int DB_VERSION = 1;

    // 세 번째 파라미터인 factory는 데이터 조회 시에 반호나하는 커서를 만들어 낼 CursorFactory 객체임
    public DBHelper(@Nullable Context context) {
        super(context, FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table if not exists dbtest("
                + " _id integer PRIMARY KEY autoincrement, "
                + "name text, "
                + "age integer, "
                + "mobile text, "
                + "email text, "
                + "address text)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        println("버전 " + oldVersion + "에서" + " 버전 " + newVersion + "으로 업데이트 되었습니다. ");

        if(newVersion > 1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS dbtest");
        }
    }

    public void println(String data) {
        MainActivity.tv.append(data + "\n");
    }
}
