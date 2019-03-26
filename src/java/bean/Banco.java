package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Banco {
    private int id;
    private String nombre;

    public Banco(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Banco(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    public static Banco obtenerBanco(int id) {
        Banco banco = null;
        try {
            ConexionBD objCBD = new ConexionBD("banco");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM banco WHERE id_banco = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                banco = new Banco(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return banco;
    }
    
    
}
