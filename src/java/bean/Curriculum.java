package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Curriculum {
    private int id;
    private Empleador cuenta;
    private String archivo;
    private Date fecha;

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Curriculum(int id, Empleador cuenta, String archivo, Date fecha) {
        this.id = id;
        this.cuenta = cuenta;
        this.archivo = archivo;
        this.fecha = fecha;
    }
    
    public Curriculum(Empleador cuenta, String archivo, Date fecha) {
        this.cuenta = cuenta;
        this.archivo = archivo;
        this.fecha = fecha;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleador getCuenta() {
        return cuenta;
    }

    public void setCuenta(Empleador cuenta) {
        this.cuenta = cuenta;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos">
    public static ArrayList<Curriculum> obtenerCurriculums(int id) {
        ArrayList<Curriculum> curriculums = new ArrayList();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM curriculum WHERE curr_cuenta = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Curriculum c = new Curriculum(rs.getInt(1), Empleador.obtenerCuenta(rs.getInt(2)), rs.getString(3), rs.getDate(4));
                curriculums.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return curriculums;
    }
    
    public static Curriculum obtenerCurriculum(int id) {
        Curriculum curriculum = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM curriculum WHERE curr_cuenta = ? ORDER BY id_curriculum DESC LIMIT 1");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                curriculum = new Curriculum(rs.getInt(1), Empleador.obtenerCuenta(rs.getInt(2)), rs.getString(3), rs.getDate(4));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return curriculum;
    }
    
    public static Curriculum obtenerCurriculum2(int id) {
        Curriculum curriculum = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM curriculum WHERE id_curriculum = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                curriculum = new Curriculum(rs.getInt(1), Empleador.obtenerCuenta(rs.getInt(2)), rs.getString(3), rs.getDate(4));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return curriculum;
    }
//</editor-fold>
}
