package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil extends AsyncTask<String, Void, JSONArray> {
    @Override
    protected JSONArray doInBackground(String... strings) {
        JSONArray memberJsonArr = null;
        try {
            //HttpURLConnection을 이용해 url에 연결하기 위한 설정
            String url = "http://192.168.219.118:8089/Server/ec";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            //커넥션에 각종 정보 설정
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");


            //응답 http코드를 가져옴
            int responseCode = conn.getResponseCode();

            ByteArrayOutputStream baos = null;
            InputStream is = null;
            String responseStr = null;

            //응답이 성공적으로 완료되었을 때
            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                responseStr = new String(byteData);

                JSONObject responseJSON = new JSONObject(responseStr);
                //json데이터가 하나의 값일 때
                String result = (String) responseJSON.get("result");
                //json데이터가 Map같은 형식일 때
                memberJsonArr =  responseJSON.getJSONArray("memberData");

                //Log.i("info", "DATA response = " + responseStr);

            } else {
                is = conn.getErrorStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                responseStr = new String(byteData);
                Log.i("info", "DATA response error msg = " + responseStr);
            }
        }
         catch (Exception e) {
            e.printStackTrace();
            Log.i("errorInfo", "error occured!" + e.getMessage());
         }

        return memberJsonArr;
    }
}
