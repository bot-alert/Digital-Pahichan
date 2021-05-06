package com.example.digitalpahichan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class EnterOtp extends AppCompatActivity {
    EditText enterOtp,enterOtp2,enterOtp3,enterOtp4,enterOtp5,enterOtp6;
    TextView phoneNumber;
    String phonenumber;
    Button checkOtp;
    String mVerificationId,code;
    EditText[] edit;
    FirebaseAuth mAuth;
    ProgressBar progressBarVerifyOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_otp);
        //OPT CODE
        enterOtp  = findViewById(R.id.enterOtp);
        enterOtp2 = findViewById(R.id.enterOtp2);
        enterOtp3 = findViewById(R.id.enterOtp3);
        enterOtp4 = findViewById(R.id.enterOtp4);
        enterOtp5 = findViewById(R.id.enterOtp5);
        enterOtp6 = findViewById(R.id.enterOtp6);
        //END OF OTP CODE

        edit = new  EditText[]{enterOtp,enterOtp2,enterOtp3,enterOtp4,enterOtp5,enterOtp6};

        enterOtp.addTextChangedListener(new GenericTextWatcher(enterOtp, edit));
        enterOtp2.addTextChangedListener(new GenericTextWatcher(enterOtp2, edit));
        enterOtp3.addTextChangedListener(new GenericTextWatcher(enterOtp3, edit));
        enterOtp4.addTextChangedListener(new GenericTextWatcher(enterOtp4, edit));
        enterOtp5.addTextChangedListener(new GenericTextWatcher(enterOtp5, edit));
        enterOtp6.addTextChangedListener(new GenericTextWatcher(enterOtp6, edit));




        progressBarVerifyOtp = findViewById(R.id.progressBarVerifyOtp);
        checkOtp = findViewById(R.id.chekOtp);//BUTTON
        phoneNumber = findViewById(R.id.phoneNumber);
        Intent intent = getIntent();
        mVerificationId = intent.getStringExtra("message");
        phoneNumber.setText(intent.getStringExtra("phone"));
        phonenumber = intent.getStringExtra("phone");


        checkOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterOtp!=null&&enterOtp2!=null&&enterOtp3!=null&&enterOtp4!=null&&enterOtp5!=null&&enterOtp6!=null){
                    checkOTP();
                    progressBarVerifyOtp.setVisibility(View.VISIBLE);
                    checkOtp.setVisibility(View.GONE);
                }
               else {
                   Toast.makeText(getApplicationContext(),"All code required",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    protected void checkOTP(){
        code = enterOtp.getText().toString()
                +enterOtp2.getText().toString()
                +enterOtp3.getText().toString()
                +enterOtp4.getText().toString()
                +enterOtp5.getText().toString()
                +enterOtp6.getText().toString();

        Log.d(":xd", "checkOTP: "+mVerificationId+""+code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarVerifyOtp.setVisibility(View.GONE);
                checkOtp.setVisibility(View.VISIBLE);
                if(task.isSuccessful()){

                    Toast.makeText(EnterOtp.this,"Verified",Toast.LENGTH_LONG).show();
                    Intent moveToHome = new Intent(EnterOtp.this,HomePage.class);
                    startActivity(moveToHome);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed to Verify",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("CREDINTIAL", "onVerificationCompleted:" + credential);

    }
    public class GenericTextWatcher implements TextWatcher {
        private final EditText[] editText;
        private View view;
        public GenericTextWatcher(View view, EditText editText[])
        {
            this.editText = editText;
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.enterOtp:
                    if (text.length() == 1)
                        editText[1].requestFocus();
                    break;
                case R.id.enterOtp2:
                    if (text.length() == 1)
                        editText[2].requestFocus();
                    else if (text.length() == 0)
                        editText[0].requestFocus();
                    break;
                case R.id.enterOtp3:
                    if (text.length() == 1)
                        editText[3].requestFocus();
                    else if (text.length() == 0)
                        editText[1].requestFocus();
                    break;
                case R.id.enterOtp4:
                    if (text.length() == 1)
                        editText[4].requestFocus();
                    else if (text.length() == 0)
                        editText[2].requestFocus();
                    break;
                case R.id.enterOtp5:
                    if (text.length() == 1)
                        editText[5].requestFocus();
                    else if (text.length() == 0)
                        editText[3].requestFocus();
                    break;
                case R.id.enterOtp6:
                    if (text.length() == 0)
                        editText[2].requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    }
}
