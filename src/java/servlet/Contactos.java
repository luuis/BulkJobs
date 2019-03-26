package servlet;

import extra.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Contactos", urlPatterns = {"/ServletContactos"})
public class Contactos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            JSONObject json = new JSONObject();
            LinkedList contactos = new LinkedList();
            LinkedList sugerencias = new LinkedList();
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList sqlQuery = new ArrayList();
            ArrayList sqlSearch = new ArrayList();
            ArrayList ids = new ArrayList();
            
            if(Integer.parseInt(request.getParameter("tipo")) == 1) {
                if (request.getParameter("busqueda").trim().length() > 0) {
                    sqlQuery.add("SELECT recl_cuenta, recl_nombre_empresa, min(mens_leido) FROM mensajes INNER JOIN reclutador ON (recl_cuenta=mens_destinatario OR recl_cuenta=mens_remitente) WHERE (mens_remitente=? OR mens_destinatario=?) AND recl_nombre_empresa LIKE ? GROUP BY mens_destinatario+mens_remitente;");
                    sqlQuery.add(request.getParameter("remitente"));
                    sqlQuery.add(request.getParameter("remitente"));
                    sqlQuery.add("%" + request.getParameter("busqueda") + "%");
                    
                    sqlSearch.add("SELECT recl_cuenta, recl_nombre_empresa FROM reclutador WHERE recl_nombre_empresa LIKE ?");
                    sqlSearch.add("%" + request.getParameter("busqueda") + "%");
                     
                } else {
                    sqlQuery.add("SELECT recl_cuenta, recl_nombre_empresa, min(mens_leido) FROM mensajes INNER JOIN reclutador ON (recl_cuenta=mens_destinatario OR recl_cuenta=mens_remitente) WHERE mens_remitente=? OR mens_destinatario=? GROUP BY mens_destinatario+mens_remitente;");
                    sqlQuery.add(request.getParameter("remitente"));
                    sqlQuery.add(request.getParameter("remitente"));
                }
                
                objCBD.consultar(sqlQuery);
                ResultSet rs = objCBD.getCdr();
                try {
                    while (rs.next()) {
                        LinkedHashMap contacto = new LinkedHashMap();
                        contacto.put("id", rs.getInt("recl_cuenta"));
                        contacto.put("nombre", rs.getString("recl_nombre_empresa"));
                        contacto.put("leido", rs.getBoolean(3));
                        contactos.add(contacto);
                        if (!ids.contains(rs.getInt("recl_cuenta"))) {
                            ids.add(rs.getInt("recl_cuenta"));
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
                if (sqlSearch.size() > 0) {
                    objCBD.consultar(sqlSearch);
                    rs = objCBD.getCdr();
                    
                    try {
                        while (rs.next()) {
                            if (!ids.contains(rs.getInt("recl_cuenta"))) {
                                LinkedHashMap sugerencia = new LinkedHashMap();
                                sugerencia.put("id", rs.getInt("recl_cuenta"));
                                sugerencia.put("nombre", rs.getString("recl_nombre_empresa"));
                                sugerencias.add(sugerencia);
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                if (request.getParameter("busqueda").trim().length() > 0) {
                    sqlQuery.add("SELECT empl_cuenta, empl_nombre, empl_ap_pat, empl_ap_mat, min(mens_leido) FROM mensajes INNER JOIN empleador ON (empl_cuenta=mens_destinatario OR empl_cuenta=mens_remitente) WHERE (mens_remitente=? OR mens_destinatario=?) AND concat(empl_nombre, ' ', empl_ap_pat, ' ', empl_ap_mat) LIKE ? GROUP BY mens_destinatario+mens_remitente;");
                    sqlQuery.add(request.getParameter("remitente"));
                    sqlQuery.add(request.getParameter("remitente"));
                    sqlQuery.add("%" + request.getParameter("busqueda") + "%");
                    
                    sqlSearch.add("SELECT empl_cuenta, empl_nombre, empl_ap_pat, empl_ap_mat FROM empleador WHERE concat(empl_nombre, ' ', empl_ap_pat, ' ', empl_ap_mat) LIKE ?;");
                    sqlSearch.add("%" + request.getParameter("busqueda") + "%");
                } else { 
                    sqlQuery.add("SELECT empl_cuenta, empl_nombre, empl_ap_pat, empl_ap_mat, min(mens_leido) FROM mensajes INNER JOIN empleador ON (empl_cuenta=mens_destinatario OR empl_cuenta=mens_remitente) WHERE mens_remitente=? OR mens_destinatario=? GROUP BY mens_destinatario+mens_remitente;");
                    sqlQuery.add(request.getParameter("remitente"));
                    sqlQuery.add(request.getParameter("remitente"));
                }
                
                objCBD.consultar(sqlQuery);
                ResultSet rs = objCBD.getCdr();
                try {
                    while (rs.next()) {
                        LinkedHashMap contacto = new LinkedHashMap();
                        contacto.put("id", rs.getInt("empl_cuenta"));
                        contacto.put("nombre", rs.getString("empl_nombre") + " " +
                                rs.getString("empl_ap_pat") + " " + rs.getString("empl_ap_mat"));
                        contacto.put("leido", rs.getBoolean(5));
                        contactos.add(contacto);
                        if (!ids.contains(rs.getInt("empl_cuenta"))) {
                            ids.add(rs.getInt("empl_cuenta"));
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
                if (sqlSearch.size() > 0) {
                    objCBD.consultar(sqlSearch);
                    rs = objCBD.getCdr();
                    
                    try {
                        while (rs.next()) {
                            if (!ids.contains(rs.getInt("empl_cuenta"))) {
                                LinkedHashMap sugerencia = new LinkedHashMap();
                                sugerencia.put("id", rs.getInt("empl_cuenta"));
                                sugerencia.put("nombre", rs.getString("empl_nombre") + " " +
                                        rs.getString("empl_ap_pat") + " " + rs.getString("empl_ap_mat"));
                                sugerencias.add(sugerencia);
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
            json.put("contactos", contactos);
            json.put("sugerencias", sugerencias);
            
            out.write(json.toString(4));
            out.flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Obtener los contactos del usuario";
    }// </editor-fold>

}
