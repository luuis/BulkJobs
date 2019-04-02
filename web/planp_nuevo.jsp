<%@page import="bean.PlanP"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="" />
</jsp:include>
<section>
        
    <form action="" method="post" autocomplete="off" data-parsley-errors-messages-disabled class="validate">
        <div class="container">
            <center><img src="img/logo-black.png"></center>
        <br/>
        <center><h1>Registro de Planes Publicitarios</center>
        </div>
        
        <% if (request.getParameter("boton") != null){
    
            PlanP planP = new PlanP(sesion.getId(), request.getParameter("nombre"), request.getParameter("desc"), Double.parseDouble(request.getParameter("precio")), Integer.parseInt(request.getParameter("tiempo")));
            boolean registrarP = planP.RegistrarP();
            
            
            if(registrarP){
                 response.sendRedirect("/planesp.jsp?m=registrado");
                } else {
                    out.println("<script>alertify.error('Comprueba los datos ingresados e intentalo de nuevo');</script>");
                
            }
    }
    
    if (request.getParameter("m") != null) {
                if (request.getParameter("m").equalsIgnoreCase("Registro")) {
                    out.println("<script>alertify.success('Tu Plan Publicitario ha sido registrado')</script>");
                }
            }
    
    




%>
        
        
        
        
        
        <div class="container">
            <center>
                <p>
                    <label>Nombre: <font color="red">*</font></label>
                    <input type="text" required="" name="nombre">
                </p>
                
                <p>
                    <label>Descripcion del plan: <font color="red">*</font></label>
                    <textarea required name="desc"></textarea>
                </p>
                
                <p>
                    <label>Precio: <font color="red">*</font></label>
                    <input type="text" required name="precio">
                </p>
                
                <p>
                    <label>Tiempo del plan: <font color="red">*</font></label>
                    <select name="tiempo">
                        <option value="2678400">1 mes</option>
                    </select>
                </p>
                
                
            </center>
        </div>
            
        <div class="container">
            <center><button type="submit" name="boton" value="registrar">Registrar</button></center> 
        </div>

    </form>
    
</section>
<%@include file="template.footer.jsp" %>