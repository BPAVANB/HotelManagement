package com.example.pavanbora.hotelmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText user,pass;
    Button login,sign;
    TextView forgot;
    public FirebaseAuth mAuth;
    private static final String PREF_NAME = "pre";
    private static final String TAG = PackageManager.class.getName();
    boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.user2);
        pass=findViewById(R.id.pass2);
        login=findViewById(R.id.login2);
        sign=findViewById(R.id.signup);
        forgot=findViewById(R.id.forgot);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if (preferences.getBoolean("isLoggedIn", isLoggedIn))
        {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                String user1 = user.getText().toString();
                String pass1 = pass.getText().toString();

                mAuth.signInWithEmailAndPassword(user1, pass1)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    isLoggedIn=true;
                                    SharedPreferences preferences1=getSharedPreferences( PREF_NAME,MODE_PRIVATE );
                                    SharedPreferences.Editor editor=preferences1.edit();
                                    editor.putBoolean( "isLoggedIn",isLoggedIn );
                                    editor.apply();
                                    editor.commit();
                                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                                    startActivity(intent);
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(MainActivity.this, "you are loggedin", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                else
                                {

                                    Log.d(TAG,"LOGIN ERROR", task.getException());
                                    Toast.makeText(MainActivity.this,"Authentication failed.Please try again later",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Signp.class);
                startActivity(intent);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Forgot.class);
                startActivity(intent);
            }
        });

    }
}
