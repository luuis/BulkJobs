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
@WebServlet(name = "Evaluacion", urlPatterns = {"/Evaluacion"})
public class Evaluacion extends HttpServlet {


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
        
        try (PrintWriter out = response.getWriter()) {
        try {
            
            int calif = Integer.parseInt(request.getParameter("c"));
            String comen = request.getParameter("co");
            int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
            int idEvalu = Integer.parseInt(request.getParameter("idEvalu"));
            
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            ArrayList instruccionBD = new ArrayList();
            instruccionBD.add("SELECT * from EVALUACION WHERE eval_empleador = ? and eval_reclutador = ? ");
            instruccionBD.add(idEvalu);
            instruccionBD.add(idCuenta);
            objCBD.consultar(instruccionBD);
            ResultSet rs = objCBD.getCdr();
            if(rs.next()){
                int idEvaluacion = rs.getInt("id_evaluacion");
                ArrayList x = new ArrayList();
                x.add("UPDATE EVALUACION SET eval_calificacion = ?, eval_comentario = ? WHERE id_evaluacion = ?");
                x.add(calif);
                x.add(comen);
                x.add(idEvaluacion);

                objCBD.ejecutarABC(x);
                out.println("Se ha actualizado la calificacion");
            } else {
                
                ArrayList x = new ArrayList();
                x.add("INSERT INTO EVALUACION VALUES(null, ?, ?,?,?)");
                x.add(idEvalu);
                x.add(idCuenta);
                x.add(calif);
                x.add(comen);
                objCBD.ejecutarABC(x);

                out.println("Se ha insertado la calificacion");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
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
