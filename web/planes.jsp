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
            <center><h2>Catalogo Planes</h2></center>
        </div>  
        
        <%
            if(request.getParameter("e") != null){
                boolean eliminar = Plan.eliminar(Integer.parseInt(request.getParameter("e")));
                
                if(eliminar){
                    response.sendRedirect("planes.jsp?m=eliminado");
                }else{
                    out.println("<script>alertify.error('No se ha eliminado correctame');</script>");
                }
            }
            
            if (request.getParameter("m") != null) {
                if (request.getParameter("m").equalsIgnoreCase("registrado")) {
                    out.println("<script>alertify.success('Se Agrego el plan correctamente!')</script>");
                }if (request.getParameter("m").equalsIgnoreCase("eliminado")) {
                    out.println("<script>alertify.success('Se Elimino Correctamente!')</script>");
                }
            }
        %>
        
        <div class="container">
            <table class="data" width="100%"> 
                <center> 
                <tr>
                    <th>Nombre</th>
                    <th>Descripcion</th>
                    <th>Precio</th>
                    <th>No. Vacantes</th> 
                    <th>Tiempo vacante</th>
                    
                    <th colspan="3">Acciones</th>
                    
                </tr> 
                
            <%
                ArrayList<Plan> planes = Plan.obtenerPlanes(); 
                for(int i=0; i<planes.size(); i++){ %>  

                <tr>
                    <td><%=planes.get(i).getNombre()%></td>
                    <td><%=planes.get(i).getDescripcion()%></td>
                    <td><%=planes.get(i).getPrecio()%></td>
                    <td><%=planes.get(i).getVacantes()%></td> 
                    <td><%=planes.get(i).getTiempo()%></td>
                    
                    <td><center><button><a href="comprar.jsp?p=<%=planes.get(i).getIdPlan()%>"><i class="fas fa-shopping-cart"></i>Comprar</a></button></center></td>
                  
                    <td><center><button><a href="plan_editar.jsp?i=<%=planes.get(i).getIdPlan()%>">Editar</a></button></center></td>
                    <td><center><button><a href="planes.jsp?e=<%=planes.get(i).getIdPlan()%>">Eliminar</a></button></center></td>
                </tr> 
                <%} %>
            </table>
        </div> 
                
        
</section>
<%@include file="template.footer.jsp" %>
