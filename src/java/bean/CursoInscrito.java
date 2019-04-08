package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class CursoInscrito {
    
    private int idCI;
    private Curso curso;
    private Cuenta cuenta;
    private Date fecha;

    public CursoInscrito(int idCI, Curso curso, Cuenta cuenta, Date fecha) {
        this.idCI = idCI;
        this.curso = curso;
        this.cuenta = cuenta;
        this.fecha = fecha;
    }

    public CursoInscrito(Curso curso, Cuenta cuenta, Date fecha) {
        this.curso = curso;
        this.cuenta = cuenta;
        this.fecha = fecha;
    }

    public CursoInscrito(Curso curso, Cuenta cuenta) {
        this.curso = curso;
        this.cuenta = cuenta;
    }

    public int getIdCI() {
        return idCI;
    }

    public void setIdCI(int idCI) {
        this.idCI = idCI;
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
    
    
    public boolean Inscrito(){
        
        boolean inscrito = false;
        
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList insBD = new ArrayList();
        insBD.add("INSERT INTO curso_inscrito VALUES(null, ?, ?, now())");
        insBD.add(curso.getIdCurso());
        insBD.add(cuenta.getId());
        
        int inscribir = objCBD.ejecutarABC(insBD);
        
        if(inscribir > 0){
            inscrito = true;
        }
                
         return inscrito;
    }
    
    public static ArrayList<CursoInscrito> ObtenerCI(int id) {
    
        ArrayList<CursoInscrito> cursosIns = new ArrayList<>();
        try {
            
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * from curso_inscrito WHERE cuin_cuenta = ? ");
            instBD.add(id);
            
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
               CursoInscrito ci= new CursoInscrito(rs.getInt("id_curso_inscrito"), Curso.obtenerCurso(rs.getInt("cuin_curso")), Empleador.obtenerCuenta(rs.getInt("cuin_cuenta")), rs.getDate("cuin_fecha_insc"));
               cursosIns.add(ci);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CursoComprado.class.getName()).log(Level.SEVERE, null, ex);
        }
            return cursosIns;
        
    }
    
    public static CursoInscrito ObtenerCurso(int id) {
        CursoInscrito cursosIns = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * from curso_inscrito WHERE id_curso_inscrito = ? ");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
                cursosIns= new CursoInscrito(rs.getInt("id_curso_inscrito"), Curso.obtenerCurso(rs.getInt("cuin_curso")), Empleador.obtenerCuenta(rs.getInt("cuin_cuenta")), rs.getDate("cuin_fecha_insc"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CursoInscrito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cursosIns;
    }
    
}
