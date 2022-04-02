package com.example.dbmaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SampleActivity2 extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);

        tv = (TextView)findViewById(R.id.tv);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myContacts();
            }
        });
    }

    // ContactsContract.Contacts : 연락처에 대한 정보
    public void myContacts() {
        // 연락처 화면을 띄우기 위한 인텐트 생성
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI); // 연락처 정보를 조회하는데 사용되는 URI 값
        startActivityForResult(intent, 101);
    }

    @Override // 사용자가 연락처를 하나 선택하면 자동 호출됨
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == 101) {
                try {
                    Uri contactUri = data.getData(); // 선택한 연락처 정보를 가리키는 Uri 객체가 리턴됨
                    String id = contactUri.getLastPathSegment(); // 선택한 연락처의 id 값 확인(선택한 연락처의 상세 정보가 다른 곳에 저장되어 있기 때문에 확인)
                    getContacts(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ContactsContract.Data : 연락처에 대한 상세 정보
    public void getContacts(String id) {
        Cursor cursor = null;
        String name = "";

        try {
            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, ContactsContract.Data.CONTACT_ID + "=?",
                    new String[] {id}, null); // CONTENT_URI의 Uri 값은 연락처의 상세 정보를 조회하는데 사용됨
            // CONTENT_ID는 연락처의 상세 정보를 저장하는 테이블의 많은 칼럼들 중에서 id 칼럼의 이름을 상수로 확인 가능함
            if(cursor.moveToFirst()) { // Cursor를 가장 첫 번째 행으로 위치시킴
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)); // 연락처의 이름 칼럼 값을 상수로 확인 가능함(칼럼 이름 -> 칼럼 인덱스 -> 이름 알아냄)
                println("이름 : " + name);

                String columns[] = cursor.getColumnNames();
                for(String column : columns) {
                    int index = cursor.getColumnIndex(column);
                    String columnOutput = ("#" + index + " -> [" + column + "] " + cursor.getString(index));
                    println(columnOutput);
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void println(String data) {
        tv.append(data + "\n");
    }
}