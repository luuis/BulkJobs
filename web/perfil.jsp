<%@page import="bean.CursoInscrito"%>
<%@page import="bean.CursoComprado"%>
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
            <center>
                <p>
                    <a href="perfil.jsp?editar"><button type="button">
                        <i class="fas fa-pencil-alt fa-fw"></i> Editar perfil
                    </button></a>
                </p>
            </center>
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
            <script>
            $(function() {
                $("#editarPerfil").on("submit", function () {
                    $(".disableit").prop("disabled", true);
                    $(this).ajaxSubmit({
                        method: "POST",
                        success: function(data, status, xhr) {
                            window.location = "perfil.jsp";
                        },
                        error: function(xhr, status, error) {
                            console.log(xhr);
                            alertify.warning(status);
                        }
                    });
                    return false;
                });
            });
            </script>
            <form id="editarPerfil" action="EditarPerfil" method="post" autocomplete="off" data-parsley-errors-messages-disabled class="validate">
            <input type="hidden" name="id" value="<%=cuenta.getId() %>">
            <input type="hidden" name="tipo" value="emp">
            <table>
                <% if (request.getParameter("editar") != null) { %>
                <tr>
                    <th>Foto de perfil:</th>
                    <td><input type="file" name="img"></td>
                </tr>
                <% } %>
                <tr>
                    <th>CURP:</th>
                    <% if (request.getParameter("editar") != null) { %>
                    <td><input type="text" required name="emp_curp" placeholder="CURP" pattern="[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9]{2}" value="<%= perfil.getCurp() %>"
                        data-parsley-minlength="18" minlength="18"
                        data-parsley-maxlength="18" maxlength="18"
                        data-parsley-maxlength-message="El CURP debe tener 18 carácteres"
                        data-parsley-minlength-message="El CURP debe tener 18 carácteres"
                        data-parsley-required-message="Debes ingresar el CURP"></td>
                    <% } else { %>
                    <td><%= perfil.getCurp() %></td>
                    <% } %>
                </tr>
                <tr>
                    <th>Estado Civil:</th>
                    <% if (request.getParameter("editar") != null) { %>
                     <td><select name="emp_ecivil">
                        <option <%=(perfil.getEstadoCivil().equals("Solter@") ? "selected" : "")%> value="Solter@">Solter@</option>
                        <option <%=(perfil.getEstadoCivil().equals("Casad@") ? "selected" : "")%> value="Casad@">Casad@</option>
                        <option <%=(perfil.getEstadoCivil().equals("Viud@") ? "selected" : "")%> value="Viud@">Viud@</option>
                        <option <%=(perfil.getEstadoCivil().equals("Divorciad@") ? "selected" : "")%> value="Divorciad@">Divorciad@</option>
                        <option <%=(perfil.getEstadoCivil().equals("Unión Libre") ? "selected" : "")%> value="Unión Libre">Unión Libre</option>
                        <option <%=(perfil.getEstadoCivil().equals("Otro") ? "selected" : "")%> value="Otro">Otro</option>
                    </select> </td>
                    <% } else { %>
                    <td><%= perfil.getEstadoCivil() %></td>
                    <% } %>
                </tr>
                <tr>
                    <th>Sexo:</th>
                    <% if (request.getParameter("editar") != null) { %>
                     <td><select name="emp_sexo">
                        <option <%=(perfil.getSexo().equals("M") ? "selected" : "")%> value="M">Masculino</option>
                        <option <%=(perfil.getSexo().equals("F") ? "selected" : "")%> value="F">Femenino</option>
                    </select> </td>
                    <% } else { %>
                    <td><%= perfil.getSexo() %></td>
                    <% } %>
                </tr>
                <tr>
                    <th>Telefono:</th>
                    <% if (request.getParameter("editar") != null) { %>
                     <td><input type="tel" required name="emp_telefono" placeholder="Teléfono" value="<%= perfil.getTelefono() %>"
                       data-parsley-minlength="10" minlength="10"
                       data-parsley-maxlength="10" maxlength="10"
                       data-parsley-minlength-message="El telefono debe tener 10 digitos"
                       data-parsley-maxlength-message="El telefono debe tener 10 digitos"
                       data-parsley-required-message="Debes ingresar el teléfono"
                       data-parsley-type-message="Debes ingresar un teléfono válido"> </td>
                    <% } else { %>
                    <td><%= perfil.getTelefono() %></td>
                    <% } %>
                </tr>
                
                <tr>
                    <th>Profesion:</th>
                    <% if (request.getParameter("editar") != null) { %>
                     <td><input type="text" required name="emp_profesion" placeholder="Profesión" value="<%= perfil.getProfesion() %>"
                       data-parsley-maxlength="18" maxlength="18"
                       data-parsley-maxlength-message="La profesión no debe tener más de 50 carácteres"
                       data-parsley-required-message="Debes ingresar la profesión"> </td>
                    <% } else { %>
                    <td><%= perfil.getProfesion() %></td>
                    <% } %>
                </tr>
                
                <tr>
                    <th>Fecha de Nacimiento:</th>
                    <% if (request.getParameter("editar") != null) { %>
                     <td><input type="date" required name="emp_date" placeholder="Fecha de nacimiento" value="<%= perfil.getFechaNacimiento() %>"
                       data-parsley-required-message="Debes ingresar la fecha de nacimiento"> </td>
                    <% } else { %>
                    <td><%= perfil.getFechaNacimiento() %></td>
                    <% } %>
                </tr>
                
                <tr>
                    <th>Direccion:</th>
                    <% if (request.getParameter("editar") != null) { %>
                     <td><input type="text" name="emp_numint" placeholder="Número interior" value="<%=perfil.getDireccionNumInterior() %>"
                       data-parsley-maxlength="50" maxlength="50"
                       data-parsley-maxlength-message="El número interior no puede ser mayor a 50 carácteres">
                    <input type="text" name="emp_numext" placeholder="Número exterior" value="<%=perfil.getDireccionNumExterior() %>"
                       data-parsley-maxlength="50" maxlength="50"
                       data-parsley-maxlength-message="El número exterior no puede ser mayor a 50 carácteres">
                    <input type="text" required name="emp_localidad" placeholder="Localidad" value="<%=perfil.getDireccionLocalidad() %>"
                       data-parsley-maxlength="50" maxlength="50"
                       data-parsley-maxlength-message="La localidad no puede ser mayor a 50 carácteres"
                       data-parsley-required-message="Debes ingresar la localidad">
                    <input type="text" required name="emp_municipio" placeholder="Municipio" value="<%=perfil.getDireccionMunicipio() %>"
                       data-parsley-maxlength="50" maxlength="50"
                       data-parsley-maxlength-message="El municipio no puede ser mayor a 50 carácteres"
                       data-parsley-required-message="Debes ingresar el municipio">
                    <input type="text" required name="emp_estado" placeholder="Estado" value="<%=perfil.getDireccionEstado() %>"
                       data-parsley-maxlength="50" maxlength="50"
                       data-parsley-maxlength-message="El estado no puede ser mayor a 50 carácteres"
                       data-parsley-required-message="Debes ingresar el estado"> </td>
                    <% } else { %>
                    <td><%= perfil.getDireccionLocalidad()%>, <%= perfil.getDireccionNumExterior() %>, <%= perfil.getDireccionNumInterior() %>, <%= perfil.getDireccionMunicipio() %>, <%= perfil.getDireccionEstado() %></td>
                    <% } %>
                </tr>
            </table>
            <% if (request.getParameter("editar") != null) { %>
            <center>
                <button type="submit" class="disableit" name="boton" value="actualizar">Actualizar</button>
            </center>
            <% } %>
            </form>
        </div>
                    
        <% if (sesion.isIniciada() && (sesion.getId() == cuenta.getId())) { %> 
        <div class="container">
            <h2>Mis Cursos</h2>
            <% ArrayList<CursoInscrito> cursosInscritos = CursoInscrito.ObtenerCI(id);
            if (cursosInscritos.size() > 0){
                for (CursoInscrito ci : cursosInscritos  ){%>
            <article>
                <table width="100%"> 
                    <tr>
                        <td width="100px">
                            <%
                            File fotoC = new File(contextPath + "../../web/subida/cursos/" + ci.getCurso().getIdCurso()+ ".jpg");
                            if (fotoC.exists()) { %>
                            <div id="photo" style="background-image: url('subida/cursos/<%=ci.getCurso().getIdCurso() %>.jpg');"></div>
                            <% } else { %>
                            <div id="photo"></div>
                            <% } %>
                        </td>
                        <td>
                            <strong><%= ci.getCurso().getNombre() %></strong><br><%= ci.getCurso().getDesc()%>
                        </td>
                    </tr>
                </table>
            </article>
            <% } } else { %>
            Este Empleador no cuenta con Cursos
            <% } %>
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
             <% ArrayList<CursoComprado> cursosComprados = CursoComprado.ObtenerC(id);
             if (cursosComprados.size() > 0) {
                for (CursoComprado cc : cursosComprados ){%>
            <article>
                <table width="100%"> 
                    <tr>
                        <td width="100px">
                        <% File fotoC = new File(contextPath + "../../web/subida/cursos/" + cc.getCurso().getIdCurso()+ ".jpg");
                        if (fotoC.exists()) { %>
                            <div id="photo" style="background-image: url('subida/cursos/<%=cc.getCurso().getIdCurso() %>.jpg');"></div>
                        <% } else { %>
                            <div id="photo"></div>
                        <% } %>
                        </td>
                        <td>
                            <strong><%= cc.getCurso().getNombre() %></strong><br><%= cc.getCurso().getDesc()%>
                            <center><a href="curso.jsp?c=<%= cc.getId() %>"><button type="submit" name="boton" value="inscrito">Inscribirse</button></a></center> 
                        </td>
                    </tr>
                </table>
            </article>
            <% } } else { %>
            Este Reclutador no cuenta con Cursos
            <% } %>
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
