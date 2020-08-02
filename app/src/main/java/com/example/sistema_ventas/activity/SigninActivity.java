package com.example.sistema_ventas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.esquemaSqlite.ConexionSqliteHelper;
import com.example.sistema_ventas.esquemaSqlite.tablas.DataBaseManager;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtNombreUsuario,txtContraseña1,txtContraseña2;
    Button btnRegistrar,btnCancelar;

    private DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        txtNombreUsuario = findViewById(R.id.txtSUserName);
        txtContraseña1 = findViewById(R.id.txtSUserPassword1);
        txtContraseña2 = findViewById(R.id.txtSUserPassword2);

        btnRegistrar = findViewById(R.id.btnSRegistrar);
        btnCancelar = findViewById(R.id.btnSCancelar);

        btnRegistrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        dataBaseManager = new DataBaseManager(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,LoginActivity.class);
        if(v.getId() == R.id.btnSRegistrar){
            if(validarCampos()){
                String NombreUsuario = txtNombreUsuario.getText().toString();
                String ContraseñaUsuario = txtContraseña1.getText().toString();
                if(dataBaseManager.getIsExistByName(NombreUsuario)){
                    dataBaseManager.createUsuario(NombreUsuario,ContraseñaUsuario);
                    Toast.makeText(this, "Registro Finalizado!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else
                    Toast.makeText(this, "Nombre de Usuario Ya registrado!", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId() == R.id.btnSCancelar){
            startActivity(intent);
            finish();
        }
    }

    private Boolean validarCampos(){
        if(!txtNombreUsuario.getText().toString().isEmpty() &&
                !txtContraseña1.getText().toString().isEmpty() &&
                !txtContraseña2.getText().toString().isEmpty()
        ){
            if(txtContraseña1.getText().toString().equals(txtContraseña2.getText().toString()))
                return true;
            else{
                Toast.makeText(this, "Contraseña no Coinsiden!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(this, "Complete los campos Vacios!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
