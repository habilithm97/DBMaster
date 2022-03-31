package com.example.dbmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
*Content Provider(내용 제공자) : 한 앱에서 관리하는 데이터를 다른 앱에서도 접근할 수 있도록 해줌
-서로 다른 앱의 데이터에 접근해야할 때의 보안을 위해 필요함
-다른 앱에게 데이터 접근 통로를 열어줄 수 있는데, 반드시 허용된 통로로만 접근해야함
 -> 허용된 통로로 접근하려면 Cotent Resolver 객체가 필요함
 */

public class CPActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        tv = (TextView)findViewById(R.id.tv);

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

            }
        });

        Button updateBtn = (Button)findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void insertUser() {
        println("insertUser() 호출됨");

        String uriString = "content://org.techtown.provider/user";
        Uri uri = new Uri.Builder().build().parse(uriString); // 문자열을 파라미터로 전달

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        String[] columns = cursor.getColumnNames(); // 결과값 처리
        println("컬럼 개수 : " + columns.length);

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