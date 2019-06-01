package com.example.pavanbora.hotelmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signp extends AppCompatActivity {
    private static final String TAG = PackageManager.class.getName();
    private static final String PREF_NAME="pref";
    private FirebaseAuth mAuth;
    EditText username,password;
    Button reg;
    boolean isLoggedIn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signp);
        mAuth = FirebaseAuth.getInstance();
        username=findViewById(R.id.email);
        password=findViewById(R.id.pass1);
        reg=findViewById(R.id.register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences preferences=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
                final String email11=username.getText().toString();
                final String pass11=password.getText().toString();
                if (!email11.isEmpty() && !pass11.isEmpty()) {
                    // final FirebaseUser user=mAuth.getCurrentUser();

                        if (email11.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email11).matches()){
                            username.setError("Enter a valid Username");
                        }
                        if (pass11.length()<6||pass11.isEmpty()){
                            password.setError("Password length should be minimum 6");
                        }
                    mAuth.createUserWithEmailAndPassword( email11, pass11 ).addOnCompleteListener( new OnCompleteListener <AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task <AuthResult> task) {

                            if (task.isSuccessful()) {
                                Intent intent = new Intent( Signp.this, MainActivity.class );
                                startActivity( intent );
                                Toast.makeText( Signp.this, "Registered successfully", Toast.LENGTH_LONG ).show();
                                finish();


                            } else {
                                Log.d( "SIGN UP ERROR", task.getException().toString() );
                            }
                        }
                    } );
                }
                else{
                    username.setError("Please fill the details");
                    password.setError("please fill the details");
                }

            }
        });
    }
}

