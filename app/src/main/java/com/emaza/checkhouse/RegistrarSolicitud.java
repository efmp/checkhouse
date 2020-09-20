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

import com.android.volley.AuthFailureError;
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
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarSolicitud extends AppCompatActivity {

    TextView txtDni, txtUsuario;
    ListView lstObligatoria, lstOpcional;
    Button btnAddObligatoria, btnAddOpcional, btnBuscar, btnRegistrar, btnCancelar;
    String descripcion;
    ConstraintLayout layout;
    JsonObject usuario;
    JsonObject  solicitante;
    ArrayList<String> listaobligatorio= new ArrayList<>();
    ArrayList<String> listaopcional= new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_solicitud);
        Bundle extras = getIntent().getExtras();
        String data = "";
        if(extras == null){
            Toast.makeText(this,"Data is null",Toast.LENGTH_SHORT).show();
        }else{
            data = extras.getString("data");
            solicitante = new JsonParser().parse(data).getAsJsonObject();
        }
        asignarReferencias();
    }
    @Override
    protected void onResume(){
        super.onResume();
        asignarReferencias();
        cerrarteclado();
    }

    private void asignarReferencias() {
        //region REFERENCIAS
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
        //endregion
        //region BOTON BUSCAR
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDni.getText().toString().equals("")){
                    Toast.makeText(RegistrarSolicitud.this,"Ingrese dni",Toast.LENGTH_SHORT).show();
                }else{
                    String url = "http://checkhouseapi.atwebpages.com/controller/usuario.php/usuario/"+txtDni.getText().toString();

                    hacerGetUsuario(url,new DataResponseListener(){
                        @Override
                        public void onResponseData(String data){
                            System.out.println("RESPONSE (DATOS USUARIO: "+data);
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
                                usuario= object;
                                txtUsuario.setText(object.get("nombres").getAsString()+" "+object.get("apellidos").getAsString());

                            }
                        }
                    });
                }

            }
        });
        //endregion
        //region AGREGAR DESCRIPCION FOTO OBLIGATORIA
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
        //endregion
        //region AGREGAR DESCRIPCION FOTO OPCIONAL
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
        //endregion

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsuario.getText().equals("")){
                    Toast.makeText(RegistrarSolicitud.this,"busque un usuario",Toast.LENGTH_SHORT).show();
                }
                else{
                    String url = "http://checkhouseapi.atwebpages.com/controller/solicitud.php/solicitud";
                    System.out.println("URL: "+url);
                    hacerPostSolicitud(url, new DataResponseListener() {
                        @Override
                        public void onResponseData(String data) {
                            System.out.println("RESPONSE2: "+ data);
                            if(!data.equals("")){
                                JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
                                System.out.println("SERVICIO: "+jsonObject.get("mensaje").getAsString());

                                String idSolicitud = jsonObject.get("data").getAsJsonObject().get("id").getAsString();

                                int cantidadObligatorio = listaobligatorio.size();
                                int cantidadOpcional = listaopcional.size();
                                String urldetalle = "http://checkhouseapi.atwebpages.com/controller/solicitud.php/detallesolicitud";
                                int i;
                                //region SERVICIO PARA OBLIGATORIO
                                for(i=0; i<cantidadObligatorio;i++){
                                    JsonObject objeto = new JsonObject();
                                    objeto.addProperty("id",i+1);
                                    objeto.addProperty("idsolicitud",idSolicitud);
                                    objeto.addProperty("descripcion",listaobligatorio.get(i).trim());
                                    objeto.addProperty("tipo","obligatorio");
                                    hacerPostDetalleSolicitud(urldetalle, new DataResponseListener() {
                                        @Override
                                        public void onResponseData(String data) {
                                            if(!data.equals("")){
                                                System.out.println("RESPUESTA OBLIGATORIA:"+data);
                                            }
                                            else{
                                                System.err.println("Error en el servicio de detalle");
                                            }
                                        }
                                    },objeto);
                                }
                                //endregion
                                System.out.println("indice contador:"+i);
                                i = i+1;
                                //region SERVICIO PARA OPCIONAL
                                for(int j=0; j<cantidadOpcional;j++) {
                                    JsonObject objeto = new JsonObject();
                                    objeto.addProperty("id",j+i);
                                    objeto.addProperty("idsolicitud",idSolicitud);
                                    objeto.addProperty("descripcion",listaopcional.get(j).trim());
                                    objeto.addProperty("tipo","opcional");
                                    hacerPostDetalleSolicitud(urldetalle, new DataResponseListener() {
                                        @Override
                                        public void onResponseData(String data) {
                                            if(!data.equals("")){
                                                System.out.println("RESPUESTA OPCIONAL:"+data);
                                            }
                                            else{
                                                System.err.println("Error en el servicio de detalle");
                                            }
                                        }
                                    },objeto);
                                }
                                //endregion

                                //INTENT EXITOSO

                                Intent crearcuenta3 = new Intent(RegistrarSolicitud.this, sucessful_access.class);
                                Bundle b = new Bundle();
                                String mensaje = "Completó correctamente el registro de la solicitud.";
                                b.putString("mensaje",mensaje);
                                b.putString("boton","Ir a Inicio");
                                b.putString("screen","listaverificaciones");
                                b.putString("data",solicitante.toString());
                                crearcuenta3.putExtras(b);
                                setResult(Activity.RESULT_OK,crearcuenta3);
                                startActivity(crearcuenta3);





                            }else{
                                Toast.makeText(RegistrarSolicitud.this,"Error servicio registrar solicitud",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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



    //region SET LISTA
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
    //endregion

    private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }




    //region SERVICE GET
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
    //endregion
    //region SERVICE POST
    private void hacerPostSolicitud(String url, final DataResponseListener mListener){
        scode=null;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("usuario", usuario.get("id").getAsString());
                parametros.put("nombres", usuario.get("nombres").getAsString());
                parametros.put("apellidos", usuario.get("apellidos").getAsString());
                parametros.put("dni", usuario.get("dni").getAsString());
                parametros.put("banco", solicitante.get("empresa").getAsString());
                parametros.put("estado", "Pendiente");
                return parametros;
            }
        };
        queue.add(stringRequest);
    }
    private void hacerPostDetalleSolicitud(String url, final DataResponseListener mListener, final JsonObject detalle){
        scode=null;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", detalle.get("id").getAsString());
                parametros.put("idsolicitud", detalle.get("idsolicitud").getAsString());
                parametros.put("descripcion", detalle.get("descripcion").getAsString());
                parametros.put("tipo", detalle.get("tipo").getAsString());

                return parametros;
            }
        };
        queue.add(stringRequest);
    }
//endregion

    public interface DataResponseListener {
        void onResponseData(String data);
    }
}