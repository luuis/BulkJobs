<%@page import="bean.PlanP"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="" />
</jsp:include>
<section>
        
    <form action="" method="post" autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">
            <center><img src="img/logo-black.png"></center>
        <br/>
        <center><h1>Registro de Planes Publicitarios</center></h1>
        </div>
        
        <% 
    PlanP p = PlanP.obtenerPlanP(Integer.parseInt(request.getParameter("i")));
        if (request.getParameter("boton") != null){
    
        
        
            PlanP planP = new PlanP(sesion.getId(), request.getParameter("nombre"), 
                    request.getParameter("desc"), Double.parseDouble(request.getParameter("precio")), 
                    Integer.parseInt(request.getParameter("tiempo")));
            boolean editar = planP.editar(Integer.parseInt(request.getParameter("i")));
            
            
            if(editar){
                 response.sendRedirect("/planesp.jsp?m=editado");
                } else {
                    out.println("<script>alertify.error('Comprueba los datos ingresados e intentalo de nuevo');</script>");
                
            }
    }




        %>
        
        
        
        
        
        <div class="container">
            <center>
                <p>
                    <label>Nombre: <font color="red">*</font></label>
                    <input type="text" required="" name="nombre" value="<%=p.getNombre()%>">
                </p>
                
                <p>
                    <label>Descripcion del plan: <font color="red">*</font></label>
                    <textarea required name="desc"><%=p.getDesc()%></textarea>
                </p>
                
                <p>
                    <label>Precio: <font color="red">*</font></label>
                    <input type="text" required name="precio" value="<%=p.getPrecio()%>">
                </p>
                
                <p>
                    <label>Tiempo del plan: <font color="red">*</font></label>
                    <input type="text" required name="tiempo" value="<%=p.getTiempo()%>">
                </p>
                
                
            </center>
        </div>
            
        <div class="container">
            <center><button type="submit" name="boton" value="registrar">Registrar</button></center> 
        </div>

    </form>
    
</section>
<%@include file="template.footer.jsp" %>
