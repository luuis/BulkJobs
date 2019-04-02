<%@page import="bean.Empleador"%>
<%@page import="bean.Curriculum"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="extra.ConexionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Sesion sesion = (Sesion) session.getAttribute("sesion"); %>
<jsp:include page="template.header.jsp">
    <jsp:param name="titulo" value="Curriculum" />
    <jsp:param name="roles" value="empleador" />
</jsp:include>
<section class="one">
    <% if (request.getParameter("a") != null) {
        if (request.getParameter("a").equalsIgnoreCase("subir")) { %>
        <div class="container">
            <h3>Subir curriculum</h3>
            <p>Puedes subir un curriculum en formato PDF para poder postularte a vacantes.</p>
            <form action="SubirCurriculum" method="post" enctype="multipart/form-data" id="uploadCV">
            <center>
                <p>
                    <input type="file" name="cv" required accept="application/pdf">
                </p>
                <p>
                    <button type="submit" class="disableit"><i class="fas fa-file-pdf"></i> Subir</button>
                </p>
            </center>
            </form>
        </div>
        <script>
        $(function() {
            $("#uploadCV").on("submit", function () {
                $(".disableit").prop("disabled", true);
                $(this).ajaxSubmit({
                    method: "POST",
                    success: function(data, status, xhr) {
                        window.location = "vacante.jsp?v=<%=request.getParameter("v")%>";
                    },
                    error: function(xhr, status, error) {
                        console.log(xhr);
                        alertify.warning(status);
                    }
                });
                return false;
            });
        });
        </script>
        <% }  else if (request.getParameter("a").equalsIgnoreCase("generar")) {
            ConexionBD objCBD = new ConexionBD("bolsadetrabajo");
            Curriculum curr = null;
            if (request.getParameter("actualizar") != null) {
                ArrayList instBD = new ArrayList();
                instBD.add("SELECT * FROM curriculum WHERE curr_cuenta = ? && curr_archivo IS NULL ORDER BY id_curriculum DESC LIMIT 1");
                instBD.add(sesion.getId());
                objCBD.consultar(instBD);
                ResultSet rs = objCBD.getCdr();
                while (rs.next()) {
                    curr = new Curriculum(rs.getInt(1), Empleador.obtenerCuenta(rs.getInt(2)), rs.getString(3), rs.getDate(4));
                }
            } %>
        <script>
            $(function() {
                $('.addRow').on('click', function() {
                    var cv = $(this).data('cv');
                    $('center#cv'+cv+' table:last').after(
                            "<table>" + $('center#cv'+cv+' table:first').html() + "</table>");
                });
                
                $('.removeRow').on('click', function() {
                    var cv = $(this).data('cv');
                    if ($('center#cv'+cv+' table').length > 1) {
                        $('center#cv'+cv+' table:last').remove();
                    } else {
                        alertify.warning("Debe haber al menos un apartado");
                    }
                });
                
                $("#cvGen").on("submit", function () {
                    $("#botonGenerar").prop("disabled", true);
                    $(this).ajaxSubmit({
                        method: "POST",
                        success: function(data, status, xhr) {
                            window.location = "vacante.jsp?v=<%=request.getParameter("v")%>";
                        },
                        error: function(xhr, status, error) {
                            console.log(xhr);
                            alertify.warning(status);
                        }
                    });
                    return false;
                });
            });
        </script>
        <form id="cvGen" action="GenerarCurriculum" method="post" autocomplete="off" class="validate" data-parsley-errors-messages-disabled>
        <div class="container">
            <h3>Generar curriculum</h3>
            <center>
                <p>
                    <label><input type="radio" name="secc" data-secc="el" checked> Experiencia laboral</label>
                    <label><input type="radio" name="secc" data-secc="fa"> Formación académica</label>
                    <label><input type="radio" name="secc" data-secc="i"> Idiomas</label>
                    <label><input type="radio" name="secc" data-secc="df"> Datos familiares</label>
                    <label><input type="radio" name="secc" data-secc="rp"> Referencias personales</label>
                </p>
                <p>
                    <button type="submit" id="botonGenerar" value="generar">Generar</button>
                </p>
            </center>
        </div>
        
        <div id="cv-el" class="container">
            <h3>Experiencia laboral</h3>
            <center>
                <button type="button" class="addRow" data-cv="el"><i class="fas fa-plus"></i> Agregar 1</button>
                <button type="button" class="removeRow" data-cv="el"><i class="fas fa-times"></i> Quitar 1</button>
            </center>
            <center id="cvel">
                <% if (curr != null) {
                ArrayList instBD = new ArrayList();
                instBD.add("SELECT * FROM cv_experiencia_laboral WHERE cvel_curriculum = ?");
                instBD.add(curr.getId());
                objCBD.consultar(instBD);
                ResultSet rs = objCBD.getCdr();
                while (rs.next()) { %>
                <table>
                    <tr>
                        <th><label>Nombre de la empresa: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvel_nombre_empresa" value="<%=rs.getString("cvel_nombre_empresa")%>" maxlength="50"></td>
                        <th><label>Puesto: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvel_puesto" value="<%=rs.getString("cvel_puesto")%>" maxlength="50"></td>
                    </tr>
                    <tr>
                        <th><label>Año inicio: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvel_anio_inicio">
                                <% for(int ai=2019; ai>=1950; ai--) { %>
                                <option <%=(rs.getInt("cvel_anio_inicio") == ai) ? "selected" : ""%>><%=ai%></option>
                                <% } %>
                            </select>
                        </td>
                        <th><label>Año fin: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvel_anio_fin">
                                <% for(int ai=2019; ai>=1950; ai--) { %>
                                <option <%=(rs.getInt("cvel_anio_fin") == ai) ? "selected" : ""%>><%=ai%></option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><label>Nombre del jefe: <font color="red">*</font></label></th>
                        <td><input type="text" required value="<%=rs.getString("cvel_nombre_jefe")%>" name="cvel_nombre_jefe" maxlength="100"></td>
                        <td><input type="text" required value="<%=rs.getString("cvel_app_jefe")%>" name="cvel_app_jefe" maxlength="100"></td>
                        <td><input type="text" required value="<%=rs.getString("cvel_apm_jefe")%>" name="cvel_apm_jefe" maxlength="100"></td>
                    </tr>
                    <tr>
                        <th><label>Telefono: <font color="red">*</font></label></th>
                        <td><input type="tel" required value="<%=rs.getString("cvel_telefono")%>" name="cvel_telefono" maxlength="10"></td>
                        <th><label>Funciones realizadas: <font color="red">*</font></label></th>
                        <td><input type="text" required value="<%=rs.getString("cvel_funciones")%>" name="cvel_funciones" maxlength="200"></td>
                    </tr>
                    <tr>
                        <th rowspan="2">Dirección</th>
                        <td><input type="text" value="<%=rs.getString("cvel_dir_num_ext")%>" name="cvel_dir_num_ext" placeholder="Número exterior" maxlength="50"></td>
                        <td><input type="text" value="<%=rs.getString("cvel_dir_num_int")%>" name="cvel_dir_num_int" placeholder="Número interior" maxlength="50"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><input type="text" required value="<%=rs.getString("cvel_dir_localidad")%>" name="cvel_dir_localidad" placeholder="Localidad (*)" maxlength="50"></td>
                        <td><input type="text" required value="<%=rs.getString("cvel_dir_municipio")%>" name="cvel_dir_municipio" placeholder="Municipio (*)" maxlength="50"></td>
                        <td><input type="text" required value="<%=rs.getString("cvel_dir_estado")%>" name="cvel_dir_estado" placeholder="Estado (*)" maxlength="50"></td>
                    </tr>
                </table>
                <% } } else { %>
                <table>
                    <tr>
                        <th><label>Nombre de la empresa: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvel_nombre_empresa" maxlength="50"></td>
                        <th><label>Puesto: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvel_puesto" maxlength="50"></td>
                    </tr>
                    <tr>
                        <th><label>Año inicio: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvel_anio_inicio">
                                <% for(int ai=2019; ai>=1950; ai--) { %>
                                <option><%=ai%></option>
                                <% } %>
                            </select>
                        </td>
                        <th><label>Año fin: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvel_anio_fin">
                                <% for(int ai=2019; ai>=1950; ai--) { %>
                                <option><%=ai%></option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><label>Nombre del jefe: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvel_nombre_jefe" maxlength="100"></td>
                        <td><input type="text" required name="cvel_app_jefe" maxlength="100"></td>
                        <td><input type="text" required name="cvel_apm_jefe" maxlength="100"></td>
                    </tr>
                    <tr>
                        <th><label>Telefono: <font color="red">*</font></label></th>
                        <td><input type="tel" required name="cvel_telefono" maxlength="10"></td>
                        <th><label>Funciones realizadas: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvel_funciones" maxlength="200"></td>
                    </tr>
                    <tr>
                        <th rowspan="2">Dirección</th>
                        <td><input type="text" name="cvel_dir_num_ext" placeholder="Número exterior" maxlength="50"></td>
                        <td><input type="text" name="cvel_dir_num_int" placeholder="Número interior" maxlength="50"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><input type="text" required name="cvel_dir_localidad" placeholder="Localidad (*)" maxlength="50"></td>
                        <td><input type="text" required name="cvel_dir_municipio" placeholder="Municipio (*)" maxlength="50"></td>
                        <td><input type="text" required name="cvel_dir_estado" placeholder="Estado (*)" maxlength="50"></td>
                    </tr>
                </table>
                <% } %>
            </center>
        </div>
        
        <div id="cv-fa" class="container" style="display: none">
            <h3>Formación académica</h3>
            <center>
                <button type="button" class="addRow" data-cv="fa"><i class="fas fa-plus"></i> Agregar 1</button>
                <button type="button" class="removeRow" data-cv="fa"><i class="fas fa-times"></i> Quitar 1</button>
            </center>
            <center id="cvfa">
                <table>
                    <tr>
                        <th><label>Nombre de la institucion: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvfa_nombre_institucion" maxlength="50"></td>
                        <th><label>Nivel: <font color="red">*</font></label></th>
                        <td>
                            <select required name="cvfa_nivel">
                                <option>Bachillerato</option>
                                <option>Educación profesional técnica</option>
                                <option>Técnico superior</option>
                                <option>Licenciatura</option>
                                <option>Ingeniería</option>
                                <option>Posgrado</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><label>Año inicio: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvfa_anio_inicio">
                                <% for(int ai=2019; ai>=1950; ai--) { %>
                                <option><%=ai%></option>
                                <% } %>
                            </select>
                        </td>
                        <th><label>Año fin: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvfa_anio_fin">
                                <% for(int ai=2019; ai>=1950; ai--) { %>
                                <option><%=ai%></option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><label>Título/certificado: <font color="red">*</font></label></th>
                        <td><input type="tel" required name="cvfa_titulo_certificado" maxlength="50"></td>
                        <th><label>Cédula: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvfa_cedula" maxlength="16"></td>
                    </tr>
                    <tr>
                        <th>Dirección</th>
                        <td><input type="text" name="cvfa_dir_num_ext" placeholder="Número exterior" maxlength="50"></td>
                        <td><input type="text" name="cvfa_dir_num_int" placeholder="Número interior" maxlength="50"></td>
                        <td><input type="text" required name="cvfa_dir_localidad" placeholder="Localidad (*)" maxlength="50"></td>
                    </tr>
                    <tr>
                        <td><input type="text" required name="cvfa_dir_municipio" placeholder="Municipio (*)" maxlength="50"></td>
                        <td><input type="text" required name="cvfa_dir_estado" placeholder="Estado (*)" maxlength="50"></td>
                        <th><label>Estatus: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvfa_estado">
                                <option value="1">Cursando</option>
                                <option value="2">Finalizado</option>
                                <option value="3">Trunco</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </center>
        </div>
        
        <div id="cv-i" class="container" style="display: none">
            <h3>Idiomas</h3>
            <center>
                <button type="button" class="addRow" data-cv="id"><i class="fas fa-plus"></i> Agregar 1</button>
                <button type="button" class="removeRow" data-cv="id"><i class="fas fa-times"></i> Quitar 1</button>
            </center>
            <center id="cvid">
                <table>
                    <tr>
                        <th><label>Idioma: <font color="red">*</font></label></th>
                        <td><input type="text" required name="idio_idioma" maxlength="50"></td>
                        <th><label>Porcentaje: <font color="red">*</font></label></th>
                        <td><input type="number" required name="idio_porcentaje" maxlength="3" min="0" max="100" value="50"></td>
                    </tr>
                </table>
            </center>
        </div>
        
        <div id="cv-df" class="container" style="display: none">
            <h3>Datos familiares</h3>
            <center>
                <button type="button" class="addRow" data-cv="df"><i class="fas fa-plus"></i> Agregar 1</button>
                <button type="button" class="removeRow" data-cv="df"><i class="fas fa-times"></i> Quitar 1</button>
            </center>
            <center id="cvdf">
                <table>
                    <tr>
                        <th><label>Nombre del familiar: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvdf_nombre" maxlength="100"></td>
                        <td><input type="text" required name="cvdf_app" maxlength="100"></td>
                        <td><input type="text" required name="cvdf_apm" maxlength="100"></td>
                    </tr>
                    <tr>
                        <th><label>Parentesco<font color="red">*</font></label></th>
                        <td><input type="text" required name="cvdf_parentesco" maxlength="50"></td>
                        <th><label>Ocupación: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvdf_ocupacion" maxlength="50"></td>
                    </tr>
                    <tr>
                        <th><label>Vive: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvdf_vive">
                                <option value="1">Sí</option>
                                <option value="0">No</option>
                            </select>
                        </td>
                        <th><label>Finado: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvdf_finado">
                                <option value="1">Sí</option>
                                <option value="0">No</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th rowspan="2">Dirección</th>
                        <td><input type="text" name="cvdf_dir_num_ext" placeholder="Número exterior" maxlength="50"></td>
                        <td><input type="text" name="cvdf_dir_num_int" placeholder="Número interior" maxlength="50"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><input type="text" required name="cvdf_dir_localidad" placeholder="Localidad (*)" maxlength="50"></td>
                        <td><input type="text" required name="cvdf_dir_municipio" placeholder="Municipio (*)" maxlength="50"></td>
                        <td><input type="text" required name="cvdf_dir_estado" placeholder="Estado (*)" maxlength="50"></td>
                    </tr>
                </table>
            </center>
        </div>
        
        <div id="cv-rp" class="container" style="display: none">
            <h3>Referencias personales</h3>
            <center>
                <button type="button" class="addRow" data-cv="rp"><i class="fas fa-plus"></i> Agregar 1</button>
                <button type="button" class="removeRow" data-cv="rp"><i class="fas fa-times"></i> Quitar 1</button>
            </center>
            <center id="cvrp">
                <table>
                    <tr>
                        <th><label>Nombre de la referencia: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvrp_nombre" maxlength="100"></td>
                        <td><input type="text" required name="cvrp_app" maxlength="100"></td>
                        <td><input type="text" required name="cvrp_apm" maxlength="100"></td>
                    </tr>
                    <tr>
                        <th><label>Teléfono: <font color="red">*</font></label></th>
                        <td><input type="tel" required name="cvrp_telefono" maxlength="10"></td>
                        <th><label>Relación: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvrp_relacion" maxlength="50"></td>
                    </tr>
                    <tr>
                        <th><label>Tiempo de conocerlo: <font color="red">*</font></label></th>
                        <td><input type="text" required name="cvrp_tiempo_conociendo" maxlength="50"></td>
                        <th><label>Tipo de referencia: <font color="red">*</font></label></th>
                        <td>
                            <select name="cvrp_tipo">
                                <option>Personal</option>
                                <option>Laboral</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th rowspan="2">Dirección</th>
                        <td><input type="text" name="cvrp_dir_num_ext" placeholder="Número exterior" maxlength="50"></td>
                        <td><input type="text" name="cvrp_dir_num_int" placeholder="Número interior" maxlength="50"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><input type="text" required name="cvrp_dir_localidad" placeholder="Localidad (*)" maxlength="50"></td>
                        <td><input type="text" required name="cvrp_dir_municipio" placeholder="Municipio (*)" maxlength="50"></td>
                        <td><input type="text" required name="cvrp_dir_estado" placeholder="Estado (*)" maxlength="50"></td>
                    </tr>
                </table>
            </center>
        </div>
        </form>
        <% } else { response.sendRedirect("perfil.jsp"); } %>
    <% } else { response.sendRedirect("perfil.jsp"); } %>
</section>
<%@include file="template.footer.jsp" %>
