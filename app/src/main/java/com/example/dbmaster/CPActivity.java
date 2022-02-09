package com.example.dbmaster;

import androidx.appcompat.app.AppCompatActivity;

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

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btn2 = (Button)findViewById(R.id.btn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btn3 = (Button)findViewById(R.id.btn3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btn4 = (Button)findViewById(R.id.btn4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void println(String data) {
        tv.append(data + "\n");
    }
}