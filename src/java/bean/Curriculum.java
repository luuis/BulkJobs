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
    
    public Curriculum(Empleador cuenta) {
        this.cuenta = cuenta;
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
    
    public boolean generarCurriculum() {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instCV = new ArrayList();
        instCV.add("INSERT INTO curriculum VALUES (null, ?, null, NOW())");
        instCV.add(cuenta.getId());
        
        int insertado = objCBD.ejecutarABC(instCV);
        if (insertado > 0) {
            id = objCBD.ultimoId();
            objCBD.cerrarConexion();
            return true;
        }
        objCBD.cerrarConexion();
        return false; 
    }
    
    public static void generarCVEL(int idCurr, String[] cvel_nombre_empresa, String[] cvel_dir_num_int, String[] cvel_dir_num_ext, String[] cvel_dir_localidad, String[] cvel_dir_municipio, String[] cvel_dir_estado, String[] cvel_telefono, String[] cvel_puesto, String[] cvel_nombre_jefe, String[] cvel_app_jefe, String[] cvel_apm_jefe, String[] cvel_funciones, String[] cvel_anio_inicio, String[] cvel_anio_fin) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<cvel_nombre_empresa.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_experiencia_laboral VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(cvel_nombre_empresa[i]);
            instCV.add(cvel_dir_num_int[i]);
            instCV.add(cvel_dir_num_ext[i]);
            instCV.add(cvel_dir_localidad[i]);
            instCV.add(cvel_dir_municipio[i]);
            instCV.add(cvel_dir_estado[i]);
            instCV.add(cvel_telefono[i]);
            instCV.add(cvel_puesto[i]);
            instCV.add(cvel_nombre_jefe[i]);
            instCV.add(cvel_app_jefe[i]);
            instCV.add(cvel_apm_jefe[i]);
            instCV.add(cvel_funciones[i]);
            instCV.add(cvel_anio_inicio[i]);
            instCV.add(cvel_anio_fin[i]);
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static void generarCVFA(int idCurr, String[] cvfa_nivel, String[] cvfa_nombre_institucion, String[] cvfa_dir_num_int, String[] cvfa_dir_num_ext, String[] cvfa_dir_localidad, String[] cvfa_dir_municipio, String[] cvfa_dir_estado, String[] cvfa_titulo_certificado, String[] cvfa_cedula, String[] cvfa_anio_inicio, String[] cvfa_anio_fin, String[] cvfa_estado) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<cvfa_nivel.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_formacion_academica VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(cvfa_nivel[i]);
            instCV.add(cvfa_nombre_institucion[i]);
            instCV.add(cvfa_dir_num_int[i]);
            instCV.add(cvfa_dir_num_ext[i]);
            instCV.add(cvfa_dir_localidad[i]);
            instCV.add(cvfa_dir_municipio[i]);
            instCV.add(cvfa_dir_estado[i]);
            instCV.add(cvfa_titulo_certificado[i]);
            instCV.add(cvfa_cedula[i]);
            instCV.add(cvfa_anio_inicio[i]);
            instCV.add(cvfa_anio_fin[i]);
            instCV.add(cvfa_estado[i]);
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static void generarCVID(int idCurr, String[] idio_idioma, String[] idio_porcentaje) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<idio_idioma.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_idiomas VALUES (null, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(idio_idioma[i]);
            instCV.add(idio_porcentaje[i]);
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static void generarCVDF(int idCurr, String[] cvdf_nombre, String[] cvdf_app, String[] cvdf_apm, String[] cvdf_parentesco, String[] cvdf_vive, String[] cvdf_finado, String[] cvdf_dir_num_int, String[] cvdf_dir_num_ext, String[] cvdf_dir_localidad, String[] cvdf_dir_municipio, String[] cvdf_dir_estado, String[] cvdf_ocupacion) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<cvdf_nombre.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_datos_familiares VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(cvdf_nombre[i]);
            instCV.add(cvdf_app[i]);
            instCV.add(cvdf_apm[i]);
            instCV.add(cvdf_parentesco[i]);
            instCV.add(cvdf_vive[i]);
            instCV.add(cvdf_finado[i]);
            instCV.add(cvdf_dir_num_int[i]);
            instCV.add(cvdf_dir_num_ext[i]);
            instCV.add(cvdf_dir_localidad[i]);
            instCV.add(cvdf_dir_municipio[i]);
            instCV.add(cvdf_dir_estado[i]);
            instCV.add(cvdf_ocupacion[i]);
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static void generarCVRP(int idCurr, String[] cvrp_nombre, String[] cvrp_app, String[] cvrp_apm, String[] cvrp_dir_num_int, String[] cvrp_dir_num_ext, String[] cvrp_dir_localidad, String[] cvrp_dir_municipio, String[] cvrp_dir_estado, String[] cvrp_telefono, String[] cvrp_relacion, String[] cvrp_tiempo_conociendo, String[] cvrp_tipo) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<cvrp_nombre.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_referencias_personales VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(cvrp_nombre[i]);
            instCV.add(cvrp_app[i]);
            instCV.add(cvrp_apm[i]);
            instCV.add(cvrp_dir_num_int[i]);
            instCV.add(cvrp_dir_num_ext[i]);
            instCV.add(cvrp_dir_localidad[i]);
            instCV.add(cvrp_dir_municipio[i]);
            instCV.add(cvrp_dir_estado[i]);
            instCV.add(cvrp_telefono[i]);
            instCV.add(cvrp_relacion[i]);
            instCV.add(cvrp_tiempo_conociendo[i]);
            instCV.add(cvrp_tipo[i]);
            
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    //</editor-fold>
}
