<%@page import="extra.ConexionBD"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Curriculum"%>
<%@page import="bean.Evaluacion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bean.Vacante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Detalles de la vacante" />
</jsp:include>
<% Vacante v = null;
try {
    v = Vacante.obtenerVacante(Integer.parseInt(request.getParameter("v")));    
} catch (Exception e) {
    out.println("<script>alertify.error('" + e.getLocalizedMessage() + "');</script>");
}
if (v != null) { %>
<section class="two small">
    <div class="big">
        <div class="container">
            <h3><%=v.getTitulo()%></h3>
            <h4>Detalles</h4>
            <p><%=v.getDetalles()%></p>
            <h4>Requisitos</h4>
            <p><%=v.getRequisitos()%></p>
            <h4>Informacion</h4>
            <p>
                <strong>Categoría</strong>: <a href="busqueda.jsp?categoria=<%=v.getCategoria().getId()%>"><%=v.getCategoria().getNombre()%></a><br>
                <strong>Paga</strong>: $<%=v.getPaga()%> <%=v.getTipoPagaS().toLowerCase()%><br>
                <strong>Publicación</strong>: <%=new SimpleDateFormat("dd/MM/YYYY").format(v.getFecha())%>
            </p>
        </div>
    </div>
    
    <div class="small">
        <div class="container">
            <h4>Empresa</h4>
            <p><%=v.getCompra().getCuenta().getNombre()%></p>
            <p>Evaluación: <%=Evaluacion.obtenerProm(v.getCompra().getCuenta().getId())%></p>
            <p><a href="perfil.jsp?c=<%=v.getCompra().getCuenta().getId()%>">
                <button type="button">Ver perfil</button>
            </a></p>
        </div>
        <% if (sesion != null && sesion.isIniciada()) {
            if (sesion.esEmpleador()) {
                Curriculum curriculum = Curriculum.obtenerCurriculum(sesion.getId()); %>
            <div class="container">
                <h3>Postularse</h3>
                <% if (curriculum != null) { %>
                <center><p>
                    <a id="postu">
                        <button type="button"><i class="fas fa-briefcase"></i> Postularse</button>
                    </a>
                    <a target="_blank" href="CV?c=<%=sesion.getId()%>">
                        <button type="button"><i class="fas fa-file-pdf"></i> Ver curriculum</button>
                    </a>
                </p></center>
                <script>
                $(function() {
                    $("#postu").on('click', function() {
                        alertify.prompt("Escribe un comentario para el reclutador.", "", function (evt, val) {
                            var vaca = "<%=v.getId()%>"; 
                            var user = "<%=sesion.getId()%>";
                            var curr = "<%=curriculum.getId()%>";
                            $.post('/Postularse', { v: vaca, u: user, c: curr, m: val }, function(returnedData) {
                                console.log(returnedData);
                                alertify.success("Te haz postulado correctamente");
                            });
                        });
                    });
                });
                </script>
                <% } else { %>
                <center><p>Debes tener un curriculum para poder postularte</p></center>
                <% } %>
                <center><p>
                    <a href="curriculum.jsp?a=subir&v=<%=v.getId()%>">
                        <button type="button"><i class="fas fa-file-upload"></i> Subir CV</button>
                    </a>
                    <% if (curriculum != null) { %>
                    <a href="curriculum.jsp?a=generar&v=<%=v.getId()%>">
                        <button type="button"><i class="fas fa-file"></i> Crear CV</button>
                    </a>
                    <a href="curriculum.jsp?a=generar&v=<%=v.getId()%>&actualizar">
                        <button type="button"><i class="fas fa-file"></i> Actualizar CV</button>
                    </a>
                    <% } else { %>
                    <a href="curriculum.jsp?a=generar&v=<%=v.getId()%>">
                        <button type="button"><i class="fas fa-file-medical"></i> Crear CV</button>
                    </a>
                    <% } %>
                </p></center>
            </div>
            <% } else if (sesion.esReclutador() && v.getCompra().getCuenta().getId() == sesion.getId()) {
            if (request.getParameter("eliminar") != null) {
                ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
                ArrayList instBD = new ArrayList();
                instBD.add("DELETE FROM vacante WHERE id_vacante=?");
                instBD.add(request.getParameter("v"));
                objCBD.ejecutarABC(instBD);
                response.sendRedirect("vacantes.jsp");
            } %>
            <div class="container">
                <center>
                    <a href="vacante_editar.jsp?v=<%=v.getId()%>"><button type="button">Editar</button></a>
                    <a href="vacante.jsp?v=<%=v.getId()%>&eliminar"><button type="button">Eliminar</button></a>
                    <a href="postulados.jsp?v=<%=v.getId()%>"><button type="button">Ver postulantes</button></a>
                </center>
            </div>
            <% } %>
        <% } else { %>
        <div class="container">
            <center>Debes iniciar sesión como un empleador para postularte</center>
        </div>
        <% } %>
    </div>
</section>
<% } else { response.sendRedirect("busqueda.jsp"); } %>
<%@include file="template.footer.jsp" %>
