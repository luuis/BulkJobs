<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Acerca de" />
</jsp:include>
<section class="one">
    <div class="container">
        <center><img src="img/logo-black.png"></center>
        <h2><i class=""></i>Acerca de BulkJobs</h2>
        <p><strong>BulkJobs</strong> es un sistema Web que se encarga de hacer más fácil la búsqueda de empleo en el municipio de Tecámac.</p>
        <p>La finalidad de este manual es la comprensión de lo que se ha ido desarrollando a lo largo de estos 4 meses, una descripción de todas las herramientas utilizadas y la manera en la que fueron aplicadas para la mejor comprensión del sistema creado, también a través de los diagramas se podrá ver si realmente el sistema fue desarrollado como se pensó en un principio o si en caso contrario a lo largo del tiempo de realización del sistema web algunas cosas se fueron cambiando.</p>
        <p>Este sistema web es pensado para todas aquellas empresas que quieran difundir sus empresas y subir las vacantes que ofrecen, capacitadores ofreciendo sus cursos para que tanto empresas como empleadores puedan comprar y una de las principales razones es para que aquellos nuevos empleadores tengan más opciones para elegir el lugar donde puedan trabajar en un futuro, haciendo la búsqueda tanto para las empresas como para los empleadores más eficaz </p>
        <h3>Un servicio de ALV Systems</h3>
        <img src="/img/alv-systems.png" width="150px" style="float: right">
        <p>ALV Systems nació en el año 2019, dirigida a las empresas del estado de México.  Especializándose en desarrollo de sistemas web innovando procesos y herramientas para la vida cotidiana de miles de usuarios. Desde la fundación ALV Systems ha buscado la innovación en todos los trabajos que ha realizado para así mantenerse a la vanguardia tecnológica actual.</p>
        <p>Siempre buscamos nuevos proyectos y buscamos obtener expansión mediante nuestro trabajo, esperando obtener la total satisfacción de nuestros clientes.</p>
        <p>Para conceptualizarnos más y tener una noción especifica analizaremos el término “Sistema de información” este se define como un conjunto de elementos que interactúan entre sí con un fin común y este permite así obtener información para satisfacer las necesidades de una organización.</p>
    </div>
</section>
<%@include file="template.footer.jsp" %>
