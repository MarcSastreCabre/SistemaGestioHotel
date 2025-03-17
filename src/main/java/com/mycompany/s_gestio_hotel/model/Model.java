/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author alumne
 */
public class Model {
    private static Map<Integer,Object> persones = new HashMap<>();
    private static Map<Integer,Object> client = new HashMap<>();
    private static Map<Integer,Object> empleat = new HashMap<>();
    private static Map<Integer,Habitacio> habitacions = new HashMap<>();
    private static ArrayList<Reserva> reserves = new ArrayList<>();
    private static GesitioDades gd = new GesitioDades();
    private static Map<Integer, Tasca> tasques = new HashMap<>();
    public void inicialitzar(){
        //gd.llistaUsuaris2();
        gd.llistaUsuaris();
        gd.llistarTasques();
        gd.llistarHabitacions();
        gd.llistarTasquesEmpleat();
    }
    
    public static ArrayList<Reserva> getReserves() {
        return reserves;
    }

    public Map<Integer,Object> getPersones() {
        return persones;
    }

    public Map<Integer, Object> getClient() {
        return client;
    }

    public Map<Integer, Object> getEmpleat() {
        return empleat;
    }

    public static Map<Integer, Tasca> getTasques() {
        return tasques;
    }

    

    
    public static Collection getTasquesList() {
        return tasques.values();
    }
    public ObservableList<Object> getTasquesListF() {
        return FXCollections.observableArrayList(getTasquesList());
    }
    public ObservableList<Object> buscarClient(int id_client){
        for (Object persona : persones.values()) {
            int id;
            if (persona.getClass() == Empleat.class) {
                continue;
            } else if(persona.getClass() == ClientEmpleat.class){
                if(((ClientEmpleat) persona).getClient().getId_client() == id_client){
                    return FXCollections.observableArrayList(persona);
                }
            } else{
                if(((Client) persona).getId_client() == id_client){
                    return FXCollections.observableArrayList(persona);
                }
            }

        }
        return FXCollections.observableArrayList();
    }
    public ObservableList<Object> formatMapTasquesToObsList(Map<String, LinkedList<Tasca>> map){
        ObservableList<Object> retList = FXCollections.observableArrayList();
        String [] arr = {"Finalitzat", "En curs", "Pendent"};
        for (String s : arr) {
            //retList.add("    - "+s.toUpperCase());
            for (Tasca t : map.get(s)) {
                retList.add(t);
            }
        }
        return retList;
        
    }
    
    public ObservableList filtrarTascaOEmpleat(Map<String, LinkedList<Object>> mapa){
        
        //return tascasF;
        LinkedList<Object> retList = new LinkedList<>();
        String [] arr = {"Finalitzat", "En curs", "Pendent"};
        for (String s : arr) {
            retList.add("    - "+s.toUpperCase());
            for (Object o : mapa.get(s)) {
                retList.add(o);
            }
        }
        return FXCollections.observableArrayList(retList);
    }
    
    public Empleat getEmpleat(Object o){
        if(Empleat.class == o.getClass()){
            return (Empleat) o;
        } else if(ClientEmpleat.class == o.getClass()){
            return ((ClientEmpleat) o).getEmpleat();
        }
        return null;
    }
    
    public Client getClient(Object o){
        if(Client.class == o.getClass()){
            return (Client) o;
        } else if(ClientEmpleat.class == o.getClass()){
            return ((ClientEmpleat) o).getClient();
        }
        return null;
    }
    public boolean isNotInt(String s){
        for (int i = 0; i < s.length(); i++) {
            char n = s.charAt(i);
            if(n < '0' || n > '9'){
                return true;
            }
        }
        return false;
    }

    public static Map<Integer, Habitacio> getHabitacions() {
        return habitacions;
    }
    
    
}
