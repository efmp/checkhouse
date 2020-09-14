package com.emaza.checkhouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {

    double lat, lon;
    public FragmentMaps(){

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        lat = -12.1059726;
        lon = -76.9687025;
        View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        if(getArguments() != null){
            this.lat = getArguments().getDouble("lat");
            this.lon = getArguments().getDouble("lon");

        }

        getMapAsync(this    );
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setTrafficEnabled(true);
        LatLng ubicacion = new LatLng(lat,lon);
        float zoom = 17;
        LatLng cclienter = new LatLng(lat, lon);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,zoom));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(ubicacion));
        googleMap.addMarker(new MarkerOptions().position(cclienter).title("\n" +
                "Prolongación Primavera 2390, Lima 15023\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        // Casa de Cliente



        LatLng casacliente = new LatLng(-12.1061903, -76.9680802);
        googleMap.addMarker(new MarkerOptions()
                .position(casacliente)
                .title("Ubicación ingresada en solicitud").icon(BitmapDescriptorFactory.fromResource(R.drawable.house32)));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(casacliente)

                .zoom(10)
                .build();

    }
}
