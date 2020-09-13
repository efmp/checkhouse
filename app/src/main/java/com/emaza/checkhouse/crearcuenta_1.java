package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class crearcuenta_1 extends AppCompatActivity {

    TextView txtcorreo, txtcelular;
    Button btnContinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_1);
        asignarReferencias();
    }

    private void asignarReferencias() {
        txtcorreo = findViewById(R.id.txtCorreo);
        txtcelular = findViewById(R.id.txtTelefono);
        btnContinuar = findViewById(R.id.btnContinuar);
    }


}