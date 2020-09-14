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
                Intent Maps = new Intent(login.this, Maps.class);
                setResult(Activity.RESULT_OK,Maps);
                startActivity(Maps);
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
                    //prueba@correo.com
                    //pw123
                    JsonObject json = new JsonParser().parse(response.trim()).getAsJsonObject();
                    if(correo.equals(json.get("correo").getAsString()) && password.equals(json.get("password").getAsString())){
                        Intent home = new Intent(login.this, Lista_de_solicitudes_terminada.class);
                        setResult(Activity.RESULT_OK,home);
                        startActivity(home);
                        Toast.makeText(login.this,"Usted se puede loguear",Toast.LENGTH_SHORT).show();
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

    private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}