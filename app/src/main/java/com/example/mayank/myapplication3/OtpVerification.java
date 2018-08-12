package com.example.mayank.myapplication3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {
EditText code;
Button left,right,resendotpbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
    code=(EditText)findViewById(R.id.code);
    left=(Button)findViewById(R.id.leftbuttonotp);
    right=(Button)findViewById(R.id.rightbuttonotp);
    resendotpbutton=(Button)findViewById(R.id.resendotpbutton);



    }
    public void verifyotp(View view){
    String otpcode=code.getText().toString();
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(Contactdetail.phoneverificationid,otpcode);
        signInWithPhoneAuthCredential(credential);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Contactdetail.auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
if(task.isSuccessful()){
    FirebaseUser user=task.getResult().getUser();


}
else
{
    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){

    }


}
            }
        });
    }

    public void resendcode(View view){
String phonenumber=Contactdetail.phonenumber.toString();
Contactdetail c=new Contactdetail();
c.setUpVerificationCallbacks();
PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60, TimeUnit.SECONDS,this,Contactdetail.verificationcallbacks,Contactdetail.resendToken);



    }
}
