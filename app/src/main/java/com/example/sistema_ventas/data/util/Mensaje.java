package com.example.sistema_ventas.data.util;

import android.content.Context;
import android.widget.Toast;

public class Mensaje {
    private Context context;

    public Mensaje(Context context) {
        this.context = context;
    }
    public void mensajeToas(Object textomensaje){
        Toast.makeText(context,textomensaje.toString(),Toast.LENGTH_SHORT).show();
    }

    public void mensajeToasGuardar(){ mensajeToas("La informacion se guardo correctamente"); }

    public void mensajeToasEliminar(){ mensajeToas("La informacion se elimino correctamente"); }
}
