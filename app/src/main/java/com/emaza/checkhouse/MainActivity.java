package com.emaza.checkhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {





    TextView txtMensaje;
    private EditText Usuario, Password;
    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asignarReferencias();

        Usuario = (EditText)findViewById(R.id.txtUsuario);
        Password = (EditText)findViewById(R.id.txtContraseña);
        Login = (Button) findViewById(R.id.btnIngresar);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Usuario.getText().toString(), Password.getText().toString());
            }
        });
    }
    private void validate (String userName, String userPassword){
        if(userName.equals("72012380") && userPassword.equals("abc123")){
            Intent intent = new Intent(MainActivity.this, Lista_de_solicitudes_terminada.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    private void asignarReferencias() {
        txtMensaje = findViewById(R.id.txtMensaje);
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
        (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
        else {
            iniciarLocalizacion();
        }
    }

    private void iniciarLocalizacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion localizacion = new Localizacion();
        localizacion.setMainActivity(this,txtMensaje);

        final boolean gpsHabilitado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsHabilitado){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,localizacion);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,localizacion);

        txtMensaje.setText("Localización agregada");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                iniciarLocalizacion();
                return;
            }
        }

    }
}