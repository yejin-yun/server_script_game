package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class goEndingActivity extends AppCompatActivity {
    RequestQueue requestQueue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_ending);
        requestQueue = Volley.newRequestQueue(this);

        TextView textView2= (TextView)findViewById(R.id.textView2);

        Log.d(this.getClass().getName(), (String)textView2.getText());
        textView2.setText("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "남산 위에 저 소나무 철갑을 두른 듯 바람서리 불변함은 우리 기상일세\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "가을 하늘 공활한데 높고 구름 없이 밝은 달은 우리 가슴 일편담심일세.\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세\n" +
                "이 기상과 이 맘으로 충성을 다하여 괴로우나 즐거우나 나라 사랑하세.\n" +
                "무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세");
        textView2.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onButtonClick10_1(View view) {

    }

    public void onButtonClickEnd2(View view) {
        //먼저 title 데이터를 전송 이것은 서버에서 key가 될것
        StringRequest request = new StringRequest( //url은 서버의 ip주소
                Request.Method.POST,
                "http://172.29.1.106:8089/Server/postEc",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("end", "응답결과: " + response);
                        //파싱
                        Gson gson = new Gson();
                        Ending res = gson.fromJson(response, Ending.class);
                        if (res.getCnt() < 0) {
                            Log.i("end", "정상적인 경로가 아닙니다.");
                            AlertDialog.Builder alert = new AlertDialog.Builder(goEndingActivity.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("정상적인 경로가 아닙니다.");
                            alert.show();
                        } else {
                            Log.i("end", "당신이 본 엔딩은 총 " + String.valueOf(res.getCnt()) + "명이 성공한 엔딩입니다.");
                            Intent intent = new Intent(getApplicationContext(), EndingActivity.class);

                            //화면간 데이터 송수신 : https://coding-factory.tistory.com/203
                            intent.putExtra("title", res.getTitle());
                            intent.putExtra("cnt", res.getCnt());
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("end", "오류: " + error.getMessage());
                    }
                }
                ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String title = "ending";  //"EndingActivity".replace("Activity", "");
                Map<String, String> params = new HashMap<>(); //https://vaert.tistory.com/107
                params.put("title", title);

                return params;
            }
        };
        request.setShouldCache(false); // Volley 자체는 캐싱을 하기때문에, 캐싱기능을 꺼버림
        requestQueue.add(request);
    }

    //https://s-engineer.tistory.com/116 --> gson
}
