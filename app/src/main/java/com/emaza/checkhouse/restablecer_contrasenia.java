package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class restablecer_contrasenia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasenia);
    }
    public void atras(View view) {
        Intent atras = new Intent(this, MainActivity.class);
        startActivity(atras);
    }
}