package com.example.test_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class Ending2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending2);

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        int cnt = intent.getExtras().getInt("cnt");
        Log.d(this.getClass().getName(), (String) textView2.getText());
        textView2.setText("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "남산 위에 저 소나무 철갑을 두른 듯 바람서리 불변함은 우리 기상일세\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "가을 하늘 공활한데 높고 구름 없이 밝은 달은 우리 가슴 일편담심일세.\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "이 기상과 이 맘으로 충성을 다하여 괴로우나 즐거우나 나라 사랑하세.\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "남산 위에 저 소나무 철갑을 두른 듯 바람서리 불변함은 우리 기상일세\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "가을 하늘 공활한데 높고 구름 없이 밝은 달은 우리 가슴 일편담심일세.\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "이 기상과 이 맘으로 충성을 다하여 괴로우나 즐거우나 나라 사랑하세.\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세"
                + "\n\n\n\n\n 해당 엔딩은 총" + cnt + "명이 본 엔딩입니다.\n"
        );
        textView2.setMovementMethod(new ScrollingMovementMethod());
    }
}
