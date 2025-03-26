/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author alumne
 */
public class Tasca {// tornar a fer
    private int id_tasca;
    private String descripcio;
    private Date data_creacio;
    private Date data_execusio;
    private String estat;
    private Map<String, LinkedList<Object>> empl_tasca_est;
    private static int nextId;

    public Tasca(int id_tasca, String descripcio, Date data_creacio, Date data_execusio, String estat) {
        this.id_tasca = id_tasca;
        this.descripcio = descripcio;
        this.data_creacio = data_creacio;
        this.data_execusio = data_execusio;
        this.estat = estat;
        this.empl_tasca_est = new HashMap<>();
        nextId = id_tasca+1;
    }

    public static int getNextId() {
        return nextId;
    }
    
    public String getEstat() {
        return estat;
    }
    public Map getEmpl_tasca_est_valMap() {
        return empl_tasca_est;
    }
    public ArrayList<Object> getEmpl_tasca_est_val() {
        ArrayList<Object> ret = new ArrayList<>();
        for (Collection<Object> arr: empl_tasca_est.values()) {
            ret.addAll(arr);
        }
        return ret;
    }
    public ArrayList<Object> getEmpl_tasca_est_val_est() {
        String[] estats = {"Finalitzat", "En curs", "Pendent"};
        ArrayList<Object> ret = new ArrayList<>();
        int[] itemsEstats = new int[3];
        for (int i = 0; i < 3; i++) {
            if( empl_tasca_est.containsKey(estats[i]) && !empl_tasca_est.get(estats[i]).isEmpty()){
                ret.add("   -"+estats[i]);
                ret.addAll(empl_tasca_est.get(estats[i]));
                itemsEstats[i]++;
            }
        }
        if(itemsEstats[1] != 0 || (itemsEstats[0] != 0 && itemsEstats[2] != 0)){
            estat = estats[1];
        } else if(itemsEstats[2] != 0){
            estat = estats[2];
        } else {
            estat = estats[0];
        }
        return ret;
    }
    
    @Override
    public String toString() {
        return descripcio;
    }
    
    public boolean empleatWorkInTask(Object e){
        /*String [] arr = {"Finalitzat", "En curs", "Pendent"};
        for (String string : arr) {
            if(empl_tasca_est.get(arr) != null && empl_tasca_est.get(arr).contains(e)){
                return true;
            }
        }
        return false;*/
        
        //return empl_tasca_est.values().contains(e);
        for (LinkedList<Object> value : empl_tasca_est.values()) {
            if(value.contains(e)){
                return true;
            }
        }
        return false;
    }

    public int getId_tasca() {
        return id_tasca;
    }

    public void setId_tasca(int id_tasca) {
        this.id_tasca = id_tasca;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Date getData_creacio() {
        return data_creacio;
    }

    public void setData_creacio(Date data_creacio) {
        this.data_creacio = data_creacio;
    }

    public Date getData_execusio() {
        return data_execusio;
    }

    public void setData_execusio(Date data_execusio) {
        this.data_execusio = data_execusio;
    }

    public Map<String, LinkedList<Object>> getEmpl_tasca_est() {
        return empl_tasca_est;
    }

    public void setEmpl_tasca_est(Map<String, LinkedList<Object>> empl_tasca_est) {
        this.empl_tasca_est = empl_tasca_est;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }
    
    public void reCalcularEstat(){
        /*// Fer mes endevant
        if(!empl_tasca_est.get("En curs").isEmpty()){
            estat = "En curs";
        } else if(empl_tasca_est.get("Finalitzat").isEmpty() ){
            
        }*/
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
        final Tasca other = (Tasca) obj;
        return this.id_tasca == other.id_tasca;
    }
    
    public LinkedList<Object> empleatsNoSeleccionats(){
        LinkedList<Object> e = new LinkedList<>(Model.getEmpleat().values());
        for (LinkedList<Object> value : empl_tasca_est.values()) {
            e.removeAll(value);
        }
        for (Object object : e) {
            System.out.println("    Res: "+e);
        }
        return e;
    }

    public void modificarAtribusTasca(String descripcio, Date data_execusio) {
        this.descripcio = descripcio;
        this.data_execusio = data_execusio;
    }
    
    
    
}
