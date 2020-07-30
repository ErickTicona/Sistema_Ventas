package com.example.sistema_ventas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistema_ventas.MainActivity;
import com.example.sistema_ventas.R;
import com.example.sistema_ventas.esquemaSqlite.tablas.DataBaseManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtUsuario, txtPassword;
    private Button btnIngresar,btnRegistro;

    private DataBaseManager dataBaseManager;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);

        btnIngresar = findViewById(R.id.btnLIngresar);
        btnIngresar.setOnClickListener(this);

        btnRegistro = findViewById(R.id.btnLRegistro);
        btnRegistro.setOnClickListener(this);

        dataBaseManager = new DataBaseManager(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLIngresar){
            String usuarioName = txtUsuario.getText().toString();
            String usuarioPassword = txtPassword.getText().toString();
            if(!usuarioName.isEmpty() && !usuarioPassword.isEmpty()){
                cursor = dataBaseManager.getUsuarioByName(usuarioName);
                cursor.moveToPosition(0);
                if(cursor.getCount() != 0){
                    if (cursor.getString(1).equals(usuarioName) && cursor.getString(2).equals(usuarioPassword)) {
                        //INGRESO COMPLETA TU CODIGO AQUI by Val
                        Intent intent2 = new Intent(this, MainActivity.class);
                        startActivity(intent2);
                        Toast.makeText(this,"Ingresastes!",Toast.LENGTH_LONG).show();
                        finish();
                    }else
                        Toast.makeText(this,"Datos Incorrectos!",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(this,"Nombre de Usuario Incorrectos!",Toast.LENGTH_LONG).show();
            }else
                txtUsuario.setError("Campos Vacios!!");
        }else if(v.getId() == R.id.btnLRegistro){
            Intent intent = new Intent(this,SigninActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
