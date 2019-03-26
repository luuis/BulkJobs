<%@page import="bean.Sesion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sf = (Sesion) session.getAttribute("sesion"); %>
            <footer>
                <span id="left">
                    BulkJobs &copy;
                    <%= (new java.text.SimpleDateFormat("Y")).format(new java.util.Date() ) %>
                </span>
                <span id="right">
                    <a href="bulkjobs.jsp">Acerca de BulkJobs</a> &middot;
                    <a href="contacto.jsp">Contáctanos</a> &middot;
                    <% if (sf != null && sf.isIniciada()) { %>
                    <a href="sesion.jsp?f=salir">Cerrar sesión</a> &middot;
                    <% } else { %>
                    <a href="sesion.jsp">Iniciar sesión</a> &middot;
                    <a href="sesion.jsp?f=registro">Regístrarse</a> &middot;
                    <a href="sesion.jsp?f=recuperar">Recuperar contraseña</a> &middot;
                    <% } %>
                    <a href="#">Volver arriba</a>
                </span>
            </footer>
        </div>
    <script src="js/main.js"></script>
    <script>
    tippy('[title]', {
	arrow: true,
	interactive: true,
	theme: "translucent"
    });
    </script>
    </body>
</html>
