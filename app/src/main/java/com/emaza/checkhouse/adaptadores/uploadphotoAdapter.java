package com.emaza.checkhouse.adaptadores;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.emaza.checkhouse.R;
import com.emaza.checkhouse.agregar_foto;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.app.Activity.RESULT_OK;

public class uploadphotoAdapter extends BaseAdapter {
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static LayoutInflater inflater = null;
    Context contexto;
    String[][] datos;

    TextView txtDescripcion, txtTipo;
    ImageView imagen;
    public uploadphotoAdapter(Context contexto, String[][] datos) {
        this.contexto = contexto;
        this.datos = datos;
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.lista_foto_model, null);
        txtDescripcion = vista.findViewById(R.id.txtDescripcionFoto);
        txtTipo = vista.findViewById(R.id.txtTipoFoto);
        imagen = vista.findViewById(R.id.imgFoto);
        txtDescripcion.setText(datos[i][3]);
        txtTipo.setText(datos[i][4]);



        File imgFile = new  File("/storage/emulated/0/Download/ic_logo.png");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());



            imagen.setImageBitmap(myBitmap);

        }

        //byte[] decodedString = Base64.decode(datos[i][2].getBytes(), Base64.DEFAULT);
        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //imagen.setImageBitmap(BitmapFactory.decodeByteArray(decodedString,0,decodedString.length));

//        datos[i][0] = jsonData.get(i).getAsJsonObject().get("id").getAsString();
//        datos[i][1] = jsonData.get(i).getAsJsonObject().get("idsolicitud").getAsString();
//        datos[i][2] = jsonData.get(i).getAsJsonObject().get("foto").getAsString();
//        datos[i][3] = jsonData.get(i).getAsJsonObject().get("descripcion").getAsString();
//        datos[i][4] = jsonData.get(i).getAsJsonObject().get("tipo").getAsString();

        return vista;
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
