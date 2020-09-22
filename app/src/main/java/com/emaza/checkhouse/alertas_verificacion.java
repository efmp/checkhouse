package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emaza.checkhouse.widget.CircularProgressBar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class alertas_verificacion extends AppCompatActivity {
    private TextView txtContador;
    private CountDownTimer countDownTimer;
    private long timeLeft = 30; //30 seg
    private long seg;
    private ImageView alerta2;
    private static String FORMATO_CONTADOR = "%02d:%02d:%02d";
    private ProgressBar progressBar;

    private String SOLICITUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas_verificacion);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            Toast.makeText(this,"Data is null",Toast.LENGTH_SHORT).show();
        }else{
            SOLICITUD = extras.getString("solicitud");
        }
        initViews();
        initializeTimer();


    }

    private void initViews() {
        alerta2 = findViewById(R.id.alerta2);
        txtContador = findViewById(R.id.txtContador);
        progressBar = findViewById(R.id.progressBar);
        alerta2.setEnabled(false);
    }

    private void initializeTimer() {
        countDownTimer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisecond) {
                seg = millisecond;

                long pro =((millisecond / 1000) % 60);

                String mContador = String.format(FORMATO_CONTADOR, millisecond / (3600 * 1000),
                        ((millisecond / 1000) % 3600) / 60, ((millisecond / 1000) % 60));
                txtContador.setText(mContador);
                progressBar.setProgress((int) pro);
                Log.w("TAG_CONTADOR", String.valueOf(pro));
                /*
                String mCounter = String.format(FORMATO_CONTADOR, TimeUnit.HOURS.toHours(millisecond),
                        TimeUnit.MINUTES.toMinutes(millisecond), TimeUnit.SECONDS.toSeconds(millisecond));
                txtContador.setText(mCounter);

                 */
            }
            @Override
            public void onFinish() {
                seg = (long) 0;
                String mCounter = String.format(FORMATO_CONTADOR, 0, 0, 0);
                txtContador.setText(mCounter);
                alerta2.setEnabled(true);
                alerta2.setImageResource(R.drawable.ic_notification_verde);

            }
        };
        countDownTimer.start();
    }
    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
    public void onClickNavigate(View view) {
        //irAScreenSucess();
        obtenerDetalleFotos();
        cancelTimer();
    }
    public void irAScreenSucess(){
        Intent finalerta = new Intent(this, sucessful_access.class);
        Bundle b = new Bundle();
        String mensaje = "Su proceso de auto-verificación domiciliaria a terminado con éxito.";
        b.putString("mensaje",mensaje);
        b.putString("boton","Ir al inicio");
        b.putString("screen","login");
        finalerta.putExtras(b);
        setResult(Activity.RESULT_OK,finalerta);
        startActivity(finalerta);
    }

    private void obtenerDetalleFotos(){
        String url = "http://checkhouseapi.atwebpages.com/controller/solicitud.php/dsolicitud/"+SOLICITUD;
        hacerGETPHOTOS(url, new DataResponseListener() {
            @Override
            public void onResponseData(String data) {
                System.out.println("RESPONSE (DATOS USUARIO: "+data);
                JsonArray jsonArray = new Gson().fromJson(data,JsonArray.class);
                if(jsonArray.size()==0){
                    Toast.makeText(alertas_verificacion.this,"Lista no encontrado",Toast.LENGTH_SHORT).show();
                }else{
                    Intent agregarfoto = new Intent(alertas_verificacion.this, agregar_foto.class);
                    Bundle b = new Bundle();
                    b.putString("data", data);
                    b.putString("indice", "1");
                    agregarfoto.putExtras(b);
                    setResult(Activity.RESULT_OK,agregarfoto);
                    startActivity(agregarfoto);
                }
            }
        });
    }
    //region SERVICE GET
    private String scode;
    private void hacerGETPHOTOS(String url, final DataResponseListener mListener){
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

    public interface DataResponseListener {
        void onResponseData(String data);
    }
}