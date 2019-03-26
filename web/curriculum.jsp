<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Curriculum" />
    <jsp:param name="roles" value="empleador" />
</jsp:include>
<section class="one">
    <% if (request.getParameter("a") != null) {
        if (request.getParameter("a").equalsIgnoreCase("subir")) { %>
        <div class="container">
            <h3>Subir curriculum</h3>
            <p>Puedes subir un curriculum en formato PDF para poder postularte a vacantes.</p>
            <form action="SubirCurriculum" method="post" enctype="multipart/form-data" id="uploadCV">
            <center>
                <p>
                    <input type="file" name="cv" required accept="application/pdf">
                </p>
                <p>
                    <button type="submit" class="disableit"><i class="fas fa-file-pdf"></i> Subir</button>
                </p>
            </center>
            </form>
        </div>
        <script>
        $(function() {
            $("#uploadCV").on("submit", function () {
                $(".disableit").prop("disabled", true);
                $(this).ajaxSubmit({
                    method: "POST",
                    success: function(data, status, xhr) {
                        window.location = "vacante.jsp?v=<%=request.getParameter("v")%>";
                    },
                    error: function(xhr, status, error) {
                        console.log(xhr);
                        alertify.warning(status);
                    }
                });
                return false;
            });
        });
        </script>
        <% }  else if (request.getParameter("a").equalsIgnoreCase("generar")) { %>
        <div class="container">
            <h3>Generar curriculum</h3>
            <center><p>
                <input type="radio" name="secc" data-secc="el" checked> Experiencia laboral
                <input type="radio" name="secc" data-secc="fa"> Formación académica
                <input type="radio" name="secc" data-secc="i"> Idiomas
                <input type="radio" name="secc" data-secc="df"> Datos familiares
                <input type="radio" name="secc" data-secc="rp"> Referencias personales
            </p></center>
        </div>
        
        <div id="cv-el" class="container">
            <h3>Experiencia laboral</h3>
        </div>
        
        <div id="cv-fa" class="container" style="display: none">
            <h3>Formación académicaa</h3>
        </div>
        
        <div id="cv-i" class="container" style="display: none">
            <h3>Idiomas</h3>
        </div>
        
        <div id="cv-df" class="container" style="display: none">
            <h3>Datos familiares</h3>
        </div>
        
        <div id="cv-rp" class="container" style="display: none">
            <h3>Referencias personales</h3>
        </div>
        <% } else { response.sendRedirect("perfil.jsp"); } %>
    <% } else { response.sendRedirect("perfil.jsp"); } %>
</section>
<%@include file="template.footer.jsp" %>
