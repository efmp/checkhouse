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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {

    double lat, lon;
    public FragmentMaps(){

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
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
        LatLng ubicacion = new LatLng(lat,lon);
        float zoom = 17;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,zoom));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(ubicacion));

    }
}
