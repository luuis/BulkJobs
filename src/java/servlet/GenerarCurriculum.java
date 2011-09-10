package servlet;

import bean.Curriculum;
import bean.Empleador;
import bean.Sesion;
import extra.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GenerarCurriculum", urlPatterns = {"/GenerarCurriculum"})
public class GenerarCurriculum extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Sesion sesion = (Sesion) request.getSession().getAttribute("sesion");
        if (sesion != null && sesion.isIniciada()) {    
            Curriculum curr = new Curriculum(Empleador.obtenerCuenta(sesion.getId()));
            if (curr.generarCurriculum()) {
                // Experiencia laboral
                Curriculum.generarCVEL(curr.getId(), request.getParameterValues("cvel_nombre_empresa"), request.getParameterValues("cvel_dir_num_int"), request.getParameterValues("cvel_dir_num_ext"), request.getParameterValues("cvel_dir_localidad"), request.getParameterValues("cvel_dir_municipio"), request.getParameterValues("cvel_dir_estado"), request.getParameterValues("cvel_telefono"), request.getParameterValues("cvel_puesto"), request.getParameterValues("cvel_nombre_jefe"), request.getParameterValues("cvel_app_jefe"), request.getParameterValues("cvel_apm_jefe"), request.getParameterValues("cvel_funciones"), request.getParameterValues("cvel_anio_inicio"), request.getParameterValues("cvel_anio_fin"));
                // Formación académica
                Curriculum.generarCVFA(curr.getId(), request.getParameterValues("cvfa_nivel"), request.getParameterValues("cvfa_nombre_institucion"), request.getParameterValues("cvfa_dir_num_int"), request.getParameterValues("cvfa_dir_num_ext"), request.getParameterValues("cvfa_dir_localidad"), request.getParameterValues("cvfa_dir_municipio"), request.getParameterValues("cvfa_dir_estado"), request.getParameterValues("cvfa_titulo_certificado"), request.getParameterValues("cvfa_cedula"), request.getParameterValues("cvfa_anio_inicio"), request.getParameterValues("cvfa_anio_fin"), request.getParameterValues("cvfa_estado"));
                // Idiomas
                Curriculum.generarCVID(curr.getId(), request.getParameterValues("idio_idioma"), request.getParameterValues("idio_porcentaje"));
                // Datos familiares
                Curriculum.generarCVDF(curr.getId(), request.getParameterValues("cvdf_nombre"), request.getParameterValues("cvdf_app"), request.getParameterValues("cvdf_apm"), request.getParameterValues("cvdf_parentesco"), request.getParameterValues("cvdf_vive"), request.getParameterValues("cvdf_finado"), request.getParameterValues("cvdf_dir_num_int"), request.getParameterValues("cvdf_dir_num_ext"), request.getParameterValues("cvdf_dir_localidad"), request.getParameterValues("cvdf_dir_municipio"), request.getParameterValues("cvdf_dir_estado"), request.getParameterValues("cvdf_ocupacion"));
                // Referencias personales
                Curriculum.generarCVRP(curr.getId(), request.getParameterValues("cvrp_nombre"), request.getParameterValues("cvrp_app"), request.getParameterValues("cvrp_apm"), request.getParameterValues("cvrp_dir_num_int"), request.getParameterValues("cvrp_dir_num_ext"), request.getParameterValues("cvrp_dir_localidad"), request.getParameterValues("cvrp_dir_municipio"), request.getParameterValues("cvrp_dir_estado"), request.getParameterValues("cvrp_telefono"), request.getParameterValues("cvrp_relacion"), request.getParameterValues("cvrp_tiempo_conociendo"), request.getParameterValues("cvrp_tipo"));
                
                try (PrintWriter out = response.getWriter()) {
                    out.println("OK");
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
