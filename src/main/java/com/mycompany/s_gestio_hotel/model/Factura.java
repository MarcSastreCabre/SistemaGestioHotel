/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

import java.sql.Date;

/**
 *
 * @author alumne
 */
public class Factura {
    private int id_factura;
    private Date data_emisio;
    private String metode_pagament;
    private double baseimposable;
    private int iva;
    private double total;

    public Factura(int id_factura, Date data_emisio, String metode_pagament, double baseimposable, int iva, double total) {
        this.id_factura = id_factura;
        this.data_emisio = data_emisio;
        this.metode_pagament = metode_pagament;
        this.baseimposable = baseimposable;
        this.iva = iva;
        this.total = total;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public Date getData_emisio() {
        return data_emisio;
    }

    public void setData_emisio(Date data_emisio) {
        this.data_emisio = data_emisio;
    }

    public String getMetode_pagament() {
        return metode_pagament;
    }

    public void setMetode_pagament(String metode_pagament) {
        this.metode_pagament = metode_pagament;
    }

    public double getBaseimposable() {
        return baseimposable;
    }

    public void setBaseimposable(double baseimposable) {
        this.baseimposable = baseimposable;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
