package extra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConexionBD {
    private String nombreBD;
    private String usuario;
    private String passw;
    private String url;
    private Connection conn;
    private PreparedStatement pst;
    private ResultSet cdr, lastResult;
    
    public ConexionBD(String nombreBD) {
        this.nombreBD = nombreBD;
        usuario = "root";
        passw = "";
        url = "jdbc:mysql://localhost/" + nombreBD;
        realizarConexion();
    }
    
    public ConexionBD(String nombreBD, String usuario, String passw, String url) {
        this.nombreBD = nombreBD;
        this.usuario = usuario;
        this.passw = passw;
        this.url = url + nombreBD;
        realizarConexion();
    }
    
    public void realizarConexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, usuario, passw);
        } catch (InstantiationException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, "No se pudo encontrar el driver", ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, "Acceso denegado", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, "No se pudo crear la instancia", ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, "No se pudo conectar a la base de datos", ex);
        }
    }
    
    public int ejecutarABC(ArrayList instruccionBD){
        int x = 0;
        try {
            pst = conn.prepareCall(instruccionBD.get(0).toString());
            for(int i=1; i<instruccionBD.size(); i++) {
                pst.setString(i, instruccionBD.get(i).toString());
            }
            x = pst.executeUpdate();
            lastResult = pst.getResultSet();
        } catch (SQLException ex) {
            //Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, "No se pudo realizar la operación", ex);
            ex.printStackTrace();
        }
        return x;
    }
    
    public ResultSet consultar(ArrayList instruccionBD){
        try {
            pst = conn.prepareCall(instruccionBD.get(0).toString());
            if(instruccionBD.size() > 1) {
                for(int i=1; i<instruccionBD.size(); i++) {
                    pst.setString(i, instruccionBD.get(i).toString());
                }
            }
            cdr = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, "No se pudo realizar la consulta", ex);
        }
        return cdr;
    }
    
    public int ultimoId(){
        int id=0;
        try {
            pst = conn.prepareCall("SELECT LAST_INSERT_ID()");
            cdr = pst.executeQuery();
            if(cdr.next()) {
                id = cdr.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, "No se pudo realizar la consulta", ex);
        }
        return id;
    }
        
    public void cerrarConexion(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.WARNING, "No se pudo cerrar la conexión", ex);
        }
    }

    public ResultSet getCdr() {
        return cdr;
    }

    public ResultSet getLastResult() {
        return lastResult;
    }
}
