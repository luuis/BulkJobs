package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Capacitador extends Cuenta {
    private String nombre;
    private long telefono;
    private String direccionNumInterior;
    private String direccionNumExterior;
    private String direccionLocalidad;
    private String direccionMunicipio;
    private String direccionEstado;
    private long numTarjeta;
    private float latitud;
    private float longitud;
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Capacitador(String correo, String contrasena, String salt, String rol, boolean activa,
            String nombre, long telefono, String direccionNumInterior, String direccionNumExterior,
            String direccionLocalidad, String direccionMunicipio, String direccionEstado,
            long numTarjeta, float latitud, float longitud) {
        super(correo, contrasena, salt, rol, activa);
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
        this.numTarjeta = numTarjeta;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Capacitador(int id, String correo, String contrasena, String salt, String rol, boolean activa,
            String nombre, long telefono, String direccionNumInterior, String direccionNumExterior,
            String direccionLocalidad, String direccionMunicipio, String direccionEstado,
            long numTarjeta, float latitud, float longitud) {
        super(id, correo, contrasena, salt, rol, activa);
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
        this.numTarjeta = numTarjeta;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Capacitador(Cuenta c, String nombre, long telefono, String direccionNumInterior,
            String direccionNumExterior, String direccionLocalidad, String direccionMunicipio,
            String direccionEstado, long numTarjeta, float latitud, float longitud) {
        super(c);
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
        this.numTarjeta = numTarjeta;
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

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
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

    public long getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(long numTarjeta) {
        this.numTarjeta = numTarjeta;
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
        instDB.add("INSERT INTO capacitador VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        instDB.add(id);
        instDB.add(nombre);
        instDB.add(telefono);
        instDB.add(direccionNumInterior);
        instDB.add(direccionNumExterior);
        instDB.add(direccionLocalidad);
        instDB.add(direccionMunicipio);
        instDB.add(direccionEstado);
        instDB.add(numTarjeta);
        instDB.add(latitud);
        instDB.add(longitud);
        
        return objCBD.ejecutarABC(instDB) > 0;
    }
    
    public static Capacitador obtenerCuenta(int id) {
        Capacitador capacitador = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM cuenta INNER JOIN capacitador WHERE capa_cuenta = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Cuenta cuenta = new Cuenta(rs.getInt("id_cuenta"), rs.getString("cuen_correo"),
                        rs.getString("cuen_contrasena"), rs.getString("cuen_salt"),
                        rs.getString("cuen_rol"), rs.getBoolean("cuen_activa"));
                capacitador = new Capacitador(cuenta, rs.getString("capa_nombre"), rs.getLong("capa_telefono"), rs.getString("capa_dir_num_int"), rs.getString("capa_dir_num_ext"), rs.getString("capa_dir_localidad"), rs.getString("capa_dir_municipio"), rs.getString("capa_dir_estado"), rs.getLong("capa_no_tarjeta"), rs.getFloat("capa_latitud"), rs.getLong("capa_longitud"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return capacitador;
    }
    //</editor-fold>
    
}
