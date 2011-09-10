package servlet;

import bean.Empleador;
import bean.Reclutador;
import bean.Sesion;
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

@WebServlet(name = "Mensaje", urlPatterns = {"/Mensaje"})
@MultipartConfig
public class Mensaje extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("mensajes.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String msg = "";

            Sesion sesion = (Sesion) request.getSession().getAttribute("sesion");
            if (sesion != null && sesion.isIniciada()) {

                if (request.getParameter("texto").trim().length() <= 0 &&
                        (request.getPart("archivo") == null || request.getPart("archivo").getSize() == 0)) {
                    msg += "Debes insertar un mensaje o adjuntar un archivo. ";
                } else {
                    ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
                    ArrayList instBD = new ArrayList();
                    instBD.add("INSERT INTO mensajes VALUES (null, ?, ?, ?, ?, NOW(), 0)");

                    int remitente = 0;
                    if(sesion.getTipo() == 1) {
                        Empleador cuenta = (Empleador) sesion.getCuenta();
                        remitente = cuenta.getId();
                    } else {
                        Reclutador cuenta = (Reclutador) sesion.getCuenta();
                        remitente = cuenta.getId();
                    }
                    instBD.add(remitente);
                    instBD.add(request.getParameter("destinatario"));

                    String nombre = "";
                    Part part = request.getPart("archivo");
                    if (part != null && part.getSize() > 0) {
                        try {
                            String workingDir =
                                    getServletContext().getRealPath("../../web/subida/mensajes/");
                            File uploads = new File(workingDir);
                            
                            String gen = Long.toHexString(Double.doubleToLongBits(Math.random()));
                            
                            String fileName = gen + "_" +
                                    Paths.get(part.getSubmittedFileName()).getFileName().toString();
                            
                            if (fileName.endsWith(".pdf")) {
                                InputStream fileContent = part.getInputStream();
                                File file = new File(uploads, fileName);
                                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                nombre = fileName;
                                
                                msg += "Se ha adjuntado el PDF. ";
                            } else {
                            }
                        } catch (IOException ex) {
                            msg += ex.getLocalizedMessage() + ". ";
                        }
                    }
                    
                    String texto = request.getParameter("texto").trim();
                    if (texto.isEmpty() && nombre.isEmpty()) {
                        msg += "Debes insertar un mensaje o adjuntar un archivo. ";
                    } else {
                        instBD.add(texto);
                        instBD.add(nombre);
                        objCBD.ejecutarABC(instBD);

                        msg += "Se ha enviado el mensaje. ";
                    }
                }
            } else {
                msg += "Debes iniciar sesión. ";
            }
            
            out.println(msg);
            response.sendRedirect("mensajes.jsp?c=" + request.getParameter("destinatario"));
        }
    }

    @Override
    public String getServletInfo() {
        return "Enviar mensaje";
    }
}
