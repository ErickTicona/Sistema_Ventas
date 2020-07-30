package com.example.sistema_ventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.example.sistema_ventas.activity.ClienteActivity;
import com.example.sistema_ventas.activity.ProductoActivity;
import com.example.sistema_ventas.activity.VentaActivity;
import com.example.sistema_ventas.activity.VentaHistorialActivity;
import com.example.sistema_ventas.esquemaSqlite.ConexionSqliteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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
}
