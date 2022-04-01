package com.example.dbmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CPActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        tv = (TextView)findViewById(R.id.tv);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SampleActivity.class);
                startActivity(intent);
            }
        });

        Button insertBtn = (Button)findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();
            }
        });

        Button queryBtn = (Button)findViewById(R.id.queryBtn);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryUser();
            }
        });

        Button updateBtn = (Button)findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });

        Button deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });
    }

    public void updateUser() {
        String uriString = "content://org.techtown.provider/user";
        Uri uri = new Uri.Builder().build().parse(uriString); // 문자열을 파라미터로 전달 -> Uri 객체 생성

        String selection = "mobile = ?";
        String[] selectionArgs = new String[] {"010-1234-5678"}; // 이 번호일 때만
        // 따라서 where 조건은 'mobile = 010-1234-5678'
        ContentValues updateValues = new ContentValues();
        updateValues.put("mobile", "010-5678-1234"); // 이 번호로 수정함
        int count = getContentResolver().update(uri, updateValues, selection, selectionArgs);
        println("update 결과 : " + count);
    }

    public void deleteUser() {
        String uriString = "content://org.techtown.provider/user";
        Uri uri = new Uri.Builder().build().parse(uriString); // 문자열을 파라미터로 전달 -> Uri 객체 생성

        String selection = "name = ?";
        String[] selectionArgs = new String[] {"habilithm"};

        int count = getContentResolver().delete(uri, selection, selectionArgs);
        println("delete 결과 : " + count);
    }

    public void queryUser() {
        try {
            String uriString = "content://org.techtown.provider/user";
            Uri uri = new Uri.Builder().build().parse(uriString); // 문자열을 파라미터로 전달 -> Uri 객체 생성

            String[] columns = new String[] {"name", "age", "mobile"};
            // 조회할 칼럼의 이름이 문자열로 들어가 있음
            Cursor cursor = getContentResolver().query(uri, columns, null, null, "name ASC");
            println("query 결과 : " + cursor.getCount()); // Cursor 객체 리턴

            // Cursor 객체가 리턴되면 각 칼럼 이름에 해당하는 칼럼 인덱스 값을 확인한 후 칼럼 값을 조회함

           int index = 0;
            while(cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(columns[0]));
                int age = cursor.getInt(cursor.getColumnIndex(columns[1]));
                String mobile = cursor.getString(cursor.getColumnIndex(columns[2]));

                println("#" + index + " -> " + name + ", " + age + ", " + mobile);
                index += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertUser() {
        println("insertUser() 호출됨");

        String uriString = "content://org.techtown.provider/user";
        Uri uri = new Uri.Builder().build().parse(uriString); // 문자열을 파라미터로 전달 -> Uri 객체 생성

        Cursor cursor = getContentResolver().query(uri, null, null, null, null); // Uri 객체를 파라미터로 전달 -> Cursor 객체 리턴
        // Cursor 객체로 결과 값을 조회할 수 있음
        String[] columns = cursor.getColumnNames(); // 결과 레코드에 들어가 있는 칼럼의 이름을 조회
        println("컬럼 개수 : " + columns.length); // getColumnNames()로 알아낸 칼럼 이름은 화면 출력용으로만 사용 가능함

        for(int i = 0; i < columns.length; i++) {
            println("#" + i + ":" + columns[i]);
        }

        ContentValues values = new ContentValues();
        values.put("name", "habilithm");
        values.put("age", 26);
        values.put("mobile", "010-1234-5678");

        uri = getContentResolver().insert(uri, values);
        println("insert 결과 : " + uri.toString());
    }

    public void println(String data) {
        tv.append(data + "\n");
    }
}