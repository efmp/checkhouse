package com.emaza.checkhouse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emaza.checkhouse.adaptadores.solicitudesAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistrarSolicitud extends AppCompatActivity {

    TextView txtDni, txtUsuario;
    ListView lstObligatoria, lstOpcional;
    Button btnAddObligatoria, btnAddOpcional, btnBuscar, btnRegistrar, btnCancelar;
    String descripcion;
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_solicitud);
        asignarReferencias();
    }
    @Override
    protected void onResume(){
        super.onResume();
        asignarReferencias();
        cerrarteclado();
    }


    ArrayList<String> listaobligatorio= new ArrayList<>();
    ArrayList<String> listaopcional= new ArrayList<>();
    ArrayAdapter<String> adapter;
    private void asignarReferencias() {
        txtDni = findViewById(R.id.txtDNI);
        txtUsuario = findViewById(R.id.txtUsuario);
        lstObligatoria = findViewById(R.id.lstObligatorio);
        lstOpcional = findViewById(R.id.lstOpcional);
        btnAddOpcional = findViewById(R.id.btnAgregarOpcional);
        btnAddObligatoria = findViewById(R.id.btnAgregarObligatorio);
        btnAddOpcional = findViewById(R.id.btnAgregarOpcional);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        layout = findViewById(R.id.layout);


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDni.getText().toString().equals("")){
                    Toast.makeText(RegistrarSolicitud.this,"Ingrese dni",Toast.LENGTH_SHORT).show();
                }else{
                    String url = "http://checkhouseapi.atwebpages.com/controller/usuario.php/usuario/"+txtDni.getText().toString();
                    System.out.println("URL: "+url);
                    hacerGetUsuario(url,new DataResponseListener(){
                        @Override
                        public void onResponseData(String data){
                            System.out.println("RESPONSE: "+data);
                            JsonArray jsonArray = new Gson().fromJson(data,JsonArray.class);
                            if(jsonArray.size()==0){
                                txtUsuario.setText("");
                                Toast.makeText(RegistrarSolicitud.this,"Usuario no encontrado",Toast.LENGTH_SHORT).show();
                            }else{
                                Intent buscaruser = new Intent(RegistrarSolicitud.this, DatosUsuarioPopup.class);
                                Bundle b = new Bundle();
                                b.putString("user", jsonArray.get(0).getAsJsonObject().toString());
                                buscaruser.putExtras(b);
                                setResult(Activity.RESULT_OK,buscaruser);
                                startActivity(buscaruser);
                                JsonObject object = jsonArray.get(0).getAsJsonObject();
                                txtUsuario.setText(object.get("nombres").getAsString()+" "+object.get("apellidos").getAsString());

                            }
                        }
                    });
                }

            }
        });
        btnAddObligatoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(RegistrarSolicitud.this);
                mydialog.setTitle("Foto Obligatoria");
                final EditText inputText = new EditText(RegistrarSolicitud.this);
                inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(inputText);

                mydialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //positiveAction;
                        try{
                            descripcion = inputText.getText().toString();
                            setListObligatorio();
                        }
                        catch (Exception e){
                            Toast.makeText(RegistrarSolicitud.this,"Error",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                mydialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mydialog.show();
            }
        });

        btnAddOpcional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(RegistrarSolicitud.this);
                mydialog.setTitle("Foto Opcional");
                final EditText inputText = new EditText(RegistrarSolicitud.this);
                inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(inputText);

                mydialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //positiveAction;

                        descripcion = inputText.getText().toString();

                        setListOpcional();
                        cerrarteclado();
                    }
                });
                mydialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        cerrarteclado();
                    }
                });
                mydialog.show();
            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarteclado();
            }
        });

    }

    private void setListObligatorio(){
        listaobligatorio.add(descripcion);
        adapter = new ArrayAdapter<String>(RegistrarSolicitud.this,android.R.layout.simple_list_item_1,listaobligatorio);
        lstObligatoria.setAdapter(adapter);
        cerrarteclado();
    }
    private void setListOpcional(){
        listaopcional.add(descripcion);
        adapter = new ArrayAdapter<String>(RegistrarSolicitud.this,android.R.layout.simple_list_item_1,listaopcional);
        lstOpcional.setAdapter(adapter);
        cerrarteclado();
    }

    private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }





    private String scode;
    private void hacerGetUsuario(String url, final DataResponseListener mListener){
        scode=null;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == "") scode = "Sin respuesta";
                        else scode = response;
                        if (mListener != null){
                            mListener.onResponseData(scode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                scode = error.getMessage();
                if (mListener != null){
                    mListener.onResponseData(scode);
                }
            }
        });
        queue.add(stringRequest);
    }
    public interface DataResponseListener {
        void onResponseData(String data);
    }
}