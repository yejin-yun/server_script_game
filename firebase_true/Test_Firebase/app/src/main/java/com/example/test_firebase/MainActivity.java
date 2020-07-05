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

        FloatingActionButton fab = findViewById(R.id.fab); //디비와 관련 없는 거.
        fab.setOnClickListener(new View.OnClickListener() {  //디비와 관련 없는 거.
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
                Log.d("test", "1");
            }
        });
    }

    //디비 읽는 법 3가지 --> https://stack07142.tistory.com/282
    private void read_db() {
        //데이터 디비다루기
        database.getReference().child("endingCnt").child(String.valueOf(position)).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //데이터의 변경사항을 기다림.(콜백함수) 즉 데이터가 변경되면 반응함.
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

    //카운트 동기화 방지인 트랜잭션으로 저장
    //https://firebase.google.com/docs/database/android/read-and-write?hl=ko
    //doTransaction은 여러번 호출되므로 null 처리를 해줘야 함.
    private void increse_cnt(DatabaseReference postRef) { //DB의 position이 있는 위치의 주소를 넘기는 것인듯
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
                Log.d("test", "2");
                return Transaction.success(mutableData);

            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                //첫번째 인자는 오류객체로 정상적으로 트랜잭션이 이루어졌다면 null값을 가짐.
                // 2번째 인자에는 committed로 true, 3번째 인자에는 트랜젝션의 결과값을 담은 객체가 전달됨.
                //https://books.google.co.kr/books?id=gzEmDwAAQBAJ&pg=PA396&lpg=PA396&dq=%ED%8C%8C%EC%9D%B4%EC%96%B4%EB%B2%A0%EC%9D%B4%EC%8A%A4+onComplete&source=bl&ots=bVl_-8S6MN&sig=ACfU3U1JmVaNzpIspvwG8eA1f8OrKDXGDw&hl=ko&sa=X&ved=2ahUKEwiD98jH0rbqAhUbIIgKHXZUC50Q6AEwA3oECAoQAQ#v=onepage&q=%ED%8C%8C%EC%9D%B4%EC%96%B4%EB%B2%A0%EC%9D%B4%EC%8A%A4%20onComplete&f=false

                //여기서 DB를 마무리 하는 듯(제일 마지막에 실행되는 듯 )
                Log.d("test", "3");
            }
        });
    }

    private void upload_db() { //값은 초기화해도 해도 내가 직접 db 만들어줘야 함.... 아닌데... 직접 만드는 게 있을텐데.. --> 밑의 ini_db가 내가 안만들어도 초기화됨.
        EndingCnt ec = new EndingCnt();

        for(int i = 0; i < 9; i++) {
            //ec.setId(i);
            String t = "ending" + String.valueOf(i);
            ec.setTitle(t);
            ec.setCnt(0);

            database.getReference().child("endingCnt").push().setValue(ec);
        }
    }

    private void ini_db() {
        EndingCnt ec_initialize = new EndingCnt();
        Map<String, Object> map = new HashMap<>();
        DatabaseReference databaseReference; //위치를 말하는 듯.

        for(int i = 0; i < 9; i++) {
            databaseReference = database.getReference().child("endingCnt").child(String.valueOf(i));

            String t = "end" + String.valueOf(i);
            map.put("title", t);
            map.put("count", 0);
            databaseReference.setValue(map);
            //ec_initialize.setTitle(t);
            //ec_initialize.setCount(0);

            //cntDatabase.getReference().child("endingCnt").push().setValue(ec);
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
