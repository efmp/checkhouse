package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.emaza.checkhouse.widget.CircularProgressBar;

import java.util.concurrent.TimeUnit;

public class alertas_verificacion extends AppCompatActivity {
    private TextView txtContador;
    private CountDownTimer countDownTimer;
    private long timeLeft = 30; //30 seg
    private long seg;
    private ImageView alerta2;
    private static String FORMATO_CONTADOR = "%02d:%02d:%02d";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas_verificacion);
        initViews();
        initializeTimer();

    }

    private void initViews() {
        alerta2 = findViewById(R.id.alerta2);
        txtContador = findViewById(R.id.txtContador);
        progressBar = findViewById(R.id.progressBar);
        alerta2.setEnabled(false);
    }

    private void initializeTimer() {
        countDownTimer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisecond) {
                seg = millisecond;

                long pro =((millisecond / 1000) % 60);

                String mContador = String.format(FORMATO_CONTADOR, millisecond / (3600 * 1000),
                        ((millisecond / 1000) % 3600) / 60, ((millisecond / 1000) % 60));
                txtContador.setText(mContador);
                progressBar.setProgress((int) pro);
                Log.w("TAG_CONTADOR", String.valueOf(pro));
                /*
                String mCounter = String.format(FORMATO_CONTADOR, TimeUnit.HOURS.toHours(millisecond),
                        TimeUnit.MINUTES.toMinutes(millisecond), TimeUnit.SECONDS.toSeconds(millisecond));
                txtContador.setText(mCounter);

                 */
            }
            @Override
            public void onFinish() {
                seg = (long) 0;
                String mCounter = String.format(FORMATO_CONTADOR, 0, 0, 0);
                txtContador.setText(mCounter);
                alerta2.setEnabled(true);
                alerta2.setImageResource(R.drawable.ic_notification_verde);
            }
        };
        countDownTimer.start();
    }
    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
    public void onClickNavigate(View view) {
        irAScreenSucess();
        cancelTimer();
    }
    public void irAScreenSucess(){
        Intent finalerta = new Intent(this, sucessful_access.class);
        Bundle b = new Bundle();
        String mensaje = "Su proceso de auto-verificación domiciliaria a terminado con éxito.";
        b.putString("mensaje",mensaje);
        b.putString("boton","Ir al inicio");
        b.putString("screen","login");
        finalerta.putExtras(b);
        setResult(Activity.RESULT_OK,finalerta);
        startActivity(finalerta);
    }
}