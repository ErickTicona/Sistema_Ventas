package com.example.sistema_ventas.data.modelo;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int clie_id;
    private String clie_nombre;
    private String clie_num_cel;
    private String clie_email;
    private String clie_direccion;

    public Cliente() {
    }

    public Cliente(int clie_id, String clie_nombre, String clie_num_cel, String clie_email, String clie_direccion) {
        this.clie_id = clie_id;
        this.clie_nombre = clie_nombre;
        this.clie_num_cel = clie_num_cel;
        this.clie_email = clie_email;
        this.clie_direccion = clie_direccion;
    }

    public int getClie_id() {
        return clie_id;
    }

    public void setClie_id(int clie_id) {
        this.clie_id = clie_id;
    }

    public String getClie_nombre() {
        return clie_nombre;
    }

    public void setClie_nombre(String clie_nombre) {
        this.clie_nombre = clie_nombre;
    }

    public String getClie_num_cel() {
        return clie_num_cel;
    }

    public void setClie_num_cel(String clie_num_cel) {
        this.clie_num_cel = clie_num_cel;
    }

    public String getClie_email() {
        return clie_email;
    }

    public void setClie_email(String clie_email) {
        this.clie_email = clie_email;
    }

    public String getClie_direccion() {
        return clie_direccion;
    }

    public void setClie_direccion(String clie_direccion) {
        this.clie_direccion = clie_direccion;
    }
}
