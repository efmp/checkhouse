package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DatosUsuarioPopup extends AppCompatActivity {

    TextView txtUsuario, txtNombres, txtApellidos, txtDNI, txtCorreo;
    Button btnAceptar;
    JsonObject usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_usuario_popup);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int ancho = metrics.widthPixels;
        int alto = metrics.heightPixels;
        getWindow().setLayout((int)(ancho*0.85),(int)(alto*0.5));
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String json_str = extras.getString("user");
            usuario = new JsonParser().parse(json_str.trim()).getAsJsonObject();
        }
        asignarReferencias();
    }

    private void asignarReferencias() {
        txtUsuario = findViewById(R.id.txtUsuario);
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtDNI = findViewById(R.id.txtDNI);
        txtCorreo = findViewById(R.id.txtCorreo);
        btnAceptar = findViewById(R.id.btnAceptar);
        System.out.println("DATOS POP: "+usuario.toString());
        txtUsuario.setText(usuario.get("id").getAsString());
        txtNombres.setText(usuario.get("nombres").getAsString());
        txtApellidos.setText(usuario.get("apellidos").getAsString());
        txtDNI.setText(usuario.get("dni").getAsString());
        txtCorreo.setText(usuario.get("correo").getAsString());
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



}