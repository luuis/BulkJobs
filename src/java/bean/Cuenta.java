package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cuenta {
    private int id;
    private String correo;
    private String contrasena;
    private String salt;
    private String rol;
    private boolean activa;

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Cuenta(String correo, String contrasena, String salt, String rol, boolean activa) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.salt = salt;
        this.rol = rol;
        this.activa = activa;
    }

    public Cuenta(int id, String correo, String contrasena, String salt, String rol, boolean activa) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.salt = salt;
        this.rol = rol;
        this.activa = activa;
    }
    
    public Cuenta(Cuenta c) {
        this.id = c.getId();
        this.correo = c.getCorreo();
        this.contrasena = c.getContrasena();
        this.salt = c.getSalt();
        this.rol = c.getRol();
        this.activa = c.isActiva();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos">
    public int registro() {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO cuenta VALUES(null, ?, ?, ?, ?, 0)");
        instBD.add(rol);
        instBD.add(correo);
        instBD.add(contrasena);
        instBD.add(salt);
        int ibd = objCBD.ejecutarABC(instBD);
        if (ibd > 0) {
            id = objCBD.ultimoId();
            return id; 
        }
        return 0;
    }
    
    public static Cuenta obtenerCuenta(int id) {
        Cuenta cuenta = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM cuenta WHERE id_cuenta = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                cuenta = new Cuenta(rs.getInt("id_cuenta"), rs.getString("cuen_correo"),
                        rs.getString("cuen_contrasena"), rs.getString("cuen_salt"),
                        rs.getString("cuen_rol"), rs.getBoolean("cuen_activa"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cuenta;
    }
    
    public static String obtenerRol(int id) {
        String rol = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT cuen_rol FROM cuenta WHERE id_cuenta = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                rol = rs.getString("cuen_rol");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rol;
    }
    
    public boolean esAdmin () { return  rol.equalsIgnoreCase("Bolsa de Trabajo"); }
    public boolean esEmpleador () { return  rol.equalsIgnoreCase("Empleador"); }
    public boolean esReclutador () { return  rol.equalsIgnoreCase("Reclutador"); }
    public boolean esCapacitador () { return  rol.equalsIgnoreCase("Capacitador"); }
    //</editor-fold>
}
