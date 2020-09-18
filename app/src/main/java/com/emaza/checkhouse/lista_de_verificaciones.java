package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class lista_de_verificaciones extends AppCompatActivity {

    Button btnEvaluar, btnRegistrarSolicitud;
    ImageView imgBanco;
    int[] datosImg = {R.drawable.banco_vacio,R.drawable.bcpicon,R.drawable.interbank};
    JsonObject usuario_json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_verificaciones);
        Bundle extras = getIntent().getExtras();
        String data = "";
        if(extras == null){
            Toast.makeText(this,"Data is null",Toast.LENGTH_SHORT).show();
        }else{
            data = extras.getString("data");
            usuario_json = new JsonParser().parse(data).getAsJsonObject();
        }
        asignarReferencias();
    }

    private void asignarReferencias() {
        btnEvaluar = findViewById(R.id.btnEvaluar);
        btnRegistrarSolicitud = findViewById(R.id.btnRegistrarSolicitud);
        imgBanco = findViewById(R.id.imgBanco);


        int indexImagen=0;
        if(usuario_json.get("empresa").getAsString().equalsIgnoreCase("Interbank")){
            indexImagen=2;
        }else if(usuario_json.get("empresa").getAsString().equalsIgnoreCase("BCP")){
            indexImagen=1;
        }
        if(indexImagen>0){
            imgBanco.setImageResource(datosImg[indexImagen]);
        }

        btnRegistrarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void irRegistrarSolicitud(){
        Intent listaVerificaciones = new Intent(this, lista_de_verificaciones.class);
        Bundle b = new Bundle();
        b.putString("data", usuario_json.toString());
        listaVerificaciones.putExtras(b);
        setResult(Activity.RESULT_OK,listaVerificaciones);
        startActivity(listaVerificaciones);
    }
}