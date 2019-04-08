<%@page import="bean.Curso"%>
<%@page import="bean.CursoInscrito"%>
<%@page import="bean.Calificacion"%>
<%@page import="java.io.File"%>
<%@page import="bean.CursoComprado"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Curriculum"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bean.Vacante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Detalles del Curso" />
</jsp:include>
<% CursoComprado c = null; 
try {
    c = CursoComprado.obtenerCurso(Integer.parseInt(request.getParameter("c")));  
    /*Llamo al Bean y obtengo el curso con el parametro que acabo de crear y lo convierto en entero porque asid ebe de ser*/
    /*Obtengo el error traducido :v y pues lo muestro*/
} catch (Exception e) {
    out.println("<script>alertify.error('" + e.getLocalizedMessage() + "');</script>");
}
if ( c != null) { %>
<section class="two small">
    <div class="big">
        <div class="container">
            <h3><%=c.getCurso().getNombre() %></h3>
            <h4>Detalles</h4>
            <p><%=c.getCurso().getDesc()%></p>
            <h4>Precio</h4>
            <p><%=c.getCurso().getPrecio() %></p>
        </div>
            
        <div class="container" >
            <% if (sesion.isIniciada()) {
                
             if(CursoInscrito.estaInscrito(c.getCurso().getIdCurso(), sesion.getId())) {
                if (CursoInscrito.estaTerminado(c.getCurso().getIdCurso(), sesion.getId())) { %>
                    <center><p>
                        Calificación: <%= Calificacion.obtenerPromC(Integer.parseInt(request.getParameter("c"))) %>
                    </p></center>
                    <input type="hidden" id="idCuenta" value="<%= sesion.getId()%>">
                    <input type="hidden" id="idCurso" value="<%= c.getId()%>">
                    <center id="calif">
                        <i title="Lametable" data-calif="1" class="far fa-thumbs-up fa-lg fa-fw"></i>
                        <i title="Malo" data-calif="2" class="far fa-thumbs-up fa-lg fa-fw"></i>
                        <i title="Regular" data-calif="3" class="far fa-thumbs-up fa-lg fa-fw"></i>
                        <i title="Me gusto" data-calif="4" class="far fa-thumbs-up fa-lg fa-fw"></i>
                        <i title="Excelente" data-calif="5" class="far fa-thumbs-up fa-lg fa-fw"></i>
                    </center>
                <% } else { %>
                   
                 <center><p>
                         <input id="cur" type="hidden" value="<%= c.getId()%>">
                    <a id="fin">
                        <button id="finalizar" type="button"><i class="fas fa-check"></i> Finalizar Curso</button>
                    </a>
                </p></center>
                
                <% }
            } %>
            
            <% } else { %>
            <center><p>
                Calificación: <%= Calificacion.obtenerPromC(Integer.parseInt(request.getParameter("c"))) %>
            </p></center>
            <% } %>
            
            
            
        </div>
        
        <div class="container">
            <% ArrayList<Calificacion> calificaciones = Calificacion.obtenerEvaluacionesC2(request.getParameter("c"));
            if(calificaciones.size() > 0){
                for(Calificacion ca : calificaciones){%>
                <article>
                    <strong><%= ca.getCaliCa().getNombre() %></strong><br><%= ca.getComen() %>
                </article>
                <% }
            } else {
                out.println("Se el primero en evaluar");
            } %>
        </div>
        <div class="container">
            <% String contextPath = getServletContext().getRealPath(File.separator);
            File fotoC = new File(contextPath + "../../web/subida/cursos/" + c.getCurso().getIdCurso()+ ".jpg");
            if (fotoC.exists()) { %>
            <div id="photo" style="background-image: url('subida/cursos/<%=c.getCurso().getIdCurso() %>.jpg');"></div>
            <% } else { %>
            <div id="photo"></div>
            <% } %></td>
        </div>
    </div>
    
    <div class="small">
        <div class="container">
            <h4>Capacitador</h4>
            <p><%=c.getCurso().getCuenta().getNombre() %></p>
            <p><a href="perfil.jsp?c=<%=c.getCurso().getCuenta().getId()%>">
                <button type="button">Ver perfil</button>
            </a></p>
        </div>
        <% if (sesion != null && sesion.isIniciada() && sesion.esEmpleador()) {
                if(!CursoInscrito.estaInscrito(c.getCurso().getIdCurso(), sesion.getId())){ %>
            <div class="container">
                <h3>Inscribirse</h3>
                <center><p>
                    <input id="cue" type="hidden" value="<%= sesion.getId() %>">
                    <input id="cur" type="hidden" value="<%= c.getCurso().getIdCurso()%>">
                    <a id="insc">
                        <button id="inscribirte" type="button"><i class="fas fa-briefcase"></i> Inscribirse</button>
                    </a>
                </p></center>
            </div>
            <% 
            }
        } else { %>
        <div class="container">
            <center>Debes iniciar sesión como un empleador para Inscribirte</center>
        </div>
        <% } %>
    </div>
</section>
<% } else { response.sendRedirect("cursos.jsp"); } %>
<%@include file="template.footer.jsp" %>
