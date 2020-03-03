package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue; //https://galmaegi74.tistory.com/10
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //통신 큐: 통신 큐에 요청을 집어넣으면 하나씩 끄집어내서 통신한다. 통신 큐 준비가 되었으니 이벤트에서 요청을 만들기
        requestQueue = Volley.newRequestQueue(this); //newRequestQueue가 리턴한 값은 RequestQueue가 됨.
    }

    //https://hwiyong.tistory.com/18
    public void onConnect(View view) {
        Log.i("end", "클릭이벤트가 들어왔다.");
        //통신 코드 작성

        // json 데이터를 파싱해서 데이터 로그를 출력!!
        //1. 요청 하고
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, //우리 get방식으로 처리함.
                "http://192.168.219.118:8089/Server/ec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //스트링으로 응답이 옴.
                        Log.i("end", "응답결과: " + response);
                        //파싱
                        Gson gson = new Gson();
                        //즉 응답-> 파싱 -> 데이터가 잘 출력이 되는 지 확인
                        Ending res = gson.fromJson(response, Ending.class); //이렇게 받게 되는거. 이 자체가 파싱이 되는거. //아까 서버에서는 to로 만듦. 여기는 from으로 받으면 됨.
                        //첫번째 파라미터는 response 값을 주면 되고, 두번째는 담을 그릇이 필요함.
                        if (res.getCnt() < 0) {
                            Log.i("end", "정상적인 경로가 아닙니다.");
                            // 이전 화면으로 이동.
                        } else {
                            Log.i("end", "당신이 본 엔딩은 총 " + String.valueOf(res.getCnt()) + "명이 성공한 엔딩입니다.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { //오류사항
                        Log.i("end", "오류: " + error.getMessage());
                    }
                }); //이렇게 해서 일단 요청이 만들어짐. {};는 오버라이딩 할게 있으면 넣고 없으면 빼기

        requestQueue.add(stringRequest);//위에서 만든 요청을 add안에 넣어주면 요청이 됨.
        //2. 응답하면 파싱을 해서 데이터가 잘 출력되는지 확인. 즉 응답-> 파싱 -> 데이터가 잘 출력이 되는 지 확인
        //안드로이드는 통신을 하겠냐 자체를 허가를 받아야 됨.
        //구성이 마무리가 되었고 데이터가 정상적으로 오면 gson으로 파싱을 하면 됨.
    }

       /* public static String POST(String url, String title){  //https://m.blog.naver.com/beodeulpiri/220730560270

        InputStream is = null;
        String result = "";

        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";
            // build jsonObject

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("title", title);

            // convert JSONObject to JSON to String
            json = jsonObject.toString();
            Log.i("end", "json = " + json);

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);
            // Set some headers to inform server about the type of the content


            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);

            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("euc-kr"));
            os.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }*/
}

