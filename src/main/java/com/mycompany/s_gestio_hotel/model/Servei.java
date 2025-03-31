/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

/**
 *
 * @author alumne
 */
public class Servei {
    private int id_servei;
    private String nom;
    private String descripcio;
    private double preu;

    public Servei(int id_servei, String nom, String descripcio, double preu) {
        this.id_servei = id_servei;
        this.nom = nom;
        this.descripcio = descripcio;
        this.preu = preu;
    }

    public int getId_servei() {
        return id_servei;
    }

    public void setId_servei(int id_servei) {
        this.id_servei = id_servei;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    @Override
    public String toString() {
        return nom;
    }
    
}
