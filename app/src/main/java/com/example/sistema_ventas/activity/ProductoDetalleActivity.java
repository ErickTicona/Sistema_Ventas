package com.example.sistema_ventas.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.data.modelo.Producto;
import com.example.sistema_ventas.data.preferencia.SessionPreferences;
import com.example.sistema_ventas.data.util.Mensaje;
import com.example.sistema_ventas.esquemaSqlite.crud.Delete;
import com.example.sistema_ventas.esquemaSqlite.crud.Insert;
import com.example.sistema_ventas.esquemaSqlite.crud.Update;
import com.example.sistema_ventas.esquemaSqlite.tablas.ProductoTabla;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductoDetalleActivity extends AppCompatActivity {

    @BindView(R.id.APDTIETNombreProducto)
    TextInputEditText nombre;

    @BindView(R.id.APDTIETPrecioProducto)
    TextInputEditText precio;

    @BindView(R.id.APDIVImagenProducto)
    ImageView imagen;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.APDLLAgregar)
    LinearLayout agregar;

    @BindView(R.id.APDLLModificar)
    LinearLayout modificarEliminar;

    Boolean bNuevo = true;

    Producto producto;
    String pathUri = "";
    final int SELECT_PICTURE = 200;

    boolean bModificado = false;

    Mensaje mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mensaje = new Mensaje(getApplicationContext());

        if (getIntent().hasExtra("bNuevo")){
            bNuevo = getIntent().getBooleanExtra("bNuevo", true);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agregar.setVisibility(bNuevo ? View.VISIBLE : View.INVISIBLE);
        modificarEliminar.setVisibility(bNuevo ? View.INVISIBLE : View.VISIBLE);

        if (!bNuevo){
            producto = (Producto)getIntent().getSerializableExtra("itemProducto");
            cargarVista(producto);
        }
    }


    @OnClick(R.id.APDBBuscarImagen)
    public void buscarImagen(){
        Intent selectIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(selectIntent, "Selecciona App de Imagen"), SELECT_PICTURE);
    }

    @OnClick(R.id.APDBQuitarImagen)
    public void quitarImagen(){
        Picasso.get().load(R.drawable.ic_menu_producto)
                .resize(180,160)
                .centerCrop()
                .into(imagen);
        pathUri="";
    }

    @OnClick(R.id.APDBAgregar)
    public void agregarProducto(){
        int codigo = SessionPreferences.get(getApplicationContext()).getProducto();

        Insert.registrar(getApplicationContext(), new Producto(codigo,
                nombre.getText().toString(),
                Double.parseDouble(precio.getText().toString()),
                pathUri, false
                ), ProductoTabla.TABLA);

        mensaje.mensajeToasGuardar();
        bModificado = true;

        nombre.setText("");
        precio.setText("");
        pathUri = "";

        Picasso.get().load(R.drawable.caja_producto)
                .resize(180,160)
                .centerCrop()
                .into(imagen);

        nombre.requestFocus();
        salirActivity();
    }

    @OnClick(R.id.APDBModificar)
    public void modificarProducto(){
        Update.actualizar(getApplicationContext(),
                new Producto(producto.getProd_id(),
                        nombre.getText().toString(),
                        Double.parseDouble(precio.getText().toString()),
                        pathUri,
                        false
                        ), ProductoTabla.TABLA);

        mensaje.mensajeToasGuardar();
        bModificado = true;
        salirActivity();
    }

    @OnClick(R.id.APDBEliminar)
    public void eliminarProducto(){
        new AlertDialog.Builder(this)
                .setTitle("Productos")
                .setMessage("¿Deseas eliminar el producto?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete.eliminar(getApplicationContext(), producto.getProd_id(), ProductoTabla.TABLA);

                        mensaje.mensajeToasEliminar();
                        bModificado = true;
                        salirActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK){
                    Uri path = data.getData();
                    assert path != null;
                    pathUri = path.toString();

                    Picasso.get().load(pathUri)
                            .resize(180, 160)
                            .centerCrop()
                            .into(imagen);
                }
        }
    }

    private void cargarVista(Producto producto) {
        nombre.setText(producto.getProd_nombre());
        precio.setText(String.valueOf(producto.getProd_precio()));

        if(producto.getProd_ruta_foto().length() <= 1 || producto.getProd_ruta_foto().isEmpty()){

            Picasso.get().load(R.drawable.caja_producto).into(imagen);

        }else{
            Picasso.get().load(producto.getProd_ruta_foto())
                    .resize(180,160)
                    .error(R.drawable.caja_producto_error)
                    .centerCrop()
                    .into(imagen);
        }
        pathUri = producto.getProd_ruta_foto();
    }

    void salirActivity(){
        if (bModificado){
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        salirActivity();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                salirActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
