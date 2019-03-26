package servlet;

import bean.Curriculum;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CV", urlPatterns = {"/CV"})
public class CV extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Curriculum c = Curriculum.obtenerCurriculum(Integer.parseInt(request.getParameter("c")));
        if (c != null) {
            if (c.getArchivo().isEmpty()) {
                // Curriculum generado
            } else {
                String fileName = c.getArchivo();
                String contextPath = getServletContext().getRealPath(File.separator);
                File pdf = new File(contextPath + "../../web/subida/curriculum/" + fileName);

                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "inline; filename=" + fileName.substring(17));
                response.setContentLength((int) pdf.length());

                FileInputStream fis = new FileInputStream(pdf);
                OutputStream ros = response.getOutputStream();
                int bytes;
                while ((bytes = fis.read()) != -1) {
                    ros.write(bytes);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
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
        return "Mostrar PDF del Curriculum";
    }// </editor-fold>

}
