package com.example.sistema_ventas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_ventas.MainActivity;
import com.example.sistema_ventas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogiinActivity extends AppCompatActivity {
    TextView mTextViewRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;
    //
    private String email="";
    private String password="";

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logiin);
        //
        mAuth = FirebaseAuth.getInstance();
        //
        mTextViewRegister = findViewById(R.id.textViewRegister);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogiinActivity.this, Register_Activity.class);
                startActivity(intent);
            }
        });
        //
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mTextInputEmail.getText().toString();
                password = mTextInputPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }
                else{
                    Toast.makeText(LogiinActivity.this,"Debe completar los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void loginUser(){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LogiinActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(LogiinActivity.this,"Compruebe los datos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        if (mAuth.getCurrentUser() !=null){
            startActivity(new Intent(LogiinActivity.this, MainActivity.class));
            finish();
        }
    }
}
