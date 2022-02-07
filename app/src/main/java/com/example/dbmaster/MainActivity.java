package com.example.dbmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
*안드로이드는 임베디드 데이터베이스로 개발된 경량급(Light-weight) 관계형 데이터베이스인 SQLite를 가지고 있음

*여기서 SQLite 데이터베이스는 파일로 만들어진 하위 수준의 구조를 가지면서도 데이터베이스의 기능까지 그대로 사용 가능하고,
저장될 때는 파일 단위로 저장이 되며, DB의 이동, 복사, 삭제가 매우 쉬움

*SQL 데이터베이스에서 가장 큰 장점은 데이터 조회 속도가 빠르고 표준 SQL을 지원함(웹이나 PC에서 기존에 사용하던 데이터 관리 기능을
그대로 사용할 수 있음) -> 개발 생산성이 높아지고 간단한 수정 가능함

*데이터 베이스의 사용
-저장소 생성(데이터베이스 생성 -> 한번 만들어지면 그 이후부터는 오픈됨)
-> 테이블 생성 -> 레코드 추가 -> 데이터 조회(조건 검색)

 */

public class MainActivity extends AppCompatActivity {

    EditText edt, edt2;
    TextView tv;

    SQLiteDatabase database;
    String tbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt = (EditText)findViewById(R.id.edt);
        edt2 = (EditText)findViewById(R.id.edt2);
        tv = (TextView)findViewById(R.id.tv);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dbName = edt.getText().toString();
                createDB(dbName);
            }
        });

        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbName = edt2.getText().toString();
                createTb(tbName);

                insertRecord();
            }
        });
    }

    private void createDB(String name) {

        // 데이터 베이스 생성
        database = openOrCreateDatabase(name, MODE_PRIVATE, null); // 파라미터로는 저장소 이름, 사용 모드, 널이 아닌 객체를 지정할 경우
        // 쿼리의 결과 값으로 반환되는 데이터를 참조하는 커서를 만들어낼 수 있는 객체가 전달됨
        println("데이터 베이스가 생성되었습니다. " + name);
    }

    private void createTb(String name) {
        if(database == null) {
            println("데이터 베이스를 먼저 생성하세요. ");
            return;
        }

        database.execSQL("create table if not exists " + name + "("
        + " _id integer PRIMARY KEY autocrement, "
        + " name text, "
        + " age integer, "
        + " mobile text, "
        + " email text, "
        + " address text)");

        println("테이블이 생성되었습니다. " + name);
    }

    private void insertRecord() {
        if(database == null) {
            println("데이터 베이스를 먼저 생성하세요. ");
            return;
        }

        if(tbName == null) {
            println("테이블을 먼저 생성하세요. ");
            return;
        }

        database.execSQL("insert into " + tbName
        + "(name, age, mobile, email, address) "
        + " values "
        + "('하빌리즘', 26, '010-1234-5678', 'habilithm97@mail.com', '폭풍의시 문방구 금은동')");

        println("레코드를 추가하였습니다. ");
    }

    public void println(String data) {
        tv.append(data + "\n");
    }
}