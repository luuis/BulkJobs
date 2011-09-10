package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tarjeta {
    private int id;
    private Banco banco;
    private long numero;
    private int ccv;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private int mesExp;
    private int anioExp;
    private double saldo;

    public Tarjeta(int id, Banco banco, long numero, int ccv, String nombre, String apPaterno, String apMaterno, int mesExp, int anioExp, double saldo) {
        this.id = id;
        this.banco = banco;
        this.numero = numero;
        this.ccv = ccv;
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.mesExp = mesExp;
        this.anioExp = anioExp;
        this.saldo = saldo;
    }

    public Tarjeta(Banco banco, long numero, int ccv, String nombre, String apPaterno, String apMaterno, int mesExp, int anioExp, double saldo) {
        this.banco = banco;
        this.numero = numero;
        this.ccv = ccv;
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.mesExp = mesExp;
        this.anioExp = anioExp;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public int getMesExp() {
        return mesExp;
    }

    public void setMesExp(int mesExp) {
        this.mesExp = mesExp;
    }

    public int getAnioExp() {
        return anioExp;
    }

    public void setAnioExp(int anioExp) {
        this.anioExp = anioExp;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public static Tarjeta obtenerTarjeta(int id) {
        Tarjeta tarjeta = null;
        
        try {
            ConexionBD objCBD = new ConexionBD("banco");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM tarjeta WHERE id_tarjeta = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                tarjeta = new Tarjeta(rs.getInt("id_tarjeta"), Banco.obtenerBanco(rs.getInt("tarj_banco")), rs.getLong("tarj_numero"),
                        rs.getInt("tarj_ccv"), rs.getString("tarj_nombre"), rs.getString("tarj_app"),
                        rs.getString("tarj_apm"), rs.getInt("tarj_exp_mes"), rs.getInt("tarj_exp_anio"), rs.getDouble("tarj_saldo"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tarjeta;
    }
    
    public static Tarjeta obtenerTarjeta(long num) {
        Tarjeta tarjeta = null;
        try {
            ConexionBD objCBD = new ConexionBD("banco");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM tarjeta WHERE tarj_numero = ?");
            instBD.add(num);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                tarjeta = new Tarjeta(rs.getInt("id_tarjeta"), Banco.obtenerBanco(rs.getInt("tarj_banco")), rs.getLong("tarj_numero"),
                        rs.getInt("tarj_ccv"), rs.getString("tarj_nombre"), rs.getString("tarj_app"),
                        rs.getString("tarj_apm"), rs.getInt("tarj_exp_mes"), rs.getInt("tarj_exp_anio"), rs.getDouble("tarj_saldo"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tarjeta;
    }
    
    public void quitarSaldo(double s){
        
        ConexionBD objCBD = new ConexionBD("banco");
            ArrayList instBD = new ArrayList();
            instBD.add("UPDATE tarjeta SET tarj_saldo = tarj_saldo - ? WHERE id_tarjeta = ?");
            instBD.add(s);
            instBD.add(id);
            objCBD.ejecutarABC(instBD);
            
                    
        
    }
    
}
