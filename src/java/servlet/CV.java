package servlet;

import bean.Curriculum;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CV", urlPatterns = {"/CV"})
public class CV extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf;charset=UTF-8");
        Curriculum c = Curriculum.obtenerCurriculum2(Integer.parseInt(request.getParameter("c")));
        
        if (c != null) {
            if (c.getArchivo() == null || c.getArchivo().trim().isEmpty()) {
                response.addHeader("Content-Disposition", "inline; filename=curriculum.pdf");
                ByteArrayOutputStream baos = Curriculum.generarPdf(c);
                baos.writeTo(response.getOutputStream());
            } else {
                String fileName = c.getArchivo();
                String contextPath = getServletContext().getRealPath(File.separator);
                File pdf = new File(contextPath + "../../web/subida/curriculum/" + fileName);

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
