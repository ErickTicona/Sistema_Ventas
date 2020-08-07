package com.example.sistema_ventas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sistema_ventas.activity.ClienteActivity;
import com.example.sistema_ventas.activity.LogiinActivity;
import com.example.sistema_ventas.activity.ProductoActivity;
import com.example.sistema_ventas.activity.VentaActivity;
import com.example.sistema_ventas.activity.VentaHistorialActivity;
import com.example.sistema_ventas.esquemaSqlite.ConexionSqliteHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Button mButtonSignout;
    private FirebaseAuth mAuth;
    private TextView mTextViewName;
    private DatabaseReference mDatabase;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ConexionSqliteHelper con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        con = new ConexionSqliteHelper(MainActivity.this);
        //
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //
        mButtonSignout = (Button)findViewById(R.id.BtnSignout);
        mButtonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LogiinActivity.class));
                finish();
            }
        });
        //
        mTextViewName = (TextView)findViewById(R.id.textViewName);
        getUserInfo();
    }

    @OnClick(R.id.AMIBProducto)
    public void clickProducto(){
        IrActivity(ProductoActivity.class);
    }

    @OnClick(R.id.AMIBCliente)
    public void clickCliente(){
        IrActivity(ClienteActivity.class);
    }

    @OnClick(R.id.AMIBVenta)
    public void clickVenta(){
        IrActivity(VentaActivity.class);
    }

    @OnClick(R.id.AMIBVentaHistorial)
    public void clickVentaHistorial(){
        IrActivity(VentaHistorialActivity.class);
    }

    void IrActivity(Class<?> paramClass){
        //Intento para mostrar otras Actividades

        Intent intent = new Intent(getApplicationContext(), paramClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        con.close();
        super.onDestroy();
    }
    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("username").getValue().toString();
                mTextViewName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
