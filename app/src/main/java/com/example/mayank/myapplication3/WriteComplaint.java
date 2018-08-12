package com.example.mayank.myapplication3;
import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;

import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class WriteComplaint extends AppCompatActivity {
    LocationManager locationManager;
    Geocoder geocoder;
    EditText mycomplaint;
    EditText AddressManual;
    List<Address> addresses;
    Button location,submit,cancel,picture;
    double latti;

    double longi;
    static final int REQUEST_LOCATION=1;
    static final int CAM_REQUEST=1;
    ImageView img;
    TextView datetimetext;
    static String complaintactuallocaton,body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_complaint);
        datetimetext = (TextView) findViewById(R.id.datetimetext);

        location = (Button) findViewById(R.id.myactuallocation);
        AddressManual = (EditText) findViewById(R.id.getmylocation);
        mycomplaint=(EditText) findViewById(R.id.mycomplaint);
        picture = (Button) findViewById(R.id.takeapicture);
        img = (ImageView) findViewById(R.id.img);
        submit = (Button) findViewById(R.id.complaintsend);

        cancel = (Button) findViewById(R.id.cancel);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

// textView is the TextView view that should display it
        datetimetext.setText(currentDateTimeString);

submit.setOnClickListener(new View.OnClickListener() {

    @Override

    public void onClick(View v) {
        body="<html><table style='border:1px solid black'><tr><td>Date:</td>"+"<td>"+datetimetext.getText().toString()+"</td></tr>"+"<tr><td>Complaint:</td>"+"<td>"+mycomplaint.getText().toString()+"</td></tr>"+"<tr><td>Address:</td>"+"<td>"+AddressManual.getText().toString()+"</td></tr>"+"<tr><td>UserActualLocation:</td><td>"+complaintactuallocaton+"</td></tr></table>";

        sendMessage();

    }
});

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location.setBackgroundResource(R.drawable.ic_gps_fixed_black_24dp);
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                try {
                    getLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,0);
            }
        });
    }


    void getLocation() throws IOException {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else{
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location!=null){
                latti=location.getLatitude();
                longi=location.getLongitude();
                geocoder=new Geocoder(this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latti, longi, 1);
                    String address=addresses.get(0).getAddressLine(0);
                    String area=addresses.get(0).getLocality();
                    String city=addresses.get(0).getAdminArea();
                    String country=addresses.get(0).getCountryName();
                    String postalcode=addresses.get(0).getPostalCode();

                    String fulladdress=address+","+area+","+city+","+country+","+postalcode+".";
                    AddressManual.setText(fulladdress);
                    complaintactuallocaton=fulladdress;


                }
                catch (IOException e){

                }
            }
            else{
                AddressManual.setText("Unable to find the location");

            }

        }

    }

    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        switch (requestCode){
            case REQUEST_LOCATION:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bm=(Bitmap)data.getExtras().get("data");
        img.setImageBitmap(bm);



    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void sendMessage() {
        final ProgressDialog dialog = new ProgressDialog(WriteComplaint.this);
        dialog.setTitle("Your Complaint is submitting");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MailingDemo sender = new MailingDemo("modi.mayank97@gmail.com", "golumayankappu");
                    sender.sendMail("Complaint", body, "modi.mayank97@gmail.com", "chalkup97@gmail.com");
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Complaint Sent",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();

    }

}
