<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Acerca de" />
</jsp:include>
<section class="one">
    <div class="container">
        <center><img src="img/logo-black.png"></center>
        <h2><i class=""></i>Acerca de BulkJobs</h2>
        <p>Somos una empresa bla bla</p>
        <table border="1">
            <tr>
                <th>Hola</th>
                <th>Andy :v</th>
            </tr>
            <tr>
                <td>xD</td>
                <td>lol</td>
            </tr>
        </table>
    </div>
</section>
<%@include file="template.footer.jsp" %>
