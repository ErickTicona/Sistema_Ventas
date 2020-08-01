package com.example.sistema_ventas.adaptador;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.data.modelo.ProductoVenta;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VentaItemRecycler extends RecyclerView.Adapter<VentaItemRecycler.ViewHolderVenta>{

    List<ProductoVenta> listaProductoVenta;
    EditText totalVenta;

    public VentaItemRecycler(List<ProductoVenta> listaProductoVenta, EditText totalVenta) {
        this.listaProductoVenta = listaProductoVenta;
        this.totalVenta = totalVenta;
    }

    @NonNull
    @Override
    public ViewHolderVenta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_venta, parent, false);
        return new ViewHolderVenta(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVenta holder, int position) {
        holder.bind(listaProductoVenta.get(position));

    }

    @Override
    public int getItemCount() {
        return listaProductoVenta.size();
    }

    class ViewHolderVenta extends RecyclerView.ViewHolder{

        EditText cantidad, precio;
        TextView nombre, totalproducto;
        ImageView imagen;
        Button quitar;

        public ViewHolderVenta(View itemView) {
            super(itemView);

            cantidad = itemView.findViewById(R.id.RIVETCantidad);
            precio = itemView.findViewById(R.id.RIVETPrecio);
            nombre = itemView.findViewById(R.id.RIVTVNombre);
            totalproducto = itemView.findViewById(R.id.RIVTVTotal);
            imagen = itemView.findViewById(R.id.RIVCIVImagen);
            quitar = itemView.findViewById(R.id.RIVBQuitar);

            cantidad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    modificarValores(cantidad, precio, totalproducto, getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            precio.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    modificarValores(cantidad, precio, totalproducto, getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            quitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quitar(getAdapterPosition());
                }
            });
        }

        void bind(final ProductoVenta productoVenta){

            cantidad.setText(String.valueOf(productoVenta.getProd_cantidad()));
            precio.setText(String.valueOf(productoVenta.getProd_precio()));
            totalproducto.setText(String.valueOf(productoVenta.getProd_precio()));
            nombre.setText(productoVenta.getProd_nombre());

            if (productoVenta.getProd_ruta_foto().length() < 0 || productoVenta.getProd_ruta_foto().isEmpty()){
                Picasso.get().load(R.drawable.caja_producto).into(imagen);
            }else {
                Picasso.get().load(productoVenta.getProd_ruta_foto())
                        .error(R.drawable.caja_producto_error)
                        .centerCrop()
                        .resize(65, 65)
                        .into(imagen);
            }
        }
    }

    private void quitar(int adapterPosition) {
        listaProductoVenta.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        sumarLista();
    }

    public void sumarLista() {
        Double temp = 0.0;
        for (ProductoVenta item : listaProductoVenta){
            temp += item.getProd_total();
        }
        totalVenta.setText(String.valueOf(temp));
    }

    private void modificarValores(EditText cantidad, EditText precio, TextView totalproducto, int adapterPosition) {

        if (cantidad.getText().length() > 0){
            if (precio.getText().length() > 0){

                int cant = Integer.parseInt(cantidad.getText().toString());
                Double pre = Double.parseDouble(precio.getText().toString());

                totalproducto.setText(String.valueOf(cant * pre));

                listaProductoVenta.get(adapterPosition).setProd_cantidad(cant);
                listaProductoVenta.get(adapterPosition).setProd_precio_venta(pre);
                listaProductoVenta.get(adapterPosition).setProd_total(cant * pre);
                sumarLista();
            }
        }
    }
}
