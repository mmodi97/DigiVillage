package com.example.mayank.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
ImageView pic1,logo1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pic1=(ImageView)findViewById(R.id.img1);
        logo1=(ImageView)findViewById(R.id.itsmylogo1);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        pic1.startAnimation(animation);
        logo1.startAnimation(animation);
        final Intent intent=new Intent(MainActivity.this,ThirdActivity.class);
        Thread timer=new Thread(){
            public void run(){

                try{
                    sleep(3000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };



        timer.start();
    }
    }

