package com.emaza.checkhouse.adaptadores;

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

import com.emaza.checkhouse.MapsActivity;
import com.emaza.checkhouse.R;
import com.emaza.checkhouse.uploadphotos;

public class solicitudesAdapter extends BaseAdapter {

//    {"1","4","Juan","apellidos","32165487",null,"Interbank","Pendiente"},
//    {"2","4","Juan","Peru","12345678",null,"BCP","Pendiente"}
    //" ha solicitado que Ud. realice la verificación de su domicilio")
    private static LayoutInflater  inflater = null;
    Context contexto;
    String[][] datos;
    int[] datosImg;

    TextView txtDescripcion, txtEstado;
    ImageView imgBanco;
    Button btnEmpezar;

    public solicitudesAdapter(Context contexto, String[][] datos, int[] datosImg) {
        this.contexto = contexto;
        this.datos = datos;
        this.datosImg = datosImg;
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.solicitud_modelo, null);
        txtDescripcion = vista.findViewById(R.id.txtDescripcion);
        txtEstado = vista.findViewById(R.id.txtEstado);
        imgBanco = vista.findViewById(R.id.imgBanco);
        btnEmpezar = vista.findViewById(R.id.btnEmpezar);

        try{
                String texto= datos[i][6]+" ha solicitado que Ud. realice la verificación de su domicilio";
                txtDescripcion.setText(texto);
                txtEstado.setText(datos[i][7]);


            if(datos[i][6].equalsIgnoreCase("BCP")){
                imgBanco.setImageResource(R.drawable.bcpicon);

            }else if(datos[i][6].equalsIgnoreCase("Interbank")){
                imgBanco.setImageResource(R.drawable.interbank);
            }else{
                imgBanco.setImageResource(R.drawable.banco_vacio);
            }


                imgBanco.setTag(i);

                btnEmpezar.setTag(i);
                btnEmpezar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent mapas = new Intent(contexto, MapsActivity.class);
                        Bundle b = new Bundle();
                        b.putString("solicitud",datos[(Integer)view.getTag()][1]);
                        mapas.putExtras(b);
                        Toast.makeText(contexto,"Solicitud:" +datos[(Integer)view.getTag()][1],Toast.LENGTH_SHORT).show();
                        contexto.startActivity(mapas);
                    }
                });

        }
        catch (Exception e){
            System.out.println("Error aqui");
        }




        return vista;
    }


    @Override
    public int getCount() {
        return datosImg.length;
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
