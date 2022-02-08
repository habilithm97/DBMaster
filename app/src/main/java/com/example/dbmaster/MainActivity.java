package com.example.dbmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
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

*헬퍼 클래스로 업그레이드
-테이블의 정의가 변경되어서 스키마(테이블 구조)를 업그레이드 해야할 때 API에서 제공하는 헬퍼 클래스를 이용함

*커서 객체
-결과 테이블에 들어있는 각각의 레코드를 순서대로 접근할 수 있는 방법을 제공함
-처음에는 어떤 레코드도 가리키지 않고, moveToNext()로 다음 레코드를 가리켜서 값을 가져올 수 있음(false 값을 반환할 때까지 레코드 값을 가져옴)
 */

public class MainActivity extends AppCompatActivity {

    EditText edt, edt2;
    static TextView tv;

    SQLiteDatabase database;
    String tbName;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn4 = (Button)findViewById(R.id.btn4);

        edt = (EditText)findViewById(R.id.edt);
        edt2 = (EditText)findViewById(R.id.edt2);
        tv = (TextView)findViewById(R.id.tv);

        Button btn3 = (Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordSelect();
            }
        });

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

    public void recordSelect() {
        Cursor cursor = database.rawQuery("select _id, name, age, mobile, email, address from dbtest", null); // SQL 실행하고 커서 객체 반환 받기
        int recordCount = cursor.getCount();
        println("레코드 개수 : " + recordCount);

        for(int i = 0; i < recordCount; i++) {
            cursor.moveToNext(); // 다음 결과 레코드로 이동, 다음 레코드가 있으면 true를, 없으면 false가 반환됨

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String mobile = cursor.getString(3);
            String email = cursor.getString(4);
            String address = cursor.getString(5);

            println("레코드" + i + " : " + id + ", " + name + ", " + age + ", " + mobile + ", " + email + ", " + address);
        }
        cursor.close(); // 저장소에 접근하기 때문에 자원이 한정되어 있어서 닫아줌
    }

    private void createDB(String name) {

        // 데이터 베이스 생성
        database = openOrCreateDatabase(name, MODE_PRIVATE, null); // 파라미터로는 저장소 이름, 사용 모드, 널이 아닌 객체를 지정할 경우
        // 쿼리의 결과 값으로 반환되는 데이터를 참조하는 커서를 만들어낼 수 있는 객체가 전달됨
        println("데이터 베이스가 생성되었습니다. " + name);

        // 헬퍼 객체 생성하고 db 객체 참조함
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    private void createTb(String name) {
        if(database == null) {
            println("데이터 베이스를 먼저 생성하세요. ");
            return;
        }

        database.execSQL("create table if not exists " + name + "("
        + " _id integer PRIMARY KEY autoincrement, "
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