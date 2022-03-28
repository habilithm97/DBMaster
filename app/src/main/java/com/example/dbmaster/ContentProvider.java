package com.example.dbmaster;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
*Content Provider(내용 제공자) : 한 앱에서 관리하는 데이터를 다른 앱에서도 접근할 수 있도록 해줌
 -앱의 보안을 위해서 필요함(서로 다른 앱의 데이터에 접근해야될 때도 있음)
 -다른 앱에게 접근 통로를 열어줄 수 있음(반드시 허용된 통로로만 접근해야됨)
  -> 허용된 통로로 접근하려면 Content Resolver 객체가 필요함


 -앱이 자체적으로 저장된 데이터, 다른 앱이 저장한 데이터에 대한 액세스 권한을 관리하도록 돕고,
   다른 앱과 데이터를 공유할 방법을 제공함
 -데이터를 캡슐화하고, 데이터 보안을 정의하는데 필요한 메커니즘을 제공함
 -한 프로세스의 데이터에 다른 프로세스에서 실행 중인 코드를 연결하는 표준 인터페이스임
 -가장 중요한 점은 다른 앱이 앱 데이터에 안전하게 액세스하여 수정할 수 있도록 허용할 수 있다는 것임
 */
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
