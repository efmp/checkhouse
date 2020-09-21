package com.emaza.checkhouse;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> realTimeMarkers = new ArrayList<>();

    Double latitud;
    Double longitud;
    String local_idvivienda;
    TextView countdowntext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        countdowntext = findViewById(R.id.countdowntext);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void countDownTimer(){
        new CountDownTimer(30000,1000){
            public void onTick(long millisUntilFinished){

                //Log.e("seconds remaining: ", "" + millisUntilFinished/1000);
                countdowntext.setText(""+millisUntilFinished/1000);
            }
            public void onFinish(){
                Toast.makeText(MapsActivity.this,"Se verifico los puntos de su ubicación y seran evaluadas", Toast.LENGTH_SHORT).show();
                onMapReady(mMap);

                final String url = "http://checkhouseapi.atwebpages.com/index.php/nuevavivienda";
                JsonObject objeto = new JsonObject();
                objeto.addProperty("latitud", String.valueOf(latitud));
                objeto.addProperty("longitud", String.valueOf(longitud));
                hacerPostDetalleSolicitud(url, new DataResponseListener() {
                    @Override
                    public void onResponseData(String data) {
                        if(!data.equals("")){
                            System.out.println("RESPUESTA:"+data);
                            JsonObject json = new JsonParser().parse(data).getAsJsonObject();
                            String idvivienda = json.get("data").getAsJsonObject().get("id").getAsString();
                            local_idvivienda = idvivienda;
                            System.out.println("SE OBTUVO VIVIENDA: "+idvivienda);

                        }
                        else{
                            System.err.println("Error en el servicio de detalle");
                        }

                    }
                },objeto);


            }
        }.start();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mDatabase.child("Usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (Marker marker:realTimeMarkers){
                    marker.remove();
                }
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){


                    MapsPojo mp = snapshot.getValue(MapsPojo.class);
                    latitud = mp.getLatitud();
                    longitud = mp.getLongitud();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitud,longitud));
                    tmpRealTimeMarkers.add(mMap.addMarker(markerOptions));
                }

                realTimeMarkers.clear();
                realTimeMarkers.addAll(tmpRealTimeMarkers);
                countDownTimer();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    String scode;
    private void hacerPostDetalleSolicitud(String url, final DataResponseListener mListener, final JsonObject vivienda){
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
                parametros.put("direccion", "null");
                parametros.put("latitud", vivienda.get("latitud").getAsString());
                parametros.put("longitud", vivienda.get("longitud").getAsString());

                System.out.println("DATOS REQUEST: "+latitud+" | "+longitud);
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