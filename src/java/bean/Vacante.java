package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vacante {
    private int id;
    private String titulo;
    private String detalles;
    private String requisitos;
    private double paga;
    private String tipoPaga;
    private int estado;
    private Categoria categoria;
    private PlanComprado compra;
    private Date fecha;
    private String descripcion;
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Vacante(int id, String titulo, String detalles, String requisitos, double paga, String tipoPaga, int estado, Categoria categoria, PlanComprado compra, Date fecha) {
        this.id = id;
        this.titulo = titulo;
        this.detalles = detalles;
        this.requisitos = requisitos;
        this.paga = paga;
        this.tipoPaga = tipoPaga;
        this.estado = estado;
        this.categoria = categoria;
        this.compra = compra;
        this.fecha = fecha;
    }

    public Vacante(String titulo, String detalles, String requisitos, double paga, String tipoPaga, Categoria categoria, PlanComprado compra) {
        this.titulo = titulo;
        this.detalles = detalles;
        this.requisitos = requisitos;
        this.paga = paga;
        this.tipoPaga = tipoPaga;
        this.categoria = categoria;
        this.compra = compra;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public double getPaga() {
        return paga;
    }

    public void setPaga(double paga) {
        this.paga = paga;
    }

    public String getTipoPaga() {
        return tipoPaga;
    }

    public String getTipoPagaS() {
        switch (tipoPaga) {
            case "S": return "Semanal"; 
            case "Q": return "Quincenal"; 
            case "M": return "Mensual"; 
            default: return tipoPaga; 
        }
    }

    public void setTipoPaga(String tipoPaga) {
        this.tipoPaga = tipoPaga;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public PlanComprado getCompra() {
        return compra;
    }

    public void setCompra(PlanComprado compra) {
        this.compra = compra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos">
    public static ArrayList<Vacante> obtenerVacantes(String titulo, String zona, int categoria) {
        ArrayList<Vacante> vacantes = new ArrayList();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            String query = "SELECT * FROM vacante WHERE vaca_estado = 1";
            
            if (!titulo.trim().isEmpty() || !zona.trim().isEmpty() || categoria != 0) {
                String filter = "";
                query += " AND (";
                if (!titulo.trim().isEmpty()) filter += " vaca_titulo LIKE '%" + titulo + "%'";
                /* if (!zona.trim().isEmpty()) {
                    filter += (!filter.isEmpty() ? " OR" : "") + " zona ...";
                } */
                if (categoria != 0) {
                    filter += (!filter.isEmpty() ? " OR" : "") + " vaca_categoria = " + categoria;
                }
                filter += ")";
                query += filter;
            }
            query += " ORDER BY id_vacante DESC";
            
            instBD.add(query);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Vacante v = new Vacante(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getInt(9), Categoria.obtenerCategoria(rs.getInt(2)), PlanComprado.obtenerPlanComprado(rs.getInt(3)), rs.getDate(10));
                vacantes.add(v);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vacantes;
    }
    
    public static Vacante obtenerVacante(int id) {
        Vacante vacante = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM vacante WHERE id_vacante = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                vacante = new Vacante(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getInt(9), Categoria.obtenerCategoria(rs.getInt(2)), PlanComprado.obtenerPlanComprado(rs.getInt(3)), rs.getDate(10));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vacante;
    }
     
     public boolean agregarVa(){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList(); 
        instBD.add("INSERT INTO vacante VALUES(null, ?, ?, ?, ?, ?, ?, ?, 1, NOW())");
        instBD.add(categoria.getId());
        System.out.println(categoria.getId());
        instBD.add(compra.getId());
        System.out.println(compra.getId());
        instBD.add(titulo);
        System.out.println(titulo);  
        instBD.add(detalles); 
        System.out.println(detalles);
        instBD.add(requisitos);
        System.out.println(requisitos); 
        instBD.add(paga);
        System.out.println(paga);
        instBD.add(tipoPaga);
        System.out.println(tipoPaga);
        //instBD.add(estado);
        //System.out.println(estado);
        int registrado = objCBD.ejecutarABC(instBD);
        
        if(registrado > 0){
            id = objCBD.ultimoId(); //una vez registrado se recupera y guardo jeje
            return  true;
        }
        
        return false; 
    }
    
    public static ArrayList<Vacante> obtenerVacantesReclutador(int reclutador) {
        ArrayList<Vacante> vacantes = new ArrayList(); 
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM vacante INNER JOIN plan_comprado ON vaca_compra = id_plan_comprado WHERE plco_cuenta = ?");
            instBD.add(reclutador);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Vacante v = new Vacante(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getInt(9), Categoria.obtenerCategoria(rs.getInt(2)), PlanComprado.obtenerPlanComprado(rs.getInt(3)), rs.getDate(10));
                vacantes.add(v);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vacantes;
    }
    
    public static ArrayList<Vacante> obtenerVacantesDelPlan(int plan) {
        ArrayList<Vacante> vacantes = new ArrayList(); 
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM vacante WHERE vaca_compra = ?");
            instBD.add(plan);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Vacante v = new Vacante(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getInt(9), Categoria.obtenerCategoria(rs.getInt(2)), PlanComprado.obtenerPlanComprado(rs.getInt(3)), rs.getDate(10));
                vacantes.add(v);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vacantes;
    }
    //</editor-fold>
}
