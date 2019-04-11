<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Centro de ayuda" />
</jsp:include>  
<section class="one"> 
    <div class="container">
        <!--<center><img src="img/logo-black.png"></center>-->
        <center><h2>Centro de ayuda</h2></center>
    </div>
     
    <div class="container">
        <p>¿Cómo me postulo?</p>
        <p>¿Puedo postularme para un trabajo sin tener algún perfil?</p>
        <p>¿Puedo expandir el periodo de compra en mi plan?</p>
    </div>
    
    <div class="container">
        <p>¿Aún tienes dudas de cómo funciona el sistema? Visita el <a href="/Manual_del_Usuario.pdf" target="_blank">Manual de Usuario</a></p>
    </div>
    
</section>
<%@include file="template.footer.jsp" %>
<a href="mensajes.jsp"></a>