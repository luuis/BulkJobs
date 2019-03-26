<%@page import="java.sql.Date"%>
<%@page import="bean.Plan"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Editar Plan" />
</jsp:include>
<section class="one">
    <form action="" method="post"  autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">
            <center><img src="img/logo-black.png"></center>
            <center><h2>Editar plan</h2></center>
        </div> 
        
        <%
            Plan c = Plan.obtenerPlan(Integer.parseInt(request.getParameter("i")));
            if(request.getParameter("boton") != null){
                
                
                boolean editar = Plan.editar(Double.parseDouble(request.getParameter("precio")),Integer.parseInt(request.getParameter("i"))); ;
                
                if(editar){
                    response.sendRedirect("planes.jsp?i=editado");
                }else{
                    out.println("<script>alertify.error('Comprueba los datos ingresados e intentalo de nuevo');</script>");
                }  
            }       
            %>
        
        <center>
        <div class="container">
            
            <p>
                <label>Precio</label>
                <input type="number" required name="precio" placeholder="Precio $$" value="<%=c.getPrecio()%>">
            </p>
            
        </div> 
            
        <div class="container">
             <button type="submit" name="boton" value="Agregar">Editar</button>
        </div> 
        </center>
                
    </form>
</section>
<%@include file="template.footer.jsp" %>
