package bean;

import extra.ConexionBD;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Alumno
 */
public class CursoComprado {
    
    private int id;
    private Curso curso;
    private Reclutador cuenta;
    private Date fecha;

    public CursoComprado(Curso curso, Reclutador cuenta) {
        this.curso = curso;
        this.cuenta = cuenta;
    }
    
    public CursoComprado(int id, Curso curso, Reclutador cuenta, Date fecha) {
        this.id = id;
        this.curso = curso;
        this.cuenta = cuenta;
        this.fecha = fecha;
    }

    public CursoComprado(Curso curso, Reclutador cuenta, Date fecha) {
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

    public Reclutador getCuenta() {
        return cuenta;
    }

    public void setCuenta(Reclutador cuenta) {
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
    
    
}
