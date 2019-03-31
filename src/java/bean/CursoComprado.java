package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Alumno
 */
public class CursoComprado {
    
    private int id;
    private Curso curso;
    private Cuenta cuenta;
    private Date fecha;

    public CursoComprado(Curso curso, Cuenta cuenta) {
        this.curso = curso;
        this.cuenta = cuenta;
    }
    
    public CursoComprado(int id, Curso curso, Cuenta cuenta, Date fecha) {
        this.id = id;
        this.curso = curso;
        this.cuenta = cuenta;
        this.fecha = fecha;
    }

    public CursoComprado(Curso curso, Cuenta cuenta, Date fecha) {
        this.curso = curso;
        this.cuenta = cuenta;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public boolean registrarC(){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO  curso_comprado values(null, ?, ?, now())");
        instBD.add(curso.getIdCurso());
        instBD.add(cuenta.getId());
        int registrar = objCBD.ejecutarABC(instBD);
        if(registrar > 0){
            return true;
        }
        return false;
    }
    
    public static ArrayList<CursoComprado> ObtenerC(int id){
        ArrayList<CursoComprado> cursosComprados = new ArrayList<>();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * from curso_comprado WHERE cuco_cuenta = ? ");
            instBD.add(id);
            
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
                CursoComprado cc = new CursoComprado(rs.getInt("id_curso_comprado"), Curso.obtenerCurso(rs.getInt("cuco_curso")), Cuenta.obtenerCuenta(rs.getInt("cuco_cuenta")), rs.getDate("cuco_fecha_hora"));
                cursosComprados.add(cc);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cursosComprados;
    }
    
    
    
    public static CursoComprado obtenerCurso(int id){ //Obtengo el cursoC apartir del id 
        CursoComprado cc = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * from curso_comprado WHERE id_curso_comprado = ? ");
            instBD.add(id);
            
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
               cc = new CursoComprado(rs.getInt("id_curso_comprado"), Curso.obtenerCurso(rs.getInt("cuco_curso")), Cuenta.obtenerCuenta(rs.getInt("cuco_cuenta")), rs.getDate("cuco_fecha_hora"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cc;
    }
    
}
