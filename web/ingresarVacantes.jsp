<%@page import="bean.Categoria"%>
<%@page import="bean.Vacante"%> 
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Ingresar Vacantes" />
</jsp:include>
<section class="one">
    <form action="" method="post"  autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">
            <center><img src="img/logo-black.png"></center>
            <center><h2>Ingresar Vacantes</h2></center>
        </div>   

        <%
            if (request.getParameter("boton") != null) {

                Vacante vacante = new Vacante(request.getParameter("nombre"),
                        request.getParameter("detalles"), request.getParameter("requisitos"),
                        Double.parseDouble(request.getParameter("paga")),
                        request.getParameter("tipPaga"),
                        Categoria.obtenerCategoria(Integer.parseInt(request.getParameter("categoria"))));

                boolean agregar = vacante.agregarVa();

                if (agregar) {
                    response.sendRedirect("ingresarVacantes.jsp");
                    out.println("<script>alertify.success('Vacante Registrada Correctamente);</script>");
                } else {
                    out.println("<script>alertify.error('Comprueba los datos ingresados e intentalo de nuevo');</script>");
                }

            }

        %> 


        <center>
            <div class="container">  

                <h4>Empresa</h4>
                

                <p>
                <label>Nombre Vacante<font color="red"></font></label>
                <input type="text" required name="nombre" placeholder="Nombre Vacante">
                </p> 

                <p>
                <label for="bsqCategoria">Categoria</label>
                <select id="bsqCategoria" name="categoria">
                    <option value="0">Cualquiera</option>
                    <% ArrayList<Categoria> categorias = Categoria.obtenerCategorias();
                            for (Categoria c : categorias) {%>
                    <option value="<%=c.getId()%>"><%=c.getNombre()%></option>
                    <% for (Categoria sc : c.getSubcategorias()) {%>
                    <option value="<%=sc.getId()%>"> | <%=sc.getNombre()%></option>
                    <% }
                                }
                               %>
                </select>
                </p>

                <p>
                <label>Detalles</label>
                <input type="text" required name="detalles" placeholder="Detalles necesarios">
                </p>

                <p>
                <label>Requisitos</label>
                <input type="text" required name="requisitos" placeholder="Descripción">
                </p>

                <p>
                <label>Sueldo a Ofrecer</label>
                <input type="text" required name="paga" placeholder="Sueldo ej. 1500">
                </p>

                <p>
                <label>Tipo de paga</label>
                <select name="tipPaga">
                    <option>Selecciona</option> 
                    <option value="S">Semanal</option> 
                    <option value="Q">Quincenal</option>
                    <option value="M">Mensual</option>
                </select>
                </p>




                <p>
                <label>Imagen</label>
                </p>
            </div> 

            <div class="container">
                <button type="submit" name="boton" value="Ingresar">Ingresar</button>
            </div> 
        </center>

    </form>    
</section>

<%@include file="template.footer.jsp" %>
