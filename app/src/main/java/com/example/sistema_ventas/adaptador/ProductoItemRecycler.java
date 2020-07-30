package com.example.sistema_ventas.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.data.modelo.Producto;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductoItemRecycler extends RecyclerView.Adapter<ProductoItemRecycler.ViewHolderProducto>{

    private List<Producto> listaProducto;
    private OnItemClickListener itemClickListener;
    private Context context;

    public ProductoItemRecycler(List<Producto> listaProducto, OnItemClickListener itemClickListener) {
        this.listaProducto = listaProducto;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_producto, parent, false);
        return new ViewHolderProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProducto holder, int position) {
        holder.bind(listaProducto.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public interface OnItemClickListener{
        void OnClickItem(Producto producto, int posicion);
    }

    class ViewHolderProducto extends RecyclerView.ViewHolder{

        CircleImageView imagen;
        TextView nombre, precio;
        LinearLayout llSeleccion;

        public ViewHolderProducto (View itemView){
            super(itemView);
            imagen = itemView.findViewById(R.id.RIPCIVImagen);
            nombre = itemView.findViewById(R.id.RIPTVNombre);
            precio = itemView.findViewById(R.id.RIPTVPrecio);
            llSeleccion = itemView.findViewById(R.id.RIPLLItemSeleccionado);
        }

        void bind(final Producto producto, final OnItemClickListener listener){
            nombre.setText(producto.getProd_nombre());
            precio.setText(String.valueOf(producto.getProd_precio()));

            llSeleccion.setBackground(ContextCompat.getDrawable(context, producto.getProd_seleccionado() ? R.color.productoSeleccion : R.color.blanco));

            if (producto.getProd_ruta_foto().length() <= 1 || producto.getProd_ruta_foto().isEmpty()){
                Picasso.get().load(R.drawable.ic_menu_producto).into(imagen);
            }else {
                Picasso.get().load(producto.getProd_ruta_foto())
                        .resize(65,65)
                        .error(R.drawable.ic_menu_producto_error)
                        .centerCrop()
                        .into(imagen);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickItem(producto, getAdapterPosition());
                }
            });
        }
    }

}
