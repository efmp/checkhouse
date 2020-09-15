package com.emaza.checkhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.emaza.checkhouse.entidades.Usuario;

public class crearcuenta_2 extends AppCompatActivity {

    ConstraintLayout layout;
    Button btnContinuar;
    TextView txtnombres, txtapellidos, txtdocumento, txtpassword, txtpasswordconfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_2);
        asignarReferencias();
    }

    private void asignarReferencias() {
        layout = findViewById(R.id.layout);
        txtnombres = findViewById(R.id.txtNombres);
        txtapellidos = findViewById(R.id.txtApellidos);
        txtdocumento = findViewById(R.id.txtDocumento);
        txtpassword = findViewById(R.id.txtPassword);
        txtpasswordconfirm = findViewById(R.id.txtPasswordConfirm);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario user = obtenerDatos();
                if(user != null){
                    irACrearCuentaPaso3(user);
                }

            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarteclado();
            }
        });
    }

    private Usuario obtenerDatos() {

        Bundle extras = getIntent().getExtras();
        String nombres = txtnombres.getText().toString();
        String apellidos = txtapellidos.getText().toString();
        String documento = txtdocumento.getText().toString();
        String password = txtpassword.getText().toString();
        String passconfirmation = txtpasswordconfirm.getText().toString();
        String correo = extras.getString("correo");
        if (nombres.equals("")){
            Toast.makeText(this,"ingrese sus nombres",Toast.LENGTH_SHORT).show();
        }
        else if (apellidos.equals("")){
            Toast.makeText(this,"ingrese sus apellidos",Toast.LENGTH_SHORT).show();
        }
        else if (documento.equals("")){
            Toast.makeText(this,"ingrese su documento",Toast.LENGTH_SHORT).show();
        }
        else if (password.equals("")){
            Toast.makeText(this,"ingrese su contraseña",Toast.LENGTH_SHORT).show();
        }
        else if (passconfirmation.equals("")){
            Toast.makeText(this,"ingrese su contraseña",Toast.LENGTH_SHORT).show();
        }
        else{
            if(password.equals(passconfirmation)){
                return new Usuario(nombres,apellidos,correo,documento,password);
            }
            else{
                Toast.makeText(this,"las contraseñas son distintas",Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void irACrearCuentaPaso3(Usuario user){
        Intent crearcuenta3 = new Intent(this, crearcuenta_3.class);
        Bundle b = new Bundle();
        b.putString("nombres",user.getNombres());
        b.putString("apellidos",user.getApellidos());
        b.putString("correo",user.getCorreo());
        b.putString("documento",user.getDni());
        b.putString("password",user.getPassword());
        crearcuenta3.putExtras(b);
        setResult(Activity.RESULT_OK,crearcuenta3);
        startActivity(crearcuenta3);
    }



}