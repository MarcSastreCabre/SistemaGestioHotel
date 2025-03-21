/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel.model;

//https://mvnrepository.com/artifact/org.openjfx/javafx-swing/11-ea+24

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GesitioDades {
    Model model = new Model();
    public void llistaUsuaris() {
        String sql = "select * from Persona";
        String sqlEm="SELECT * FROM Empleat where id_persona = ?";
        String sqlCl="SELECT * FROM Client where id_persona = ?";
        Connexio dbc = new Connexio();
        Connection connection = dbc.connecta();
        if(connection == null){
            System.out.println("BROOOOOO");
        }
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                Client c = null;
                Empleat e = null;
                int id_p = resultSet.getInt(1);
                PreparedStatement ordreEm = connection.prepareStatement(sqlEm);
                ordreEm.setInt(1, id_p);
                ResultSet resultSetEm = ordreEm.executeQuery();
                if(resultSetEm.next()){
                    System.out.println("El empleat: Existeix");
                    //LinkedList<Integer> tasques = new LinkedList<>();// fer una consulta a tasques per despres posar el id de les tasques (ho la propia tasca) cal arrayList
                    
                    e = new Empleat(resultSetEm.getInt(1), resultSetEm.getString(2), resultSetEm.getDate(3), resultSetEm.getInt(4), resultSetEm.getString(5), resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getDate(7), resultSet.getString(8));
                }
                PreparedStatement ordreCl = connection.prepareStatement(sqlCl);
                ordreCl.setInt(1, id_p);
                ResultSet resultSetCl = ordreCl.executeQuery();
                if(resultSetCl.next()){
                    System.out.println("El client: Existeix");
                    c = new Client(resultSetCl.getInt(1), resultSetCl.getDate(2), resultSetCl.getString(3), resultSetCl.getString(4), resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getDate(7), resultSet.getString(8), llistarReserves(resultSetCl.getInt(1)));
                    //System.out.println(llistarReserves(resultSetCl.getInt(1)).size());
                }
                if(c != null && e != null){
                    ClientEmpleat ce = new ClientEmpleat(c, e);
                    model.getPersones().put(id_p, ce);
                    model.getClient().put(c.getId_client(), ce);
                    model.getEmpleat().put(e.getId_empleat(), ce);
                } else if(c != null){
                    model.getPersones().put(id_p, c);
                    model.getClient().put(c.getId_client(), c);
                } else if(e != null){
                    model.getPersones().put(id_p, e);
                    model.getEmpleat().put(e.getId_empleat(), e);
                }
            }

            connection.close();

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
    }
    
    public ArrayList<Reserva> llistarReserves(int id_cli){
        ArrayList<Reserva> arrRes = new ArrayList<>();
        String sql ="SELECT * FROM Reserva where id_Client = ?";
        Connexio dbc = new Connexio();
        Connection connection = dbc.connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setInt(1, id_cli);
            ResultSet resultSet = ordre.executeQuery();
            while(resultSet.next()){
                //(int id_reserva, Date data_reserva, Date data_inici, Date data_fi, String tipus_reserva, int tipus_iva, double preu_total_reserva)
                int id_fact = resultSet.getInt(10);
                System.out.println("        Valor nulo: "+id_fact);
                Factura f;
                if(id_fact == 0){
                    f = null;
                } else {
                    f = llistarFactura(id_fact);
                }
                //System.out.println("    Factura "+f.getId_factura());
                Reserva r = new Reserva(resultSet.getInt(1), resultSet.getDate(2), resultSet.getDate(3), resultSet.getDate(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getDouble(7), resultSet.getInt(8), resultSet.getInt(9), f);
                arrRes.add(r);
                model.getReserves().add(r);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GesitioDades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrRes;
    }
    
    public void llistarHabitacions(){
        ArrayList<Reserva> arrRes = new ArrayList<>();
        String sql ="SELECT * FROM Habitacio";
        Connexio dbc = new Connexio();
        Connection connection = dbc.connecta();
        if(connection == null){
            System.out.println("Error");
        }
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                Habitacio h = new Habitacio(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getDouble(6), resultSet.getString(7), resultSet.getString(8));
                model.getHabitacions().put(h.getId_habitacio(), h);
            }

            connection.close();

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
    }
    public Factura llistarFactura(int id_fac){
        Factura ret = null;
        String sql ="SELECT * FROM Factura WHERE id_factura = ?";
        Connexio dbc = new Connexio();
        Connection connection = dbc.connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setInt(1, id_fac);
            ResultSet resultSet = ordre.executeQuery();
            while(resultSet.next()){
                ret = new Factura(resultSet.getInt(1), resultSet.getDate(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getInt(5), resultSet.getDouble(6));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GesitioDades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    //public LinkedList<LinkedList<Tasca>>
    
    public void llistarTasques(){
        String sql = "select * from Tasca";
        String sqlET = 
                "SELECT id_persona, estat \n" +
                "FROM Empleat_Tasca et\n" +
                "INNER JOIN Empleat e\n" +
                "ON e.id_empleat = et.id_empleat\n" +
                "WHERE id_tasca = ?;";
        Connexio dbc = new Connexio();
        Connection connection = dbc.connecta();
        if(connection == null){
            System.out.println("BROOOOOO");
        }
        try {
            Statement ordre = connection.createStatement();
            //Statement ordreET = connection.createStatement();
            
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                //int id_p = resultSet.getInt(1);
                //(int id_tasca, String descripcio, Date data_creacio, Date data_execusio, String estat)
                PreparedStatement ordreET = connection.prepareStatement(sqlET);
                ordreET.setInt(1, resultSet.getInt(1));
                ResultSet resultSetET = ordreET.executeQuery();
                //ordreET.setInt(1, resultSet.getInt(1));
                /*Map<String, LinkedList<Object>> estat_emp = new HashMap<>();
                while (resultSetET.next()) {
                    estat_emp.putIfAbsent(resultSetET.getString(2), new LinkedList<>());// si no esta ho creo i si no ho afegeixo a la llistad
                    estat_emp.get(resultSetET.getString(2)).add(model.getPersones().get(resultSetET.getInt(1)));
                    //model.getPersones().get(resultSetET.getInt(1));
                    //Object nextElement = en.nextElement();
                    
                }*/
                //System.out.println("Tamano tareas"+estat_emp.values().size());
                Tasca t = new Tasca(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3), resultSet.getDate(4), resultSet.getString(5)/*, estat_emp*/);
                System.out.println("        Tasca "+ t);
                model.getTasques().put(t.getId_tasca(), t);
                //model.getTasques().get(t.getEstat()).add(t);
            }

            connection.close();

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
    }
    
    public void llistarTasquesEmpleat(){
        String sql ="SELECT * FROM Empleat_Tasca";
        Connexio dbc = new Connexio();
        Connection connection = dbc.connecta();
        if(connection == null){
            System.out.println("Error");
        }
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                Object e = model.getEmpleat().get(resultSet.getInt(1));
                Tasca t = model.getTasques().get(resultSet.getInt(2));
                System.out.println("        Tasca "+t+"     Empleat     "+e);
                t.getEmpl_tasca_est().putIfAbsent(resultSet.getString(3), new LinkedList<>());
                
                System.out.println(resultSet.getInt(1)+"    "+resultSet.getInt(2)+"     "+resultSet.getString(3));
                model.getEmpleat(e).getTasca_est().putIfAbsent(resultSet.getString(3), new LinkedList<>());
                System.out.println("        Tasca "+t+"     Empleat     "+e);
                t.getEmpl_tasca_est().get(resultSet.getString(3)).add(e);
                model.getEmpleat(e).getTasca_est().get(resultSet.getString(3)).add(t);
            }

            connection.close();

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
    }
    public boolean afegeixPersona(Persona persona) throws SQLException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO Persona VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setInt(1, persona.getId_persona());
            ordre.setString(2, persona.getNom());
            ordre.setString(3, persona.getCognom());
            ordre.setString(4, persona.getAdresa());
            ordre.setString(5, persona.getDNI());
            ordre.setInt(6, persona.getTelefon());
            ordre.setDate(7, persona.getData_naixement());
            ordre.setString(8, persona.getEmail());
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
    
    public boolean afegeixEmpleats(Empleat empleat) throws SQLException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO Empleat VALUES (?,?,?,?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setInt(1, empleat.getId_empleat());
            ordre.setString(2, empleat.getLlocFeina());
            ordre.setDate(3, empleat.getData_contratacio());
            ordre.setInt(4, empleat.getSalari_brut());
            ordre.setString(5, empleat.getEstat_laboral());
            ordre.setInt(6, empleat.getId_persona());
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
    public boolean afegeixClients(Client client) throws SQLException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO Client VALUES (?,?,?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setInt(1, client.getId_client());
            ordre.setDate(2, client.getData_registre());
            ordre.setString(3, client.getTipus_client());
            ordre.setString(4, client.getTargeta_credit());
            ordre.setInt(5, client.getId_persona());
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
    public boolean afegeixReserva(Reserva r) throws SQLException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO Reserva VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setInt(1, r.getId_reserva());
            ordre.setDate(2, r.getData_reserva());
            ordre.setDate(3, r.getData_inici());
            ordre.setDate(4, r.getData_fi());
            ordre.setString(5, r.getTipus_reserva());
            ordre.setInt(6, r.getTipus_iva());
            ordre.setDouble(7, r.getPreu_total_reserva());
            ordre.setInt(8, r.getId_client());
            ordre.setInt(9, r.getId_habitacio());
            if(r.getFactura() != null){
                ordre.setInt(10, r.getFactura().getId_factura());
            } else{
               ordre.setNull(10, Types.INTEGER); 
            }
            
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
    public boolean afegeixFactura(Factura f) throws SQLException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO Factura VALUES (?,?,?,?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setInt(1, f.getId_factura());
            ordre.setDate(2, f.getData_emisio());
            ordre.setString(3, f.getMetode_pagament());
            ordre.setDouble(4, f.getBaseimposable());
            ordre.setInt(5, f.getIva());
            ordre.setDouble(6, f.getTotal());
            
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
    
    public boolean afegeixTasca(Tasca t) throws SQLException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO Tasca VALUES (?,?,?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setInt(1, t.getId_tasca());
            ordre.setString(2, t.getDescripcio());
            ordre.setDate(3, t.getData_creacio());
            ordre.setDate(4, t.getData_execusio());
            ordre.setString(5, t.getEstat());
            
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
    
    public boolean afegeixEmpleatTasca(Empleat e, Tasca t, String estat) throws SQLException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO Empleat_Tasca VALUES (?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setInt(1, e.getId_empleat());
            ordre.setInt(2, t.getId_tasca());
            ordre.setString(3, estat);
            
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
    
    public boolean modificarPersona(Persona p){
        boolean ok = false;
        String sql = "UPDATE Persona SET nom=?,cognom=?,adresa=?,telefon=?,data_naixement=?,email=? where id_persona=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, p.getNom());
            ordre.setString(2, p.getCognom());
            ordre.setString(3, p.getAdresa());
            ordre.setInt(4, p.getTelefon());
            ordre.setDate(5, p.getData_naixement());
            ordre.setString(6, p.getEmail());
            ordre.setInt(7, p.getId_persona());
            ordre.executeUpdate();
            ok = true;
            System.out.println("usuari modificat");
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage() +" Modificar persona");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage() +" Modificar persona");
            }
        }
                return ok;
    }
    public boolean modificarEmpleat(Empleat e){
        boolean ok = false;
        String sql = "UPDATE Empleat SET lloc_feina=?,data_contratacio=?,salariBrut=?,estatLaboral=? where id_persona=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, e.getLlocFeina());
            ordre.setDate(2, e.getData_contratacio());
            ordre.setInt(3, e.getSalari_brut());
            ordre.setString(4, e.getEstat_laboral());
            ordre.setInt(5, e.getId_persona());
            ordre.executeUpdate();
            ok = true;
            System.out.println("usuari modificat");
        } catch (SQLException ex) {
            System.out.println("Error SQL:" + ex.getMessage() +" Modificar empleat");
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Error:" + ex.getMessage() +" Modificar empleat");
            }
        }
                return ok;
    }
    public boolean modificarClient(Client c){
        boolean ok = false;
        String sql = "UPDATE Client SET data_registre=?,tipus_client=?,targeta_credit=? where id_persona=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setDate(1, c.getData_registre());
            ordre.setString(2, c.getTipus_client());
            ordre.setString(3, c.getTargeta_credit());
            ordre.setInt(4, c.getId_persona());
            ordre.executeUpdate();
            ok = true;
            System.out.println("usuari modificat");
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage() +" Modificar client");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage() +" Modificar client");
            }
        }
                return ok;
    }  
    public boolean modificarReserva(Reserva r){
        boolean ok = false;
        String sql = "UPDATE Reserva SET data_reserva =?,data_inici =?,data_fi =?,tipus_reseva =?,tipus_iva =?,preu_total_reseva =?,id_client =?,id_habitacio =? where id_reserva=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setDate(1, r.getData_reserva());
            ordre.setDate(2, r.getData_inici());
            ordre.setDate(3, r.getData_fi());
            ordre.setString(4, r.getTipus_reserva());
            ordre.setInt(5, r.getTipus_iva());
            ordre.setDouble(6, r.getPreu_total_reserva());
            ordre.setInt(7, r.getId_client());
            ordre.setInt(8, r.getId_habitacio());
            ordre.setInt(9, r.getId_reserva());
            ordre.executeUpdate();
            ok = true;
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage() +" Modificar Reserva");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage() +" Modificar Reserva");
            }
        }
                return ok;
    }    
    public boolean modificarTasca(Tasca t){
        boolean ok = false;
        String sql = "UPDATE Tasca SET descripcio =?,data_execusio =? where id_tasca=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, t.getDescripcio());
            ordre.setDate(2, t.getData_execusio());
            ordre.setInt(3, t.getId_tasca());
            ordre.executeUpdate();
            ok = true;
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage() +" Modificar Reserva");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage() +" Modificar Reserva");
            }
        }
                return ok;
    }      
    public boolean modificarEstatTasca(Tasca t){
        boolean ok = false;
        String sql = "UPDATE Tasca SET estat =? where id_tasca=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, t.getEstat());
            ordre.setInt(2, t.getId_tasca());
            ordre.executeUpdate();
            ok = true;
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage() +" Modificar Reserva");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage() +" Modificar Reserva");
            }
        }
                return ok;
    }      
    public boolean modificarEmpleatTasca(Empleat em, Tasca t, String estat){
        boolean ok = false;
        String sql = "UPDATE Empleat_Tasca SET estat =? where id_empleat=? and id_tasca=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, estat);
            ordre.setInt(2, em.getId_empleat());
            ordre.setInt(3, t.getId_tasca());
            ordre.executeUpdate();
            ok = true;
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage() +" EmpleatTasca ");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage() +" EmpleatTasca");
            }
        }
                return ok;
    }
    public boolean afegirFacturaReserva(Reserva r){
        boolean ok = false;
        String sql = "UPDATE Reserva SET  id_factura=? where id_reserva=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setInt(1, r.getFactura().getId_factura());
            ordre.setInt(2, r.getId_reserva());
            ordre.executeUpdate();
            ok = true;
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage() +" Modificar Reserva afg habitacio");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage() +" Modificar Reserva afg habitacio");
            }
        }
                return ok;
    }
    public boolean eliminarPersona(Persona p) {
        boolean ok = false;
        String sql = "DELETE FROM Persona WHERE id_persona=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setInt(1, p.getId_persona());
            ok = ordre.executeUpdate() > 0;
        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
        return ok;
    }
    public boolean eliminarEmpleat(Empleat e) {
        boolean ok = false;
        String sql = "DELETE FROM Empleat WHERE id_persona=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setInt(1, e.getId_persona());
            ok = ordre.executeUpdate() > 0;
        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
        return ok;
    }
    public boolean eliminarClient(Client c) {
        boolean ok = false;
        String sql = "DELETE FROM Client WHERE id_persona=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setInt(1, c.getId_persona());
            ok = ordre.executeUpdate() > 0;
        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
        return ok;
    }

    public boolean cancelarReserva(Reserva r) {
        boolean ok = false;
        if(r.getFactura() == null){
            String sql = "DELETE FROM Reserva WHERE id_reserva=?";
            Connection connection = new Connexio().connecta();
            try {
                PreparedStatement ordre = connection.prepareStatement(sql);
                ordre.setInt(1, r.getId_reserva());
                ok = ordre.executeUpdate() > 0;
            } catch (SQLException throwables) {
                System.out.println("Error:" + throwables.getMessage());
            }
        }
        return ok;
    }
}

/*
    public boolean modificarUsuari(Usuari usuari) throws IOException, SQLException {
        boolean ok = false;
        // String sql = "UPDATE usuaris SET nom=?,dataNaixement=?,telefon=?,correu=? WHERE nif=?";
        String sql = "UPDATE usuaris SET nom=?,dataNaixement=?,telefon=?,correu=?,imatge=? WHERE nif=?";

        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, usuari.getNom());
            ordre.setDate(2, Date.valueOf(usuari.getDataNaixement()));
            ordre.setString(3, usuari.getTelefon());
            ordre.setString(4, usuari.getCorreu());
            ordre.setString(6, usuari.getNif());

            Image ima = usuari.getImatgeJ();
            if (ima != null) {
                BufferedImage imagenB = SwingFXUtils.fromFXImage(ima, null);
                ByteArrayOutputStream s = new ByteArrayOutputStream();
                javax.imageio.ImageIO.write(imagenB, "jpg", s);

                byte[] imaBytes = s.toByteArray();
                Blob b = connection.createBlob();
                b.setBytes(1, imaBytes);
                System.out.println("imatge creada ");
                ordre.setBlob(5, b);
            } 

            ordre.executeUpdate();
            ok = true;
            System.out.println("usuari modificat");
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
        return ok;
    }

    public boolean eliminarUsuari(Usuari usuari) {
        boolean ok = false;
        String sql = "DELETE FROM usuaris WHERE nif=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, usuari.getNif());
            ok = ordre.executeUpdate() > 0;
        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
        return ok;
    }

    public boolean afegeixUsuari(Usuari usuari) throws SQLException, FileNotFoundException, IOException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO usuaris VALUES (?,?,?,?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setString(1, usuari.getNif());
            ordre.setString(2, usuari.getNom());
            ordre.setDate(3, Date.valueOf(usuari.getDataNaixement()));
            ordre.setString(4, usuari.getTelefon());
            ordre.setString(5, usuari.getCorreu());

            Image ima = usuari.getImatgeJ();
            BufferedImage imagenB = SwingFXUtils.fromFXImage(ima, null);

            ByteArrayOutputStream s = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(imagenB, "jpg", s);

            byte[] imaBytes = s.toByteArray();
            Blob b = connection.createBlob();
            b.setBytes(1, imaBytes);
            ordre.setBlob(6, b);
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }



    public Usuari consultarUsuari(String nif) {
        Usuari usuari = null;
        String sql = "select nif,nom, dataNaixement,telefon, correu, imatge from usuaris where nif=?";
        Connection connection = new Connexio().connecta();
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, nif);
            ResultSet resultSet = ordre.executeQuery();
            if (resultSet.next()) {
                usuari = new Usuari(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        new Image(resultSet.getBlob(6).getBinaryStream())
                );
            }
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
        return usuari;
    }




    public void llistaUsuaris() {
        //String ret = "";
        String sql = "select * from Persona";
        //String sql="select nom from usuaris";
        Connexio dbc = new Connexio();
        Connection connection = dbc.connecta();
        if(connection == null){
            System.out.println("BROOOOOO");
        }
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                for (int i = 1; i < 6; i++) {
                    System.out.println(resultSet.getString(i));
                    //ret+= resultSet.getString(i)+'\n';
                }
                //Persona p = new Persona(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(6), resultSet.getString(7));
                Persona p = new Persona(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getDate(7), resultSet.getString(8));
                
            }

            connection.close();

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
    }
*/