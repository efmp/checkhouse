package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class crearcuenta_3 extends AppCompatActivity {
    ConstraintLayout layout;
    Button btnCrear, btnNoAcepto;
    CheckBox terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_3);
        asignarReferencias();
    }
    private void asignarReferencias() {
        layout = findViewById(R.id.layout);
        btnCrear = findViewById(R.id.btnCrear);
        btnNoAcepto = findViewById(R.id.btnNoAcepto);
        terms = findViewById(R.id.chkTerms);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(terms.isChecked()){
                    registrarUsuario();
                }else {
                    Toast toast = Toast.makeText(crearcuenta_3.this, "Debe aceptar los terminos y condiciones para continuar.", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void registrarUsuario(){
        String url = "http://checkhouseapi.atwebpages.com/index.php/usuarios";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast toast = Toast.makeText(crearcuenta_3.this, "el usuario se registró correctamente", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                irAScreenSucess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(crearcuenta_3.this, "ocurrio un error", Toast.LENGTH_LONG);
                Log.i("--->>", error.toString());
            }
        }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Bundle extras = getIntent().getExtras();
                String nombres = extras.getString("nombres");
                String apellidos = extras.getString("apellidos");
                String correo = extras.getString("correo");
                String documento = extras.getString("documento");
                String password = extras.getString("password");
                Map<String,String> parametros = new HashMap<>();
                parametros.put("nombres",nombres);
                parametros.put("apellidos",apellidos);
                parametros.put("dni",documento);
                parametros.put("correo",correo);
                parametros.put("password",password);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void irAScreenSucess(){
        Intent crearcuenta3 = new Intent(this, sucessful_access.class);
        Bundle b = new Bundle();
        String mensaje = "Completó correctamente el registro.";
        b.putString("mensaje",mensaje);
        crearcuenta3.putExtras(b);
        setResult(Activity.RESULT_OK,crearcuenta3);
        startActivity(crearcuenta3);
    }


}