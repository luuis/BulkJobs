package bean;

import extra.ConexionBD;
import java.util.ArrayList;
import java.util.Date;

public class Postulacion {
    private int id;
    private Vacante vacante;
    private Empleador cuenta;
    private Curriculum curriculum;
    private String comentario;
    private Date fecha;
    private int estado;
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Postulacion(int id, Vacante vacante, Empleador cuenta, Curriculum curriculum, String comentario, Date fecha, int estado) {
        this.id = id;
        this.vacante = vacante;
        this.cuenta = cuenta;
        this.curriculum = curriculum;
        this.comentario = comentario;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Postulacion(Vacante vacante, Empleador cuenta, Curriculum curriculum, String comentario, Date fecha, int estado) {
        this.vacante = vacante;
        this.cuenta = cuenta;
        this.curriculum = curriculum;
        this.comentario = comentario;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Postulacion(Vacante vacante, Empleador cuenta, Curriculum curriculum, String comentario) {
        this.vacante = vacante;
        this.cuenta = cuenta;
        this.curriculum = curriculum;
        this.comentario = comentario;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vacante getVacante() {
        return vacante;
    }

    public void setVacante(Vacante vacante) {
        this.vacante = vacante;
    }

    public Empleador getCuenta() {
        return cuenta;
    }

    public void setCuenta(Empleador cuenta) {
        this.cuenta = cuenta;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public void ingresar() {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO postulacion VALUES (null, ?, ?, ?, ?, NOW(), 0)");
        instBD.add(vacante.getId());
        instBD.add(cuenta.getId());
        instBD.add(curriculum.getId());
        instBD.add(comentario);
        objCBD.ejecutarABC(instBD);
    }
    //</editor-fold>
    
}
