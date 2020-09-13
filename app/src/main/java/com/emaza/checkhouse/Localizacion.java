package com.emaza.checkhouse;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

public class Localizacion implements LocationListener {

    MainActivity mainActivity;
    TextView txtMensaje;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity, TextView txtMensaje) {
        this.mainActivity = mainActivity;
        this.txtMensaje = txtMensaje;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Este m se ejecuta cuando el GPS recibe nuevas coordenadas
        String texto = "Mi ubicación es : "+ "\n Latitud :" + location.getLatitude()+
                                        "\n Longitud :" + location.getLongitude();

        txtMensaje.setText(texto);
        mapa(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        switch (status){
            case LocationProvider.AVAILABLE:
                Log.d("=>","Disponible");
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("=>","Fuera de servicio");
                break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("=>", "Unavailable");
                    break;
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
                txtMensaje.setText("GPS Activado");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        txtMensaje.setText("GPS Desactivado");
    }

    public void mapa(double lat, double lon){
        FragmentMaps fragmento = new FragmentMaps();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", new Double(lat));
        bundle.putDouble("lon", new Double(lon));
        fragmento.setArguments(bundle);


        FragmentManager manager = getMainActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, fragmento, null);
        transaction.commit();
    }
}
