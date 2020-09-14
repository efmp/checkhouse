package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lista_de_solicitudes_terminada extends AppCompatActivity {

    Button btnEmpezar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_solicitudes_terminada);
        asignarReferencias();
    }

    private void asignarReferencias() {
        btnEmpezar = findViewById(R.id.btnEmpezar1);
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Lista_de_solicitudes_terminada.this, Maps.class);
                setResult(Activity.RESULT_OK,home);
                startActivity(home);
            }
        });
    }
}