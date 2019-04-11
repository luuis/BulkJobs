<%@page import="bean.PlanComprado"%>
<%@page import="bean.Categoria"%>
<%@page import="bean.Vacante"%> 
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Ingresar Vacantes" />
</jsp:include>
<section class="one">
    <form action="/IngresarVacantes" method="post"  enctype="multipart/form-data"  autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">
            <center><img src="img/logo-black.png"></center>
            <center><h2>Ingresar Vacantes</h2></center>
        </div> 
        <% if (request.getParameter("m") != null) {
            if (request.getParameter("m").equalsIgnoreCase("Registro")) {
                out.println("<script>alertify.success('Se agrego la vacante correctamente!')</script>");
            }
        } %>
        <center>
            <div class="container">  
                <h4>Ingresar vacante</h4>
                <input type="hidden" name="p" value="<%=request.getParameter("p")%>">
                <p>
                    <label>Ttulo de la vacante<font color="red"></font></label>
                    <input type="text" required name="nombre" placeholder="Título de la vacante">
                </p> 
                <p>
                    <label for="bsqCategoria">Categoria</label>
                    <select id="bsqCategoria" name="categoria">
                        <% ArrayList<Categoria> categorias = Categoria.obtenerCategorias();
                        for (Categoria c : categorias) {%>
                        <option value="<%=c.getId()%>"><%=c.getNombre()%></option>
                            <% for (Categoria sc : c.getSubcategorias()) {%>
                            <option value="<%=sc.getId()%>"> | <%=sc.getNombre()%></option>
                            <% }
                        } %>
                    </select>
                </p>
                <p>
                    <label>Detalles</label>
                    <textarea required name="detalles" rows="6" placeholder="Detalles"></textarea>
                </p>
                <p>
                    <label>Requisitos</label>
                    <textarea required name="requisitos" rows="6" placeholder="Requisitos"></textarea>
                </p>
                <p>
                    <label>Sueldo a Ofrecer</label>
                    <input type="text" required name="paga" placeholder="Sueldo">
                </p>
                <p>
                    <label>Tipo de paga</label>
                    <select name="tipPaga">
                        <option>Selecciona</option> 
                        <option value="S">Semanal</option> 
                        <option value="Q">Quincenal</option>
                        <option value="M">Mensual</option>
                    </select>
                </p>
                <p>   
                    <label>Imagen <font color="red">*</font></label>
                    <input type="file" required name="img">
                </p>  
            </div>
            <div class="container">
                <button type="submit" name="boton" value="Ingresar">Ingresar</button>
            </div> 
        </center>
    </form>    
</section>
<%@include file="template.footer.jsp" %>
