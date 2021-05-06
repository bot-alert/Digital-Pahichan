package com.example.digitalpahichan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    EditText phoneNumber;
    Button getOtp;
    ProgressBar progressBarGetOtp;
    private FirebaseAuth mAuth;
    public String mVerificationId;
    public PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumber = findViewById(R.id.phoneNumber);
        getOtp = findViewById(R.id.getOtp);
        mAuth = FirebaseAuth.getInstance();
        progressBarGetOtp = findViewById(R.id.progressBarGetOtp);
        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumber.getText().toString().length()==10){
                    sendVerification();
                    progressBarGetOtp.setVisibility(View.VISIBLE);
                    getOtp.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Incomplete Phone Number",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
protected void sendVerification(){
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d("hello", "onVerificationFailed: "+e);
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // Save verification ID and resending token so we can use them later
            progressBarGetOtp.setVisibility(View.GONE);
            getOtp.setVisibility(View.VISIBLE);
            mVerificationId = verificationId;
            mResendToken = token;
            Log.d("1", "onCodeSent:"+verificationId+"---"+token);
            Intent movetoEnterOtp = new Intent(MainActivity.this,EnterOtp.class);
            if (verificationId==null){
                Log.d("kklll", "onCodeSent: EEEEEEEEEEEEEEEEEEEEE");
            }
            movetoEnterOtp.putExtra("message",verificationId);
            movetoEnterOtp.putExtra("phone",phoneNumber.getText().toString());
            startActivity(movetoEnterOtp);
        }
    };


    PhoneAuthOptions options =
            PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber("+977"+phoneNumber.getText().toString())       // Phone number to verify
                    .setTimeout(60L,TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(MainActivity.this)                 // Activity (for callback binding)
                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                    .build();
    PhoneAuthProvider.verifyPhoneNumber(options);
}

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            Intent intent = new Intent(MainActivity.this,HomePage.class);
            startActivity(intent);
        }

    }
}