<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="" />
</jsp:include>
<section class="one">
    <form action="" method="post"  autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">
            <center><img src="img/logo-black.png"></center>
            <center><h2>Catalogo Vacantes</h2></center>
        </div>  
</section>
<%@include file="template.footer.jsp" %> 
