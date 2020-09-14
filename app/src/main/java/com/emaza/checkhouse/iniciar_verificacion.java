package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class iniciar_verificacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_verificacion);
    }
    public void iniciarVerificacion(View view) {
        Intent iniciar = new Intent(this, alertas_verificacion.class);
        startActivity(iniciar);
    }
    public void atras(View view) {
        Intent atras = new Intent(this, Lista_de_solicitudes_terminada.class);
        startActivity(atras);
    }
}