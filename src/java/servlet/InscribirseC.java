package servlet;


import bean.Sesion;
import extra.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alumno
 */
@WebServlet(name = "InscribirseC", urlPatterns = {"/InscribirseC"})
public class InscribirseC extends HttpServlet {

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
      
           try (PrintWriter out = response.getWriter()) {
        
          Sesion sesion = (Sesion) request.getSession().getAttribute("sesion");
          
          
        if (sesion != null && sesion.isIniciada()) {
            
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instBD = new ArrayList();
            instBD.add("SELECT * FROM curso_inscrito WHERE cuin_cuenta = ? and cuin_curso = ?");
            instBD.add(request.getParameter("idCuenta"));
            instBD.add(request.getParameter("idCurso"));
            objCBD.consultar(instBD);
            ResultSet rs = objCBD.getCdr();
            if(rs.next()){
                out.println("Ya estas inscrito en este curso");
            }else{
            ArrayList instruccionBD = new ArrayList();
            instruccionBD.add("INSERT INTO curso_inscrito VALUES (null, ?, ?, NOW(), 0);");
            instruccionBD.add(request.getParameter("idCurso"));
            instruccionBD.add(request.getParameter("idCuenta"));
            objCBD.ejecutarABC(instruccionBD);
            
            out.println("Te has inscrito al Curso ");
            }
             
        }
        
        
        
        
           } catch (SQLException ex) {
            ex.printStackTrace();
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
