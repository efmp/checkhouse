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

        //region OBTENER DATOS DE ANTERIOR PANTALLA
        Bundle extras = getIntent().getExtras();
        String data = "";
        if(extras == null){
            Toast.makeText(this,"Data is null",Toast.LENGTH_SHORT).show();
        }else{
            data = extras.getString("data");
            usuario_json = new JsonParser().parse(data).getAsJsonObject();
        }
        //endregion
        asignarReferencias();
    }

    private void asignarReferencias() {
        btnEvaluar = findViewById(R.id.btnEvaluar);
        btnRegistrarSolicitud = findViewById(R.id.btnRegistrarSolicitud);
        imgBanco = findViewById(R.id.imgBanco);

        //region MOSTRAR FOTO BANCO
        int indexImagen=0;
        if(usuario_json.get("empresa").getAsString().equalsIgnoreCase("Interbank")){
            indexImagen=2;
        }else if(usuario_json.get("empresa").getAsString().equalsIgnoreCase("BCP")){
            indexImagen=1;
        }
        if(indexImagen>0){
            imgBanco.setImageResource(datosImg[indexImagen]);
        }
        //endregion

        btnRegistrarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irRegistrarSolicitud();
            }
        });

        btnEvaluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irEvaluarSolicitud();
            }
        });

    }

    private void irRegistrarSolicitud(){
        Intent registrar = new Intent(this, RegistrarSolicitud.class);
        Bundle b = new Bundle();
        b.putString("data", usuario_json.toString());
        registrar.putExtras(b);
        setResult(Activity.RESULT_OK,registrar);
        startActivity(registrar);
    }
    private void irEvaluarSolicitud(){
        Intent evaluar = new Intent(this, ListaSolicitudesEvaluar.class);
        Bundle b = new Bundle();
        b.putString("data", usuario_json.toString());
        evaluar.putExtras(b);
        setResult(Activity.RESULT_OK,evaluar);
        startActivity(evaluar);
    }

}