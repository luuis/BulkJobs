package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author CB09
 */
public class Evaluacion {
    
    private int id;

    public Evaluacion(Empleador empleador, Reclutador reclutador, float evaluacion, String comen) {
        this.empleador = empleador;
        this.reclutador = reclutador;
        this.evaluacion = evaluacion;
        this.comen = comen;
    }
    private Empleador empleador;
    private Reclutador reclutador;
    private float evaluacion;
    private String comen; 

    public Evaluacion(int id, Empleador empleador, Reclutador reclutador, float evaluacion, String comen) {
        this.id = id;
        this.empleador = empleador;
        this.reclutador = reclutador;
        this.evaluacion = evaluacion;
        this.comen = comen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleador getEmpleador() {
        return empleador;
    }

    public void setEmpleador(Empleador empleador) {
        this.empleador = empleador;
    }

    public Reclutador getReclutador() {
        return reclutador;
    }

    public void setReclutador(Reclutador reclutador) {
        this.reclutador = reclutador;
    }

    public float getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(float evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getComen() {
        return comen;
    }

    public void setComen(String comen) {
        this.comen = comen;
    }

   public static float obtenerProm(int id){
       
       float prom = 0.0f;
       try {
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList insBD = new ArrayList();
           insBD.add("SELECT AVG(EVAL_CALIFICACION) AS PROMEDIO FROM EVALUACION ");
           objCBD.consultar(insBD);
           ResultSet rs = objCBD.getCdr();
           while(rs.next()){
               
           prom = rs.getFloat("PROMEDIO");
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       
       return prom;
   }
   
   
   public static float obtenerCal(int idR, int idE){
       
       float prom = 0.0f;
       try {
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList insBD = new ArrayList();
           insBD.add("SELECT EVAL_CALIFICACION FROM EVALUACION WHERE eval_empleador = ? and eval_reclutador = ?");
           insBD.add(idE);
           insBD.add(idR);
           objCBD.consultar(insBD);
           ResultSet rs = objCBD.getCdr();
           while(rs.next()){
               
           prom = rs.getFloat("EVAL_CALIFICACION");
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       
       return prom;
   }
   
   public static ArrayList<Evaluacion> obtenerEvaluaciones (){
   ArrayList<Evaluacion> evaluaciones = new ArrayList<>();
       try {
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList insBD = new ArrayList();
           insBD.add("SELECT * FROM EVALUACION");
           objCBD.consultar(insBD);
           ResultSet rs = objCBD.getCdr();
           while(rs.next()){
               Evaluacion ev = new Evaluacion(rs.getInt("id_evaluacion"), Empleador.obtenerCuenta(rs.getInt("eval_empleador")),
                       Reclutador.obtenerCuenta(rs.getInt("eval_reclutador")), rs.getFloat("eval_calificacion"), rs.getString("eval_comentario"));
               evaluaciones.add(ev);
                       
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return evaluaciones;
   }
   
    
    
}

