/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author alumne
 */
public class Empleat extends Persona {
    private int id_empleat;
    private String llocFeina;
    private Date data_contratacio;
    private int salari_brut;
    private String estat_laboral;
    private static int nextIdE = 0;
    private Map<String, LinkedList<Tasca>> tasca_est;
    // el control de tasques d'empleats es portara unicament des de tasques fent que amb una funcio puguis obtindre segons un empleat les el estat de els seves tasques
    // el estat de la tasca es calculara segons el estat que tenen els empleats ó
    /*private LinkedList<Integer> tasquesFetes;
    private LinkedList<Integer> tasquesFentse;
    private LinkedList<Integer> tasquesPerFer;*/

    public Empleat(int id_empleat, String llocFeina, Date data_contratacio, int salari_brut, String estat_laboral,/* LinkedList<Integer> tasquesFetes, LinkedList<Integer> tasquesFentse, LinkedList<Integer> tasquesPerFer,*/ int id_persona, String nom, String cognom, String adresa, String DNI, int telefon, Date data_naixement, String email) {
        super(id_persona, nom, cognom, adresa, DNI, telefon, data_naixement, email);
        this.id_empleat = id_empleat;
        this.llocFeina = llocFeina;
        this.data_contratacio = data_contratacio;
        this.salari_brut = salari_brut;
        this.estat_laboral = estat_laboral;
        /*this.tasquesFetes = tasquesFetes;
        this.tasquesFentse = tasquesFentse;
        this.tasquesPerFer = tasquesPerFer;*/
        if(this.id_empleat >= nextIdE){
            nextIdE = this.id_empleat+1;
        }
        tasca_est = new HashMap<>();
    }

    /*public LinkedList<LinkedList<Integer>> getTasques() {
        return new LinkedList<>(Arrays.asList(tasquesFetes, tasquesFentse, tasquesPerFer));
    }*/

    public static int getNextIdE() {
        return nextIdE;
    }

    public Map<String, LinkedList<Tasca>> getTasca_est() {
        return tasca_est;
    }

    public void setTasca_est(Map<String, LinkedList<Tasca>> tasca_est) {
        this.tasca_est = tasca_est;
    }
    
    public ObservableList<Tasca> getTascaObsList(){
        LinkedList<Tasca> t = new LinkedList<>();
        for (LinkedList<Tasca> value : tasca_est.values()) {
            t.addAll(value);
            System.out.println("Hola");
        }
        return FXCollections.observableArrayList(t);
    }
    
    public ObservableList<Object> getTascaFiltObsList(){
        LinkedList<Object> t = new LinkedList<>();
        for (String value : tasca_est.keySet()) {
            t.add("     -"+value);
            t.addAll(tasca_est.get(value));
        }
        return FXCollections.observableArrayList(t);
    }
    public int getId_empleat() {
        return id_empleat;
    }

    public void setId_empleat(int id_empleat) {
        this.id_empleat = id_empleat;
    }

    public String getLlocFeina() {
        return llocFeina;
    }

    public void setLlocFeina(String llocFeina) {
        this.llocFeina = llocFeina;
    }

    public Date getData_contratacio() {
        return data_contratacio;
    }

    public void setData_contratacio(Date data_contratacio) {
        this.data_contratacio = data_contratacio;
    }

    public int getSalari_brut() {
        return salari_brut;
    }

    public void setSalari_brut(int salari_brut) {
        this.salari_brut = salari_brut;
    }

    public String getEstat_laboral() {
        return estat_laboral;
    }

    public void setEstat_laboral(String estat_laboral) {
        this.estat_laboral = estat_laboral;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    public void pujarTasca(Tasca t, Object empl){// normalment es faria amb this pero com que tinc el client empleat ho haig de duplicar
        String[] estats = {"Pendent", "En curs", "Finalitzat"};
        int i = 0;
        boolean tascaTrovada = false;
        while (i<2) {
            if(tasca_est.containsKey(estats[i]) && tasca_est.get(estats[i]).contains(t)){
                tasca_est.get(estats[i]).remove(t);
                t.getEmpl_tasca_est().get(estats[i]).remove(t);
                tascaTrovada = true;
                break;
            }
            i++;
            if(tascaTrovada){
                tasca_est.get(estats[i]).add(t);
                t.getEmpl_tasca_est().get(estats[i]).add(t);
            }
        }
            
    }

    @Override
    public boolean equals(Object obj) { // optimitzar
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        System.out.println(toString()+" fdsa "+obj.toString());
        if (obj.getClass() == ClientEmpleat.class){
            return ((ClientEmpleat) obj).equals(this);
        }
        if(obj.getClass() == Client.class){
            return super.equals(obj);
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Empleat other = (Empleat) obj;
        return this.id_empleat == other.id_empleat || super.equals(obj);
    }
    public ArrayList<Tasca> tasquesNoSeleccionades(){
        ArrayList<Tasca> t = new ArrayList<>(Model.getTasquesList());
        for (LinkedList<Tasca> value : tasca_est.values()) {
            t.removeAll(value);
        }
        return t;
    }
    public void afegirTasca(Tasca t, String est, Object eOce){
        /*tasca_est.putIfAbsent(est, new LinkedList<>());
        tasca_est.get(est).add(t);
        t.getEmpl_tasca_est().putIfAbsent(est, new LinkedList<>());
        t.getEmpl_tasca_est().get(est).add(eOce);*/
    }
}
