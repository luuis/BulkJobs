<%@page import="extra.TimeTools"%>
<%@page import="bean.PlanP"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Curso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Catalogo Cursos" />
</jsp:include>
<section class="one">
        <div class="container">
        <center><img src="img/logo-black.png"></center>
        <br/>
        <center> <h1>Catalogo de Planes Publicitarios </center></h1>
    </div>
    <% if(request.getParameter("e") != null){
        boolean eliminar = PlanP.eliminarPP(Integer.parseInt(request.getParameter("e")));
        if(eliminar){
            response.sendRedirect("/planesp.jsp?m=eliminado");
        } else {
            out.println("<script>alertify.error('No se ha eliminado');</script>");
        }
    }    

    if (request.getParameter("m") != null) {
        if (request.getParameter("m").equalsIgnoreCase("Registrado")) {
            out.println("<script>alertify.success('Tu plan publicitario ha sido registrado')</script>");
        } else if (request.getParameter("m").equalsIgnoreCase("Eliminado")) {
            out.println("<script>alertify.success('Tu plan publicitario ha sido eliminado')</script>");
        } else if (request.getParameter("m").equalsIgnoreCase("Editado")) {
            out.println("<script>alertify.success('Tu plan publicitario ha sido editado')</script>");
        }
    } %>
    <div class="container">
        <table class="data" width="100%" >
            <tr>
                <th>Nombre</th>
                <th>Descripcion</th>
                <th>Precio</th>
                <th>Tiempo</th>
                <th colspan="3">Acciones</th>
            </tr>
            <% ArrayList<PlanP> planP = PlanP.obtenerPlanesP();
            for (int i=0; i<planP.size(); i++) { %>
            <tr>
                <td><%=planP.get(i).getNombre()%></td>
                <td><%=planP.get(i).getDesc()%></td>
                <td><%=planP.get(i).getPrecio()%></td>
                <td><%=TimeTools.getTimeFrom(planP.get(i).getTiempo(), true)%></td>
                <% if (sesion.esReclutador()) { %>
                    <td><center><a href="comprar.jsp?i=<%=planP.get(i).getIdPlanP()%>"><button><i class="fas fa-shopping-cart"></i> Comprar</button></a></center></td>
                <% } else if (sesion.esAdmin()) { %>
                    <td><center><a href="planp_editar.jsp?i=<%=planP.get(i).getIdPlanP()%>"><button><i class="fas fa-edit"></i> Editar</button></a></center></td>
                    <td><center><a href="planesp.jsp?e=<%=planP.get(i).getIdPlanP()%>"><button><i class="fas fa-trash"></i> Eliminar</button></a></center></td>
                <% } %>
            </tr>
            <% } %>
        </table>
    </div>
</section>
<%@include file="template.footer.jsp" %>
