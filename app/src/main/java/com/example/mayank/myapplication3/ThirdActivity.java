package com.example.mayank.myapplication3;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayank.myapplication3.Model.Userdetail;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ThirdActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private Button googlesignin;
    public static  String email,name,img_url;
    private GoogleApiClient googleApiClient1;
    private static final int REQ_Code=9001;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    FirebaseAuth auth;
    FirebaseDatabase data;
    DatabaseReference reference;
    //RelativeLayout rootlayout;


    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);



        googlesignin=(Button) findViewById(R.id.googlebtn);
        //  rootlayout = (RelativeLayout) findViewById(R.id.mylayout);
        googlesignin.setOnClickListener(this);

        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient1=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.googlebtn:
                String mystatus=getConnectivityStatusString(getApplicationContext());
                if(mystatus=="wifi"){
                    Toast.makeText(getApplicationContext(),"Welcome to TodaRaiSingh",Toast.LENGTH_LONG).show();
                    signIn();
                   // myfun2();
                }
                else if(mystatus=="mobiledata")
                {
                    Toast.makeText(getApplicationContext(),"Welcome to TodaRaiSingh",Toast.LENGTH_LONG).show();
                    signIn();
                  //  myfun2();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"you are not connected with internet",Toast.LENGTH_LONG).show();

                }

                break;

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void signIn(){
        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient1);
        startActivityForResult(intent,REQ_Code);

    }
    private void handleResult(GoogleSignInResult googleSignInResult){
        if(googleSignInResult.isSuccess()){
            /*boolean b=myfun1();
            if(b==true){
                Toast.makeText(this,"fetching data",Toast.LENGTH_SHORT).show();
            }
            else{
                myfun2();
                Toast.makeText(this,"data updated",Toast.LENGTH_SHORT).show();
            }*/
            Intent intent1=new Intent(ThirdActivity.this,HomeActivity.class);
            startActivity(intent1);
            GoogleSignInAccount account=googleSignInResult.getSignInAccount();

            name=account.getDisplayName();
            email=account.getEmail();
            img_url=account.getPhotoUrl().toString();




        }
        else {
            Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_Code){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }



    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = ThirdActivity.getConnectivityStatus(context);
        String  status=null;
        if (conn == ThirdActivity.TYPE_WIFI) {

            status="wifi";
        } else if (conn == ThirdActivity.TYPE_MOBILE) {
            status="mobiledata";
        } else if (conn == ThirdActivity.TYPE_NOT_CONNECTED) {
            status="notconnected";
        }
        return status;
    }

    /* public boolean myfun1(){
         auth = FirebaseAuth.getInstance();
         data = FirebaseDatabase.getInstance();
         reference = data.getReference("users");
         auth.signInWithEmailAndPassword(email,email).addOnSuccessListener(
                 new OnSuccessListener<AuthResult>() {
                     @Override
                     public void onSuccess(AuthResult authResult) {

                         startActivity(new Intent(ThirdActivity.this,HomeActivity.class));

                         finish();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {


                 Snackbar.make(rootlayout, "Failed ", Snackbar.LENGTH_SHORT).show();

             }
         });
     return true;

     }
 */
   /* public boolean myfun2(){

        auth.createUserWithEmailAndPassword(email.toString(),email.toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Userdetail userdetail=new Userdetail();
                userdetail.setEmail(email.toString());
                userdetail.setEmail1(email.toString());
                userdetail.setName(name.toString());

                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userdetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //   Snackbar.make(rootlayout, "Registered Successfully ", Snackbar.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        // Snackbar.make(rootlayout, "Not Registered try again"+e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

                // Snackbar.make(rootlayout, "Error Occured in Registration"+e.getMessage(), Snackbar.LENGTH_SHORT).show();

            }
        });
        return true;
    }*/
}

