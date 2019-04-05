package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sesion<E> {
    private boolean iniciada;
    private boolean activa;
    private int id;
    private int tipo;
    private E cuenta;
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Sesion() {
        iniciada = false;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public boolean isIniciada() {
        return iniciada;
    }

    public void setIniciada(boolean iniciada) {
        this.iniciada = iniciada;
    }
    
    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public E getCuenta() {
        return cuenta;
    }

    public void setCuenta(E cuenta) {
        this.cuenta = cuenta;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos">
    public boolean iniciarSesion(String correo, String contrasena) {
        boolean sesion = false;
        if (!correo.equalsIgnoreCase("") && !contrasena.equalsIgnoreCase("")) {
            try {
                ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
                ArrayList instBD = new ArrayList();
                instBD.add("SELECT * FROM cuenta WHERE cuen_correo=? LIMIT 1");
                instBD.add(correo);
                objCBD.consultar(instBD);
                ResultSet rs = objCBD.getCdr();
                while (rs.next()) {
                    activa = rs.getBoolean("cuen_activa");
                    boolean comprobar = extra.PasswordUtils.verifyUserPassword(
                            contrasena, rs.getString("cuen_contrasena"), rs.getString("cuen_salt"));
                    
                    if (comprobar) {
                        switch (rs.getString("cuen_rol")) {
                            case "Bolsa de Trabajo":
                                tipo = 0;
                                break;
                            case "Empleador":
                                tipo = 1;
                                instBD = new ArrayList();
                                instBD.add("SELECT * FROM empleador WHERE empl_cuenta=? LIMIT 1");
                                instBD.add(rs.getInt("id_cuenta"));
                                objCBD.consultar(instBD);
                                ResultSet rse = objCBD.getCdr();
                                while (rse.next()) {
                                    cuenta = (E) new Empleador(rs.getInt("id_cuenta"), rs.getString("cuen_correo"), rs.getString("cuen_contrasena"), rs.getString("cuen_salt"), rs.getString("cuen_rol"), rs.getBoolean("cuen_activa"), rse.getString("empl_nombre"), rse.getString("empl_ap_pat"), rse.getString("empl_ap_mat"), rse.getString("empl_sexo"), rse.getLong("empl_telefono"), rse.getDate("empl_fecha_nacimiento"), rse.getString("empl_curp"), rse.getString("empl_profesion"), rse.getString("empl_dir_num_int"), rse.getString("empl_dir_num_ext"), rse.getString("empl_dir_localidad"), rse.getString("empl_dir_municipio"), rse.getString("empl_dir_estado"), rse.getString("empl_estado_civil"), rse.getFloat("empl_latitud"), rse.getFloat("empl_longitud"));
                                    id = rs.getInt("id_cuenta");
                                }
                                break;
                            case "Reclutador":
                                tipo = 2;
                                instBD = new ArrayList();
                                instBD.add("SELECT * FROM reclutador WHERE recl_cuenta=? LIMIT 1");
                                instBD.add(rs.getInt("id_cuenta"));
                                objCBD.consultar(instBD);
                                ResultSet rsr = objCBD.getCdr();
                                while (rsr.next()) {
                                    cuenta = (E) new Reclutador(rs.getInt("id_cuenta"), rs.getString("cuen_correo"), rs.getString("cuen_contrasena"), rs.getString("cuen_salt"), rs.getString("cuen_rol"), rs.getBoolean("cuen_activa"), rsr.getString("recl_nombre_empresa"), Categoria.obtenerCategoria(rsr.getInt("recl_categoria")), rsr.getLong("recl_telefono"), rsr.getString("recl_dir_num_int"), rsr.getString("recl_dir_num_ext"), rsr.getString("recl_dir_localidad"), rsr.getString("recl_dir_municipio"), rsr.getString("recl_dir_estado"), rsr.getFloat("recl_latitud"), rsr.getFloat("recl_longitud"));
                                    id = rs.getInt("id_cuenta");
                                }
                                break;
                            case "Capacitador":
                                tipo = 3;
                                instBD = new ArrayList();
                                instBD.add("SELECT * FROM capacitador WHERE capa_cuenta=? LIMIT 1");
                                instBD.add(rs.getInt("id_cuenta"));
                                objCBD.consultar(instBD);
                                ResultSet rsc = objCBD.getCdr();
                                if (rsc.next()) {
                                    cuenta = (E) new Capacitador(rs.getInt("id_cuenta"), rs.getString("cuen_correo"), rs.getString("cuen_contrasena"), rs.getString("cuen_salt"), rs.getString("cuen_rol"), rs.getBoolean("cuen_activa"), rsc.getString("capa_nombre"), rsc.getLong("capa_telefono"), rsc.getString("capa_dir_num_int"), rsc.getString("capa_dir_num_ext"), rsc.getString("capa_dir_localidad"), rsc.getString("capa_dir_municipio"), rsc.getString("capa_dir_estado"), rsc.getLong("capa_no_tarjeta"), rsc.getFloat("capa_latitud"), rsc.getFloat("capa_longitud"));
                                    id = rs.getInt("id_cuenta");
                                }
                                break;
                        }
                        iniciada = true;
                        sesion = true;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return sesion;
    }
    
    public boolean esAdmin () { return iniciada && tipo == 0; }
    public boolean esEmpleador () { return iniciada && tipo == 1; }
    public boolean esReclutador () { return iniciada && tipo == 2; }
    public boolean esCapacitador () { return iniciada && tipo == 3; }
    
    public boolean tienePermisos (String r) {
        if (r.trim().isEmpty()) return false;
        String[] roles = r.split(",");
       
        for (String rol : roles) {
            if (rol.equalsIgnoreCase("admin") && esAdmin()) return true;
            if (rol.equalsIgnoreCase("empleador") && esEmpleador()) return true;
            if (rol.equalsIgnoreCase("reclutador") && esReclutador()) return true;
            if (rol.equalsIgnoreCase("capacitador") && esCapacitador()) return true;
        }
        return false;
    }
    //</editor-fold>
}
