package servlet;

import bean.Curriculum;
import bean.Empleador;
import bean.Postulacion;
import bean.Vacante;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Postularse", urlPatterns = {"/Postularse"})
public class Postularse extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Postularse</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Postularse at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("v") != null && request.getParameter("u") != null && request.getParameter("c") != null) {
            Vacante v = Vacante.obtenerVacante(Integer.parseInt(request.getParameter("v")));
            Empleador u = Empleador.obtenerCuenta(Integer.parseInt(request.getParameter("u")));
            Curriculum c = Curriculum.obtenerCurriculum2(Integer.parseInt(request.getParameter("c")));
            
            Postulacion p = new Postulacion(v, u, c, request.getParameter("m"));
            p.ingresar();
        }
    }

    @Override
    public String getServletInfo() {
        return "Postularse";
    }// </editor-fold>

}
