package bean;

import extra.ConexionBD;
import java.util.ArrayList;
import java.util.Date;

public class Movimiento {
    private int id;
    private Tarjeta tarjeta;
    private String descripcion;
    private Date fecha;
    private double costo;

    public Movimiento(int id, Tarjeta tarjeta, String descripcion, Date fecha, double costo) {
        this.id = id;
        this.tarjeta = tarjeta;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.costo = costo;
    }

    public Movimiento(Tarjeta tarjeta, String descripcion, Date fecha, double costo) {
        this.tarjeta = tarjeta;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    public static void registrar(Tarjeta tarj, String desc, double costo) {
        ConexionBD objCBD = new ConexionBD("banco");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO movimiento VALUES (null, ?, ?, NOW(), ?)");
        instBD.add(tarj.getId());
        instBD.add(desc);
        instBD.add(costo);
        objCBD.ejecutarABC(instBD);
        
    }
    
}
