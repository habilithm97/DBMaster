package com.example.dbmaster;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
*Content Provider(내용 제공자) : 한 앱에서 관리하는 데이터를 다른 앱에서도 접근할 수 있도록 해줌
 -앱의 보안을 위해서 필요함(서로 다른 앱의 데이터에 접근해야될 때도 있음)
 -다른 앱에게 접근 통로를 열어줄 수 있음(반드시 허용된 통로로만 접근해야됨)
  -> 허용된 통로로 접근하려면 Content Resolver 객체가 필요함

 -앱이 자체적으로 저장된 데이터, 다른 앱이 저장한 데이터에 대한 액세스 권한을 관리하도록 돕고, 다른 앱과 데이터를 공유할 방법을 제공함
 -데이터를 캡슐화하고, 데이터 보안을 정의하는데 필요한 메커니즘을 제공함
 -한 프로세스의 데이터에 다른 프로세스에서 실행 중인 코드를 연결하는 표준 인터페이스임
 -가장 중요한 점은 다른 앱이 앱 데이터에 안전하게 액세스하여 수정할 수 있도록 허용할 수 있다는 것임

  -앱의 직접적인 코드 변경 없이 데이터 접근 및 변경할 수 있도록 함
  -Loader나 CursorAdapter 같은 클래스들도 사용함
  -다른 사용자들이 앱에 안전하게 접근, 사용, 수정할 권한을 줌

  -Content Resolver : 단말기 안에 여러 앱과 여러 Provider가 있기 때문에 이들을 관리 및 흐름을 통제하고, 앱이 접근하고자 하는 Provider 사이에서 중개자 역할을 함
   -> Content Resolver 객체에는 여러 가지 메서드들이 정의되어 있으며 Content Provider의 URI를 파라미터로 전달하면서 데이터를 조회, 추가, 수정, 삭제하는 작업이 가능함
 */
public class ContentProvider extends android.content.ContentProvider {

    private static final String AUTHORITY = "org.techtown.provider"; // 특정 내용 제공자를 구분하는 고유값
    private static final String BASE_PATH = "user"; // user 테이블을 가리킴, 요청할 데이터의 타입을 결정함(여기서는 user 테이블 이름)
    // Content Provider를 만들기 위해서는 고유한 값을 가진 content URI를 만들어야함
    // content:// : Content Provider에 의해 제어되는 데이터
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final int USERS = 1;
    private static final int USER_ID = 2; // 맨 뒤의 숫자를 가리키며 요청할 데이터의 레코드를 지정함

    private static final UriMatcher uriMather = new UriMatcher(UriMatcher.NO_MATCH); // Uri를 매칭하는데에 사용됨(Content Provider가 받는 Uri의 종류를 결정함, match()를 호출하면 UriMather에
    // addURI() 메서드를 이용해 추가된 URI 중에서 실행 가능한 것이 있는지 확인함
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
    @Override // Content Provider를 이용해서 값 조회하기(uri, 어떤 칼럼 조회?(null이면 모든 칼럼 조회), where절, 세번째 파라미터 대체, 정렬)
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (uriMather.match(uri)) {
            case USERS:
                cursor = database.query(DBHelper2.TB_NAME, DBHelper2.All_COLUMNS, s, null, null, null,  DBHelper2.USER_NAME + " ASC");
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri); // 변경이 일어났음을 알려주고, Content Provider에 접근함(Content Provider의 URI를 파라미터로 전달하면서 데이터를 조작이 가능함)
        return cursor;
    }

    @Nullable
    @Override // MIME 타입이 무엇인지를 알고 싶을 때 사용함 -> Uri 객체가 파라미터로 전달되며 결과 값으로 MIME 타입이 리턴됨, 타입을 알 수 없으면 null 값이 리턴됨
    public String getType(@NonNull Uri uri) {
        switch (uriMather.match(uri)) {
            case USERS:
                return "vnd.android.cursor.dir/users"; // MIME 타입으로 리턴
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) { // 저장할 칼럼명과 값들이 들어간 contentValues 객체
        long id = database.insert(DBHelper2.TB_NAME, null, contentValues);

        if(id > 0) {
            Uri uri1 = ContentUris.withAppendedId(CONTENT_URI, id); // 변경될 새로운 Uri 객체
            getContext().getContentResolver().notifyChange(uri1, null);
            return uri1; // 새로 추가된 값의 uri가 리턴
        }
        throw new SQLException("추가 실패 : URI : " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) { // (uri, where절, 두 번째 대체)
        int count = 0;
        switch (uriMather.match(uri)) {
            case USERS:
                count = database.delete(DBHelper2.TB_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count; // 영향 받은 레코드 개수 리턴
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int count = 0;
        switch (uriMather.match(uri)) {
            case USERS:
                count = database.update(DBHelper2.TB_NAME, contentValues, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count; // 영향 받은 레코드 개수 리턴
    }
}
