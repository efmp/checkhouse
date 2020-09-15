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
import com.emaza.checkhouse.entidades.Solicitud;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class ListaSolicitudes extends AppCompatActivity {


    String[] from = new String[]{"descripcion","estado"};
    int[] to = new int[]{R.id.txtDescripcion, R.id.txtEstado};
    private ArrayList<Solicitud> listaSolicitudes;
    JsonObject usuario_json;
    TextView lblNombreUsuario;
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

        //obtenerData();
        //mostrarSolicitudes();
    }

    private void asignarReferencias() {
        lstSolicitudes = findViewById(R.id.lstSolicitudes);
        lblNombreUsuario = findViewById(R.id.lblNombre);
        lblNombreUsuario.setText(usuario_json.get("nombres").getAsString());
        listaSolicitudes = new ArrayList<>();
        lblNombreUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerData();
                mostrarSolicitudes();
            }
        });

        lstSolicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public void transformarData(){
        Bundle extras = getIntent().getExtras();
        String data = "";
        data = extras.getString("dataSolicitudes");

        JsonArray jsonArray = new Gson().fromJson(data,JsonArray.class);
        for (int i=0;i<jsonArray.size();i++){
            Solicitud solicitud = new Solicitud(
                    jsonArray.get(i).getAsJsonObject().get("id").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("usuario").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("nombres").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("apellidos").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("dni").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("banco").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("estado").getAsString());
            listaSolicitudes.add(solicitud);
        }
        System.out.println(listaSolicitudes.get(0).getNombres());
        System.out.println("orueta");
    }
    public void obtenerData(){
        String keyName = usuario_json.get("id").getAsString();
        String url = "http://checkhouseapi.atwebpages.com/index.php/solicitudes/" + keyName;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JsonArray jsonArray = new Gson().fromJson(response,JsonArray.class);
                    for (int i=0;i<jsonArray.size();i++){
                        Solicitud solicitud = new Solicitud(
                        jsonArray.get(i).getAsJsonObject().get("id").getAsInt(),
                        jsonArray.get(i).getAsJsonObject().get("usuario").getAsInt(),
                        jsonArray.get(i).getAsJsonObject().get("nombres").getAsString(),
                        jsonArray.get(i).getAsJsonObject().get("apellidos").getAsString(),
                        jsonArray.get(i).getAsJsonObject().get("dni").getAsString(),
                        jsonArray.get(i).getAsJsonObject().get("banco").getAsString(),
                        jsonArray.get(i).getAsJsonObject().get("estado").getAsString());
                        listaSolicitudes.add(solicitud);
                    }
                    System.out.println(listaSolicitudes.get(0).getNombres());
                    System.out.println("RESPONSE"+listaSolicitudes);


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
        System.out.println("existe solicitud");
    }
    public void mostrarSolicitudes(){
        //listaProductos = daoProducto.cargarProductos();
        if (listaSolicitudes.size()<1){

        }else{
            ArrayList<HashMap<String,String>> productos = new ArrayList<>();
            for(Solicitud solicitud:listaSolicitudes){
                HashMap<String,String> dataProducto = new HashMap<>();
                dataProducto.put("id", String.valueOf(solicitud.getId()));
                dataProducto.put("usuario", String.valueOf(solicitud.getIdusuario()));
                dataProducto.put("nombres", "S/. "+solicitud.getNombres());
                dataProducto.put("apellidos", solicitud.getApellidos());
                dataProducto.put("dni", solicitud.getDni());
                dataProducto.put("banco", solicitud.getBanco());
                dataProducto.put("estado",solicitud.getEstado());
                dataProducto.put("descripcion",solicitud.getBanco()+" ha solicitado que Ud. realice la verificación de su domicilio");
                dataProducto.put("btnempezar","Empezar2");
                productos.add(dataProducto);
                lstSolicitudes = findViewById(R.id.lstSolicitudes);
                SimpleAdapter adapter = new SimpleAdapter(this, productos, R.layout.solicitud_modelo, from, to);
                lstSolicitudes.setAdapter(adapter);
            }
        }
    }

}