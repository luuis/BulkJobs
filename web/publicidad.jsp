<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bean.PlanPComprado"%>
<%@page import="bean.Vacante"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.PlanComprado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="" />
    <jsp:param name="roles" value="reclutador" />
</jsp:include>  
<section class="one">
    <div class="container">
        <center><h2>Catalogo Publicidad</h2></center>
        <% Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<PlanPComprado> planesPComprados = PlanPComprado.obtenerPlanCompradoRecl(sesion.getId());

        if (planesPComprados.size() > 0) {
            for (PlanPComprado pp : planesPComprados) { %>
        <article>
            <table width="100%">
                <tr>
                    <% if (fechaActual.before(pp.getFechaF())) { %>
                    <td>
                        <h4>Plan <%= pp.getPlanc().getNombre()%> &middot; Activo</h4>
                        <p>Comprado el <%=sdf.format(pp.getFecha())%> &middot; Caduca el <%=sdf.format(pp.getFechaF())%></p>
                    </td>
                    <td style="text-align: right">
                        <a href="planpc_editar.jsp?i=<%=pp.getId()%>"><button><i class="fas fa-edit"></i> Editar</button></a>
                        <a href="comprar.jsp?ri=<%=pp.getId()%>"><button type="button"><i class="far fa-clock"></i> Agregar Tiempo</button></a>
                    </td>  
                    <% } else { %>
                    <td>
                        <h4>Plan <%= pp.getPlanc().getNombre()%> &middot;Caducado</h4>
                    </td>
                    <td style="text-align: right">
                        <a href="planpc_editar.jsp?i=<%=pp.getId()%>"><button><i class="fas fa-edit"></i> Editar</button></a>
                        <a href="comprar.jsp?ri=<%=pp.getId()%>"><button type="button"><i class="far fa-clock"></i> Renovar</button></a>
                    </td> 
                    <% } %>
                </tr>
            </table>
        </article>
            <% }
        } %>
    </div>
</section>
<%@include file="template.footer.jsp" %> 
