/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

/**
 *
 * @author alumne
 */
public class ServeisContractats {
    private Servei s;
    private int quantitat;
    private double preuTotal;

    public ServeisContractats(Servei s, int quantitat, double preuTotal) {
        this.s = s;
        this.quantitat = quantitat;
        this.preuTotal = preuTotal;
    }
    

    public Servei getS() {
        return s;
    }

    public void setS(Servei s) {
        this.s = s;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public double getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(double preuTotal) {
        this.preuTotal = preuTotal;
    }

    @Override
    public String toString() {
        return s + "x" + quantitat + ", "+preuTotal;
    }
    
}
