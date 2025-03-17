/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author alumne
 */
public class Client extends Persona{
    private int id_client;
    private Date data_registre;
    private String tipus_client;
    private String targeta_credit;
    private ArrayList<Reserva> reserves;
    private static int nextIdC = 0;
    public Client(int id_client, Date data_registre, String tipus_client, String targeta_credit, int id_persona, String nom, String cognom, String adresa, String DNI, int telefon, Date data_naixement, String email, ArrayList<Reserva> reserves) {
        super(id_persona, nom, cognom, adresa, DNI, telefon, data_naixement, email);
        this.id_client = id_client;
        this.data_registre = data_registre;
        this.tipus_client = tipus_client;
        this.targeta_credit = targeta_credit;
        this.reserves = reserves;
        if(this.id_client >= nextIdC){
            nextIdC = this.id_client+1;
        }
    }

    public static int getNextIdC() {
        return nextIdC;
    }

    public ArrayList<Reserva> getReserves() {
        return reserves;
    }

    public int getId_client() {
        return id_client;
    }

    public Date getData_registre() {
        return data_registre;
    }

    public String getTipus_client() {
        return tipus_client;
    }

    public String getTargeta_credit() {
        return targeta_credit;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setData_registre(Date data_registre) {
        this.data_registre = data_registre;
    }

    public void setTipus_client(String tipus_client) {
        this.tipus_client = tipus_client;
    }

    public void setTargeta_credit(String targeta_credit) {
        this.targeta_credit = targeta_credit;
    }

    public void setReserves(ArrayList<Reserva> reserves) {
        this.reserves = reserves;
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
        if (obj.getClass() == ClientEmpleat.class){
            return ((ClientEmpleat) obj).equals(this);
        }
        if(obj.getClass() == Empleat.class){
            return super.equals(obj);
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        return this.id_client == other.id_client || super.equals(obj);
    }
    
    
    
}
