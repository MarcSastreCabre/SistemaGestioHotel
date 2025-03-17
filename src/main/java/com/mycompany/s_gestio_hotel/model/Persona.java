/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author alumne
 */
public class Persona {
    private int id_persona;
    private String nom;
    private String cognom;
    private String adresa;
    private String DNI;
    private int telefon;
    private Date data_naixement;
    private String email;
    private static int nextId = 0;

    public Persona(int id_persona, String nom, String cognom, String adresa, String DNI, int telefon, Date data_naixement, String email) {
        this.id_persona = id_persona;
        this.nom = nom;
        this.cognom = cognom;
        this.adresa = adresa;
        this.DNI = DNI;
        this.telefon = telefon;
        this.data_naixement = data_naixement;
        this.email = email;
        if(this.id_persona >= nextId){
            nextId = this.id_persona+1;
        }
    }

    public static int getNextId() {
        return nextId;
    }
    
    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName()+ " "+id_persona;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    public Date getData_naixement() {
        return data_naixement;
    }

    public void setData_naixement(Date data_naixement) {
        this.data_naixement = data_naixement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /*@Override
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
        final Persona other = (Persona) obj;
        if (this.id_persona != other.id_persona) {
            return false;
        }
        return Objects.equals(this.DNI, other.DNI);
    }*/

    @Override
    public boolean equals(Object obj) { // quan son dos clients amb el mateix dni no es diferencia
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Persona)) { // instanceof comprova si el objecte es una instancia de la classe, comprova si es una sub classe
            return false;
        }
        final Persona other = (Persona) obj;
        return Objects.equals(this.DNI, other.DNI) || this.id_persona == other.id_persona;
    }
    
    
    
    
}
