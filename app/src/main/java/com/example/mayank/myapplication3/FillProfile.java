package com.example.mayank.myapplication3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FillProfile extends AppCompatActivity {
    TextView email,name;
    ImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);
        email=(TextView)findViewById(R.id.email);
        name=(TextView)findViewById(R.id.name);
        pic=(ImageView)findViewById(R.id.pic);
        email.setText(ThirdActivity.email.toString());
        name.setText(ThirdActivity.name.toString());
        loadmyurl(ThirdActivity.img_url);

    }
    private void loadmyurl(String myurl){
        Picasso.with(this).load(myurl).error(R.mipmap.ic_launcher).into(pic, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

    }
}