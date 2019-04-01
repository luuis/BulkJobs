<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bean.PlanPComprado"%>
<%@page import="bean.PlanP"%>
<%@page import="java.util.Date"%>
<%@page import="bean.PlanComprado"%>
<%@page import="bean.Plan"%>
<%@page import="bean.Movimiento"%>
<%@page import="bean.Banco"%>
<%@page import="bean.Tarjeta"%>
<%@page import="bean.Reclutador"%>
<%@page import="bean.CursoComprado"%>
<%@page import="bean.Curso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Compra" />
    <jsp:param name="roles" value="reclutador" />
</jsp:include>
    <form action="" method="post"  autocomplete="off" data-parsley-errors-messages-disabled class="validate">
<section class="two small">
    <% if (request.getParameter("comprar") != null) {
        if (request.getParameter("tipo").equals("curso")) {
            CursoComprado cc = new CursoComprado(Curso.obtenerCurso(Integer.parseInt(request.getParameter("id"))), Reclutador.obtenerCuenta(sesion.getId()));      
            
            Tarjeta t = Tarjeta.obtenerTarjeta(Long.parseLong(request.getParameter("numero")));
            
            if (t != null) {
                System.out.println(t.getSaldo());
                System.out.println(request.getParameter("total"));
                if (t.getSaldo() >= Double.parseDouble(request.getParameter("total"))) {
                    boolean registrado = cc.registrarC();
            
                    if (registrado) {
                        out.println("<script>alertify.success('Se ha registrado');</script>");
                        t.quitarSaldo(Double.parseDouble(request.getParameter("total")));
                        Movimiento.registrar(t, "Pago de compra: Curso", Double.parseDouble(request.getParameter("total")));
                    } else {
                        out.println("<script>alertify.error('No se ha registrado');</script>");
                    }
            
                } else {
                    out.println("<script>alertify.error('No tines saldo suficiente, intente con otra');</script>");
                }
            } else {
                out.println("<script>alertify.error('La tarjeta no existe');</script>");
            }
        } else if (request.getParameter("tipo").equals("plan")) {
            Date fl = new Date();
            fl.setTime(fl.getTime() + (Long.parseLong(request.getParameter("tiempo")) * 1000)); 
            PlanComprado pc = new PlanComprado(Plan.obtenerPlan(Integer.parseInt(request.getParameter("id"))), Reclutador.obtenerCuenta(sesion.getId()), fl);
            
            Tarjeta t = Tarjeta.obtenerTarjeta(Long.parseLong(request.getParameter("numero")));
            
            if (t != null) {
                System.out.println(t.getSaldo());
                System.out.println(request.getParameter("total"));
                
                if (t.getSaldo() >= Double.parseDouble(request.getParameter("total"))) {
                    boolean registrado = pc.registrarC();
                
                    if (registrado) {
                        out.println("<script>alertify.success('Se ha registrado');</script>");
                        t.quitarSaldo(Double.parseDouble(request.getParameter("total")));
                        Movimiento.registrar(t, "Pago de compra: Plan", Double.parseDouble(request.getParameter("total")));
                    } else {
                         out.println("<script>alertify.error('No se ha registrado');</script>");
                    }
                } else {
                    out.println("<script>alertify.error('No tines saldo suficiente, intente con otra');</script>");
                }
            } else {
                out.println("<script>alertify.error('La tarjeta no existe');</script>");
            }
        } else if (request.getParameter("tipo").equals("planp")) {
            Date fl = new Date(); /*Primero obtenga la fecha actual(sistema) y lo guardo en fecha limite*/
            fl.setTime(fl.getTime() + (Long.parseLong(request.getParameter("tiempo")) * 1000));
            /*Esta en segundos y lo paso a milisegundos xkhe no lo se xd jajaja
            y eso lo convierto a long(porque asi tiene que ser) y al final lo agrega al tiempo(cuando lo obtenga)*/
            PlanPComprado ppc = new PlanPComprado(PlanP.obtenerPlanP(Integer.parseInt(request.getParameter("id"))), Reclutador.obtenerCuenta(sesion.getId()), fl);
            
            Tarjeta t = Tarjeta.obtenerTarjeta(Long.parseLong(request.getParameter("numero")));
            
            if (t != null) {
                System.out.println(t.getSaldo());
                System.out.println(request.getParameter("total"));
                
                if (t.getSaldo() >= Double.parseDouble(request.getParameter("total"))) {
                    boolean registrado = ppc.registrarPPC();
                
                    if (registrado) {
                        out.println("<script>alertify.success('Se ha registrado');</script>");
                        t.quitarSaldo(Double.parseDouble(request.getParameter("total")));
                        Movimiento.registrar(t, "Pago de compra: Plan Publicitario", Double.parseDouble(request.getParameter("total")));
                    } else {
                         out.println("<script>alertify.error('No se ha registrado');</script>");
                    }
                } else {
                    out.println("<script>alertify.error('No tines saldo suficiente, intente con otra');</script>");
                }
            } else {
                out.println("<script>alertify.error('La tarjeta no existe');</script>");
            }    
        } else if(request.getParameter("tipo").equals("RenovarPlan")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fl = sdf.parse(request.getParameter("limite"));
            
            fl.setTime(fl.getTime() + (Long.parseLong(request.getParameter("tiempo")) * 1000)); 
            
            Tarjeta t = Tarjeta.obtenerTarjeta(Long.parseLong(request.getParameter("numero")));
            
            if (t != null) {
                System.out.println(t.getSaldo());
                System.out.println(request.getParameter("total"));
                
                if (t.getSaldo() >= Double.parseDouble(request.getParameter("total"))) {
                    boolean registrado = PlanComprado.renovarPlan(Integer.parseInt(request.getParameter("id")), fl);
                
                    if (registrado) {
                        out.println("<script>alertify.success('Se ha renovado');</script>");
                        t.quitarSaldo(Double.parseDouble(request.getParameter("total")));

                        Movimiento.registrar(t, "Pago de renovacion: Plan", Double.parseDouble(request.getParameter("total")));
                    } else {
                         out.println("<script>alertify.error('No se ha renovado');</script>");
                    }
                } else {
                    out.println("<script>alertify.error('No tines saldo suficiente, intente con otra');</script>");
                }
            } else {
                out.println("<script>alertify.error('La tarjeta no existe');</script>");
            }
        }
    } %>
    <div class="big">
        <div class="container">
            <h2>Datos de facturación</h2>
            <p>
                <label>Titular de la tarjeta <font color="red">*</font></label>
                <input type="text" name="nombre" class="cardHelper" required
                    data-el="cardHolder" data-side="front" placeholder="Nombre completo"
                    data-parsley-maxlength="152" maxlength="152" 
                    data-parsley-maxlength-message="El nombre del titular no debe superar a 152 carácteres"
                    data-parsley-required-message="Debes ingresar el nombre del titular">
            </p>
            <p>
                <label>Número de tarjeta <font color="red">*</font></label>
                <input type="text" name="numero" class="cardHelper" required
                    data-el="cardNumber" data-side="front" placeholder="Número de tarjeta"
                    data-parsley-maxlength="16" maxlength="16"
                    data-parsley-minlength="16" minlength="16"
                    data-parsley-maxlength-message="El número de tarjeta debe ser 16 dígitos"
                    data-parsley-required-message="Debes ingresar el número de tarjeta">
            </p>
            <p>
                <label>Fecha de vencimiento <font color="red">*</font></label>
                <input type="number" name="mes" class="cardHelper" required
                   data-el="cardMonth" data-side="front" placeholder="Mes"
                   data-parsley-maxlength="2" maxlength="2" min="1" max="12"
                   data-parsley-minlength="2" minlength="2" value="1"
                   data-parsley-required-message="Debes ingresar el mes de vencimiento">
                <input type="number" name="anio" class="cardHelper" required
                   data-el="cardYear" data-side="front" placeholder="Año"
                   data-parsley-maxlength="2" maxlength="2" min="19" max="99"
                   data-parsley-minlength="2" minlength="2" value="19"
                   data-parsley-required-message="Debes ingresar el año de vencimiento">
            </p>
            <p>
                <label>CCV <font color="red">*</font></label>
                <input type="text" name="ccv" class="cardHelper" required
                   data-el="cardCCV" data-side="back" placeholder="CCV"
                   data-parsley-maxlength="3" maxlength="3"
                   data-parsley-minlength="3" minlength="3"
                   data-parsley-maxlength-message="El CCV debe ser de 3 caracteres"
                   data-parsley-required-message="Debes ingresar el CCV">
            </p>
            <p><center>
                <div id="card"> 
                    <div class="side front"> 
                        <div class="card_logo" id="none"></div>
                        <p class="card_number"><span class="help" id="cardNumber" data-ot="1234 5678 1234 5678">1234 5678 1234 5678</span></p>
                        <div class="space-75">
                            <span class="label">Titular de la tarjeta</span>
                            <p class="info">
                                <span class="help" id="cardHolder" data-ot="Andrés Manuel López Obrador">Andrés Manuel López Obrador</span>
                            </p>
                        </div>
                        <div class="space-25">
                            <span class="label">Expira</span>
                            <p class="info">
                                <span class="help" id="cardMonth" data-ot="04">04</span>/<span class="help" id="cardYear" data-ot="19">19</span>
                            </p>
                        </div>
                    </div> 
                    <div class="side back">
                        <div class="black-line"></div>
                        <div class="back-content">
                            <div class="secret">
                                <p class="secret--last"><span class="help" id="cardCCV" data-ot="123">123</span></p>
                            </div>
                        </div>
                    </div> 
                </div> 
                <script>
                $(function($) {
                    $("#card").flip({ trigger: 'manual' });
                    $(".cardHelper").on('focus', function () {
                        var el = $(this).data("el");
                        $(".help").removeClass("highlight");
                        $(".help#"+el).addClass("highlight");
                        if ($(this).data("side") === "front") {
                            $("#card").flip(false);
                        } else {
                            $("#card").flip(true);
                        }
                    });
                    
                    $(".cardHelper").on('keypress change blur', function () {
                        var val = $(this).val();
                        var el = $(this).data("el");
                        var ot = $(this).data("ot");
                        
                        if (val > 0) {
                            if (el === cardNumber) {
                                val = val.replace(/[^a-z0-9]+/gi, "").replace(/(.{4})/g, "$1");
                                $(".help#"+el).val(val);
                            } else {
                                $(".help#"+el).val(val);
                            }
                        } else {
                            $(".help#"+el).val(ot);
                        }
                    });
                    
                    $(".cardHelper").on("copy cut paste", function () {
                        setTimeout(function() {
                            $(this).trigger("change");
                        });
                    });
                    
                    $('input[name=numero]').validateCreditCard(function(result) {
                        $('.card_logo').prop("id", (result.card_type === null ? 'none' : result.card_type.name));
                    });
                });
                </script>
            </center></p>
        </div>
    </div>
    <div class="small">
        <div class="container">
            <h2>Compra</h2>
            <% if (request.getParameter("c") != null) { // Curso
                Curso c = Curso.obtenerCurso(Integer.parseInt(request.getParameter("c")));
                if (c != null) { %>
            <table width="100%">
                <tr>
                    <th>Concepto</th>
                    <th>Precio</th>
                </tr>
                <tr>
                    <td>Curso: <%=c.getNombre()%></td>
                    <td><%=c.getPrecio()%></td>
                </tr>
                <tr>
                    <th>Total</th>
                    <td><%=c.getPrecio()%></td>
                </tr>
            </table>
            <input type="text" name="tipo" value="curso">
            <input type="text" name="id" value="<%=c.getIdCurso()%>">
            <input type="text" name="total" value="<%=c.getPrecio()%>">
                <% } else {
                    response.sendRedirect("cursos.jsp");
                }
            } else if (request.getParameter("p") != null) { // Plan
                Plan p = Plan.obtenerPlan(Integer.parseInt(request.getParameter("p")));
                if (p != null) { %>
            <table width="100%">
                <tr>
                    <th>Concepto</th>
                    <th>Precio</th>
                </tr>
                <tr>
                    <td>Plan: <%=p.getNombre()%></td>
                    <td><%=p.getPrecio()%></td>
                </tr>
                <tr>
                    <td>Tiempo:</td>
                    <td><%=p.getTiempo()%></td>
                </tr>
                <tr>
                    <th>Total</th>
                    <td><%=p.getPrecio()%></td>
                </tr>
            </table>
            <input type="text" name="tipo" value="plan">
            <input type="text" name="id" value="<%=p.getIdPlan()%>">
            <input type="text" name="tiempo" value="<%=p.getTiempo()%>">
            <input type="text" name="total" value="<%=p.getPrecio()%>">
                <% } else {
                    response.sendRedirect("planes.jsp");
                }
            } else if (request.getParameter("i") != null) { // Plan publicitario
                PlanP pp = PlanP.obtenerPlanP(Integer.parseInt(request.getParameter("i")));
                if (pp != null) { %>
            <table width="100%">
                <tr>
                    <th>Concepto</th>
                    <th>Precio</th>
                </tr>
                <tr>
                    <td>Plan Publicitario: <%=pp.getNombre()%></td>
                    <td><%=pp.getPrecio()%></td>
                </tr>
                <tr>
                    <td>Tiempo:</td>
                    <td><%=pp.getTiempo()%></td>
                </tr>
                <tr>
                    <th>Total</th>
                    <td><%=pp.getPrecio()%></td>
                </tr>
            </table>
            <input type="text" name="tipo" value="planp">
            <input type="text" name="id" value="<%=pp.getIdPlanP()%>">
            <input type="text" name="tiempo" value="<%=pp.getTiempo()%>">
            <input type="text" name="total" value="<%=pp.getPrecio()%>">
                <% } else {
                    response.sendRedirect("planesp.jsp");
                }
            } else if(request.getParameter("rp") != null) { // Renovar plan
                PlanComprado rp = PlanComprado.obtenerPlanComprado(Integer.parseInt(request.getParameter("rp")));
                if (rp != null) { %>
            <table width="100%">
                <tr>
                    <th>Concepto</th>
                    <th>Precio</th>
                </tr>
                <tr>
                    <td>Renovación del Plan: <%=rp.getPlan().getNombre()%></td>
                    <td><%=rp.getPlan().getPrecio()%></td>
                </tr>
                <tr>
                    <td>Tiempo:</td>
                    <td><%=rp.getPlan().getTiempo()%> más</td>
                </tr>
                <tr>
                    <th>Total</th>
                    <td><%=rp.getPlan().getPrecio()%></td>
                </tr>
            </table>
            <input type="text" name="tipo" value="RenovarPlan">
            <input type="text" name="id" value="<%=rp.getId() %>">
            <input type="text" name="tiempo" value="<%=rp.getPlan().getTiempo()%>">
            <% SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String x = sdf2.format(rp.getFechaLimite()); %>
            <input type="text" name="limite" value="<%=x%>">
            <input type="text" name="total" value="<%=rp.getPlan().getPrecio()%>">
                <% } else {
                    response.sendRedirect("vacantes.jsp");
                }
            } else {
                 response.sendRedirect("index.jsp");
            } %>
            <center><button type="submit" name="comprar">
                <i class="fas fa-shopping-cart"></i> Pagar
            </button></center>
        </div>
    </div>
</section>
</form>
<%@include file="template.footer.jsp" %>
