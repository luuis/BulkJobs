package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class Curso {
    
    private int idCurso;
    private int cuenta;
    private String nombre;
    private String desc;
    private double precio;

    public Curso(int idCurso, int cuenta, String nombre, String desc, double precio) {
        this.idCurso = idCurso;
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.desc = desc;
        this.precio = precio;
    }

    public Curso(int cuenta, String nombre, String desc, double precio) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.desc = desc;
        this.precio = precio;
    }

    
    
    /**
     * @return the idCurso
     */
    public int getIdCurso() {
        return idCurso;
    }

    /**
     * @param idCurso the idCurso to set
     */
    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    /**
     * @return the cuenta
     */
    public int getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
    
    public boolean Registrar(){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO curso values(null, ?, ?, ?, ?)");
        instBD.add(cuenta);
        instBD.add(nombre);
        instBD.add(desc);
        instBD.add(precio);
        int registrar = objCBD.ejecutarABC(instBD);
        if(registrar > 0){
            return true;
        }
        return false;
    }
    
    public static ArrayList obtenerCursos(){
        
            ArrayList<Curso> cursos = new ArrayList();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            Curso curso= null;
            instBD.add("SELECT * from curso");
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
                curso = new Curso(rs.getInt("id_curso"), rs.getInt("curs_cuenta"), rs.getString("curs_nombre"), rs.getString("curs_descripcion"), rs.getDouble("curs_precio"));
                cursos.add(curso);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         return cursos;
    }
    
    
        public static Curso obtenerCurso(int id){
        
            Curso curso= null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * from curso WHERE id_curso = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
                curso = new Curso(rs.getInt("id_curso"), rs.getInt("curs_cuenta"), rs.getString("curs_nombre"), rs.getString("curs_descripcion"), rs.getDouble("curs_precio"));
    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         return curso;
    }
    
    
    public static boolean eliminar(int id){
     
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList instBD = new ArrayList();
           instBD.add("DELETE FROM curso WHERE id_curso=?");
           instBD.add(id);
           int eliminar = objCBD.ejecutarABC(instBD);
           if(eliminar > 0){
               return true;
           }
           return false;
    }
    
    public  boolean editar(int id){
        
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("UPDATE curso SET curs_cuenta=?, curs_nombre=?, curs_descripcion=?, curs_precio=? WHERE id_curso=?");
        instBD.add(cuenta);
        instBD.add(nombre);
        instBD.add(desc);
        instBD.add(precio);
        instBD.add(id);
        int editar = objCBD.ejecutarABC(instBD);
        if(editar > 0){
            return true;
        }
        return false;
        
    }
    
    
    
}
