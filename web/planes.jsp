<%@page import="extra.TimeTools"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Date"%>
<%@page import="bean.Plan"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Catalago de Planes" />
</jsp:include>
<section class="one">
    <div class="container">
        <center><img src="img/logo-black.png"></center>
        <center><h2>Catálogo Planes</h2></center>
    </div>   
          
    <% if (request.getParameter("e") != null) {
        boolean eliminar = Plan.eliminar(Integer.parseInt(request.getParameter("e")));

        if (eliminar) {
            response.sendRedirect("planes.jsp?m=eliminado");
        } else {
            out.println("<script>alertify.error('No se ha eliminado correctame');</script>");
        }
    }

    if (request.getParameter("m") != null) {
        if (request.getParameter("m").equalsIgnoreCase("registrado")) {
            out.println("<script>alertify.success('Se Agrego el plan correctamente!')</script>");
        } else if (request.getParameter("m").equalsIgnoreCase("eliminado")) {
            out.println("<script>alertify.success('Se Elimino Correctamente!')</script>");
        }
    } %>
        
    <div class="container">
        <table class="data" width="100%"> 
            <center>  
            <tr>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>No. Vacantes</th> 
                <th>Tiempo vacante</th>
                <th colspan="3">Acciones</th>
            </tr> 

            <% ArrayList<Plan> planes = Plan.obtenerPlanes(); 
            for (Plan plan : planes) { %>  
            <tr>
                <td><%=plan.getNombre()%></td>
                <td><%=plan.getDescripcion()%></td>
                <td><%=plan.getPrecio()%></td>
                <td><%=plan.getVacantes()%></td> 
                <td><%=TimeTools.getTimeFrom(plan.getTiempo(), true)%></td>
                <% if (sesion.esReclutador()) { %>
                    <td><center><a href="comprar.jsp?p=<%=plan.getIdPlan()%>"><button type="button"><i class="fas fa-shopping-cart"></i> Comprar</button></a></center></td>
                <% } else if (sesion.esAdmin()) { %>
                    <td><center><a href="plan_editar.jsp?i=<%=plan.getIdPlan()%>"><button type="button">Editar</button></a></center></td>
                    <td><center><a href="planes.jsp?e=<%=plan.getIdPlan()%>"><button type="button">Eliminar</button></a></center></td>
                <% } %>
            </tr> 
            <% } %>
        </table>
    </div>
</section>
<%@include file="template.footer.jsp" %>
