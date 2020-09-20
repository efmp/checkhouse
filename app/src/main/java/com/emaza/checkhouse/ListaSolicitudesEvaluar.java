package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emaza.checkhouse.adaptadores.solicitudesAdapter;
import com.emaza.checkhouse.adaptadores.verificacionesAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ListaSolicitudesEvaluar extends AppCompatActivity {

    //SOLICITUD
    //id, usuario, nombres, apellidos, dni, vivienda, banco, estado
    //filtro banco y estado


    //Listar solicitudes de estado Evaluacion filtrado por banco
//    String[][] datos = {{"1","4","Juan","Peralta","12345678","1","Interbank","Evaluar"},
//            {"2","4","Diana","Mendoza","87654321","2","BCP","Evaluar"}
//    };
    JsonObject usuario_json;
    ListView lstVerificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region OBTENER DATOS DE ANTERIOR PANTALLA
        Bundle extras = getIntent().getExtras();
        String data = "";
        if(extras == null){
            Toast.makeText(this,"Data is null",Toast.LENGTH_SHORT).show();
        }else{
            data = extras.getString("data");
            usuario_json = new JsonParser().parse(data).getAsJsonObject();
        }
        //endregion

        setContentView(R.layout.activity_lista_solicitudes_evaluar);
        lstVerificaciones = findViewById(R.id.lstVerificacion);

        //region SERVICIO
        String url = "http://checkhouseapi.atwebpages.com/controller/solicitud.php/solicitudes/bank/"+usuario_json.get("empresa").getAsString();
        hacerGet(url, new DataResponseListener() {
            @Override
            public void onResponseData(String data) {
                JsonArray jsonArray = new Gson().fromJson(data,JsonArray.class);
                if(jsonArray.size()==0){
                    //Toast.makeText(ListaSolicitudes.this,"Igual a Cero",Toast.LENGTH_SHORT).show();

                }
                else if(jsonArray.size()>0){
                    //Toast.makeText(ListaSolicitudes.this,"Mayor a Cero",Toast.LENGTH_SHORT).show();
                    if (!data.equals("")){
                        JsonArray jsonResponse= new Gson().fromJson(data,JsonArray.class);
                        String [][] datos = transformarData(jsonResponse);
                        lstVerificaciones.setAdapter(new verificacionesAdapter(ListaSolicitudesEvaluar.this,datos));
                    }
                    else {
                        System.out.println("hubo algun error");
                    }
                }
            }
        });

        //endregion


    }
    String scode;
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


    public String[][] transformarData(JsonArray jsonData){
        String[][] datos = new String[jsonData.size()][8];
        for (int i=0;i<jsonData.size();i++){
            datos[i][0] = jsonData.get(i).getAsJsonObject().get("id").getAsString();
            datos[i][1] = jsonData.get(i).getAsJsonObject().get("usuario").getAsString();
            datos[i][2] = jsonData.get(i).getAsJsonObject().get("nombres").getAsString();
            datos[i][3] = jsonData.get(i).getAsJsonObject().get("apellidos").getAsString();
            datos[i][4] = jsonData.get(i).getAsJsonObject().get("dni").getAsString();
            if(jsonData.get(i).getAsJsonObject().get("vivienda").isJsonNull()){
                datos[i][5] = null;
            }else{
                datos[i][5] = jsonData.get(i).getAsJsonObject().get("vivienda").getAsString();
            }
            datos[i][6] = jsonData.get(i).getAsJsonObject().get("banco").getAsString();
            datos[i][7] = jsonData.get(i).getAsJsonObject().get("estado").getAsString();

        }
        System.out.println("dataobjeto:"+datos[0][0]);
        return  datos;
    }

}