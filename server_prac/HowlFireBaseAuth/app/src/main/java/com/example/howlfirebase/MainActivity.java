package com.example.howlfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity{

    private static final int RC_SIGN_IN = 10;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth.AuthStateListener mAuthListener; //이 리스너는 로그인 했을 때 얘가 듣고 프로세스를 실행하도록 하는 애.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance(); //싱글톤 패턴으로 작동됨.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build(); //구글 플러스 준비

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //이 버튼을 누르면 구글 플러스에 물어봄. 이사람이 구글 사용자니?
        SignInButton googleLogin = (SignInButton)findViewById(R.id.login_button);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent(); //로그인 인증해주는 intent
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        editTextEmail = (EditText)findViewById(R.id.edittext_email);
        editTextPassword = (EditText)findViewById(R.id.edittext_password);

        Button emailLogin = (Button)findViewById(R.id.email_login_button);
        emailLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString()); //클릭할 때 호출하는데 아이디와 비번을 넘겨주면 됨.
            }
        });

        //로그인 부분
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); //실행하자 마자 자기는 사라짐.//해당 액티비티가 종료 https://www.charlezz.com/?p=838
                }else {

                }
            }
        };
    }

    //비밀번호 기반 계정으로 인증 --> 인강 2강
    private void createUser(final String email, final String password) {
        //아래의 코드만 있으면 바로 회원 가입이 끝남.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                        }else { //실패 - 아이디가 있거나, 비밀번호가 안 맞거나
                            //아이디가 있는 것으로 간주를 하고 여기에 넣어주겠다.
                            loginUser(email,password);
                        }
                        /*else { //로그인 실패 했을 때
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        } */

                        // ...
                    }
                });
    }

    //5강- 리스너(비밀번호 인증)
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "이메일 로그인 완료", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //구글 사용자가 맞으면 아래의 코드로 값이 넘어옴.
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account); //그리고 나서 파이어베이스로 값을 넘겨주는 거.
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) { //위에서 넘겨준 값을 파이어베이스에서 받아서, 이사람에 대한 정보를 받아서 토큰을 받아서 인증한 것을 파이어베이스로 넘겨줘서 회원가입 했던 부분인 authentication으로 값이 넘어감.

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        }else {
                            Toast.makeText(MainActivity.this, "Firebase아이디 생성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        }

                        /*else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }*/

                        // ...
                    }
                });
    }

    //리스너의 귀를 만들어주는 부분.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
