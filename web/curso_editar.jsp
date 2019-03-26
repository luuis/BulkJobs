<%@page import="bean.Curso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion");
   %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="EditarCursos" />
</jsp:include>
<section class="one">
    <form action="" method="post" autocomplete="off" data-parsley-errors-messages-disabled>
    <div class="container">
        <center><img src="img/logo-black.png"></center>
        <br/>
        <center> <h1>Editar Cursos </center></h1>
    </div>
    <%  Curso c = Curso.obtenerCurso(Integer.parseInt(request.getParameter("i")));
        if (request.getParameter("boton") != null){
    
        
        
            Curso curso = new Curso(sesion.getId(), request.getParameter("nombre"), request.getParameter("desc"), Double.parseDouble(request.getParameter("precio")));
            boolean editar = curso.editar(Integer.parseInt(request.getParameter("i")));
            
            
            if(editar){
                 response.sendRedirect("/cursos.jsp?m=editado");
                } else {
                    out.println("<script>alertify.error('Comprueba los datos ingresados e intentalo de nuevo');</script>");
                
            }
    }
    
    
    
    
    
    




%>
    <div class="container">
        <center>
            <p>
                <label>Nombre:  <font color="red">*</font></label>
                <input type="text" required name="nombre" value="<%=c.getNombre()%>">
            </p>
            
            <p>
                <label>Descripción:  <font color="red">*</font></label>
                <textarea required name="desc"><%=c.getDesc()%></textarea>
            </p>
            
            <p>
                <label>Precio:  <font color="red">*</font></label>
                <input type="text" required name="precio" value="<%=c.getPrecio()%>">
            </p>
            
           
        </center>
    </div>
        
        <div class="container">
            <center><button type="submit" name="boton" value="editar">Editar</button></center> 
        </div>
            
        
    </form>
</section>
<%@include file="template.footer.jsp" %>
