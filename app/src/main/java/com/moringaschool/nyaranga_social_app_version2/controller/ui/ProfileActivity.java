package com.moringaschool.nyaranga_social_app_version2.controller.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 101){
                Log.e("error","3");
                onCaptureImageResult(data);
            }
        }
    }
}