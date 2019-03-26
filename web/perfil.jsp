<%@page import="java.util.ArrayList"%>
<%@page import="bean.Evaluacion"%>
<%@page import="java.io.File"%>
<%@page import="bean.Capacitador"%>
<%@page import="bean.Reclutador"%>
<%@page import="bean.Empleador"%>
<%@page import="bean.Cuenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Perfil" />
</jsp:include>
<%
Cuenta cuenta = null;
int id = 0;
try {
    if(request.getParameter("c") == null) {
        if (sesion != null && sesion.isIniciada()) {
            id = sesion.getId();
        } else {
            response.sendRedirect("sesion.jsp");
        }
    } else {
        id = Integer.parseInt(request.getParameter("c"));
    }
    
    cuenta = Cuenta.obtenerCuenta(id);
    if (cuenta == null) {
        response.sendRedirect("perfil.jsp");
    }
} catch (NumberFormatException ex) {
    out.println("<script>alertify.error('Debes insertar una cuenta válida');</script>");
    response.sendRedirect("perfil.jsp");
} catch (NullPointerException ex) {
    out.println("<script>alertify.error('No se pudo cargar el perfil');</script>");
    response.sendRedirect("perfil.jsp");
} %>
<section class="two small">
    <div class="small">
        <div class="container">
            <% String contextPath = getServletContext().getRealPath(File.separator);
            File foto = new File(contextPath + "../../web/subida/perfil/" + cuenta.getId() + ".jpg");
            if (foto.exists()) { %>
            <div id="photo" style="background-image: url('subida/perfil/<%=cuenta.getId()%>.jpg');"></div>
            <% } else { %>
            <div id="photo"></div>
            <% }
            if (sesion.getId() == cuenta.getId()) { %>
            <center><p>
                <button><i class="fas fa-pencil-alt fa-fw"></i> Editar perfil</button>
            </p></center>
            <% } %>
        </div>
        <% if (cuenta.esReclutador()) { %>
        <div class="container">
            <% if (sesion.isIniciada() && (sesion.getId() != cuenta.getId())) { %>
            <center><p>Calificación: <%= Evaluacion.obtenerProm(id) %><br>
                    Mi calificación: <%= Evaluacion.obtenerCal(id, sesion.getId()) %></p></center>
            <input type="hidden" id="idCuenta" value="<%= cuenta.getId()%>">
            
            <input type="hidden" id="idEvalu" value="<%= sesion.getId()%>">
            <center id="evaluar">
                <i title="Lametable" data-calif="1" class="far fa-star fa-lg fa-fw"></i>
                <i title="Malo" data-calif="2" class="far fa-star fa-lg fa-fw"></i>
                <i title="Regular" data-calif="3" class="far fa-star fa-lg fa-fw"></i>
                <i title="Me gusto" data-calif="4" class="far fa-star fa-lg fa-fw"></i>
                <i title="Excelente" data-calif="5" class="far fa-star fa-lg fa-fw"></i>
            </center>
            <% } else { %>
            <center><p>Calificación: <%= Evaluacion.obtenerProm(id) %></p></center>
            <% } %>
        </div>
        
        <div class="container">
            <%
            ArrayList<Evaluacion> evaluaciones = Evaluacion.obtenerEvaluaciones();
            if(evaluaciones.size() > 0){
                for(Evaluacion ev : evaluaciones){%>
                <article>
                    <strong><%= ev.getEmpleador().getNombre() %></strong><br><%= ev.getComen() %>
                </article>
                <% }
                
            } else {
                out.println("Se el primero en evaluar");
            } %>
        </div>
        <% } %>
    </div>
    <div class="big">
        <% if (cuenta.esEmpleador()) {
        Empleador perfil = Empleador.obtenerCuenta(id); %>
        <div class="container">
            <h2><%=perfil.getNombre()%> <%=perfil.getApPaterno()%> <%=perfil.getApMaterno()%></h2>
            
            <h3><%=cuenta.getRol()%></h3>
            <table>
                <tr>
                    <th>CURP:</th>
                    <td><%= perfil.getCurp() %></td>
                </tr>
                <tr>
                    <th>Estado Civil:</th>
                    <td><%= perfil.getEstadoCivil() %></td>
                </tr>
                <tr>
                    <th>Sexo:</th>
                    <td><%= perfil.getSexo() %></td>
                </tr>
                <tr>
                    <th>Telefono:</th>
                    <td><%= perfil.getTelefono() %></td>
                </tr>
                
                <tr>
                    <th>Profesion:</th>
                    <td><%= perfil.getProfesion() %></td>
                </tr>
                
                <tr>
                    <th>Fecha de Nacimiento:</th>
                    <td><%= perfil.getFechaNacimiento() %></td>
                </tr>
                
                <tr>
                    <th>Direccion:</th>
                    <td><%= perfil.getDireccionLocalidad()%>, <%= perfil.getDireccionNumExterior() %>, <%= perfil.getDireccionNumInterior() %>, <%= perfil.getDireccionMunicipio() %>, <%= perfil.getDireccionEstado() %></td>
                    
                </tr>
            
                
                
                
            </table>
        </div>
                    
                     <% if (sesion.isIniciada() && (sesion.getId() == cuenta.getId())) { %> 
                    <div class="container">
                        
                        <h2>Mis Cursos</h2>
                        
                        
                    </div>     
                    <% } %>
                    
        <% } else if (cuenta.esReclutador()) {
        Reclutador perfil = Reclutador.obtenerCuenta(id); %>
        <div class="container">
            <h2><%=perfil.getNombre()%></h2>
            <h3><%=cuenta.getRol()%></h3>
            <table>
                <tr>
                    <th>Telefono: </th>
                    <td><%= perfil.getTelefono() %></td>
                </tr>
                
                <tr>
                    <th>Categoria: </th>
                    <td><%= perfil.getCategoria().getNombre() %></td>
                </tr>
                
                   <tr>
                    <th>Direccion:</th>
                    <td><%= perfil.getDireccionLocalidad()%>, <%= perfil.getDireccionNumExterior() %>, <%= perfil.getDireccionNumInterior() %>, <%= perfil.getDireccionMunicipio() %>, <%= perfil.getDireccionEstado() %></td>
                    
                </tr>
                
            </table>
        </div>
                    
                    <div class="container">
                        <h2>Cursos Disponibles</h2>
                    </div>
                    
        <% } else if (cuenta.esCapacitador()) {
        Capacitador perfil = Capacitador.obtenerCuenta(id); %>
        <div class="container">
            <h2><%=perfil.getNombre()%></h2>
            <h3><%=cuenta.getRol()%></h3>
            <table>
                <tr>
                      <th>Telefono: </th>
                    <td><%= perfil.getTelefono() %></td>
                </tr>
                
                
                   <tr>
                    <th>Direccion:</th>
                    <td><%= perfil.getDireccionLocalidad()%>, <%= perfil.getDireccionNumExterior() %>, <%= perfil.getDireccionNumInterior() %>, <%= perfil.getDireccionMunicipio() %>, <%= perfil.getDireccionEstado() %></td>
                    
                </tr>
            </table>
        </div>
        <% } else {
            response.sendRedirect("index.jsp");
        } %>
    </div>
</section>
<script>
    
</script>
<%@include file="template.footer.jsp" %>
