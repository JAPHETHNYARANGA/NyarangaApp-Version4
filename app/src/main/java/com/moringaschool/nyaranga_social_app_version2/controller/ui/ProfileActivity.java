package com.moringaschool.nyaranga_social_app_version2.controller.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moringaschool.nyaranga_social_app_version2.R;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    ImageView selectedImage;
    Button cameraBtn,galleryBtn;
    String currentPhotoPath;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        selectedImage = findViewById(R.id.displayImageView);
        cameraBtn = findViewById(R.id.cameraBtn);
        galleryBtn = findViewById(R.id.galleryBtn);

        storageReference = FirebaseStorage.getInstance().getReference();


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });

        galleryBtn.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GALLERY_REQUEST_CODE);
        });

    }
    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }

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

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap)data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte bb[] = bytes.toByteArray();
        myimage.setImageBitmap(thumbnail);
        
        uploadToFirebase(bb);

    }

    private void uploadToFirebase(byte[] bb) {
        StorageReference sr = mstorageRef.child("myimages/a.jpg");
        sr.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ProfileActivity.this,"uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(ProfileActivity.this,"Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }
}