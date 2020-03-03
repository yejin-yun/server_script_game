package com.example.howlfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private RecyclerView recycleView;
    private List<ImageDT0> imageDT0List = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        database = FirebaseDatabase.getInstance(); ///다른 클래스에서도 쓰려면 싱글톤 패턴으로 선언해줘야 함. 이제 이 디비를 읽어올 것.
        auth = FirebaseAuth.getInstance();

        //아래의 코드를 끝으로 세팅 완료
        recycleView = (RecyclerView)findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recycleView.setAdapter(boardRecyclerViewAdapter);
        //이제 읽어오기. 읽어오는 방법은 여러가지가 있음.
        database.getReference().child("images").addValueEventListener(new ValueEventListener() {
            //옵저블 패턴은 계속 관찰자가 있어서 데이터가 변한 순간 이벤트를 발생시키는 거.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 글자가 하나 바뀔때마다 dataSnapshot이 데이터가 넘어옴. 옵저블 패턴으로 글자가 하나씩 바뀔 대마다 클라이언트한테 알려줌.
                // 다른 사람이 어떤 데이터를 수정했으면 내가 보고 있는 화면은 자동적으로 새로고침이 됨.
                imageDT0List.clear(); //수정될 때마다 계속 데이터가 날라오는데 클리어를 안해주면 쌓임
                uidLists.clear(); //새로고침할 때마다 리스트가 쌓이지 않고 바로바로 플러쉬함..?
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) { //dataSnapshot.getChildren()는 처음부터 끝까지 다 읽어들인다는 거. child를 하나씩 읽어드려서 리스트에 담음.
                    //ImageDT0 imageDT0 = snapshot.getValue(ImageDT0.class); //snapshot을 ImageDT0의 클래스 형으로 받겠다.
                    System.out.println(snapshot);
                    ImageDT0 imageDT0 = snapshot.getValue(ImageDT0.class);
                    String uidKey = snapshot.getKey();

                    imageDT0List.add(imageDT0); //꺼낸 것을 바로 담아줌.
                    uidLists.add(uidKey);
                }
                //이렇게 리스트에 집어넣어서 imageDT0List 데이터를 어댑터가 쓰면 화면이 갱신 됨.
                boardRecyclerViewAdapter.notifyDataSetChanged(); //계속 갱신되기 때문에 새로고침 해줘야 함.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
            return new CustomViewHolder(view); //뷰를 리턴
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            //setting
            //텍스트 먼저 캐스팅 해주자...
            ((CustomViewHolder)holder).textView1.setText(imageDT0List.get(position).title); //이미지 넘어온 것의 title
            ((CustomViewHolder)holder).textView2.setText(imageDT0List.get(position).description);
            //글라이드에 컨텍스트를 넣어주고 로드가서 포지션 받아와서 주소 알려주고 어디로 보낼거냐(into) 이미지뷰는 customViewHolder가 가지고 있음 --> 디비에 있는 사진을 가져오기 위해서 하는 거
            String url = imageDT0List.get(position).imageUrl;
            Glide.with(holder.itemView.getContext()).load(url).into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).starButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    onStarClicked(database.getReference().child("images").child(uidLists.get(position)));
                }
                //onStarClicked(database.getReference().child("images").child(uidLists여기까지 접근해서 get(position)은  몇번째에 접근하라
            });

            //stars 안에는 내 uid랑 boolean 값이 담김.
            if (imageDT0List.get(position).stars.containsKey(auth.getCurrentUser().getUid())){
                //1번을 불러올때 1번의 starts를 다 불러옴. 이 stars안에 내 uid가 있는지 물어보는 거. 있으면 하트가 칠해진 것으로 세팅하면 됨
                ((CustomViewHolder)holder).starButton.setImageResource(R.drawable.baseline_favorite_black_18dp);
            }else {
                ((CustomViewHolder)holder).starButton.setImageResource(R.drawable.baseline_favorite_border_black_18dp);
            }
        }

        private void onStarClicked(DatabaseReference postRef) { //DatabaseReference postRef는 어디 데이터 베이스에 접근할 것인지
            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    ImageDT0 imageDT0 = mutableData.getValue(ImageDT0.class);
                    if (imageDT0 == null) {
                        return Transaction.success(mutableData);
                    }


                    if (imageDT0.stars.containsKey(auth.getCurrentUser().getUid())) { //좋아요 버튼 눌렀는지 물어보는 코드 //stars를 검색해서 키가 있으면..//좋아요 누른 사람이 배열에 들어가는 데 그 배열에 내 Uid가 있는지 물어보는 거.
                        // Unstar the post and remove self from stars --> 싫어요 이벤트
                        imageDT0.starCount = imageDT0.starCount - 1;
                        imageDT0.stars.remove(auth.getCurrentUser().getUid());
                    } else { //게시물에 내 아이디가 없으면 누를 수 있게 해주는 부분.
                        // Star the post and add self to stars
                        imageDT0.starCount = imageDT0.starCount + 1;
                        imageDT0.stars.put(auth.getCurrentUser().getUid(), true);
                    }

                    // Set value and report transaction success
                    mutableData.setValue(imageDT0);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b,
                                       DataSnapshot dataSnapshot) {
                    // Transaction completed
                    //Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                }
            });
        }

        @Override
        public int getItemCount() {
            return imageDT0List.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView textView1;
            TextView textView2;
            ImageView starButton;

            //셋팅. 이제 읽어오면 됨.
            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = (ImageView)itemView.findViewById(R.id.item_imageView);
                textView1 = (TextView)itemView.findViewById(R.id.item_textView);
                textView2 = (TextView)itemView.findViewById(R.id.item_textView2);
                starButton = (ImageView)itemView.findViewById(R.id.item_starButton_imageView);
            }
        }
    }
}
