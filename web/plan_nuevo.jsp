<%@page import="java.sql.Date"%>
<%@page import="bean.Plan"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Agregar Plan" />
</jsp:include> 
<section class="one">
    <form action="/PlanNuevo" method="post" enctype="multipart/form-data" autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">
            <center><img src="img/logo-black.png"></center>
            <center><h2>Agregar Plan</h2></center>
        </div>  
        <% if (request.getParameter("m") != null) {
            if (request.getParameter("m").equalsIgnoreCase("Registro")) {
                out.println("<script>alertify.success('Se Agrego el plan correctamente!')</script>");
            } 
        } %>
        <center>
        <div class="container">
            <p>
                <label>Nombre Plan<font color="red">*</font></label> 
                <input type="text" required name="nombre"  placeholder="Nombre Plan">
            </p>
            <p>
                <label>Descripción</label>
                <textarea required name="descripcion" placeholder="Descripción"></textarea>
            </p>
            <p>
                <label>Precio</label>
                <input type="number" required name="precio" placeholder="Precio $$">
            </p>
            <p>
                <label>Número de Vacantes</label>
                <input type="text" required name="vacantes" placeholder="No. Vacantes">
            </p>
            <p>
                <label>Tiempo de Vacantes<font color="red">*</font></label>
                <select name="tiempo" placeholder="Duracion">
                    <option value="2592000">1 mes</option> 
                    <option value="5184000">2 meses</option>
                    <option value="7776000">3 meses</option>
                </select>
            </p>
            <p>
                <label>Imagen  <font color="red">*</font></label>
                <input type="file" required name="img">
            </p>
        </div>  
            
        <div class="container">
             <button type="submit" name="boton" value="Agregar">Agregar</button>
        </div> 
        </center>
                 
    </form>
</section>
<%@include file="template.footer.jsp" %>
