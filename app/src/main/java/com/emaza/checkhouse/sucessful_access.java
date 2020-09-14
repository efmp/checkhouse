package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sucessful_access extends AppCompatActivity {

    TextView mensaje;
    Button goTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucessful_access);
        asignarReferencias();
    }
    private void asignarReferencias() {
        Bundle extras = getIntent().getExtras();
        String texto = extras.getString("mensaje");
        mensaje = findViewById(R.id.lblMensaje);
        goTo = findViewById(R.id.btnGoTo);
        mensaje.setText(texto);

        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}