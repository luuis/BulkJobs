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
    <form action="" method="post"  autocomplete="off" data-parsley-errors-messages-disabled class="validate">
        <div class="container">
            <center><h2>Catalogo Vacantes</h2></center>
            <style>
                .pcv{
                    display: none;
                    
                }
            </style>
            
            <script>
            $(function() {
                
             $('.apc').on('click', function() {
                 var v = $(this).data('pc');
                 $('#pc-'+v).slideToggle();
             });
            });    
            </script>
            
            <% 
            Date fechaActual = new Date();
            ArrayList<PlanComprado> planesComprados = PlanComprado.obtenerPlanCompradoRecl(sesion.getId());
            if(planesComprados.size() > 0){
                for(PlanComprado pc : planesComprados){
                    ArrayList<Vacante> av = Vacante.obtenerVacantesDelPlan(pc.getId()); %>
            <article>
                <table width="100%">
                    <tr>
                        <% if (fechaActual.before(pc.getFechaLimite())) { %>
                        <td>
                            <h4>Plan <%= pc.getPlan().getNombre()%> &middot;
                                Comprado el <%=pc.getFecha() %> &middot;
                                Caduca el <%=pc.getFechaLimite() %> - Activo</h4>
                                <p><%=av.size() %> de <%=pc.getPlan().getVacantes()%> Vacantes Agregadas<p>
                        </td>
                        <td style="text-align: right">
                            <a><button type="button" class="apc" data-pc="<%=pc.getId()%>"><i class="fas fa-plus"></i></button></a>
                            <% if (av.size() < pc.getPlan().getVacantes()) { %> 
                            <a href="ingresarVacantes.jsp?p=<%=pc.getId()%>"><button type="button"><i class="fas fa-address-card"></i> Agregar Vacante</button></a>
                            <% } %>
                            <a href="comprar.jsp?rp=<%=pc.getId()%>"><button type="button"><i class="far fa-clock"></i> Agregar Tiempo</button></a>
                        </td>  
                        <% } else { %>
                        <td>
                            <h4>Plan <%= pc.getPlan().getNombre()%> &middot; Caducado
                            <p><%=av.size() %> de <%=pc.getPlan().getVacantes()%> Vacantes Agregadas<p>
                        </td>
                        <td style="text-align: right">
                            <a><button type="button" class="apc" data-pc="<%=pc.getId()%>"><i class="fas fa-plus"></i></button></a>
                            <a href="comprar.jsp?rp=<%=pc.getId()%>"><button type="button"><i class="far fa-clock"></i> Renovar</button></a>
                        </td> 
                        <% } %>
                    </tr>
                </table>
            </article>
            
            <article class="pcv" id="pc-<%=pc.getId()%>">
                <% for(Vacante v : av){ %>
                <p><%=v.getTitulo()%> - <a href="vacante.jsp?v=<%=v.getId()%>">Ver más</a> - <a href="postulados.jsp?v=<%=v.getId()%>">Ver postulados</a></p>
                <% } %>
            </article>
            
                <%}
                    }else{

                }
                    %>
            
        </div>   
    </form>
</section>
<%@include file="template.footer.jsp" %> 
