package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emaza.checkhouse.adaptadores.solicitudesAdapter;
import com.emaza.checkhouse.adaptadores.uploadphotoAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class uploadphotos extends AppCompatActivity {

    ListView lstImagenes;
    Button btnContinuar;
    String solicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadphotos);
        lstImagenes = findViewById(R.id.lstfoto);
        btnContinuar = findViewById(R.id.btnContinuar);

        Bundle extras = getIntent().getExtras();
        String data = "";
        if(extras == null){
            Toast.makeText(this,"Data is null",Toast.LENGTH_SHORT).show();
        }else{
            data = extras.getString("solicitud");
            solicitud = data;
        }

        //region PERMISOS ABRIR CAMARA
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.CAMERA
                    }, 100);
        }
        //endregion

        obtenerlistasolicitudconfoto();

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }



    private void obtenerlistasolicitudconfoto(){
        String key="2";
        String url = "http://checkhouseapi.atwebpages.com/controller/solicitud.php/dsolicitud/"+solicitud;
        hacerGetUsuario(url, new DataResponseListener() {
            @Override
            public void onResponseData(String data) {
                JsonArray jsonArray = new Gson().fromJson(data,JsonArray.class);
                if(jsonArray.size()==0){
                    Toast.makeText(uploadphotos.this,"no se encontro lista",Toast.LENGTH_SHORT).show();
                }
                else if(jsonArray.size()>0) {
                    JsonArray jsonResponse= new Gson().fromJson(data,JsonArray.class);
                    String [][] datos = transformarData(jsonResponse);
                    System.out.println("CANTIDAD:"+data.length());
                    lstImagenes.setAdapter(new uploadphotoAdapter(uploadphotos.this,datos));
                    System.out.println("!!!todo correcto");
                }
            }
        });
    }

    public String[][] transformarData(JsonArray jsonData){
        String[][] datos = new String[jsonData.size()][8];
        for (int i=0;i<jsonData.size();i++){
            datos[i][0] = jsonData.get(i).getAsJsonObject().get("id").getAsString();
            datos[i][1] = jsonData.get(i).getAsJsonObject().get("idsolicitud").getAsString();
            datos[i][2] = jsonData.get(i).getAsJsonObject().get("foto").getAsString();
            datos[i][3] = jsonData.get(i).getAsJsonObject().get("descripcion").getAsString();
            datos[i][4] = jsonData.get(i).getAsJsonObject().get("tipo").getAsString();
        }
        System.out.println("dataobjeto:"+datos[0][0]);
        return  datos;
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