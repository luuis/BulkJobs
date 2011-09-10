package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Alumno
 */
public class PlanP {
    
    private int idPlanP;
    private String nombre;
    private String desc;
    private double precio;
    private int tiempo; 

    public PlanP(int idPlanP, String nombre, String desc, double precio, int tiempo) {
        this.idPlanP = idPlanP;
        this.nombre = nombre;
        this.desc = desc;
        this.precio = precio;
        this.tiempo = tiempo;
    }

    public PlanP() {
    }

    public int getIdPlanP() {
        return idPlanP;
    }

    public void setIdPlanP(int idPlanP) {
        this.idPlanP = idPlanP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
    public boolean RegistrarP(){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO plan_publ values(null, ?,?,?,?)");
        instBD.add(nombre);
        instBD.add(desc);
        instBD.add(precio);
        instBD.add(tiempo);
        int registrarP = objCBD.ejecutarABC(instBD);
        if(registrarP > 0){
            return true;
        }
        return false;
    }
    
    public static ArrayList obtenerPlanesP(){
        
            ArrayList<PlanP> planesp = new ArrayList();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            PlanP planp = null;
            instBD.add("SELECT * from plan_publ");
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
                planp= new PlanP(rs.getInt("id_plan_publ"), rs.getString("plpu_nombre"), rs.getString("plpu_descripcion"), rs.getDouble("plpu_precio"), rs.getInt("plpu_tiempo"));
                planesp.add(planp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         return planesp;
    }
    
    public static PlanP obtenerPlanP(int id){
        
            PlanP planP = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * from plan_publ WHERE id_plan_publ = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
                planP = new PlanP(rs.getInt("id_plan_publ"), rs.getString("plpu_nombre"), rs.getString("plpu_descripcion"), rs.getDouble("plpu_precio"), rs.getInt("plpu_tiempo"));
    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         return planP;
    }
    
    public static boolean eliminarPP(int id){
     
           ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
           ArrayList instBD = new ArrayList();
           instBD.add("DELETE FROM plan_publ WHERE id_plan_publ =?");
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
        instBD.add("UPDATE plan_publ SET plpu_nombre=?, plpu_descripcion=?, plpu_precio=?, plpu_tiempo=?  WHERE id_plan_publ=?");
        instBD.add(nombre);
        instBD.add(desc);
        instBD.add(precio);
        instBD.add(tiempo);
        instBD.add(id);
        int editar = objCBD.ejecutarABC(instBD);
        if(editar > 0){
            return true;
        }
        return false;
        
    }
    
    
}
