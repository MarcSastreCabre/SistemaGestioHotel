/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

/**
 *
 * @author alumne
 */
public class Habitacio {
    private int id_habitacio;
    private int numero_habitacio;
    private String tipus;
    private int capacitat;
    private double preu_nit_AD;
    private double preu_nit_PM;
    private String estat;
    private String descripcio;

    public Habitacio(int id_habitacio, int numero_habitacio, String tipus, int capacitat, double preu_nit_AD, double preu_nit_PM, String estat, String descripcio) {
        this.id_habitacio = id_habitacio;
        this.numero_habitacio = numero_habitacio;
        this.tipus = tipus;
        this.capacitat = capacitat;
        this.preu_nit_AD = preu_nit_AD;
        this.preu_nit_PM = preu_nit_PM;
        this.estat = estat;
        this.descripcio = descripcio;
    }

    public int getId_habitacio() {
        return id_habitacio;
    }

    public void setId_habitacio(int id_habitacio) {
        this.id_habitacio = id_habitacio;
    }

    public int getNumero_habitacio() {
        return numero_habitacio;
    }

    public void setNumero_habitacio(int numero_habitacio) {
        this.numero_habitacio = numero_habitacio;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public int getCapacitat() {
        return capacitat;
    }

    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    public double getPreu_nit_AD() {
        return preu_nit_AD;
    }

    public void setPreu_nit_AD(double preu_nit_AD) {
        this.preu_nit_AD = preu_nit_AD;
    }

    public double getPreu_nit_PM() {
        return preu_nit_PM;
    }

    public void setPreu_nit_PM(double preu_nit_PM) {
        this.preu_nit_PM = preu_nit_PM;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @Override
    public String toString() {
        return "Habitacio nº" + numero_habitacio;
    }
    
    
}
