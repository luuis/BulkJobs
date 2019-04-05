package servlet;

import extra.ConexionBD;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author luuis
 */
@WebServlet(name = "EditarPerfil", urlPatterns = {"/EditarPerfil"})
@MultipartConfig
public class EditarPerfil extends HttpServlet {

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
            if (request.getParameter("tipo").equals("emp")) {
                ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
                ArrayList aibd = new ArrayList();
                aibd.add("UPDATE empleador SET empl_estado_civil = ?, empl_profesion = ?, empl_sexo = ?, empl_telefono = ?, empl_fecha_nacimiento = ?, empl_curp = ?, empl_dir_num_int = ?, empl_dir_num_ext = ?, empl_dir_localidad = ?, empl_dir_municipio = ?, empl_dir_estado = ? WHERE empl_cuenta = ?");
                aibd.add(request.getParameter("emp_ecivil"));
                aibd.add(request.getParameter("emp_profesion"));
                aibd.add(request.getParameter("emp_sexo"));
                aibd.add(request.getParameter("emp_telefono"));
                aibd.add(request.getParameter("emp_date"));
                aibd.add(request.getParameter("emp_curp"));
                aibd.add(request.getParameter("emp_numint"));
                aibd.add(request.getParameter("emp_numext"));
                aibd.add(request.getParameter("emp_localidad"));
                aibd.add(request.getParameter("emp_municipio"));
                aibd.add(request.getParameter("emp_estado"));
                aibd.add(request.getParameter("id"));
                
                objCBD.ejecutarABC(aibd);
                
                if (request.getPart("img") != null && request.getPart("img").getSize() > 0) {
                    try {
                        Part part = request.getPart("img");
                        String workingDir = getServletContext().getRealPath("../../web/subida/perfil/");
                        File uploads = new File(workingDir);
                        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

                        if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                            InputStream fileContent = part.getInputStream();
                            File file = new File(uploads, request.getParameter("id") + ".jpg");
                            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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
