package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlanComprado {
    private int id;
    private Plan plan;
    private Reclutador cuenta;
    private Date fecha;
    private Date fechaLimite;

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public PlanComprado(int id, Plan plan, Reclutador cuenta, Date fecha, Date fechaLimite) {
        this.id = id;
        this.plan = plan;
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.fechaLimite = fechaLimite;
    }

    public PlanComprado(Plan plan, Reclutador cuenta, Date fecha, Date fechaLimite) {
        this.plan = plan;
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.fechaLimite = fechaLimite;
    }

    public PlanComprado(Plan plan, Reclutador cuenta, Date fechaLimite) {
        this.plan = plan;
        this.cuenta = cuenta;
        this.fechaLimite = fechaLimite;
    }
    
     
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
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

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método">
    public static PlanComprado obtenerPlanComprado(int id) {
        PlanComprado planComprado = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM plan_comprado WHERE id_plan_comprado = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr(); 
            while (rs.next()) {
                planComprado = new PlanComprado(rs.getInt(1), Plan.obtenerPlan(rs.getInt(2)), Reclutador.obtenerCuenta(rs.getInt(3)), rs.getDate(4), rs.getDate(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return planComprado;
    }
    
    public boolean registrarC(){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO plan_comprado VALUES (NULL, ?, ?, now(), ?)");
        instBD.add(plan.getIdPlan());
        instBD.add(cuenta.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        
        instBD.add(sdf.format(fechaLimite));
        int registrar = objCBD.ejecutarABC(instBD);
        
        
        if(registrar > 0){
            return true;
        }
        
        return false;
    }
//</editor-fold>
}
