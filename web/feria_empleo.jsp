<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="" />
</jsp:include> 
<section class="one">
    <form action="/FeriaEmpleo" method="post" enctype="multipart/form-data" autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">  
            <center><img src="img/logo-black.png"></center>
            <center><h2>Agregar Feria de Empleo</h2></center>
        </div> 
         
        <%
            if (request.getParameter("m") != null) {
                    if (request.getParameter("m").equalsIgnoreCase("Registro")) {
                        out.println("<script>alertify.success('Se agrego la Feria de Empleo correctamente!')</script>");
                    }
                }  
            %>
        
        <center>
        <div class="container">        
             <p>
                <label>Imagen (Feria de Empleo) <font color="red">*</font></label>
                <input type="file" required name="img">
             </p>
             
             <p>
                <label>Nombre</label>
                <input type="text" required name="nombre" placeholder="Nombre Imagen">
             </p>
             
        </div>

        <div class="container">
             <button type="submit" name="boton" value="Agregar">Añadir</button>
        </div> 
        
        </center>
    </form>
</section>
<%@include file="template.footer.jsp" %>
