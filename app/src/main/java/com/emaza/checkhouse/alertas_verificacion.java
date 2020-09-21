package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class alertas_verificacion extends AppCompatActivity {
    private TextView txtContador;
    private Button btnInciar;
    private CountDownTimer countDownTimer;
    private long timeLeft = 600000000; //10 min
    private boolean timeRunnig;
    private ImageView alerta1, alerta2, alerta3, alerta4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas_verificacion);
    }

}