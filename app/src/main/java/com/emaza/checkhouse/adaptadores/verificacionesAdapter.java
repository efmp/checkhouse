package com.emaza.checkhouse.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emaza.checkhouse.R;
import com.emaza.checkhouse.login;
import com.emaza.checkhouse.sucessful_access;
import com.emaza.checkhouse.uploadphotos;

public class verificacionesAdapter extends BaseAdapter {
    private static LayoutInflater  inflater = null;
    Context contexto;
    String[][] datos;

    TextView txtUsuario, txtDNI;
    Button btnEvaluar;
    public verificacionesAdapter(Context contexto, String[][] datos) {
        this.contexto = contexto;
        this.datos = datos;
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.verificacion_lista_modelo, null);
        txtUsuario = vista.findViewById(R.id.txtUsuario);
        txtDNI = vista.findViewById(R.id.txtDNI);
        btnEvaluar = vista.findViewById(R.id.btnEvaluar);

        txtUsuario.setText(datos[i][2]+" "+datos[i][3]);
        txtDNI.setText(datos[i][4]);
        btnEvaluar.setTag(i);

        btnEvaluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photo = new Intent(contexto, uploadphotos.class);
                Bundle b = new Bundle();
                b.putString("solicitud",datos[(Integer)view.getTag()][1]);
                photo.putExtras(b);
                Toast.makeText(contexto,"Solicitud:" +datos[(Integer)view.getTag()][1],Toast.LENGTH_SHORT).show();
                contexto.startActivity(photo);


            }
        });
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
