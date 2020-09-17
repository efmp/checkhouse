package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.emaza.checkhouse.entidades.Solicitud;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class ListaSolicitudes extends AppCompatActivity {

    int[] datosImg = {R.drawable.bcpicon,R.drawable.interbank};
    JsonObject usuario_json;
    TextView lblNombreUsuario;
    TextView txtNoSolicitudes;
    ListView lstSolicitudes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_solicitudes);

        Bundle extras = getIntent().getExtras();
        String data = "";
        if(extras == null){
            Toast.makeText(this,"Data is null",Toast.LENGTH_SHORT).show();
        }else{
            data = extras.getString("data");
            usuario_json = new JsonParser().parse(data).getAsJsonObject();
        }
        asignarReferencias();
        accionGetDataSolicitud();
    }

    private void asignarReferencias() {
        lstSolicitudes = findViewById(R.id.lstSolicitudes);
        lblNombreUsuario = findViewById(R.id.lblNombre);
        txtNoSolicitudes = findViewById(R.id.txtNoSolicitudes);
        txtNoSolicitudes.setVisibility(View.INVISIBLE);
        lblNombreUsuario.setText(usuario_json.get("nombres").getAsString());
        lblNombreUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        lstSolicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        //lstSolicitudes.setAdapter(new solicitudesAdapter(this,datos,datosImg));
    }

    String scode;
    private void accionGetDataSolicitud(){
        String keyName = usuario_json.get("id").getAsString();
        String url = "http://checkhouseapi.atwebpages.com/index.php/solicitudes/" + keyName;
        hacerGet(url, new DataResponseListener() {
            @Override
            public void onResponseData(String data) {
                JsonArray jsonArray = new Gson().fromJson(data,JsonArray.class);
                if(jsonArray.size()==0){
                    //Toast.makeText(ListaSolicitudes.this,"Igual a Cero",Toast.LENGTH_SHORT).show();
                    txtNoSolicitudes.setVisibility(View.VISIBLE);
                }
                else if(jsonArray.size()>0){
                    //Toast.makeText(ListaSolicitudes.this,"Mayor a Cero",Toast.LENGTH_SHORT).show();
                    if (!data.equals("")){
                        System.out.println("todo correcto");
                        String[][] datos = transformarData(data);
                        lstSolicitudes.setAdapter(new solicitudesAdapter(ListaSolicitudes.this,datos,datosImg));
                        Toast.makeText(ListaSolicitudes.this,"Estado 200 OK",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        System.out.println("hubo algun error");
                    }
                }
            }
        });
    }

    private void hacerGet(String url, final DataResponseListener mListener){
        scode = null;
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

    public String[][] transformarData(String data){
        JsonArray jsonArray = new Gson().fromJson(data,JsonArray.class);
        String[][] datos = new String[jsonArray.size()][8];
        for (int i=0;i<jsonArray.size();i++){
            datos[i][0] = jsonArray.get(i).getAsJsonObject().get("id").getAsString();
            datos[i][1] = jsonArray.get(i).getAsJsonObject().get("usuario").getAsString();
            datos[i][2] = jsonArray.get(i).getAsJsonObject().get("nombres").getAsString();
            datos[i][3] = jsonArray.get(i).getAsJsonObject().get("apellidos").getAsString();
            datos[i][4] = jsonArray.get(i).getAsJsonObject().get("dni").getAsString();
            if(jsonArray.get(i).getAsJsonObject().get("vivienda").isJsonNull()){
                datos[i][5] = null;
            }else{
                datos[i][5] = jsonArray.get(i).getAsJsonObject().get("vivienda").getAsString();
            }
            datos[i][6] = jsonArray.get(i).getAsJsonObject().get("banco").getAsString();
            datos[i][7] = jsonArray.get(i).getAsJsonObject().get("estado").getAsString();
        }
        System.out.println("dataobjeto:"+datos[0][0]);
        return  datos;
    }



}