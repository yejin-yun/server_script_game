package com.example.test_firebase;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import java.lang.String;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private int position;
    private List<EndingCnt> ecList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();


        //외부에 파일 업로드 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView text2 = (TextView)findViewById(R.id.text2);

        Log.d(this.getClass().getName(), (String) text2.getText());
        text2.setText("hihello");
        text2.setMovementMethod(new ScrollingMovementMethod());


        /*position = 0;
        //데이터 디비다루기
        database.getReference().child("endingCnt").child(String.valueOf(position)).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EndingCnt ec = dataSnapshot.getValue(EndingCnt.class);
                if(ec != null) {
                    cnt = ec.getCnt() + 1; //얘를 intent에 넣어서 전해주면 될 듯.
                }

               System.out.println(cnt);
                *//*final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(ec.getCnt()).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();*//*

                Map<String, Object> map = new HashMap<>();
                map.put("cnt", cnt);
                database.getReference().child("endingCnt").child(String.valueOf(position)).updateChildren(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        //데이터 올리기
        //upload_db();



        position = 3;
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increse_cnt(database.getReference().child("endingCnt").child(String.valueOf(position)));
            }
        });
    }

    //디비 읽는 법 3가지 --> https://stack07142.tistory.com/282
    private void read_db() {
        //데이터 디비다루기
        database.getReference().child("endingCnt").child(String.valueOf(position)).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EndingCnt ec = dataSnapshot.getValue(EndingCnt.class);
                int cnt = ec.getCnt() + 1; //얘를 intent에 넣어서 전해주면 될 듯.
                System.out.println(cnt);

                Map<String, Object> map = new HashMap<>();
                map.put("cnt", cnt);
                database.getReference().child("endingCnt").child(String.valueOf(position)).updateChildren(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void increse_cnt(DatabaseReference postRef) { //카운트 동기화 방지인 트랜잭션으로 저장
        postRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                EndingCnt ec = mutableData.getValue(EndingCnt.class);

                if(ec == null) {
                    return Transaction.success(mutableData);
                }

                int cnt = ec.getCnt() + 1;
                System.out.println(cnt);

                //Log.i("end", "당신이 본 엔딩은 총 " + String.valueOf(ec.getCnt()) + "명이 성공한 엔딩입니다.");
                Intent intent = new Intent(getApplicationContext(), Ending2Activity.class);

                //화면간 데이터 송수신 : https://coding-factory.tistory.com/203
                intent.putExtra("cnt", cnt);
                startActivity(intent);

                ec.setCnt(cnt);
                mutableData.setValue(ec);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });
    }

    private void upload_db() {
        EndingCnt ec = new EndingCnt();

        for(int i = 0; i < 9; i++) {
            //ec.setId(i);
            String t = "ending" + String.valueOf(i);
            ec.setTitle(t);
            ec.setCnt(0);

            database.getReference().child("endingCnt").push().setValue(ec);
        }
    }

    /*

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

     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
