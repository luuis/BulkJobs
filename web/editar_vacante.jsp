<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Editar Vacante" />
</jsp:include>
<section class="one">
    <div class="container">
        <center><img src="img/logo-black.png"></center>
            <br/>
            <center><h1>Modificar Vacantes</center> 
    </div>
    
     
    
</section>
<%@include file="template.footer.jsp" %>
