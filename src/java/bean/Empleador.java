package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Empleador extends Cuenta {
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String sexo;
    private long telefono;
    private Date fechaNacimiento;
    private String curp;
    private String profesion;
    private String direccionNumInterior;
    private String direccionNumExterior;
    private String direccionLocalidad;
    private String direccionMunicipio;
    private String direccionEstado;
    private String estadoCivil;
    private float latitud;
    private float longitud;

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Empleador(String nombre, String apPaterno, String apMaterno, String sexo,
            long telefono, Date fechaNacimiento, String curp, String profesion,
            String direccionNumInterior, String direccionNumExterior, String direccionLocalidad,
            String direccionMunicipio, String direccionEstado, String estadoCivil, String correo,
            float latitud, float longitud, String contrasena, String salt, String rol, boolean activa) {
        super(correo, contrasena, salt, rol, activa);
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.sexo = sexo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.profesion = profesion;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
        this.estadoCivil = estadoCivil;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Empleador(int id, String correo, String contrasena, String salt, String rol, boolean activa,
            String nombre, String apPaterno, String apMaterno, String sexo, long telefono,
            Date fechaNacimiento, String curp, String profesion, String direccionNumInterior,
            String direccionNumExterior, String direccionLocalidad, String direccionMunicipio,
            String direccionEstado, String estadoCivil, float latitud, float longitud) {
        super(id, correo, contrasena, salt, rol, activa);
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.sexo = sexo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.profesion = profesion;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
        this.estadoCivil = estadoCivil;
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
    public Empleador(Cuenta c, String nombre, String apPaterno, String apMaterno, String sexo,
            long telefono, Date fechaNacimiento, String curp, String profesion, String direccionNumInterior,
            String direccionNumExterior, String direccionLocalidad, String direccionMunicipio,
            String direccionEstado, String estadoCivil, float latitud, float longitud) {
        super(c);
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.sexo = sexo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.profesion = profesion;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
        this.estadoCivil = estadoCivil;
        this.latitud = latitud;
        this.longitud = longitud;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDireccionNumInterior() {
        return direccionNumInterior;
    }

    public void setDireccionNumInterior(String direccionNumInterior) {
        this.direccionNumInterior = direccionNumInterior;
    }

    public String getDireccionNumExterior() {
        return direccionNumExterior;
    }

    public void setDireccionNumExterior(String direccionNumExterior) {
        this.direccionNumExterior = direccionNumExterior;
    }

    public String getDireccionLocalidad() {
        return direccionLocalidad;
    }

    public void setDireccionLocalidad(String direccionLocalidad) {
        this.direccionLocalidad = direccionLocalidad;
    }

    public String getDireccionMunicipio() {
        return direccionMunicipio;
    }

    public void setDireccionMunicipio(String direccionMunicipio) {
        this.direccionMunicipio = direccionMunicipio;
    }

    public String getDireccionEstado() {
        return direccionEstado;
    }

    public void setDireccionEstado(String direccionEstado) {
        this.direccionEstado = direccionEstado;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLontigud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos">
    public boolean registrar(int id) {
        ArrayList instDB = new ArrayList();
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        
        // TODO: GeoAPI 
        float latitud = 0;
        float longitud = 0;
        instDB.add("INSERT INTO empleador VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        instDB.add(id);
        instDB.add(estadoCivil);
        instDB.add(profesion);
        instDB.add(nombre);
        instDB.add(apPaterno);
        instDB.add(apMaterno);
        instDB.add(sexo);
        instDB.add(telefono);
        instDB.add(fechaNacimiento);
        instDB.add(curp);
        instDB.add(direccionNumInterior);
        instDB.add(direccionNumExterior);
        instDB.add(direccionLocalidad);
        instDB.add(direccionMunicipio);
        instDB.add(direccionEstado);
        instDB.add(latitud);
        instDB.add(longitud);
        
        return objCBD.ejecutarABC(instDB) > 0;
    }
    
    public static Empleador obtenerCuenta(int id) {
        Empleador empleador = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM cuenta INNER JOIN empleador ON empl_cuenta=id_cuenta WHERE empl_cuenta = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Cuenta cuenta = new Cuenta(rs.getInt("id_cuenta"), rs.getString("cuen_correo"),
                        rs.getString("cuen_contrasena"), rs.getString("cuen_salt"),
                        rs.getString("cuen_rol"), rs.getBoolean("cuen_activa"));
                empleador = new Empleador(cuenta, rs.getString("empl_nombre"), rs.getString("empl_ap_pat"), rs.getString("empl_ap_mat"), rs.getString("empl_sexo"), rs.getLong("empl_telefono"), rs.getDate("empl_fecha_nacimiento"), rs.getString("empl_curp"), rs.getString("empl_profesion"), rs.getString("empl_dir_num_int"), rs.getString("empl_dir_num_ext"), rs.getString("empl_dir_localidad"), rs.getString("empl_dir_municipio"), rs.getString("empl_dir_estado"), rs.getString("empl_estado_civil"), rs.getFloat("empl_latitud"), rs.getFloat("empl_longitud"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empleador;
    }
    //</editor-fold>
}
