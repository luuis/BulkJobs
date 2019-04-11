<%@page import="java.io.File"%>
<%@page import="bean.PlanComprado"%>
<%@page import="bean.Categoria"%>
<%@page import="bean.Vacante"%> 
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Ingresar Vacantes" />
</jsp:include>
<% Vacante v = Vacante.obtenerVacante(Integer.parseInt(request.getParameter("v")));
if (v != null) { %>
<section class="one">
    <form action="/ModificarVacante" method="post"  enctype="multipart/form-data"  autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">
            <center><img src="img/logo-black.png"></center>
            <center><h2>Modificar Vacante</h2></center>
        </div> 
        <% if (request.getParameter("m") != null) {
            if (request.getParameter("m").equalsIgnoreCase("Registro")) {
                out.println("<script>alertify.success('Se agrego la vacante correctamente!')</script>");
            }
        } %>
        <center>
            <div class="container">  
                <h4>Editar vacante</h4>
                <input type="hidden" name="id" value="<%=v.getId()%>">
                <p>
                    <label>Título de la vacante<font color="red"></font></label>
                    <input type="text" required name="nombre" placeholder="Título de la vacante" value="<%=v.getTitulo()%>">
                </p> 
                <p>
                    <label for="bsqCategoria">Categoria</label>
                    <select id="bsqCategoria" name="categoria">
                        <% ArrayList<Categoria> categorias = Categoria.obtenerCategorias();
                        for (Categoria c : categorias) {%>
                        <option <%=(c.getId() == v.getCategoria().getId())?"selected":""%> value="<%=c.getId()%>"><%=c.getNombre()%></option>
                            <% for (Categoria sc : c.getSubcategorias()) {%>
                            <option <%=(sc.getId() == v.getCategoria().getId())?"selected":""%> value="<%=sc.getId()%>"> | <%=sc.getNombre()%></option>
                            <% }
                        } %>
                    </select>
                </p>
                <p>
                    <label>Detalles</label>
                    <textarea required name="detalles" rows="6" placeholder="Detalles"><%=v.getDetalles()%></textarea>
                </p>
                <p>
                    <label>Requisitos</label>
                    <textarea required name="requisitos" rows="6" placeholder="Requisitos"><%=v.getRequisitos()%></textarea>
                </p>
                <p>
                    <label>Sueldo a Ofrecer</label>
                    <input type="text" required name="paga" placeholder="Sueldo" value="<%=v.getPaga()%>">
                </p>
                <p>
                    <label>Tipo de paga</label>
                    <select name="tipPaga">
                        <option <%=(v.getTipoPaga().equalsIgnoreCase("S"))?"selected":""%> value="S">Semanal</option> 
                        <option <%=(v.getTipoPaga().equalsIgnoreCase("Q"))?"selected":""%> value="Q">Quincenal</option>
                        <option <%=(v.getTipoPaga().equalsIgnoreCase("M"))?"selected":""%> value="M">Mensual</option>
                    </select>
                </p>
                <center>
                    <% String contextPath = getServletContext().getRealPath(File.separator);
                    File fotoC = new File(contextPath + "../../web/subida/vacantes/" + v.getId()+ ".jpg");
                    if (fotoC.exists()) { %>
                    <div id="picture" style="background-image: url('subida/vacantes/<%=v.getId()%>.jpg');"></div>
                    <% } else { %>
                    <div id="picture"></div>
                    <% } %>
                </center>
                <p>   
                    <label>Imagen <font color="red">*</font></label>
                    <input type="file" name="img">
                </p>  
            </div>
            <div class="container">
                <button type="submit" name="boton" value="actualizar">Actualizar</button>
            </div> 
        </center>
    </form>    
</section>
<% } else { response.sendRedirect("busqueda.jsp"); } %>
<%@include file="template.footer.jsp" %>
