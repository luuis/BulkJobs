package bean;

import extra.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Categoria {
    private int id;
    private String nombre;
    private ArrayList<Categoria> subcategorias;

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Categoria(int id, String nombre, ArrayList<Categoria> subcategorias) {
        this.id = id;
        this.nombre = nombre;
        this.subcategorias = subcategorias;
    }

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Categoria> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(ArrayList<Categoria> subcategorias) {
        this.subcategorias = subcategorias;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public static ArrayList<Categoria> obtenerCategorias() {
        ArrayList<Categoria> categorias = new ArrayList();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM categoria WHERE cate_subcategoria IS NULL");
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Categoria c = new Categoria(rs.getInt(1), rs.getString(2),
                        obtenerSubcategorias(rs.getInt(1)));
                categorias.add(c);
            }
            rs.close();
            objCBD.cerrarConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categorias;
    }
    public static ArrayList<Categoria> obtenerSubcategorias(int id) {
        ArrayList<Categoria> subcategorias = new ArrayList();
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instBD = null;
        ResultSet rs = null;
        try {
            instBD = new ArrayList();
            instBD.add("SELECT * FROM categoria WHERE cate_subcategoria = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            rs = objCBD.getCdr();
            while (rs.next()) {
                Categoria c = new Categoria(rs.getInt(1), rs.getString(2));
                subcategorias.add(c);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        objCBD.cerrarConexion();
        return subcategorias;
    }
    
    public static Categoria obtenerCategoria(int id) {
        Categoria categoria = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM categoria WHERE id_categoria = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                categoria = new Categoria(rs.getInt(1), rs.getString(2),
                        obtenerSubcategorias(rs.getInt(1)));
            }
            rs.close();
            objCBD.cerrarConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categoria;
    }
    //</editor-fold>
}
