<%@page import="bean.PlanPComprado"%>
<%@page import="bean.FeriaDeEmpleo"%>
<%@page import="bean.Categoria"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Vacante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Búsqueda" />
</jsp:include>
<section class="two small">
    <div class="big">
        <div class="container">
            <h2>Vacantes</h2>
            <% String fTitulo = (request.getParameter("titulo") != null) ? request.getParameter("titulo") : "";
            String fZona = (request.getParameter("zona") != null) ? request.getParameter("zona") : "";
            int fCategoria = (request.getParameter("categoria") != null) ?
                    Integer.parseInt(request.getParameter("categoria")) : 0;
            ArrayList<Vacante> vacantes = Vacante.obtenerVacantes(fTitulo, fZona, fCategoria);
            if (vacantes.size() > 0) {
                for (Vacante v : vacantes) { %>
                <article>
                    <h3><%=v.getTitulo()%></h3>
                    <p>
                        <strong><%=v.getCategoria().getNombre()%></strong> &middot;
                        <%=v.getCompra().getCuenta().getDireccionMunicipio()%>, <%=v.getCompra().getCuenta().getDireccionEstado()%> &middot; <a href="vacante.jsp?v=<%=v.getId()%>">Leer más...</a></p>
                </article>
                <% }
            } else { %>
            <center><em>No hay vacantes disponibles</em></center>
            <% } %>
        </div>
    </div>
    <div class="small">
        <div class="container">
            <h3><i class="fas fa-filter fa-fw"></i> Filtros</h3>
            <form action="busqueda.jsp" method="get" autocomplete="off">
                <p>
                    <label for="bsqTitulo">Título</label>
                    <input id="bsqTitulo" type="text" name="titulo" value="${param.titulo}">
                </p>
                <p>
                    <label for="bsqZona">Zona</label>
                    <input id="bsqZona" type="text" name="zona" value="${param.zona}">
                </p>
                <hr>
                <p>
                    <label for="bsqCategoria">Categoría</label>
                    <select id="bsqCategoria" name="categoria">
                        <option value="0">Cualquiera</option>
                        <% ArrayList<Categoria> categorias = Categoria.obtenerCategorias();
                        for (Categoria c : categorias) { %>
                            <option value="<%=c.getId()%>"><%=c.getNombre()%></option>
                            <% for (Categoria sc : c.getSubcategorias()) { %>
                            <option value="<%=sc.getId()%>"> | <%=sc.getNombre()%></option>
                            <% }
                        } %>
                    </select>
                </p>
                <p><center><button type="submit">Filtrar</button></center></p>
            </form>
        </div>
        <%
        PlanPComprado ppc = PlanPComprado.obtenerP();
        if (ppc != null) { %>
            <div class="container">
                <a href="<%=ppc.getVinculo()%>" target="_blank">
                    <img src="subida/publicidad/<%=ppc.getId()%>.jpg" width="100%" height="300px">
                </a>
            </div>
        <% }
        FeriaDeEmpleo fde = FeriaDeEmpleo.obtenerFeria();
        if(fde != null){ %>
            <div class="container">
                <div id="photo" title="<%= fde.getNombre()%>" style="background-image: url('subida/ferias/<%=fde.getIdFeria()%>.jpg');"></div> 
            </div>
        <% } %>
    </div>
</section>
<%@include file="template.footer.jsp" %>
