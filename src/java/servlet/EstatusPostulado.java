package servlet;

import extra.ConexionBD;
import extra.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alumno
 */
@WebServlet(name = "EstatusPostulado", urlPatterns = {"/EstatusPostulado"})
public class EstatusPostulado extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. 
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("status") != null) {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            
            try (PrintWriter out = response.getWriter()) {
                if (request.getParameter("status").equalsIgnoreCase("A")) {
                    instBD.add("UPDATE postulacion SET post_estado = 1 WHERE id_postulacion = ?");
                    instBD.add(request.getParameter("postu"));
                    objCBD.ejecutarABC(instBD);
                    // aquí lo del correo de la cita
                    String url = request.getServerName() + ":" + request.getServerPort();
                    Email m = new Email();
                    m.setRemitente("no-reply@bulkjobs.com");
                    m.setDestinatario(request.getParameter("email"));
                    m.setAsunto("Has sido aceptado a una vacante");
                    m.setCuerpo("<p>En buena hora!, Usted ha sido aceptad@ a realizar la entrevista"
                            + " de empleo para el trabajo seleccionado. Inicie sesión y concatese con el reclutador en " +
                            "<a target='blank' href='http://" + url + "/mensajes.jsp?c=" + request.getParameter("chat") + "'>" +
                            "http://" + url + "/mensajes.jsp?c=" + request.getParameter("chat") + "</a></p>");
                    m.enviarCorreo(); 
                    
                    out.println("Se ha aceptado la vacante");
                } else {
                    instBD.add("UPDATE postulacion SET post_estado = 2 WHERE id_postulacion = ?");
                    instBD.add(request.getParameter("postu"));
                    objCBD.ejecutarABC(instBD);
                    //aqui va el rechazo de la cita
                    Email m = new Email();
                    m.setRemitente("no-reply@bulkjobs.com");
                    m.setDestinatario(request.getParameter("email"));
                    m.setAsunto("Notificación Vacante");
                    m.setCuerpo("<p>Lamentamos informale que usted ha sido rechazado para realizar la entrevistra de "
                            + "empleo en la vacante solicitada. Le recordamos que contamos con diversas vacantes que "
                            + "lo esperan para portularse.</p>"
                            + "<p>Atte. Coordinación de BulkJobs</p>");
                    m.enviarCorreo(); 
                    
                    out.println("Se ha rechazado la vacante");
                }
            
            
            }
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
