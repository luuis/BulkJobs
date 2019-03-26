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
 * @author Dani
 */
public class Plan {
    
   private int idPlan;
   private String nombre;
   private String descripcion;
   private double precio;
   private int vacantes;
   private int tiempo;

    public Plan(int idPlan, String nombre, String descripcion, double precio, int vacantes, int tiempo) {
        this.idPlan = idPlan;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.vacantes = vacantes;
        this.tiempo = tiempo;
    }

    public Plan(String nombre, String descripcion, double precio, int vacantes, int tiempo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.vacantes = vacantes;
        this.tiempo = tiempo;
    }
   
    
    
    
    /**
     * @return the idPlan
     */ 
    public int getIdPlan() {
        return idPlan;
    }

    /**
     * @param idPlan the idPlan to set
     */
    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    /**
     * @return the vacantes
     */
    public int getVacantes() {
        return vacantes;
    }

    /**
     * @param vacantes the vacantes to set
     */
    public void setVacantes(int vacantes) {
        this.vacantes = vacantes;
    }

    /**
     * @return the tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    } 
   
    public boolean registrar(){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO plan VALUES(null, ?, ?, ?, ?, ?)");
        instBD.add(nombre);
        instBD.add(descripcion);
        instBD.add(precio);
        instBD.add(vacantes); 
        instBD.add(tiempo);
        
        int registrado = objCBD.ejecutarABC(instBD);
        
        if(registrado > 0){
            return  true;
        }
        
        return false;
        
    }
   
    public static ArrayList obtenerPlanes(){
        ArrayList<Plan> planes = new ArrayList<>();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();

            Plan plan = null;
            instBD.add("SELECT * FROM plan");
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                plan = new Plan(rs.getInt("id_plan"), rs.getString("plan_nombre"), rs.getString("plan_descripcion"), rs.getDouble("plan_precio"), rs.getInt("plan_num_vacantes"), rs.getInt("plan_tiempo"));
                planes.add(plan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
        }

        return planes; 
    }
    
    public static boolean eliminar(int id){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("DELETE FROM plan WHERE id_plan = ?");
        instBD.add(id);
        int eli = objCBD.ejecutarABC(instBD);
        
        if(eli > 0){
            return  true;
        }
        
        return false;
    }
    
    public static Plan obtenerPlan(int id){
            Plan plan = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM plan WHERE id_plan = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                plan = new Plan(rs.getInt("id_plan"), rs.getString("plan_nombre"), rs.getString("plan_descripcion"), rs.getDouble("plan_precio"), rs.getInt("plan_num_vacantes"), rs.getInt("plan_tiempo"));
     
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
        }

        return plan; 
    }
    
   public static boolean editar(double precio, int id){
       ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("UPDATE plan SET plan_precio = ? WHERE id_plan = ?");
        instBD.add(precio);
        System.out.println(precio);
        System.out.println(id);
        instBD.add(id);
        
        int editado = objCBD.ejecutarABC(instBD);
        
        if(editado > 0){
            return  true;
        }
        
        return false;
       
   }
}
