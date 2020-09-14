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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class crearcuenta_1 extends AppCompatActivity {

    Context context = this;
    ConstraintLayout layout;
    TextView txtcorreo;
    Button btnContinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_1);
        asignarReferencias();
    }

    private void asignarReferencias() {
        txtcorreo = findViewById(R.id.txtCorreo);
        btnContinuar = findViewById(R.id.btnContinuar);
        layout = findViewById(R.id.layout);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCorreo();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarteclado();
            }
        });
    }

    private void validarCorreo() {
        if(txtcorreo.getText().toString().equals(""))Toast.makeText(this,"ingrese su correo",Toast.LENGTH_SHORT).show();
        else {
            final String correo = txtcorreo.getText().toString();
            String url = "http://checkhouseapi.atwebpages.com/index.php/existecorreo/" + correo;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        String mensaje = response.replace("\"","").trim();
                        boolean estado = Boolean.parseBoolean(mensaje);
                        if(!estado){
                            irACrearCuentaPaso2(correo);
                        }else{
                            Toast.makeText(context, "el correo ya se encuentra registrado", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception error) {
                        Log.i("--->>", error.toString());
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
    }

    private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void irACrearCuentaPaso2(String correo){
        Intent crearcuenta2 = new Intent(this, crearcuenta_2.class);
        Bundle b = new Bundle();
        b.putString("correo",correo);
        crearcuenta2.putExtras(b);
        setResult(Activity.RESULT_OK,crearcuenta2);
        startActivity(crearcuenta2);
    }

}