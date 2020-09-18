package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RegistrarSolicitud extends AppCompatActivity {

    TextView txtDni, txtUsuario;
    ListView lstObligatoria, lstOpcional;
    Button btnAddObligatoria, btnAddOpcional, btnBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_solicitud);
        asignarReferencias();
    }

    private void asignarReferencias() {


        btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrarSolicitud.this,BuscarUsuario.class));
            }
        });
    }
}