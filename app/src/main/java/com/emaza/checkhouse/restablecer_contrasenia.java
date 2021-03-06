package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class restablecer_contrasenia extends AppCompatActivity {

    EditText txtCorreo;
    Button btnContinuar;
    String correo, asunto, mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasenia);
        asignarReferencia();
        limpiar();

    }
    private void asignarReferencia(){
        txtCorreo = findViewById(R.id.txtCorreo);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturarDatos();

            }
        });
    }
    private void cambiarContraseña(){
        String url = "http://checkhouseapi.atwebpages.com/controller/usuario.php/resetpassword";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                irAScreenSucess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("===>>>",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> parametros = new HashMap<>();
                parametros.put("correo", txtCorreo.getText().toString());
                parametros.put("password", getRandomString(6));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public static String getRandomString(int i){
        final String characters = "abcdefghijklmnopqrstuvyxwz0123456789";
        StringBuilder result = new StringBuilder();
        while (i>0){
            Random rand = new Random();
            result.append(characters.charAt(rand.nextInt(characters.length())));
            i--;
        }
        return result.toString();
    }
    public void atras(View view) {
        Intent atras = new Intent(this, Lista_de_solicitudes_terminada.class);
        startActivity(atras);
    }
    private void capturarDatos(){
        correo = txtCorreo.getText().toString();
        if(correo.equals("")){
            Toast toast = Toast.makeText(this,"El campo correo no puede estar vacío",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            limpiar();
        }else
            cambiarContraseña();
            enviarCorreo();
    }
    private void limpiar(){
        txtCorreo.setText("");
    }
    public void irAScreenSucess(){
        Intent restablecer = new Intent(this, sucessful_access.class);
        Bundle b = new Bundle();
        String mensaje = "Su contraseña fue restablecida.";
        b.putString("mensaje",mensaje);
        b.putString("boton","Regresar a Login");
        b.putString("screen","login");
        restablecer.putExtras(b);
        setResult(Activity.RESULT_OK,restablecer);
        startActivity(restablecer);
    }
    private void enviarCorreo(){
        String enviarcorreo = txtCorreo.getText().toString();
        String enviarasunto = "Contraseña restablecida";
        String enviarmensaje = "Su nueva contraseña es la siguiente: "+getRandomString(6);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[] {enviarcorreo});
        intent.putExtra(Intent.EXTRA_SUBJECT, enviarasunto);
        intent.putExtra(Intent.EXTRA_TEXT, enviarmensaje);

        intent.setType("message/rfc822");

        // Lanzo el selector de cliente de Correo
        startActivity(
                Intent
                        .createChooser(intent,"francorossell93@gmail.com"));

    }
}