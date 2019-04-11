<%@page import="bean.CursoComprado"%>
<%@page import="bean.CursoInscrito"%>
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
        <br/><center><h1>Catalogo Cursos</center></h1>
    </div>
    <% if(request.getParameter("e") != null){
        boolean eliminar = Curso.eliminar(Integer.parseInt(request.getParameter("e")));
        if(eliminar){
            response.sendRedirect("/cursos.jsp?m=eliminado");
        } else {
            out.println("<script>alertify.error('No se ha eliminado');</script>");
        }
    }    

    if (request.getParameter("m") != null) {
        if (request.getParameter("m").equalsIgnoreCase("Registrado")) {
            out.println("<script>alertify.success('Tu curso ha sido registrado')</script>");
        } else if (request.getParameter("m").equalsIgnoreCase("Eliminado")) {
            out.println("<script>alertify.success('Tu curso ha sido eliminado')</script>");
        } else if (request.getParameter("m").equalsIgnoreCase("Editado")) {
            out.println("<script>alertify.success('Tu curso ha sido editado')</script>");
        }
    } %>
    <div class="container">
        <table class="data" width="100%" >
            <tr>
                <th>Nombre</th>
                <th>Descripcion</th>
                <th>Precio</th>
                
                <th colspan="3">Acciones</th>
                
            </tr>
            <% ArrayList<Curso> cursos = Curso.obtenerCursos();
            for(int i=0; i<cursos.size(); i++){ %>
            <tr>
                <td><%=cursos.get(i).getNombre()%></td>
                <td><%=cursos.get(i).getDesc()%></td>
                <td><%=cursos.get(i).getPrecio()%></td>
                <% if (sesion.esReclutador() || sesion.esEmpleador()) { 
                
                if(CursoComprado.estaComprado(cursos.get(i).getIdCurso(), sesion.getId())){ %>
                <td><center>Ya esta comprado</center></td> 
                <% } else { %>
                <td><center><a href="comprar.jsp?c=<%=cursos.get(i).getIdCurso()%>"><button><i class="fas fa-shopping-cart"></i> Comprar</button></a></center></td>
                <% } } else { %>
                <td><center><a href="curso_editar.jsp?i=<%=cursos.get(i).getIdCurso()%>"><button><i class="fas fa-edit"></i> Editar</button></a></center></td>
                <td><center><a href="cursos.jsp?e=<%=cursos.get(i).getIdCurso()%>"><button><i class="fas fa-trash"></i> Eliminar</button></a></center></td>
                <% } %>
            </tr>
            <% } %>
        </table>
    </div>
</section>
<%@include file="template.footer.jsp" %>
