package com.emaza.checkhouse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class agregar_foto extends AppCompatActivity {

   final String url = "http://checkhouseapi.atwebpages.com/controller/solicitud.php/registrarfoto";
   String imagenStr;


    private ImageView mimageView;
    Button btnTomarFoto;
    Button btnSiguiente;


    private static final int REQUEST_IMAGE_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_foto);
        mimageView = findViewById(R.id.imgfoto);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnTomarFoto = findViewById(R.id.boton);





        //region BOTON SIGUIENTE
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject json = new JsonObject();
                json.addProperty("foto",imagenStr);
               hacerPostDetalleSolicitud(url, new DataResponseListener() {
                   @Override
                   public void onResponseData(String data) {
                       if(!data.equals("")){
                           System.out.println("RESPUESTA:"+data);
                       }
                       else{
                           System.err.println("Error en el servicio de detalle");
                       }
                   }
               },json);
            }
        });
        //endregion

        //region BOTON TOMAR FOTO
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TomaFoto2();
                mimageView.buildDrawingCache();
                Bitmap bitmap = mimageView.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,90,stream);
                byte[] image = stream.toByteArray();
                System.out.println("BYTE IMAGE>::"+image);
                imagenStr = Base64.encodeToString(image,0);
                System.out.println("BYTE>>>> "+imagenStr);
            }
        });
        //endregion




        if (ContextCompat.checkSelfPermission(agregar_foto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(agregar_foto.this, new String[]
                    {
                            Manifest.permission.CAMERA
                    }, 100);
        }

    }




    public void TomaFoto(View view)
    {
        Intent imageTakenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakenIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(imageTakenIntent,REQUEST_IMAGE_CAPTURE);
        }

    }


    public void TomaFoto2()
    {
        Intent imageTakenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakenIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(imageTakenIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    String scode;
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
                parametros.put("id", "1");
                parametros.put("idsolicitud", "3");
                parametros.put("foto", imagenStr);
                return parametros;
            }
        };
        queue.add(stringRequest);
    }
    public interface DataResponseListener {
        void onResponseData(String data);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mimageView.setImageBitmap(imageBitmap);
        }
    }}

