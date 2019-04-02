package servlet;

import extra.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CB09
 */
@WebServlet(name = "EvaluacionC", urlPatterns = {"/EvaluacionC"})
public class EvaluacionC extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        
        try (PrintWriter out = response.getWriter()) {
        try {
            
            float calif = Float.parseFloat(request.getParameter("c"));
             //   System.out.println(calif);
            String comen = request.getParameter("co");
            int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
            int idCurso = Integer.parseInt(request.getParameter("idCurso"));
            
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instruccionBD = new ArrayList();
            instruccionBD.add("SELECT * from CALIFICACION WHERE cali_curso = ? and cali_cuenta = ? ");
            instruccionBD.add(idCurso);
            instruccionBD.add(idCuenta);
            objCBD.consultar(instruccionBD);
            ResultSet rs = objCBD.getCdr();
            if(rs.next()){
                int idCalif = rs.getInt("id_calificacion");
                ArrayList x = new ArrayList();
                x.add("UPDATE CALIFICACION SET cali_calificacion = ?, cali_comentario = ? WHERE id_calificacion = ?");
                x.add(calif);
                x.add(comen);
                x.add(idCalif);


                objCBD.ejecutarABC(x);
                out.println("Se ha actualizado la calificacion");
            } else {
                
                ArrayList ca = new ArrayList();
                ca.add("INSERT INTO CALIFICACION VALUES(null,?, ?,?, NOW(), ?)");
                ca.add(idCurso);
                ca.add(idCuenta);
                ca.add(calif);
                ca.add(comen);
                objCBD.ejecutarABC(ca);

                out.println("Se ha insertado la calificacion");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
