package com.example.sistema_ventas.data.modelo;

public class VentaDetalle {
    private int vd_id;
    private int vd_cantidad;
    private Double vd_precio;
    private int vc_id;
    private String prod_nombre;
    private String prod_ruta_foto;

    public VentaDetalle() {
    }

    public VentaDetalle(int vd_id, int vd_cantidad, Double vd_precio, int vc_id, String prod_nombre, String prod_ruta_foto) {
        this.vd_id = vd_id;
        this.vd_cantidad = vd_cantidad;
        this.vd_precio = vd_precio;
        this.vc_id = vc_id;
        this.prod_nombre = prod_nombre;
        this.prod_ruta_foto = prod_ruta_foto;
    }

    public int getVd_id() {
        return vd_id;
    }

    public void setVd_id(int vd_id) {
        this.vd_id = vd_id;
    }

    public int getVd_cantidad() {
        return vd_cantidad;
    }

    public void setVd_cantidad(int vd_cantidad) {
        this.vd_cantidad = vd_cantidad;
    }

    public Double getVd_precio() {
        return vd_precio;
    }

    public void setVd_precio(Double vd_precio) {
        this.vd_precio = vd_precio;
    }

    public int getVc_id() {
        return vc_id;
    }

    public void setVc_id(int vc_id) {
        this.vc_id = vc_id;
    }

    public String getProd_nombre() {
        return prod_nombre;
    }

    public void setProd_nombre(String prod_nombre) {
        this.prod_nombre = prod_nombre;
    }

    public String getProd_ruta_foto() {
        return prod_ruta_foto;
    }

    public void setProd_ruta_foto(String prod_ruta_foto) {
        this.prod_ruta_foto = prod_ruta_foto;
    }
}
