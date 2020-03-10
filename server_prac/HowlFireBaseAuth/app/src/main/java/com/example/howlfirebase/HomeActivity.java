package com.example.howlfirebase;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.loader.content.CursorLoader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int GALLERY_CODE = 10;
    private TextView nameTextView;
    private TextView emailTextView;
    private FirebaseAuth auth; //싱글톤패턴으로 어느 액티비티를 가든 참조할 수 있다. 단 쓰기 전에 인스턴스를 받아와야 한다. 얘로 아이디와 비밀번호 받아오면 됨.
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private ImageView imageView;
    private EditText title;
    private EditText description;
    private Button button;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        //다 찾아와서 적용하기. 이제 onActivityResult에서 이미지뷰 적용해주면 됨.
        imageView = (ImageView)findViewById(R.id.imageView);
        title = (EditText)findViewById(R.id.title);
        description = (EditText)findViewById(R.id.description);
        button = (Button)findViewById(R.id.button);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //권한요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        //findViewById로 id를 찾아서 연동해주면됨. NavigationView로 연동해주면 됨.
        NavigationView navigationView = findViewById(R.id.nav_view);
        //id를 어떻게 찾아주냐면 View에 navigationView를 담아줌
        View view = navigationView.getHeaderView(0);

        nameTextView = (TextView)view.findViewById(R.id.header_name_textView);
        emailTextView = (TextView)view.findViewById(R.id.header_email_textView);

        nameTextView.setText(auth.getCurrentUser().getDisplayName());
        emailTextView.setText(auth.getCurrentUser().getEmail());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //https://hyesunzzang.tistory.com/29
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_logout){
                    auth.signOut();
                    //페이스북 로그인도 로그아웃 코드를 넣어줘야 함. 이걸 넣어줘야 페이스북도 연동이 되는 거.
                    //LoginManager.getInstance().logOut();
                    //자기는 사라짐
                    finish();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }else if(id == R.id.nav_gallery) {
                    //앨범 불러오는 코드
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                    startActivityForResult(intent, GALLERY_CODE); // 액티비티를 생성한 후 생성한 액티비티가 종료되었을 시, 기존 액티비티에게 신호를 주기 위한 메소드. https://mommoo.tistory.com/21
                }else if(id == R.id.nav_board) {
                    startActivity(new Intent(HomeActivity.this, BoardActivity.class));
                }
                return false;
            }
        });

        //이미지, title, description을 데이터에 담아서 업로드하기 위해서 버튼에 리스너를 달기
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //버튼에 업로드 코드를 넣어주면 됨.
                upload(imagePath);//imagePath넣어주면 파일이 올라감.
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //이미지를 클릭하면 결과값이 넘어감. 결과값이 onActivityResult에 넘어감. 결과값은 코드값을 말하는 건가...

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_CODE) { // (requestCode == GALLERY_CODE)면 작동한다.
            //String path = getPath(data.getData()); //데이터를 가져와서 getData. 원래 이러면 파일 주소가 넘어와야 함. 경로가 넘어와야 하는데 바로 경로가 넘어오지 않음. 따로 쳐줘야함.
            if(data.getData() != null) {
                //System.out.println(data.getData());
                //System.out.println(getPath(data.getData()));

                imagePath = getPath(data.getData()); //imagePath는 나중에 업로드할 때 씀.
                //내가 누른 사진의 경로를 파일로 만들어서 그 파일을 이미지 뷰에 적용시켜주는 코드임.
                File f = new File(imagePath); //이렇게 하면 경로가 넘어오는 데 바로 적용해주기 위해서는 파일로 만들어줘야 함.
                //파일에 담아주고 나서 이미지 뷰에 적용하면 됨.
                imageView.setImageURI(Uri.fromFile(f)); //파일을 집어넣어 줌. 집어넣어 주면 바로 이미지 셋팅이 됨.
            }

        }
    }
    //https://firebase.google.com/docs/storage/android/create-reference?hl=ko
    // uri 절대경로 가져오기
    public String getPath(Uri uri){

        String [] proj = {MediaStore.Images.Media.DATA};
                CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }

    private void upload(String uri) { //경로를 매개변수로 준다. 나중에 버튼 누르면 작동될 것.
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://howlfirebase-4dd43.appspot.com");

        //파일을 업로드 해주는 코드
        Uri file = Uri.fromFile(new File(uri));
        final StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

       /* final String downloadUrl = storage.getReference().child("images").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        }).toString();*/

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //성공했을 때 어떻게 처리할 지 추가하는 부분
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // 파일만 올리면 안되고 데이터베이스에 어떤 파일을 올리고 파일에 대한 설명도 올리고
                // string->uri 형변환: https://frog-hindleg.tistory.com/332
           /*     if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot.getMetadata().getReference() != null) {

                    }
                }*/
                //Uri downloadUrl = Uri.parse(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()); //url주소 넘어오는 얘 https://www.it-swarm.asia/ko/android/tasksnapshotgetdownloadurl-%EB%A9%94%EC%86%8C%EB%93%9C%EA%B0%80-%EC%9E%91%EB%8F%99%ED%95%98%EC%A7%80-%EC%95%8A%EC%8A%B5%EB%8B%88%EB%8B%A4/838306
                //String downloadUrl = riversRef.getDownloadUrl().toString();

                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) { //onSeuccess 안에있는  코드를 riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() 밖에서 썼을 때는 안되었음.
                        //디비에 저장을 함. child라는 경로에 데이터를 setvalue하겠다.
                        //setValue에 들어갈 데이터
                        ImageDT0 imageDT0 = new ImageDT0();

                        //Log.d("★★★★★★★★11111", uri.toString());
                        imageDT0.imageUrl = uri.toString();
                        imageDT0.title = title.getText().toString(); //title은 아까 만들어 줬던 EditText
                        imageDT0.description = description.getText().toString();
                        imageDT0.uid = auth.getCurrentUser().getUid();
                        imageDT0.userId = auth.getCurrentUser().getEmail();
                        if(imageDT0.imageUrl != null)
                            Log.d("imageUrl", imageDT0.imageUrl);
                        //db에 접근
                        database.getReference().child("images").push().setValue(imageDT0); //child에 이미지 넘겨주고 setValue에 데이터 넘겨주고 // ImageDT0를 통째로 넘겨주면 파일이 저장됨.
                    }
                });


            }
        });
    }

}
