package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
        String texto="",textoBoton="",screen="";
        if(extras!= null){
            texto = extras.getString("mensaje");
            textoBoton = extras.getString("boton");
            screen = extras.getString("screen");
        }

        mensaje = findViewById(R.id.lblMensaje);
        goTo = findViewById(R.id.btnGoTo);
        mensaje.setText(texto);
        goTo.setText(textoBoton);

        final String finalScreen = screen;
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (finalScreen){
                    case "login":
                        regresarALogin();
                        break;
                    case "listaverificaciones":
                        regresarAListaVerificaciones();
                        break;
                    default:
                        System.out.println("No hacer nada");
                        break;
                }

            }
        });
    }

    private void regresarALogin(){
        Intent login = new Intent(this, login.class);
        Bundle b = new Bundle();
        String mensaje = "Completó correctamente el registro.";
        b.putString("mensaje",mensaje);
        login.putExtras(b);
        setResult(Activity.RESULT_OK,login);
        startActivity(login);
    }

    private void regresarAListaVerificaciones(){
        Bundle extras = getIntent().getExtras();
        String data="";
        if(extras!= null){
            data = extras.getString("data");
        }

        Intent opciones_solicitante = new Intent(this, lista_de_verificaciones.class);
        Bundle b = new Bundle();
        b.putString("data",data);
        opciones_solicitante.putExtras(b);
        setResult(Activity.RESULT_OK,opciones_solicitante);
        startActivity(opciones_solicitante);
    }
}