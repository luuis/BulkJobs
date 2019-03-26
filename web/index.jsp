<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Inicio" />
    <jsp:param name="body" value="landing" />
</jsp:include>
    <video autoplay muted loop poster="img/bg.jpg">
        <source src="img/bg.mp4" type="video/mp4">
    </video>
    <section>
        <div id="logo"><a href="/"><img src="img/logo-white.png"></a></div>
        <div id="landing-search">
            <div id="label">Busca el trabajo de tus sueños</div>
            <div id="field">
                <form action="busqueda.jsp" method="get">
                    <input type="text" placeholder="ej. Programación web" name="titulo">
                    <input type="text" placeholder="ej. Tecámac, Edo. Méx." name="zona">
                    <button type="submit">Buscar</button>
                </form>
            </div>
        </div>
    </section>
<%@include file="template.footer.jsp" %>
