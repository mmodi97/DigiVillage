package com.example.mayank.myapplication3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Contactdetail extends AppCompatActivity {
    Button right,left;
    EditText countrycode,contactno;
    private static final String TAG="PhoneAuth";
    static String phoneverificationid ,phonenumber;
    public static PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationcallbacks;
    public static PhoneAuthProvider.ForceResendingToken resendToken;
    public static FirebaseAuth auth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactdetails);
        right=(Button)findViewById(R.id.rightbutton);
        left=(Button)findViewById(R.id.leftbutton);
        countrycode=(EditText)findViewById(R.id.countrycode);
        contactno=(EditText)findViewById(R.id.number);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendcode(right);
               Intent intent=new Intent(Contactdetail.this,OtpVerification.class);
               startActivity(intent);

            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Contactdetail.this,ThirdActivity.class);
                startActivity(intent);
            }
        });

        countrycode.setEnabled(false);
        auth=FirebaseAuth.getInstance();


    }
    public void sendcode(View view){
 phonenumber=contactno.getText().toString();
setUpVerificationCallbacks();
PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60, TimeUnit.SECONDS,this,verificationcallbacks);

    }

    public  void setUpVerificationCallbacks() {
        verificationcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
signInwithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Log.d(TAG,"Invalid"+e.getLocalizedMessage());

                }
                else if(e instanceof FirebaseTooManyRequestsException){
                Log.d(TAG,"SMS quota exceeded");

                }

            }
        };
    }
    public  void onCodeSent(String phoneverification,PhoneAuthProvider.ForceResendingToken Token){
            phoneverificationid=phoneverification;
            resendToken=Token;
            Intent intent=new Intent(Contactdetail.this,OtpVerification.class);
            startActivity(intent);

    }

    private void signInwithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
    }
}

