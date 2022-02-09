package com.example.dbmaster;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContentProvider extends android.content.ContentProvider {

    private static final String AUTHORITY = "org.techtown.provider"; // 특정 내용 제공자를 구분하는 고유값
    private static final String BASE_PATH = "user"; // user 테이블을 가리킴
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH); // 내용 제공자에 의해 제어되는 데이터

    private static final int USERS = 1;
    private static final int USER_ID = 2;

    private static final UriMatcher uriMather = new UriMatcher(UriMatcher.NO_MATCH); // Uri를 매칭하는데에 사용됨
    static {
        uriMather.addURI(AUTHORITY, BASE_PATH, USERS);
        uriMather.addURI(AUTHORITY, BASE_PATH + "/#", USER_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBHelper2 helper = new DBHelper2(getContext());
        database = helper.getWritableDatabase();

        return true;
    }

    @Nullable
    @Override // 내용 제공자를 이용해서 값 조회하기
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
