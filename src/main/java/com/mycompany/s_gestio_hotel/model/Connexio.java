/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.*;
public class Connexio {
    private final String URL = "jdbc:mysql://localhost:3306/sistema_gestio_hotel_bd";//nom bd
  //  private final String URL = "jdbc:mysql://192.168.2.104:3306/dam_bd";//nom bd 

    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "usuaridam";
    private final String PASSWD = "usuaridam";   
   

    public Connection connecta() {
        Connection connexio = null;
        try {
            //Carreguem el driver          
            Class.forName(DRIVER); 
            connexio = DriverManager.getConnection(URL, USER, PASSWD); 
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(throwables.toString());
            System.out.println(throwables.getLocalizedMessage());
        }    
        return connexio;
    }
}
