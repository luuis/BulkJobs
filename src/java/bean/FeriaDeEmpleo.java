package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FeriaDeEmpleo {
    
    private int idFeria;
    private String nombre;
    private String fecha; 

    public FeriaDeEmpleo(int idFeria, String nombre, String fecha) {
        this.idFeria = idFeria;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public FeriaDeEmpleo(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public FeriaDeEmpleo(String nombre) {
        this.nombre = nombre;
    }
    
    

    public FeriaDeEmpleo() {
    }

    public int getIdFeria() {
        return idFeria;
    }

    public void setIdFeria(int idFeria) {
        this.idFeria = idFeria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public boolean registrar(){
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = new ArrayList();
        instBD.add("INSERT INTO feria_de_empleo VALUES(null, ?, now())");
        instBD.add(nombre);
         
        int registrado = objCBD.ejecutarABC(instBD);
        
        if(registrado > 0){
             
            idFeria = objCBD.ultimoId(); //una vez registrado se recupera y guardo jeje
            return  true; 
        } 
          
        return false;
    }
    
    public static FeriaDeEmpleo obtenerFeria(){
        FeriaDeEmpleo feria = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM feria_de_empleo ORDER BY id_feria desc LIMIT 1");
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                feria = new FeriaDeEmpleo(rs.getInt("id_feria"), rs.getString("feri_nombre"), rs.getString("feri_fecha")); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
        }

        return feria;
        
        
        
    }
   
    
}
