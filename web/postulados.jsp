<%@page import="java.util.ArrayList"%>
<%@page import="bean.Postulacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Postulados" />
</jsp:include>
<section class="one">    
    <% ArrayList <Postulacion> postulaciones =
            Postulacion.obtenerPostulado(Integer.parseInt(request.getParameter("v")));
    %>
    <div class="container">
        <center>
            <h2>Lista de Postulados</h2>
        </center 
    </div> 
    
    <div class="container">
        
        <article>
            <table width="100%">
                <%  if (postulaciones.size() > 0) {
                    for (Postulacion ps : postulaciones) { %>
                <tr id="post-<%=ps.getId()%>" data-post="<%=ps.getId()%>"> 
                    <td>
                        <h3>Nombre del postulado</h3>
                        <p><%= ps.getCuenta().getNombre() %></p>
                        <h3>Comentario</h3>
                        <p><%= ps.getComentario() %></p>
                    </td>
                    <td>
                        <center>
                            <p><a href="CV?c=<%=ps.getCurriculum().getId()%>" target="_blank">
                                <button type="button">Descargar Curriculum</button>
                            </a></p>
                            <p><button class="postAceptar" type="button"><i class="fas fa-check fa-fw"></i> Aceptar</button></p>
                            <p><button class="postRechazar" type="button"><i class="fas fa-times fa-fw"></i> Rechazar</button>
                            </p>
                        </center>
                    </td>
                   
                </tr>
                <% }
                } else { %>
                No hay postulados en este momento
                <% } %>
            </table>
        </article>
    
    </div>
    
    <script>
        $(function() {
            $(".postAceptar").on("click", function () {
                var post = $(this).parents("tr").data("post");
                console.log(post);
                $.post('/EstatusPostulado', { 
                   postu: post, 
                   status: "A"
                }, function(data, status) {
                    alertify.success(data); //alertify no es nativa, es una libreria
                    $("tr#post-"+post).remove();
                });
            }); 
            
            $(".postRechazar").on("click", function () {
                var post = $(this).parents("tr").data("post");
                $.post('/EstatusPostulado', { 
                   postu: post, 
                   status: "R"
                }, function(data, status) {
                    alertify.warning(data); //alertify no es nativa, es una libreria
                    $("tr#post-"+post).remove();
                });
            });
        });
    </script>
        

        
</section>|
<%@include file="template.footer.jsp" %>
