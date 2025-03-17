/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author alumne
 */
public class ClientEmpleat{
    private Client client;
    private Empleat empleat;

    public ClientEmpleat(Client client, Empleat empleat) {
        this.client = client;
        this.empleat = empleat;
    }

    @Override
    public String toString() {
        return "Client i Empleat" + ' '+client.getId_persona();
    }

    public Client getClient() {
        return client;
    }

    public Empleat getEmpleat() {
        return empleat;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setEmpleat(Empleat empleat) {
        this.empleat = empleat;
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
        if(client.equals(obj) || empleat.equals(obj)){
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClientEmpleat other = (ClientEmpleat) obj;
        return Objects.equals(this.empleat, other.empleat) || Objects.equals(this.client, other.client);
    }
    
}
