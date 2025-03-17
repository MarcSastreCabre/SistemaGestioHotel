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
public class Reserva {
    private int id_reserva;
    private Date data_reserva;
    private Date data_inici;
    private Date data_fi;
    private String tipus_reserva;
    private int tipus_iva;
    private double preu_total_reserva;
    private int id_client;
    private Factura factura;
    private int id_habitacio;
    private static int lastIdReserva = 0;
    public Reserva(int id_reserva, Date data_reserva, Date data_inici, Date data_fi, String tipus_reserva, int tipus_iva, double preu_total_reserva, int id_client, int id_habitacio, Factura factura) {
        this.id_reserva = id_reserva;
        this.data_reserva = data_reserva;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.tipus_reserva = tipus_reserva;
        this.tipus_iva = tipus_iva;
        this.preu_total_reserva = preu_total_reserva;
        this.id_client = id_client;
        this.id_habitacio = id_habitacio;
        this.factura = factura;
        if(this.id_reserva >= lastIdReserva){
            lastIdReserva = this.id_reserva+1;
        }
    }   

    @Override
    public String toString() {
        return "Reserva " +  id_reserva;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public Date getData_reserva() {
        return data_reserva;
    }

    public void setData_reserva(Date data_reserva) {
        this.data_reserva = data_reserva;
    }

    public Date getData_inici() {
        return data_inici;
    }

    public void setData_inici(Date data_inici) {
        this.data_inici = data_inici;
    }

    public Date getData_fi() {
        return data_fi;
    }

    public void setData_fi(Date data_fi) {
        this.data_fi = data_fi;
    }

    public String getTipus_reserva() {
        return tipus_reserva;
    }

    public void setTipus_reserva(String tipus_reserva) {
        this.tipus_reserva = tipus_reserva;
    }

    public int getTipus_iva() {
        return tipus_iva;
    }

    public void setTipus_iva(int tipus_iva) {
        this.tipus_iva = tipus_iva;
    }

    public double getPreu_total_reserva() {
        return preu_total_reserva;
    }

    public void setPreu_total_reserva(double preu_total_reserva) {
        this.preu_total_reserva = preu_total_reserva;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public int getId_habitacio() {
        return id_habitacio;
    }

    public void setId_habitacio(int id_habitacio) {
        this.id_habitacio = id_habitacio;
    }

    

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reserva other = (Reserva) obj;
        return this.id_reserva == other.id_reserva;
    }

    public static int getLastIdReserva() {
        return lastIdReserva;
    }
    
    
    
    
    
}
