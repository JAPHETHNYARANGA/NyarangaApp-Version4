package com.moringaschool.nyaranga_social_app_version2.controller.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.moringaschool.nyaranga_social_app_version2.R;

public class ProfileActivity extends AppCompatActivity {
    private StorageReference mstorageRef;
    ImageView myimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

       mstorageRef = FirebaseStorage.getInstance().getReference();

       myimage = findViewById(R.id.myimage);
    }
    public void uploadImage(View v){

    }
}