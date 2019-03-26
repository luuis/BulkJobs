package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Reclutador extends Cuenta {
    private String nombre;
    private Categoria categoria;
    private long telefono;
    private String direccionNumInterior;
    private String direccionNumExterior;
    private String direccionLocalidad;
    private String direccionMunicipio;
    private String direccionEstado;
    private float latitud;
    private float longitud;
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Reclutador(String correo, String contrasena, String salt, String rol, boolean activa,
            String nombre, Categoria categoria, long telefono,
            String direccionNumInterior, String direccionNumExterior, String direccionLocalidad,
            String direccionMunicipio, String direccionEstado, float latitud, float longitud) {
        super(correo, contrasena, salt, rol, activa);
        this.nombre = nombre;
        this.categoria = categoria;
        this.telefono = telefono;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Reclutador(int id, String correo, String contrasena, String salt, String rol, boolean activa,
            String nombre, Categoria categoria, long telefono,
            String direccionNumInterior, String direccionNumExterior, String direccionLocalidad,
            String direccionMunicipio, String direccionEstado, float latitud, float longitud) {
        super(id, correo, contrasena, salt, rol, activa);
        this.nombre = nombre;
        this.categoria = categoria;
        this.telefono = telefono;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Reclutador(Cuenta c, String nombre, Categoria categoria, long telefono,
            String direccionNumInterior, String direccionNumExterior, String direccionLocalidad,
            String direccionMunicipio, String direccionEstado, float latitud, float longitud) {
        super(c);
        this.nombre = nombre;
        this.categoria = categoria;
        this.telefono = telefono;
        this.direccionNumInterior = direccionNumInterior;
        this.direccionNumExterior = direccionNumExterior;
        this.direccionLocalidad = direccionLocalidad;
        this.direccionMunicipio = direccionMunicipio;
        this.direccionEstado = direccionEstado;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
        instDB.add("INSERT INTO reclutador VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        instDB.add(id);
        instDB.add(categoria.getId());
        instDB.add(nombre);
        instDB.add(direccionNumInterior);
        instDB.add(direccionNumExterior);
        instDB.add(direccionLocalidad);
        instDB.add(direccionMunicipio);
        instDB.add(direccionEstado);
        instDB.add(telefono);
        instDB.add(latitud);
        instDB.add(longitud);
        
        return objCBD.ejecutarABC(instDB) > 0;
    }
    
    public static Reclutador obtenerCuenta(int id) {
        Reclutador reclutador = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM cuenta INNER JOIN reclutador WHERE recl_cuenta = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Cuenta cuenta = new Cuenta(rs.getInt("id_cuenta"), rs.getString("cuen_correo"),
                        rs.getString("cuen_contrasena"), rs.getString("cuen_salt"),
                        rs.getString("cuen_rol"), rs.getBoolean("cuen_activa"));
                reclutador = new Reclutador(cuenta, rs.getString("recl_nombre_empresa"),
                        Categoria.obtenerCategoria(rs.getInt("recl_categoria")),
                        rs.getLong("recl_telefono"), rs.getString("recl_dir_num_int"),
                        rs.getString("recl_dir_num_ext"), rs.getString("recl_dir_localidad"),
                        rs.getString("recl_dir_municipio"), rs.getString("recl_dir_estado"),
                        rs.getFloat("recl_latitud"), rs.getFloat("recl_longitud"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reclutador;
    }
    //</editor-fold>
    
}
