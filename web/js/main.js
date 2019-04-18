$(window).on('load', function() {
    if($('.validate').length > 0) {
        $('.validate').parsley().on('form:error', function (fi) {
            for (var idx in fi.fields) {
                if(fi.fields[idx].getErrorsMessages()[0]) {
                    alertify.error(fi.fields[idx].getErrorsMessages()[0]);
                }
            }
        });
    }
    
    if($('.people').length > 0) {
        var typingTimer;
        var $input = $('#busquedaTermino');
        var $tipo = $('#busquedaTipo');
        var $remit = $('#busquedaRemitente');

        $input.on('keyup', function () {
          clearTimeout(typingTimer);
          typingTimer = setTimeout(doneTyping, 500);
        });

        $input.on('keydown', function () {
          clearTimeout(typingTimer);
        });

        function doneTyping() {
        $.ajax({
            url: 'ServletContactos',
            type: 'GET',
            dataType: 'json',
            data: {tipo: $tipo.val(), remitente: $remit.val(), busqueda: $input.val()},
            success: function(data) {
                var c = "", s = "";
                if(data.contactos.length > 0) {
                    Object.keys(data.contactos).forEach(function(key) {
                        c += '<div class="contact"><a href="mensajes.jsp?c=' +
                          data.contactos[key].id + '">';
                        if (data.contactos[key].leido) {
                            c+= data.contactos[key].nombre;
                        } else {
                            c+= '<strong>' + data.contactos[key].nombre + '</strong>';
                        }
                        c += '</a></div>';
                    });
                } else {
                    c += '<div class="contact"><em>Sin contactos</em></div>';
                }
                $("#contacts").html(c);
                
                if(data.sugerencias.length > 0) {
                    Object.keys(data.sugerencias).forEach(function(key) {
                        s += '<div class="contact"><a href="mensajes.jsp?c=' +
                          data.sugerencias[key].id + '">' + data.sugerencias[key].nombre + '</a></div>';
                    });
                    $("#suggestions").html(s);
                    $("#suggs").show();
                } else {
                    $("#suggs").hide();
                }
            },
            error: function() {
                console.log('fail');
                console.log(typeof data);
            }
        });
     }
     
        doneTyping();
    }
    if ($("#conversation").length > 0) {
        $("#conversation").scrollTop($("#conversation")[0].scrollHeight);
    }
    
    $(window).on('keyup', { keys: 'alt+shift+a' }, function () { window.location = "perfil.jsp"; });
    $(window).on('keyup', { keys: 'alt+shift+b' }, function () { window.location = "busqueda.jsp"; });
    $(window).on('keyup', { keys: 'alt+shift+c' }, function () { window.location = "cursos.jsp"; });
    $(window).on('keyup', { keys: 'alt+shift+m' }, function () { window.location = "mensajes.jsp"; });
    $(window).on('keyup', { keys: 'alt+shift+p' }, function () { window.location = "planes.jsp"; });
    $(window).on('keyup', { keys: 'alt+shift+h' }, function () { window.location = "ayuda.jsp"; });
});

$('input[name=tipo]').on('change', function() {
    $('.registro').slideUp();
    $('.registro fieldset').prop("disabled", true);
    $('#reg-' + $(this).data("num")).slideDown();
    $('#reg-' + $(this).data("num") + " fieldset").prop("disabled", false);
});

$('input[name=secc]').on('change', function() {
    $('[id^=cv-]').slideUp();
    $('#cv-' + $(this).data("secc")).slideDown();
});

$('#evaluar i').on('mouseover', function () {
    $('#evaluar i').removeClass('fas');
    $('#evaluar i').removeClass('far');
    var calif = $(this).data('calif');
    $('#evaluar i').each(function() {
        if ($(this).data("calif") <= calif) {
            $(this).addClass('fas');
        } else {
            $(this).addClass('far');
        }
    });
});



$('#evaluar i').on('click', function() {
    var calif = $(this).data('calif');
    alertify.prompt("Escribe un comentario para el reclutador.", "", function (evt, val) {
        /*POST tiene 3 atributos: la pagina que se va a mandar, los campos, y funcion que se hara despuess*/
        $.post('/Evaluacion',
        /*Enviar los campos al Servlets*/
        { 
           c: calif, 
           co: val,
           idCuenta: $("#idCuenta").val(), //recuperar el input y luego el valor
           idEvalu: $("#idEvalu").val()

        }, function(data, status) {
            alertify.success(data); //alertify no es nativa, es una libreria
            window.location.reload();
        });
    });
});

$('#inscribirte').on('click', function() { 
    alertify.confirm("¿Estas seguro de inscribirte?", function () {
        var cue = $("#cue").val(); 
        var cur = $("#cur").val();
        
        $.post('/InscribirseC', {
            //Aqui voy a mandra toda la informacion que hay en el servlet
            idCuenta: cue,
            idCurso: cur
        }, function(data, status) {
            window.location.reload();
            alertify.success(data);
        }); //PUSE POST PARA AJAX JEJE 
    });
});
 
$('#calif i').on('mouseover', function () {
    $('#calif i').removeClass('fas');
    $('#calif i').removeClass('far');
    var calif = $(this).data('calif');
    $('#calif i').each(function() {
        if ($(this).data("calif") <= calif) {
            $(this).addClass('fas');
        } else {
            $(this).addClass('far');
        }
    });
});
    
$('#calif i').on('click', function() {
        var calif = $(this).data('calif');
        alertify.prompt("Ingresa un comentario.", "", function (evt, val) {
        /*POST tiene 3 atributos: la pagina que se va a mandar, los campos, y funcion que se hara despuess*/
        $.post('/EvaluacionC',
        /*Enviar los campos al Servlets*/
        { 
           c: calif, 
           co: val,
           idCuenta: $("#idCuenta").val(), //recuperar el input y luego el valor
           idCurso: $("#idCurso").val()

        }, function(data, status) {
            alertify.success(data); //alertify no es nativa, es una libreria
            window.location.reload(); 
        });
    });
});
 
$('#finalizar').on('click', function() {
    /*POST tiene 3 atributos: la pagina que se va a mandar, los campos, y funcion que se hara despuess*/
    $.post('/FinalizarC',
    /*Enviar los campos al Servlets*/
    {
       idCursoInscrito: $("#cur").val(), //recuperar el input y luego el valor
    }, function(data, status) {
        alertify.success(data); //alertify no es nativa, es una libreria
        window.location.reload(); 
    });
});