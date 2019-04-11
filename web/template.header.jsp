<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="extra.ConexionBD"%>
<%@page import="bean.Sesion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% if(session.getAttribute("sesion") == null) {
    session.setAttribute("sesion", new Sesion());
}
ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
Sesion sh = (Sesion) session.getAttribute("sesion"); %>
<% if (request.getParameter("roles") != null) {
    if (!sh.tienePermisos(request.getParameter("roles"))) {
        out.print("<script>window.location = 'sesion.jsp';</script>");
    }
} %>
<!DOCTYPE html>
<html>
    <head>
        <% if (request.getParameter("titulo") != null) { %>
        <title>BulkJobs &middot; ${param.titulo}</title>
        <% } else { %>
        <title>BulkJobs</title>
        <% } %>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/alertify.min.css">
        <link rel="stylesheet" href="css/all.min.css">
        <link rel="stylesheet" href="css/tippy.css">
        <link rel="stylesheet" href="css/main.css?<%=System.currentTimeMillis()%>">
        <script src="js/jquery.min.js"></script>
        <script src="js/jquery.form.min.js"></script>
        <script src="js/jquery.creditCardValidator.js"></script>
        <script src="js/jquery.flip.min.js"></script>
        <script src="js/jquery.hotkeys.min.js"></script>
        <script src="js/alertify.min.js"></script>
        <script src="js/tippy.min.js"></script>
        <script src="js/parsley.min.js"></script>
    </head>
    <body class="${param.body}">
        <div id="content">
            <% if (request.getParameter("body") == null ||
                !request.getParameter("body").equalsIgnoreCase("landing")) { %>
            <header>
                <div id="left"><a href=""><img src="img/logo-dark.png" alt="BulkJobs" height="24px"></a></div>
                <div id="right">
                    <ul id="menu" role="navigation">
                        <li><a href="/">Inicio</a></li>
                        <li><a href="busqueda.jsp">Búsqueda</a></li>
                        <% if (sh != null && sh.isIniciada()) { %>
                        <li>
                            <a href="cursos.jsp" aria-haspopup="true">Cursos</a>
                            <% if (sh.esCapacitador()) { %>
                            <ul class="dropdown" aria-label="submenu">
                                <li><a href="cursos.jsp">Administrar</a></li>
                                <li><a href="curso_nuevo.jsp">Agregar</a></li>
                            </ul><% } %>
                        </li>
                        <li>
                            <a href="planes.jsp" aria-haspopup="true">Planes</a>
                            <% if (sh.esAdmin()) { %>
                            <ul class="dropdown" aria-label="submenu">
                                <li><a href="planes.jsp">Administrar planes</a></li>
                                <li><a href="plan_nuevo.jsp">Agregar plan</a></li>
                            </ul><% } %>
                        </li>
                        <% if (sh.esCapacitador() || sh.esReclutador() || sh.esAdmin()) { %>
                        <li>
                            <a href="planesp.jsp" aria-haspopup="true">Publicidad</a>
                            <ul class="dropdown" aria-label="submenu">
                                <% if (sh.esAdmin()) { %>
                                <li><a href="planesp.jsp">Administrar Planes Publicitarios</a></li>
                                <li><a href="planp_nuevo.jsp">Agregar Plan Publicitario</a></li>
                                <% } else { %>
                                <li><a href="publicidad.jsp">Mis anuncios</a></li>
                                <% } %>
                            </ul>
                        </li>
                        <% }
                        if (sh.esAdmin()) { %>
                        <li><a href="feria_empleo.jsp">Feria de Empleo</a></li>
                        <% }
                        if (sh.esReclutador()) { %>
                        <li><a href="vacantes.jsp">Mis vacantes
                            <sup style="color: firebrick;"><i class="fas fa-exclamation-circle"></i></sup>
                        </a></li><% }
                        } else { %>
                        <li><a href="sesion.jsp">Iniciar sesión</a></li>
                        <li><a href="sesion.jsp?f=registro">Regístrarse</a></li>
                        <% } %>
                    </ul>
                    <% if (sh != null && sh.isIniciada()) { %>
                    <ul id="menu">
                        <li><a href="perfil.jsp?c=<%=sh.getId()%>" title="Perfil">
                                <i class="fas fa-user fa-fw"></i></a></li>
                        <% ArrayList sqlMensajes = new ArrayList();
                        sqlMensajes.add("SELECT count(mens_leido) FROM mensajes "
                                + "WHERE mens_destinatario=? AND mens_leido=0");
                        sqlMensajes.add(sh.getId());
                        objCBD.consultar(sqlMensajes);
                        ResultSet rs = objCBD.getCdr();
                        String mensajes = "";
                        while (rs.next()) {
                            if (rs.getInt(1) > 0) {
                                mensajes = "<sup style='color: firebrick;'><i class='fas fa-exclamation-circle'></i></sup>";
                            }
                        }
                        %>
                        <li><a href="mensajes.jsp" title="Mensajes">
                            <i class="fas fa-envelope fa-fw"></i><%=mensajes%>
                        </a></li>
                        <li><a href="sesion.jsp?f=salir">Cerrar sesión</a></li>
                    </ul>
                    <% } %>
                </div>
            </header>
            <% } %>
