package com.example.dbmaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;

public class SampleActivity extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        img = (ImageView)findViewById(R.id.img);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT); // 액션 정보 설정 -> MIME 타입이 image로 시작하는 데이터를 가져와라

       startActivityForResult(intent, 101); // 앨범에서 사진을 선택할 수 있는 화면을 띄워줌
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101) {
            if(resultCode == RESULT_OK) {
                Uri fileUri = data.getData(); // fileUri는 ContentResolver를 이용해 참조할 수 있는 이미지 파일을 가리킴

                ContentResolver resolver = getContentResolver();

                try {
                    InputStream inputStream = resolver.openInputStream(fileUri); // 파일 읽기
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    img.setImageBitmap(bitmap);

                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}