package servlet;

import bean.Categoria;
import bean.PlanComprado;
import bean.Sesion;
import bean.Vacante;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author alumno
 */
@WebServlet(name = "IngresarVacantes", urlPatterns = {"/IngresarVacantes"})
@MultipartConfig
public class IngresarVacantes extends HttpServlet {

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
         Sesion sesion = (Sesion) request.getSession().getAttribute("sesion");
         
         System.out.println("pierde sesion");
        if (sesion != null && sesion.isIniciada()) {
            Vacante vacante = new Vacante(request.getParameter("nombre"),
                        request.getParameter("detalles"), request.getParameter("requisitos"),
                        Double.parseDouble(request.getParameter("paga")),
                        request.getParameter("tipPaga"),
                        Categoria.obtenerCategoria(Integer.parseInt(request.getParameter("categoria"))),
                        PlanComprado.obtenerPlanComprado(Integer.parseInt(request.getParameter("p"))));
            System.out.println("Si agrega en base");
            boolean agregar = vacante.agregarVa(); 
            
            if(agregar){ 
                
                System.out.println("falta que ingrese imagen");
            if (request.getPart("img") != null && request.getPart("img").getSize() > 0) {
                try {
                    Part part = request.getPart("img");
                    //working la carpeta/ruta en la que trabajamos 
                    String workingDir =
                            getServletContext().getRealPath("../../web/subida/vacantes/");
                    //Crear archivo en esta carpeta 
                    File uploads = new File(workingDir);
                    //part es el archivo que envio (por partes :v) y obtengo el nombre del archico enviado
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                        InputStream fileContent = part.getInputStream(); //de las partes (juntas :v) obtengo el contenido y lo guardo en fileC
                        File file = new File(uploads, vacante.getId()+ ".jpg"); //del contenido creo un archivo(vacio) con ruta y nombre
                        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING); //despues copio el contenido de las partes juntas 
                        
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                 
            }
                
                response.sendRedirect("/planes.jsp?m=registrado");
                } else {
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
