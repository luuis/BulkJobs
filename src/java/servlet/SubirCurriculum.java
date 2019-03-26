package servlet;

import bean.Sesion;
import extra.ConexionBD;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

@WebServlet(name = "SubirCurriculum", urlPatterns = {"/SubirCurriculum"})
@MultipartConfig
public class SubirCurriculum extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("busqueda.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Sesion sesion = (Sesion) request.getSession().getAttribute("sesion");
        if (sesion != null && sesion.isIniciada()) {
            if (request.getPart("cv") != null && request.getPart("cv").getSize() > 0) {
                try {
                    Part part = request.getPart("cv");
                    String workingDir =
                            getServletContext().getRealPath("../../web/subida/curriculum/");
                    File uploads = new File(workingDir);
                    
                    String gen = Long.toHexString(Double.doubleToLongBits(Math.random()));
                    
                    String fileName = gen + "_" +
                            Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    
                    if (fileName.endsWith(".pdf")) {
                        InputStream fileContent = part.getInputStream();
                        File file = new File(uploads, fileName);
                        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        
                        ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
                        ArrayList instBD = new ArrayList();
                        instBD.add("INSERT INTO curriculum VALUES (null, ?, ?, NOW())");
                        instBD.add(sesion.getId());
                        instBD.add(fileName);
                        objCBD.ejecutarABC(instBD);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Subir curriculum";
    }

}
