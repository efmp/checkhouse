package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Lista_de_solicitudes_terminada extends AppCompatActivity {

    JsonObject usuario;
    Button btnEmpezar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_solicitudes_terminada);
        Bundle extras = getIntent().getExtras();
        String data = "";
        if(extras == null){
            Toast.makeText(this,"Data is null",Toast.LENGTH_SHORT).show();
        }else{
            data = extras.getString("data");
            usuario = new JsonParser().parse(data).getAsJsonObject();
        }
        asignarReferencias();

    }

    private void asignarReferencias() {

        btnEmpezar = findViewById(R.id.btnEmpezar1);
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Lista_de_solicitudes_terminada.this, MapsActivity.class);
                setResult(Activity.RESULT_OK,home);
                startActivity(home);
            }
        });
    }
}