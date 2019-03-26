<%@page import="java.sql.ResultSet"%>
<%@page import="bean.Reclutador"%>
<%@page import="bean.Empleador"%>
<%@page import="java.util.ArrayList"%>
<%@page import="extra.ConexionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Mensajes" />
    <jsp:param name="roles" value="empleador,reclutador" />
</jsp:include>
<% if (sesion != null && sesion.isIniciada()) { %>
<section class="two small">
    <div class="small">
        <div class="container">
            <input type="text" maxlength="150" id="busquedaTermino" placeholder="Buscar o iniciar conversación">
            <input type="hidden" id="busquedaTipo" value="<% out.print(sesion.getTipo()); %>">
            <input type="hidden" id="busquedaRemitente" value="<% out.print(sesion.getId()); %>">
            <h4>Contactos</h4>
            <div class="people" id="contacts">
                
            </div>
            <div id="suggs" style="display: none">
                <h4>Sugerencias</h4>
                <div class="people" id="suggestions">

                </div>
            </div>
        </div>
    </div>
    <div class="big">
        <div class="container" id="messages">
            <% if (request.getParameter("c") != null) {
                ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
                String nombre = "";
                if (sesion.getTipo() == 1) {
                    ArrayList sqlCuenta = new ArrayList();
                    sqlCuenta.add("SELECT recl_nombre_empresa FROM reclutador WHERE recl_cuenta=? LIMIT 1");
                    sqlCuenta.add(request.getParameter("c"));
                    objCBD.consultar(sqlCuenta);
                    ResultSet rsc = objCBD.getCdr();
                
                    if (rsc.next()) {
                        nombre = rsc.getString("recl_nombre_empresa");
                    } else {
                        response.sendRedirect("mensajes.jsp");
                    }
                } else {
                    ArrayList sqlCuenta = new ArrayList();
                    sqlCuenta.add("SELECT empl_nombre, empl_ap_pat, empl_ap_mat FROM empleador WHERE empl_cuenta=? LIMIT 1");
                    sqlCuenta.add(request.getParameter("c"));
                    objCBD.consultar(sqlCuenta);
                    ResultSet rsc = objCBD.getCdr();
                
                    if (rsc.next()) {
                        nombre = rsc.getString("empl_nombre") + " " + rsc.getString("empl_ap_pat") + " " + rsc.getString("empl_ap_mat");
                    } else {
                        response.sendRedirect("mensajes.jsp");
                    }
                } %>
            <h3><%=nombre%></h3>
            <div id="conversation">
                <%
                ArrayList sqlQuery = new ArrayList();
                sqlQuery.add("SELECT * FROM mensajes WHERE (mens_remitente=? AND mens_destinatario=?) OR (mens_remitente=? AND mens_destinatario=?)");
                sqlQuery.add(request.getParameter("c"));
                sqlQuery.add(sesion.getId());
                sqlQuery.add(sesion.getId());
                sqlQuery.add(request.getParameter("c"));
                objCBD.consultar(sqlQuery);
                ResultSet rs = objCBD.getCdr();
                while (rs.next()) { %>
                <div id="msg-<% out.print(rs.getInt("id_mensaje")); %>" class="message <%=(rs.getInt("mens_remitente")==sesion.getId())?"sended":"received"%>" title="<%= rs.getString("mens_fecha_hora") %>">
                    <% if (rs.getString("mens_texto") != null && !rs.getString("mens_texto").trim().isEmpty()) { %>
                    <div class="text"><%=rs.getString("mens_texto") %></div><% } %>
                    <% if (rs.getString("mens_archivo") != null && !rs.getString("mens_archivo").trim().isEmpty()) { %>
                    <div class="text"><a target="_blank" href="subida/mensajes/<%=rs.getString("mens_archivo")%>">
                        <i class="fas fa-arrow-circle-down fa-fw"></i> <%=rs.getString("mens_archivo")%>
                    </a></div><% } %> <!-- .substring(17) -->
                </div>
                <% } %>
            </div>
            <form action="Mensaje" enctype="multipart/form-data" method="post" id="sendMsg" autocomplete="off">
                <div id="newmessage">
                    <div id="text">
                        <input type="text" name="texto" placeholder="Escribir un mensaje">
                        <input type="hidden" name="destinatario" value="<%=request.getParameter("c")%>">
                    </div>
                    <div id="file">
                        <input id="fileBut" accept="application/pdf" type="file" name="archivo" class="hideFile">
                        <button class="disableit" type="button"><label for="fileBut">
                            <i class="far fa-clipboard fa-fw"></i> <span id="fn">Adjuntar PDF</span>
                        </label></button>
                    </div>
                    <div id="send">
                        <button class="disableit" name="enviar" type="submit">
                            <i class="fas fa-envelope fa-fw"></i> Enviar
                        </button>
                    </div>
                </div>
            </form>
            <script>
            $(function() {
                $("#sendMsg").on("submit", function () {
                    $(".disableit").prop("disabled", true);
                    $(this).ajaxSubmit({
                        method: "POST",
                        success: function(data, status, xhr) {
                            window.location = "mensajes.jsp?c=<%=request.getParameter("c")%>";
                        },
                        error: function(xhr, status, error) {
                            console.log(xhr);
                            alertify.warning(status);
                        }
                    });
                    return false;
                });
                
                $("#fileBut").on('change', function() {
                    var fn = $(this).val().split(/(\\|\/)/g).pop();

                    console.log(fn);
                    $("#fn").text(fn);
                });
            });
            </script>
            <% 
                ArrayList sqlRead = new ArrayList();
                sqlRead.add("UPDATE mensajes SET mens_leido=1 WHERE mens_remitente=? AND mens_destinatario=?");
                sqlRead.add(request.getParameter("c"));
                sqlRead.add(sesion.getId()); 
                objCBD.ejecutarABC(sqlRead);
            } else { %>
            <h3>Selecciona una conversación</h3>
            <% } %>
        </div>
    </div>
</section>
<% } else {
    response.sendRedirect("/sesion.jsp");
} %>
<%@include file="template.footer.jsp" %>
