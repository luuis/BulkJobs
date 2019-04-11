package bean;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import extra.ConexionBD;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Curriculum {
    private int id;
    private Empleador cuenta;
    private String archivo;
    private Date fecha;

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Curriculum(int id, Empleador cuenta, String archivo, Date fecha) {
        this.id = id;
        this.cuenta = cuenta;
        this.archivo = archivo;
        this.fecha = fecha;
    }
    
    public Curriculum(Empleador cuenta, String archivo, Date fecha) {
        this.cuenta = cuenta;
        this.archivo = archivo;
        this.fecha = fecha;
    }
    
    public Curriculum(Empleador cuenta) {
        this.cuenta = cuenta;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleador getCuenta() {
        return cuenta;
    }

    public void setCuenta(Empleador cuenta) {
        this.cuenta = cuenta;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos">
    public static ArrayList<Curriculum> obtenerCurriculums(int id) {
        ArrayList<Curriculum> curriculums = new ArrayList();
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM curriculum WHERE curr_cuenta = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                Curriculum c = new Curriculum(rs.getInt(1), Empleador.obtenerCuenta(rs.getInt(2)), rs.getString(3), new Date(rs.getTimestamp(4).getTime()));
                curriculums.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return curriculums;
    }
    
    public static Curriculum obtenerCurriculum(int id) {
        Curriculum curriculum = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM curriculum WHERE curr_cuenta = ? ORDER BY id_curriculum DESC LIMIT 1");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                curriculum = new Curriculum(rs.getInt(1), Empleador.obtenerCuenta(rs.getInt(2)), rs.getString(3), new Date(rs.getTimestamp(4).getTime()));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return curriculum;
    }
    
    public static Curriculum obtenerCurriculum2(int id) {
        Curriculum curriculum = null;
        try {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM curriculum WHERE id_curriculum = ?");
            instBD.add(id);
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            while (rs.next()) {
                curriculum = new Curriculum(rs.getInt(1), Empleador.obtenerCuenta(rs.getInt(2)), rs.getString(3), new Date(rs.getTimestamp(4).getTime()));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return curriculum;
    }
    
    public boolean generarCurriculum() {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList instCV = new ArrayList();
        instCV.add("INSERT INTO curriculum VALUES (null, ?, null, NOW())");
        instCV.add(cuenta.getId());
        
        int insertado = objCBD.ejecutarABC(instCV);
        if (insertado > 0) {
            id = objCBD.ultimoId();
            objCBD.cerrarConexion();
            return true;
        }
        objCBD.cerrarConexion();
        return false; 
    }
    
    public static void generarCVEL(int idCurr, String[] cvel_nombre_empresa, String[] cvel_dir_num_int, String[] cvel_dir_num_ext, String[] cvel_dir_localidad, String[] cvel_dir_municipio, String[] cvel_dir_estado, String[] cvel_telefono, String[] cvel_puesto, String[] cvel_nombre_jefe, String[] cvel_app_jefe, String[] cvel_apm_jefe, String[] cvel_funciones, String[] cvel_anio_inicio, String[] cvel_anio_fin) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<cvel_nombre_empresa.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_experiencia_laboral VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(cvel_nombre_empresa[i]);
            instCV.add(cvel_dir_num_int[i]);
            instCV.add(cvel_dir_num_ext[i]);
            instCV.add(cvel_dir_localidad[i]);
            instCV.add(cvel_dir_municipio[i]);
            instCV.add(cvel_dir_estado[i]);
            instCV.add(cvel_telefono[i]);
            instCV.add(cvel_puesto[i]);
            instCV.add(cvel_nombre_jefe[i]);
            instCV.add(cvel_app_jefe[i]);
            instCV.add(cvel_apm_jefe[i]);
            instCV.add(cvel_funciones[i]);
            instCV.add(cvel_anio_inicio[i]);
            instCV.add(cvel_anio_fin[i]);
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static void generarCVFA(int idCurr, String[] cvfa_nivel, String[] cvfa_nombre_institucion, String[] cvfa_dir_num_int, String[] cvfa_dir_num_ext, String[] cvfa_dir_localidad, String[] cvfa_dir_municipio, String[] cvfa_dir_estado, String[] cvfa_titulo_certificado, String[] cvfa_cedula, String[] cvfa_anio_inicio, String[] cvfa_anio_fin, String[] cvfa_estado) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<cvfa_nivel.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_formacion_academica VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(cvfa_nivel[i]);
            instCV.add(cvfa_nombre_institucion[i]);
            instCV.add(cvfa_dir_num_int[i]);
            instCV.add(cvfa_dir_num_ext[i]);
            instCV.add(cvfa_dir_localidad[i]);
            instCV.add(cvfa_dir_municipio[i]);
            instCV.add(cvfa_dir_estado[i]);
            instCV.add(cvfa_titulo_certificado[i]);
            instCV.add(cvfa_cedula[i]);
            instCV.add(cvfa_anio_inicio[i]);
            instCV.add(cvfa_anio_fin[i]);
            instCV.add(cvfa_estado[i]);
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static void generarCVID(int idCurr, String[] idio_idioma, String[] idio_porcentaje) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<idio_idioma.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_idiomas VALUES (null, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(idio_idioma[i]);
            instCV.add(idio_porcentaje[i]);
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static void generarCVDF(int idCurr, String[] cvdf_nombre, String[] cvdf_app, String[] cvdf_apm, String[] cvdf_parentesco, String[] cvdf_vive, String[] cvdf_finado, String[] cvdf_dir_num_int, String[] cvdf_dir_num_ext, String[] cvdf_dir_localidad, String[] cvdf_dir_municipio, String[] cvdf_dir_estado, String[] cvdf_ocupacion) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<cvdf_nombre.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_datos_familiares VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(cvdf_nombre[i]);
            instCV.add(cvdf_app[i]);
            instCV.add(cvdf_apm[i]);
            instCV.add(cvdf_parentesco[i]);
            instCV.add(cvdf_vive[i]);
            instCV.add(cvdf_finado[i]);
            instCV.add(cvdf_dir_num_int[i]);
            instCV.add(cvdf_dir_num_ext[i]);
            instCV.add(cvdf_dir_localidad[i]);
            instCV.add(cvdf_dir_municipio[i]);
            instCV.add(cvdf_dir_estado[i]);
            instCV.add(cvdf_ocupacion[i]);
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static void generarCVRP(int idCurr, String[] cvrp_nombre, String[] cvrp_app, String[] cvrp_apm, String[] cvrp_dir_num_int, String[] cvrp_dir_num_ext, String[] cvrp_dir_localidad, String[] cvrp_dir_municipio, String[] cvrp_dir_estado, String[] cvrp_telefono, String[] cvrp_relacion, String[] cvrp_tiempo_conociendo, String[] cvrp_tipo) {
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        for (int i=0; i<cvrp_nombre.length; i++) {
            ArrayList instCV = new ArrayList();
            instCV.add("INSERT INTO cv_referencias_personales VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            instCV.add(idCurr);
            instCV.add(cvrp_nombre[i]);
            instCV.add(cvrp_app[i]);
            instCV.add(cvrp_apm[i]);
            instCV.add(cvrp_dir_num_int[i]);
            instCV.add(cvrp_dir_num_ext[i]);
            instCV.add(cvrp_dir_localidad[i]);
            instCV.add(cvrp_dir_municipio[i]);
            instCV.add(cvrp_dir_estado[i]);
            instCV.add(cvrp_telefono[i]);
            instCV.add(cvrp_relacion[i]);
            instCV.add(cvrp_tiempo_conociendo[i]);
            instCV.add(cvrp_tipo[i]);
            
            objCBD.ejecutarABC(instCV);
        }
        objCBD.cerrarConexion();
    }
    
    public static ByteArrayOutputStream generarPdf(Curriculum c) {
        Document document = new Document();
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
        ArrayList aibd = null;
        ResultSet rs = null;
        PdfPCell hcell;
        
        try {
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            
            PdfPTable tableIP = new PdfPTable(4);
            PdfPTable tableEL = new PdfPTable(4);
            PdfPTable tableFA = new PdfPTable(4);
            PdfPTable tableID = new PdfPTable(4);
            PdfPTable tableDF = new PdfPTable(4);
            PdfPTable tableRP = new PdfPTable(4);
            
            //<editor-fold defaultstate="collapsed" desc="Informacion personal">
            tableIP.setWidthPercentage(80);
            tableIP.setWidths(new int[]{1, 1, 1, 1});

            hcell = new PdfPCell(new Phrase("Nombre", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getNombre() + " " + c.getCuenta().getApPaterno() + " " + c.getCuenta().getApMaterno()));
            hcell.setColspan(3);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Sexo", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getSexoS()));
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Estado civil", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getEstadoCivil()));
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Profesión", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getProfesion()));
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Teléfono", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getTelefono()));
            tableIP.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Correo electrónico", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getCorreo()));
            hcell.setColspan(3);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase("CURP", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getCurp()));
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Fecha de nacimiento", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getFechaNacimiento().toString()));
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Dirección", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableIP.addCell(hcell);

            hcell = new PdfPCell(new Phrase(c.getCuenta().getDireccionNumExterior() + ", " + c.getCuenta().getDireccionNumInterior() + ", " + c.getCuenta().getDireccionLocalidad() + ", " + c.getCuenta().getDireccionMunicipio() + ", " + c.getCuenta().getDireccionEstado()));
            hcell.setColspan(3);
            tableIP.addCell(hcell);
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Experiencia laboral">
            aibd = new ArrayList();
            aibd.add("SELECT * FROM cv_experiencia_laboral WHERE cvel_curriculum = ?");
            aibd.add(c.getId());
            objCBD.consultar(aibd);
            rs = objCBD.getCdr();
            
            while (rs.next()) {
                tableEL.setWidthPercentage(80);
                tableEL.setWidths(new int[]{1, 1, 1, 1});

                hcell = new PdfPCell(new Phrase("Nombre de la empresa", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableEL.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvel_nombre_empresa")));
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Puesto", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvel_puesto")));
                tableEL.addCell(hcell);

                hcell = new PdfPCell(new Phrase("Año de inicio", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableEL.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvel_anio_inicio")));
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Año de fin", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvel_anio_fin")));
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Nombre del jefe", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvel_nombre_jefe") + " " + rs.getString("cvel_app_jefe") + " " + rs.getString("cvel_apm_jefe")));
                hcell.setColspan(3);
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Teléfono", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableEL.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvel_telefono")));
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Funciones realizadas", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvel_funciones")));
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Dirección", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableEL.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvel_dir_num_ext") + ", " + rs.getString("cvel_dir_num_int") + ", " + rs.getString("cvel_dir_localidad") + ", " + rs.getString("cvel_dir_municipio") + ", " + rs.getString("cvel_dir_estado")));
                hcell.setColspan(3);
                tableEL.addCell(hcell);
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Formación académica">
            aibd = new ArrayList();
            aibd.add("SELECT * FROM cv_formacion_academica WHERE cvfa_curriculum = ?");
            aibd.add(c.getId());
            objCBD.consultar(aibd);
            rs = objCBD.getCdr();
            
            while (rs.next()) {
                tableFA.setWidthPercentage(80);
                tableFA.setWidths(new int[]{1, 1, 1, 1});

                hcell = new PdfPCell(new Phrase("Nombre de la institución", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFA.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvfa_nombre_institucion")));
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Nivel", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvfa_nivel")));
                tableFA.addCell(hcell);

                hcell = new PdfPCell(new Phrase("Año de inicio", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFA.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvfa_anio_inicio")));
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Año de fin", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvfa_anio_fin")));
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Título/certificado", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFA.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvfa_titulo_certificado")));
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Cédula", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvfa_cedula")));
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Dirección", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvfa_dir_num_ext") + ", " + rs.getString("cvfa_dir_num_int") + ", " + rs.getString("cvfa_dir_localidad") + ", " + rs.getString("cvfa_dir_municipio") + ", " + rs.getString("cvfa_dir_estado")));
                hcell.setColspan(3);
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Estatus", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFA.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvfa_estado")));
                hcell.setColspan(3);
                tableFA.addCell(hcell);
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Idiomas">
            aibd = new ArrayList();
            aibd.add("SELECT * FROM cv_idiomas WHERE idio_curriculum = ?");
            aibd.add(c.getId());
            objCBD.consultar(aibd);
            rs = objCBD.getCdr();
            
            while (rs.next()) {
                tableID.setWidthPercentage(80);
                tableID.setWidths(new int[]{1, 1, 1, 1});

                hcell = new PdfPCell(new Phrase("Idioma", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableID.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("idio_idioma")));
                tableID.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Porcentaje", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableID.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("idio_porcentaje")));
                tableID.addCell(hcell);
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Datos familiares">
            aibd = new ArrayList();
            aibd.add("SELECT * FROM cv_datos_familiares WHERE cvdf_curriculum = ?");
            aibd.add(c.getId());
            objCBD.consultar(aibd);
            rs = objCBD.getCdr();
            
            while (rs.next()) {
                tableDF.setWidthPercentage(80);
                tableDF.setWidths(new int[]{1, 1, 1, 1});
                
                hcell = new PdfPCell(new Phrase("Nombre del familiar", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableDF.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvdf_nombre") + " " + rs.getString("cvel_app") + " " + rs.getString("cvel_apm")));
                hcell.setColspan(3);
                tableDF.addCell(hcell);

                hcell = new PdfPCell(new Phrase("Parentesco", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableDF.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvdf_parentesco")));
                tableDF.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Ocupación", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableDF.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvdf_ocupacion")));
                tableDF.addCell(hcell);

                hcell = new PdfPCell(new Phrase("Vive", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableDF.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvdf_vive")));
                tableDF.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Finado", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableDF.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvdf_finado")));
                tableDF.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Teléfono", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableDF.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Dirección", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableDF.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvdf_dir_num_ext") + ", " + rs.getString("cvdf_dir_num_int") + ", " + rs.getString("cvdf_dir_localidad") + ", " + rs.getString("cvdf_dir_municipio") + ", " + rs.getString("cvdf_dir_estado")));
                hcell.setColspan(3);
                tableDF.addCell(hcell);
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Datos familiares">
            aibd = new ArrayList();
            aibd.add("SELECT * FROM cv_datos_familiares WHERE cvdf_curriculum = ?");
            aibd.add(c.getId());
            objCBD.consultar(aibd);
            rs = objCBD.getCdr();
            
            while (rs.next()) {
                tableRP.setWidthPercentage(80);
                tableRP.setWidths(new int[]{1, 1, 1, 1});
                
                hcell = new PdfPCell(new Phrase("Nombre de la referencia", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableRP.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvrp_nombre") + " " + rs.getString("cvrp_app") + " " + rs.getString("cvrp_apm")));
                hcell.setColspan(3);
                tableRP.addCell(hcell);

                hcell = new PdfPCell(new Phrase("Teléfono", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableRP.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvrp_telefono")));
                tableRP.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Relación", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableRP.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvrp_relacion")));
                tableRP.addCell(hcell);

                hcell = new PdfPCell(new Phrase("Tiempo de conocerlo", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableRP.addCell(hcell);

                hcell = new PdfPCell(new Phrase(rs.getString("cvrp_tiempo_conociendo")));
                tableRP.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Tipo de referencia", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableRP.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvrp_tipo")));
                tableRP.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase("Dirección", headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableRP.addCell(hcell);
                
                hcell = new PdfPCell(new Phrase(rs.getString("cvrp_dir_num_ext") + ", " + rs.getString("cvrp_dir_num_int") + ", " + rs.getString("cvrp_dir_localidad") + ", " + rs.getString("cvrp_dir_municipio") + ", " + rs.getString("cvrp_dir_estado")));
                hcell.setColspan(3);
                tableRP.addCell(hcell);
            }
            //</editor-fold>
            
            PdfWriter.getInstance(document, bout);
            document.open();
            
            document.add(new Paragraph("Currículum Vitae", headFont));
            document.add(tableIP);
            document.add(new Paragraph("Experiencia laboral", headFont));
            document.add(tableEL);
            document.add(new Paragraph("Formación académica", headFont));
            document.add(tableFA);
            document.add(new Paragraph("Idiomas", headFont));
            document.add(tableID);
            document.add(new Paragraph("Datos familiares", headFont));
            document.add(tableDF);
            document.add(new Paragraph("Referencias personales", headFont));
            document.add(tableRP);
            
            document.close();
        } catch (DocumentException | SQLException ex) {
            ex.printStackTrace();
        }
        
        return bout;
    }
    //</editor-fold>
}
