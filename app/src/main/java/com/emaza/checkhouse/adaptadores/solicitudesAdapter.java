package com.emaza.checkhouse.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emaza.checkhouse.Maps;
import com.emaza.checkhouse.R;

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


        String texto= datos[i][6]+" ha solicitado que Ud. realice la verificación de su domicilio";
        txtDescripcion.setText(texto);
        txtEstado.setText(datos[i][7]);
        int indexImagen=-1;
        if(datos[i][6].equalsIgnoreCase("Interbank")){
            indexImagen=1;
        }else if(datos[i][6].equalsIgnoreCase("BCP")){
            indexImagen=0;
        }
        imgBanco.setImageResource(datosImg[indexImagen]);
        imgBanco.setTag(indexImagen);
        btnEmpezar.setTag(i);
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hacer Intent
                Intent visorImagen = new Intent(contexto, Maps.class);
                //enviar Data
                contexto.startActivity(visorImagen);
            }
        });


        return vista;
    }

//        dataProducto.put("id", String.valueOf(solicitud.getId()));
//        dataProducto.put("usuario", String.valueOf(solicitud.getIdusuario()));
//        dataProducto.put("nombres", "S/. "+solicitud.getNombres());
//        dataProducto.put("apellidos", solicitud.getApellidos());
//        dataProducto.put("dni", solicitud.getDni());
//        dataProducto.put("banco", solicitud.getBanco());
//        dataProducto.put("estado",solicitud.getEstado());
//        dataProducto.put("descripcion",solicitud.getBanco()+" ha solicitado que Ud. realice la verificación de su domicilio");
//        dataProducto.put("btnempezar","Empezar2");
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
