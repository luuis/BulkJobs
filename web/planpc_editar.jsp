<%@page import="bean.PlanPComprado"%>
<%@page import="bean.PlanP"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="" />
</jsp:include>
<section>
        
    <form action="/EditarPu" method="post" enctype="multipart/form-data" autocomplete="off" data-parsley-errors-messages-disabled>
        <div class="container">
            <center><img src="img/logo-black.png"></center>
        <br/>
        <center><h1>Registro de Planes Publicitarios</center></h1>
        </div>
        
       
        
        
        
        
        
        <div class="container">
            <center>
      
                <input type="hidden" value="<%= request.getParameter("i") %>" name="i" >
                
                <p>
                    <label>Imagen:  <font color="red">*</font></label>
                    <input type="file" required name="img">
                </p>
                
               <p>
                    <label>Vinculo:  <font color="red">*</font></label>
                    <input type="url" required name="vinculo">
                </p>
                
            </center>
        </div>
            
        <div class="container">
            <center><button type="submit" name="boton" value="editar">Editar</button></center> 
        </div>

    </form>
    
</section>
<%@include file="template.footer.jsp" %>
