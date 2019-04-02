package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class PlanPComprado {
    
    private int id;
    private PlanP planc;
    private Reclutador ppcue;
    private String vinculo;
    private Date fecha;
    private Date fechaF;

    public PlanPComprado(int id, PlanP planc, Reclutador ppcue, String vinculo, Date fecha, Date fechaF) {
        this.id = id;
        this.planc = planc;
        this.ppcue = ppcue;
        this.fecha = fecha;
        this.fechaF = fechaF;
        this.vinculo = vinculo;
    }

    public PlanPComprado(PlanP planc, Reclutador ppcue, String vinculo, Date fecha, Date fechaF) {
        this.planc = planc;
        this.ppcue = ppcue;
        this.fecha = fecha;
        this.fechaF = fechaF;
        this.vinculo = vinculo;
    }

    public PlanPComprado(PlanP planc, Reclutador ppcue, Date fechaF) {
        this.planc = planc;
        this.ppcue = ppcue;
        this.fechaF = fechaF;
        this.vinculo = vinculo;
    }

    public PlanPComprado(int id, String vinculo) {
        this.id = id;
        this.vinculo = vinculo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlanP getPlanc() {
        return planc;
    }

    public void setPlanc(PlanP planc) {
        this.planc = planc;
    }

    public Reclutador getPpcue() {
        return ppcue;
    }

    public void setPpcue(Reclutador ppcue) {
        this.ppcue = ppcue;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }
    
    
    
    public boolean registrarPPC(){
        
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO plan_publ_comprado values(null, ?, ?, now(), ?)"); 
        instBD.add(planc.getIdPlanP());
        instBD.add(ppcue.getId());
        
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        
        instBD.add(sdf.format(fechaF));
        
        int registrarP = objCBD.ejecutarABC(instBD);
        if(registrarP > 0){
            return true;
        }
        
        return false;
        
    }
    
    public static PlanPComprado obtenerP(){
         PlanPComprado ppc = null;
         
        try {
           
            
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM plan_publ_comprado ORDER BY RAND() LIMIT 1 ");
            
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while(rs.next()){
                ppc = new PlanPComprado(rs.getInt("id_plan_publ_comprado"), PlanP.obtenerPlanP(rs.getInt("ppco_plan")), Reclutador.obtenerCuenta(rs.getInt("ppco_cuenta")), rs.getString("ppco_vinculo"), rs.getDate("ppco_fecha"), rs.getDate("ppco_fecha_limite"));
                
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PlanPComprado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ppc;
    }

     
    public static ArrayList<PlanPComprado> obtenerPlanCompradoRecl(int cuenta){
        ArrayList<PlanPComprado> planComprado = new ArrayList<>();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM plan_publ_comprado WHERE ppco_cuenta = ?");
            instBD.add(cuenta);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                PlanPComprado pp = new PlanPComprado(rs.getInt(1),
                        PlanP.obtenerPlanP(rs.getInt(2)),
                        Reclutador.obtenerCuenta(rs.getInt(3)), rs.getString(4), rs.getDate(5), rs.getDate(6));
                planComprado.add(pp);
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return planComprado;
    }
    
    public static PlanPComprado obtenerPlanComprado(int id){
        PlanPComprado planComprado = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM plan_publ_comprado WHERE id_plan_publ_comprado = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                planComprado = new PlanPComprado(rs.getInt(1), PlanP.obtenerPlanP(rs.getInt(2)),
                        Reclutador.obtenerCuenta(rs.getInt(3)), rs.getString(4), rs.getDate(5), rs.getDate(6));
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return planComprado;
    }
    
    public static boolean renovarPlanP(int id, Date fechaLimite){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("UPDATE plan_publ_comprado SET ppco_fecha_limite = ? WHERE id_plan_publ_comprado = ?");
        
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        instBD.add(sdf.format(fechaLimite));
        instBD.add(id);
        
        int agregar = objCBD.ejecutarABC(instBD);
        
        if(agregar > 0){
            return true;
        }
        return false; 
    }
    
     public  boolean editarPC(){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("UPDATE plan_publ_comprado SET ppco_vinculo = ? WHERE id_plan_publ_comprado = ?");
        instBD.add(vinculo);
        instBD.add(id);
        
        int editado = objCBD.ejecutarABC(instBD);
        
        if(editado > 0){
            return  true;
        }
        return false;
   }
    
}
