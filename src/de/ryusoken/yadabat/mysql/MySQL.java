package de.ryusoken.yadabat.mysql;

import de.ryusoken.yadabat.Misc.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {

    static Connection con;

    public static void connect(String host, String database, String user, String passwd ) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String ConnectionCmd = "jdbc:mysql://" + host + ":3306/" + database + "?user=" + user + "&password=" + passwd;
            con = DriverManager.getConnection(ConnectionCmd);
            System.out.println("Die Verbindung zur MySQL Datenbank konnte hergestellt werden.");
        } catch (Exception ex) {
            System.out.println("Die Verbindung zur MySQL Datenbank konnte nicht hergestellt werden.");
            System.exit(0);
            ex.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            con.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertIntoDvd(String title, String label, String medium, String release, String EAN) {
        if(con != null) {
            try {
                String isExisting = "SELECT * FROM dvd WHERE EAN =" + EAN;

                String qry = "INSERT INTO dvd(Titel,Label,Medium,Erscheinungsdatum,Eintragung,EAN) VALUES (?,?,?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(qry);

                ps.setString(1, title);
                ps.setString(2, label);
                ps.setString(3, medium);
                ps.setString(4, release);
                ps.setString(5, Utils.getCurrentDate());
                ps.setString(6, EAN);

                ps.executeUpdate();

                ps.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void insertIntoBooks(String title, String authors, String release, String EAN) {
        if(con != null) {
            try {
                String qry = "INSERT INTO books(Titel,Autoren,Erscheinungsdatum,Eintragung,ISBN) VALUES (?,?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(qry);

                ps.setString(1, title);
                ps.setString(2, authors);
                ps.setString(3, release);
                ps.setString(4, Utils.getCurrentDate());
                ps.setString(5, EAN);

                ps.executeUpdate();

                ps.close();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}