package com.example.sistema_ventas.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.data.modelo.VentaCabecera;

import java.util.List;

public class VentaHistorialItemRecycler extends RecyclerView.Adapter<VentaHistorialItemRecycler.ViewHolderVentaHistorial>{

    List<VentaCabecera> listaCabecera;
    OnItemClickListener itemClickListener;

    public VentaHistorialItemRecycler(List<VentaCabecera> listaCabecera, OnItemClickListener itemClickListener) {
        this.listaCabecera = listaCabecera;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderVentaHistorial onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_venta_historial, parent, false);
        return new ViewHolderVentaHistorial(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVentaHistorial holder, int position) {
        holder.bind(listaCabecera.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaCabecera.size();
    }

    public interface OnItemClickListener{
        void onItemClick(VentaCabecera ventaCabecera, int position);
    }

    class ViewHolderVentaHistorial extends RecyclerView.ViewHolder{
        TextView nombre, fecha, hora, total;

        public ViewHolderVentaHistorial(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.RIVHTVNombre);
            fecha = itemView.findViewById(R.id.RIVHTVFecha);
            hora = itemView.findViewById(R.id.RIVHTVHora);
            total = itemView.findViewById(R.id.RIVHTVTotal);
        }

        void bind(final VentaCabecera ventaCabecera, OnItemClickListener clickListener){
            nombre.setText(ventaCabecera.getClie_nombre());
            fecha.setText(ventaCabecera.getVc_fecha());
            hora.setText(ventaCabecera.getVc_hora());
            total.setText(String.valueOf(ventaCabecera.getVc_monto()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(ventaCabecera, getAdapterPosition());
                }
            });
        }
    }
}
