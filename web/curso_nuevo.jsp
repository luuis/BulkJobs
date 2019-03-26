<%@page import="bean.Curso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion");
   %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Registrar Cursos" />
</jsp:include>
<section class="one">
    <form action="" method="post" autocomplete="off" data-parsley-errors-messages-disabled>
    <div class="container">
        <center><img src="img/logo-black.png"></center>
        <br/>
        <center> <h1>Registrar Cursos </center></h1>
    </div>
    <% if (request.getParameter("boton") != null){
    
            Curso curso = new Curso(sesion.getId(), request.getParameter("nombre"), request.getParameter("desc"), Double.parseDouble(request.getParameter("precio")));
            boolean registrar = curso.Registrar();
            
            
            if(registrar){
                 response.sendRedirect("/cursos.jsp?m=registrado");
                } else {
                    out.println("<script>alertify.error('Comprueba los datos ingresados e intentalo de nuevo');</script>");
                
            }
    }
    
    if (request.getParameter("m") != null) {
                if (request.getParameter("m").equalsIgnoreCase("Registro")) {
                    out.println("<script>alertify.success('Tu curso ha sido registrado')</script>");
                }
            }
    
    




%>
    <div class="container">
        <center>
            <p>
                <label>Nombre:  <font color="red">*</font></label>
                <input type="text" required name="nombre">
            </p>
            
            <p>
                <label>Descripción:  <font color="red">*</font></label>
                <textarea required name="desc"></textarea>
            </p>
            
            <p>
                <label>Precio:  <font color="red">*</font></label>
                <input type="text" required name="precio">
            </p>
            
           
        </center>
    </div>
        
        <div class="container">
            <center><button type="submit" name="boton" value="registrar">Registrar</button></center> 
        </div>
            
        
    </form>
</section>
<%@include file="template.footer.jsp" %>
