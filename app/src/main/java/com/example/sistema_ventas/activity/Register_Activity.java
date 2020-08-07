package com.example.sistema_ventas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sistema_ventas.MainActivity;
import com.example.sistema_ventas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Activity extends AppCompatActivity {

    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputUsername;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonRegister;

    //variables de datos a registrar
    private String username="";
    private String email="";
    private String password="";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //CircleImage
        mCircleImageViewBack = findViewById(R.id.circleImageBack);
        mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Datos de layout
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputUsername = findViewById(R.id.textInputUsername);
        mTextInputPassword = findViewById(R.id.textInputPassword);

        mButtonRegister = findViewById(R.id.btnRegister);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mTextInputUsername.getText().toString();
                email = mTextInputEmail.getText().toString();
                password = mTextInputPassword.getText().toString();

                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()){

                    if (password.length() >=6){
                        registerUser();
                    }
                    else {
                        Toast.makeText(Register_Activity.this,"La contraseña debe tener almenos 6 caracteres",Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(Register_Activity.this,"Debe completar los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object>map=new HashMap<>();
                    map.put("username",username);
                    map.put("email",email);
                    map.put("password",password);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(Register_Activity.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(Register_Activity.this,"No se pudo guardar los datos correctamente",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Register_Activity.this,"No se pudo registrar este usuario",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}