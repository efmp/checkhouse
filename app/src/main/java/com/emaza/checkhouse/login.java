package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class login extends AppCompatActivity {


    ConstraintLayout layout;
    TextView lblOlvido;
    TextView txtCorreo, txtPassword;
    Button btnCrearCuenta, btnIngresar;
    private JsonObject usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        asignarReferencias();
    }

    private void asignarReferencias() {
        layout = findViewById(R.id.layout);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        btnIngresar = findViewById(R.id.btnIngresar);
        lblOlvido = findViewById(R.id.lnkOlvideClave);

        //prueba@correo.com
        //pw123
        txtCorreo.setText("prueba@correo.com");
        txtPassword.setText("pw123");

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent crearcuenta1 = new Intent(login.this, crearcuenta_1.class);
                setResult(Activity.RESULT_OK,crearcuenta1);
                startActivity(crearcuenta1);
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hacerLogin();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarteclado();
            }
        });
        lblOlvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reestablecer = new Intent(login.this, restablecer_contrasenia.class);
                setResult(Activity.RESULT_OK,reestablecer);
                startActivity(reestablecer);
            }
        });
    }

    private void hacerLogin(){
        final String correo = txtCorreo.getText().toString();
        final String password = txtPassword.getText().toString();
        String url = "http://checkhouseapi.atwebpages.com/index.php/usuario/correo/" + correo;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                     usuario = new JsonParser().parse(response.trim()).getAsJsonObject();
                    if(correo.equals(usuario.get("correo").getAsString()) && password.equals(usuario.get("password").getAsString())){
                        if(usuario.get("estado").getAsString().equalsIgnoreCase("activo")){
                            if(usuario.get("tipo").getAsString().equals("usuario")){
                                System.out.println("login tipo usuarioo");
                                irSiguientePantalla_Usuario();
                            }else if(usuario.get("tipo").getAsString().equals("admin")){
                                irSiguientePantalla_Admin();
                            }
                        }else{
                            Toast.makeText(login.this,"Usted se encuentra bloqueado",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(login.this,"Usuario o contraseña incorrecto",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception error) {
                    Toast.makeText(login.this,"el correo no se encuentra registrado",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("--->>", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void irSiguientePantalla_Usuario(){
        Intent listaSolicitudes = new Intent(this, ListaSolicitudes.class);
        Bundle b = new Bundle();
        b.putString("data", usuario.toString());
        listaSolicitudes.putExtras(b);
        setResult(Activity.RESULT_OK,listaSolicitudes);
        startActivity(listaSolicitudes);
    }
    private void irSiguientePantalla_Admin(){
        Intent listaVerificaciones = new Intent(this, lista_de_verificaciones.class);
        Bundle b = new Bundle();
        b.putString("data", usuario.toString());
        listaVerificaciones.putExtras(b);
        setResult(Activity.RESULT_OK,listaVerificaciones);
        startActivity(listaVerificaciones);
    }

    private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}