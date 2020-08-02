package com.example.sistema_ventas.esquemaSqlite.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sistema_ventas.data.modelo.Cliente;
import com.example.sistema_ventas.data.modelo.Producto;
import com.example.sistema_ventas.data.modelo.VentaCabecera;
import com.example.sistema_ventas.data.modelo.VentaDetalle;
import com.example.sistema_ventas.data.util.Metodos;
import com.example.sistema_ventas.esquemaSqlite.ConexionSqliteHelper;
import com.example.sistema_ventas.esquemaSqlite.tablas.ClienteTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.ProductoTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaDetalleTabla;

import java.util.ArrayList;
import java.util.List;

public class Select {
    private static ConexionSqliteHelper con = null;
    private static SQLiteDatabase db = null;

    private static List<Object> seleccionarRegistros (Context context, String tabla){

        List<Object> listaRetorno = new ArrayList<>();
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor cLista = db.query(tabla, null, null, null, null, null, null);

        while (cLista.moveToNext()){

            switch (tabla){
                case ClienteTabla.TABLA:
                    listaRetorno.add(
                            new Cliente(
                                    cLista.getInt(cLista.getColumnIndex(ClienteTabla.CLIE_ID)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NUM_CEL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_EMAIL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_DIRECCION))
                            )
                    );
                    break;
                case ProductoTabla.TABLA:
                    listaRetorno.add(
                            new Producto(
                                    cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                                    cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO)),
                                    false
                            )
                    );
                    break;
            }

        }

        return listaRetorno;
    }

    public static void seleccionarClientes(Context context, List<Cliente> lista, String buscar){

        lista.clear();

        List<Object> tempLista = seleccionarRegistros(context, ClienteTabla.TABLA);

        for (Object item : tempLista){

            Cliente _item = (Cliente)item;

            if (buscar.length() > 0){
                if (_item.getClie_nombre().length() >= buscar.length()){

                    String cadenaRecortada = _item.getClie_nombre().substring(0, buscar.length());

                    if(buscar.equals(cadenaRecortada))lista.add(_item);

                }
            }else {
                lista.add(_item);
            }
        }

    }

    public static void seleccionarProductos(Context context, List<Producto> lista, String buscar){

        lista.clear();

        List<Object> tempLista = seleccionarRegistros(context, ProductoTabla.TABLA);

        for (Object item : tempLista){

            Producto _item = (Producto)item;

            if (buscar.length() > 0){
                if (_item.getProd_nombre().length() >= buscar.length()){

                    String cadenaRecortada = _item.getProd_nombre().substring(0, buscar.length());

                    if(buscar.equals(cadenaRecortada))lista.add(_item);

                }
            }else {
                lista.add(_item);
            }
        }

    }

    public static void seleccionarVentaCabecera(Context context, List<VentaCabecera> lista, String fecha, String buscar){

        lista.clear();
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor tempLista;

        String query = Metodos.concatenar(new Object[]{"select * from ", VentaCabeceraTabla.TABLA, " where ",
        VentaCabeceraTabla.VC_FECHA, " = ? order by ", VentaCabeceraTabla.VC_FECHA, " , ", VentaCabeceraTabla.VC_HORA, " desc"});

        tempLista = db.rawQuery(query, new String[]{fecha});

        while (tempLista.moveToNext()){
            VentaCabecera item = new VentaCabecera(
                    tempLista.getInt(tempLista.getColumnIndex(VentaCabeceraTabla.VC_ID)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_FECHA)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_HORA)),
                    tempLista.getDouble(tempLista.getColumnIndex(VentaCabeceraTabla.VC_MONTO)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_COMENTARIO)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.CLIE_NOMBRE))
            );

            if (buscar.length() > 0){
                if (item.getClie_nombre().length() >= buscar.length()){

                    String cadenaRecortada = item.getClie_nombre().substring(0, buscar.length());

                    if(buscar.equals(cadenaRecortada))lista.add(item);

                }
            }else {
                lista.add(item);
            }
        }
    }

    public static void seleccionarVentaDetalle(Context context, List<VentaDetalle> lista, int vc_id){
        lista.clear();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor tempLista;

        String query = Metodos.concatenar(new Object[]{"select * from ", VentaDetalleTabla.TABLA, " where ",
                VentaDetalleTabla.VC_ID, " = ? order by ", VentaDetalleTabla.PROD_NOMBRE, " desc"});

        tempLista = db.rawQuery(query, new String[]{String.valueOf(vc_id)});

        while (tempLista.moveToNext()) {
            lista.add(new VentaDetalle(
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VD_ID)),
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VD_CANTIDAD)),
                    tempLista.getDouble(tempLista.getColumnIndex(VentaDetalleTabla.VD_PRECIO)),
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VC_ID)),
                    tempLista.getString(tempLista.getColumnIndex(VentaDetalleTabla.PROD_NOMBRE)),
                    tempLista.getString(tempLista.getColumnIndex(VentaDetalleTabla.PROD_RUTA_FOTO))
            ));
        }

    }
}
