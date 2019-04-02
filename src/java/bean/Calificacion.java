package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Calificacion {
    
    private int id;
    private float caliC;
    private CursoInscrito caliCu;
    private Empleador caliCa;
    private Date fecha;
    private String comen;

    public Calificacion(int id, float caliC, CursoInscrito caliCu, Empleador caliCa, Date fecha, String comen) {
        this.id = id;
        this.caliC = caliC;
        this.caliCu = caliCu;
        this.caliCa = caliCa;
        this.fecha = fecha;
        this.comen = comen;
    }

    public Calificacion(float caliC, CursoInscrito caliCu, Empleador caliCa, Date fecha, String comen) {
        this.caliC = caliC;
        this.caliCu = caliCu;
        this.caliCa = caliCa;
        this.fecha = fecha;
        this.comen = comen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCaliC() {
        return caliC;
    }

    public void setCaliC(float caliC) {
        this.caliC = caliC;
    }

    public CursoInscrito getCaliCu() {
        return caliCu;
    }

    public void setCaliCu(CursoInscrito caliCu) {
        this.caliCu = caliCu;
    }

    public Empleador getCaliCa() {
        return caliCa;
    }

    public void setCaliCa(Empleador caliCa) {
        this.caliCa = caliCa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComen() {
        return comen;
    }

    public void setComen(String comen) {
        this.comen = comen;
    }


   
    
    public static float obtenerPromC(int id){
       
       float prom = 0.0f; 
       try {
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList insBD = new ArrayList();
           insBD.add("SELECT AVG(cali_calificacion) AS PROMEDIO FROM CALIFICACION ");
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

       
   public static float obtenerCalC(int cu, int ca){
       
       float prom = 0.0f;
       try {
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList insBD = new ArrayList();
           insBD.add("SELECT cali_calificacion FROM CALIFICACION WHERE cali_curso = ? and  cali_cuenta = ?");
           insBD.add(cu);
           insBD.add(ca);
           objCBD.consultar(insBD);
           ResultSet rs = objCBD.getCdr();
           while(rs.next()){
               
           prom = rs.getFloat("cali_calificacion");
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       
       return prom;
   }
    
   public static ArrayList<Calificacion> obtenerEvaluacionesC (){
   ArrayList<Calificacion> calificaciones = new ArrayList<>();
       try {
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList insBD = new ArrayList();
           insBD.add("SELECT * FROM CALIFICACION");
           objCBD.consultar(insBD);
           ResultSet rs = objCBD.getCdr();
           while(rs.next()){
               
               Calificacion cal = new Calificacion(rs.getInt("id_calificacion"), 
                      rs.getFloat("cali_calificacion"),
                       CursoInscrito.ObtenerCurso(rs.getInt("cali_curso")),
                       Empleador.obtenerCuenta(rs.getInt("cali_cuenta")),
                       rs.getDate("cali_fecha_hora"),
                       rs.getString("cali_comentario"));
               calificaciones.add(cal);
                    /*   Reclutador.obtenerCuenta(rs.getInt("eval_reclutador")), rs.getFloat("eval_calificacion"), rs.getString("eval_comentario"));
               evaluaciones.add(ev);*/
                       
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return calificaciones;
   }
    
   public static ArrayList<Calificacion> obtenerEvaluacionesC2 (String id){
   ArrayList<Calificacion> calificaciones = new ArrayList<>();
       try {
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList insBD = new ArrayList();
           insBD.add("SELECT * FROM CALIFICACION WHERE cali_curso = ?");
           insBD.add(id);
           System.out.println(id);
           objCBD.consultar(insBD);
           ResultSet rs = objCBD.getCdr();
           while(rs.next()){
               
               Calificacion cal = new Calificacion(rs.getInt("id_calificacion"), 
                      rs.getFloat("cali_calificacion"),
                       CursoInscrito.ObtenerCurso(rs.getInt("cali_curso")),
                       Empleador.obtenerCuenta(rs.getInt("cali_cuenta")),
                       rs.getDate("cali_fecha_hora"),
                       rs.getString("cali_comentario"));
               
               calificaciones.add(cal);  //Calificaciones es el array y llamo a cal para que se vean los coemntarios 
                       
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return calificaciones;
   }
   
    
    
}
 