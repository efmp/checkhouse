package com.emaza.checkhouse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class agregar_foto extends AppCompatActivity {

    private ImageView mimageView;

    private static final int REQUEST_IMAGE_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_foto);
        mimageView = findViewById(R.id.imgfoto);



        if (ContextCompat.checkSelfPermission(agregar_foto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(agregar_foto.this, new String[]
                    {
                            Manifest.permission.CAMERA
                    }, 100);
        }

    }




    public void TomaFoto(View view)
    {
        Intent imageTakenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakenIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(imageTakenIntent,REQUEST_IMAGE_CAPTURE);
        }
    }


    public void TomaFoto2(View view)
    {
        Intent imageTakenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakenIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(imageTakenIntent,REQUEST_IMAGE_CAPTURE);
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mimageView.setImageBitmap(imageBitmap);
        }
    }}