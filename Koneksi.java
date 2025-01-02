
Package bioskop;
import java.awt.component;
import java.sql.*;
import javax.swing.JOptionPane;

public class Koneksi {
    public statement st;
    public ResultSet rs;
    public Connection Con;
    public Sring Sql = "";

    connection cn;
    public static Connection BukaKoneksi(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection cn = DriverManager.getConnection("Jdbc:mysql://locakhost:3306/bioskop","root","");
            return cn;
            }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
            }
        }
    }