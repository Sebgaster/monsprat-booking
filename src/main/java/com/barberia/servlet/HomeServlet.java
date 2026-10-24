/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.barberia.servlet;

import com.barberia.model.Turno;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {

    private static final List<String> FESTIVOS_COLOMBIA = Arrays.asList(
        "2026-01-01", "2026-01-12", "2026-03-23", "2026-04-02", "2026-04-03", 
        "2026-05-01", "2026-05-18", "2026-06-08", "2026-06-15", "2026-06-29", 
        "2026-07-20", "2026-08-07", "2026-08-17", "2026-10-12", "2026-11-02", 
        "2026-11-16", "2026-12-08", "2026-12-25"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String fechaFiltro = request.getParameter("fechaFiltro");
        if (fechaFiltro == null || fechaFiltro.trim().isEmpty()) {
            fechaFiltro = LocalDate.now().toString();
        }

        String barberoFiltro = request.getParameter("barberoFiltro");
        if (barberoFiltro == null || barberoFiltro.trim().isEmpty()) {
            barberoFiltro = "Santiago Monsalve";
        }

        String clientePrevio = request.getParameter("cliente");
        if (clientePrevio == null) clientePrevio = "";

        String telefonoPrevio = request.getParameter("telefono");
        if (telefonoPrevio == null) telefonoPrevio = "";

        // PERSISTENCIA MULTI-SERVICIO: Lectura de servicios elegidos previa recarga (separados por coma)
        String serviciosPreviosStr = request.getParameter("servicio");
        List<String> serviciosPrevios = new ArrayList<>();
        if (serviciosPreviosStr != null && !serviciosPreviosStr.trim().isEmpty()) {
            serviciosPrevios = Arrays.asList(serviciosPreviosStr.split(","));
        }

        ServletContext context = getServletContext();
        List<Turno> listaTurnos = (List<Turno>) context.getAttribute("listaTurnos");
        if (listaTurnos == null) {
            listaTurnos = new ArrayList<>();
        }

        List<String> horasBase = Arrays.asList(
            "10:00", "11:00", "12:00", "13:00", 
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"
        );

        List<String> horasOcupadas = new ArrayList<>();
        for (Turno t : listaTurnos) {
            if (t.getFecha().equals(fechaFiltro) && t.getBarbero() != null && t.getBarbero().equals(barberoFiltro)) {
                horasOcupadas.add(t.getHora());
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Monsprat Barber Studio - Turnos</title>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css'>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/flatpickr/dist/themes/dark.css'>");

            out.println("<style>");
            out.println("* { box-sizing: border-box; }");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #080808; color: #f1f1f1; margin: 0; padding: 30px 20px; display: flex; justify-content: center; align-items: center; min-height: 100vh; }");
            out.println(".main-wrapper { display: flex; flex-wrap: wrap; gap: 30px; max-width: 900px; width: 100%; justify-content: center; position: relative; }");
            out.println(".card { background: linear-gradient(145deg, #151515, #0a0a0a); padding: 30px; border-radius: 12px; box-shadow: 0 8px 30px rgba(197, 160, 89, 0.12); border: 1px solid #2a2a2a; flex: 1 1 400px; max-width: 440px; position: relative; }");
            out.println(".header-logo { text-align: center; margin-bottom: 20px; }");
            out.println(".header-logo img { max-width: 130px; height: auto; border-radius: 8px; border: 2px solid #c5a059; padding: 4px; background: #080808; box-shadow: 0 0 15px rgba(197, 160, 89, 0.25); }");
            out.println("h2 { text-align: center; color: #d4af37; margin-top: 0; margin-bottom: 25px; font-weight: 600; letter-spacing: 1px; text-transform: uppercase; font-size: 1.2rem; border-bottom: 2px solid #c5a059; padding-bottom: 10px; }");
            out.println(".form-group { margin-bottom: 18px; }");
            out.println("label { display: block; margin-bottom: 8px; font-size: 13px; color: #d4af37; text-transform: uppercase; letter-spacing: 0.5px; font-weight: bold; }");
            out.println("input[type='text'], input[type='tel'] { width: 100%; padding: 12px; border: 1px solid #333; border-radius: 6px; background-color: #1a1a1a; color: #fff; font-size: 14px; transition: all 0.3s; }");
            out.println("input:focus { outline: none; border-color: #c5a059; box-shadow: 0 0 8px rgba(197, 160, 89, 0.4); }");

            // ESTILOS CONTENEDOR MULTI-SERVICIO DE CHECKBOXES
            out.println(".services-container { background: #111; border: 1px solid #333; border-radius: 8px; padding: 12px; max-height: 220px; overflow-y: auto; }");
            out.println(".service-category { color: #d4af37; font-size: 11px; font-weight: bold; text-transform: uppercase; margin: 10px 0 6px 0; border-bottom: 1px dashed #333; padding-bottom: 3px; }");
            out.println(".service-category:first-child { margin-top: 0; }");
            out.println(".checkbox-label { display: flex; align-items: center; gap: 10px; padding: 6px 8px; border-radius: 4px; cursor: pointer; transition: background 0.2s; font-size: 13px; color: #ddd; margin-bottom: 2px; }");
            out.println(".checkbox-label:hover { background: #1f1f1f; }");
            out.println(".checkbox-label input[type='checkbox'] { width: 16px; height: 16px; accent-color: #d4af37; cursor: pointer; }");
            out.println(".checkbox-label.disabled-option { opacity: 0.35; cursor: not-allowed; }");

            out.println(".barber-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(130px, 1fr)); gap: 12px; }");
            out.println(".barber-card { background: #111; border: 2px solid #2a2a2a; border-radius: 12px; padding: 14px 10px; text-align: center; cursor: pointer; transition: all 0.3s ease; display: flex; flex-direction: column; align-items: center; justify-content: center; position: relative; }");
            out.println(".barber-card:hover { border-color: #c5a059; background: #181818; transform: translateY(-2px); }");
            out.println(".barber-card.selected { border-color: #d4af37; background: linear-gradient(145deg, #1f1b0e, #111111); box-shadow: 0 0 15px rgba(212, 175, 55, 0.35); }");
            out.println(".barber-avatar { width: 70px; height: 70px; border-radius: 50%; object-fit: cover; border: 2px solid #c5a059; margin-bottom: 10px; box-shadow: 0 0 10px rgba(197, 160, 89, 0.3); }");
            out.println(".barber-name { font-size: 13px; font-weight: bold; color: #ffffff; margin-bottom: 6px; line-height: 1.2; }");
            out.println(".barber-meta { display: flex; gap: 6px; align-items: center; font-size: 11px; }");
            out.println(".barber-badge { background: #1f1f1f; color: #aaa; padding: 3px 8px; border-radius: 12px; border: 1px solid #333; font-size: 10px; font-weight: 600; }");
            out.println(".barber-card.selected .barber-badge { color: #d4af37; border-color: #d4af37; background: #2a220e; }");
            out.println(".barber-rating { color: #f1c40f; font-weight: bold; font-size: 11px; }");
            out.println("button { width: 100%; padding: 14px; background: linear-gradient(90deg, #c5a059, #d4af37); border: none; border-radius: 6px; color: #000; font-weight: bold; font-size: 15px; cursor: pointer; margin-top: 15px; text-transform: uppercase; letter-spacing: 1px; transition: transform 0.2s, box-shadow 0.3s; box-shadow: 0 4px 15px rgba(197, 160, 89, 0.25); }");
            out.println("button:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(197, 160, 89, 0.45); }");
            out.println(".filter-box { background: #111; padding: 12px; border-radius: 6px; margin-bottom: 15px; border: 1px solid #333; }");
            out.println(".schedule-grid { display: grid; grid-template-columns: 1fr; gap: 10px; margin-top: 15px; max-height: 280px; overflow-y: auto; padding-right: 5px; }");
            out.println(".time-slot { background: #1a1a1a; padding: 12px; border-radius: 6px; text-align: center; border: 1px solid #333; font-size: 14px; cursor: pointer; transition: all 0.2s ease; display: flex; justify-content: space-between; align-items: center; }");
            out.println(".time-slot:hover { border-color: #c5a059; background: #222; box-shadow: 0 0 10px rgba(197, 160, 89, 0.2); }");
            out.println(".time-slot.selected { background: #c5a059; color: #000; border-color: #d4af37; font-weight: bold; box-shadow: 0 0 12px rgba(197, 160, 89, 0.5); }");
            out.println(".time-slot.selected .status-badge { background-color: #000; color: #d4af37; border: 1px solid #d4af37; }");
            out.println(".time-slot.disabled { background-color: #111; border-color: #222; color: #666; cursor: not-allowed; opacity: 0.8; }");
            out.println(".status-badge { display: inline-block; padding: 4px 8px; border-radius: 4px; font-size: 11px; font-weight: bold; background-color: #1a1a1a; color: #d4af37; border: 1px solid #c5a059; }");
            out.println(".status-badge.occupied { background-color: #1a1a1a; color: #888; border: 1px solid #444; }");
            out.println(".note { font-size: 12px; color: #888; margin-top: 15px; text-align: center; }");
            out.println(".btn-admin-access { position: absolute; top: 12px; right: 15px; color: #888; font-size: 11px; text-decoration: none; border: 1px solid #444; padding: 4px 8px; border-radius: 4px; transition: all 0.3s ease; }");
            out.println(".btn-admin-access:hover { color: #000; border-color: #c5a059; background: #c5a059; box-shadow: 0 0 10px rgba(197, 160, 89, 0.4); }");
            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<form action='ReservarTurnoServlet' method='POST' id='bookingForm' class='main-wrapper'>");
            out.println("<input type='hidden' id='hora' name='hora' required>");
            out.println("<input type='hidden' id='barbero' name='barbero' value='" + barberoFiltro + "' required>");

            // PRIMERA TARJETA: FORMULARIO
            out.println("<div class='card'>");
            out.println("<a href='AdminServlet' class='btn-admin-access'>⚙️ Admin</a>");
            out.println("<div class='header-logo'><img src='images/logo.jpg' alt='Monsprat Barber Studio Logo'></div>");
            out.println("<h2>Reserva de Turno</h2>");

            out.println("<div class='form-group'>");
            out.println("<label for='cliente'>Nombre Completo</label>");
            out.println("<input type='text' id='cliente' name='cliente' value='" + clientePrevio + "' required placeholder='Ej. Carlos Mendoza'>");
            out.println("</div>");

            out.println("<div class='form-group'>");
            out.println("<label for='telefono'>Número de WhatsApp</label>");
            out.println("<input type='tel' id='telefono' name='telefono' value='" + telefonoPrevio + "' required placeholder='Ej. 3001234567'>");
            out.println("</div>");

            out.println("<div class='form-group'>");
            out.println("<label>Elige tu Barbero</label>");
            out.println("<div class='barber-grid'>");
            boolean esSantiago = barberoFiltro.equals("Santiago Monsalve");
            out.println("<div class='barber-card " + (esSantiago ? "selected" : "") + "' onclick=\"seleccionarBarbero('Santiago Monsalve', this)\">");
            out.println("<img src='images/santiago.jpg' alt='Santiago Monsalve' class='barber-avatar' onerror=\"this.src='images/logo.jpg'\">");
            out.println("<span class='barber-name'>Santiago Monsalve</span>");
            out.println("<div class='barber-meta'>");
            out.println("<span class='barber-badge'>Barbero</span>");
            out.println("<span class='barber-rating'>★ 5.0</span>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            // LISTA MULTI-SELECCIÓN DE SERVICIOS
            out.println("<div class='form-group'>");
            out.println("<label>Servicios (Puedes elegir varios)</label>");
            out.println("<div class='services-container'>");

            out.println("<div class='service-category'>Cortes y Barba</div>");
            imprimirCheckbox(out, "Corte de Cabello", serviciosPrevios);
            imprimirCheckbox(out, "Arreglo de Barba", serviciosPrevios);
            imprimirCheckbox(out, "Corte + Barba", serviciosPrevios);
            imprimirCheckbox(out, "Pigmentación de Barba", serviciosPrevios);

            out.println("<div class='service-category'>Color</div>");
            imprimirCheckbox(out, "Tintura / Color", serviciosPrevios);

            out.println("<div class='service-category'>Facial y Depilación</div>");
            imprimirCheckbox(out, "Arreglo de Cejas", serviciosPrevios);
            imprimirCheckbox(out, "Depilación de Nariz", serviciosPrevios);
            imprimirCheckbox(out, "Depilación de Oídos", serviciosPrevios);
            imprimirCheckbox(out, "Mascarilla Puntos Negros", serviciosPrevios);
            imprimirCheckbox(out, "Mascarilla de Colágeno", serviciosPrevios);
            imprimirCheckbox(out, "Exfoliación Facial", serviciosPrevios);
            imprimirCheckbox(out, "Mascarilla Oro", serviciosPrevios);

            out.println("</div>"); // .services-container
            out.println("</div>");

            out.println("<div class='form-group'>");
            out.println("<label for='fecha'>Fecha</label>");
            out.println("<input type='text' id='fecha' name='fecha' value='" + fechaFiltro + "' required placeholder='Selecciona una fecha disponible'>");
            out.println("</div>");
            out.println("</div>");

            // SEGUNDA TARJETA: DISPONIBILIDAD
            out.println("<div class='card'>");
            out.println("<h2>Disponibilidad en Tiempo Real</h2>");

            out.println("<div class='filter-box'>");
            out.println("<label for='fechaFiltroInput'>Filtro actual:</label>");
            out.println("<input type='text' id='fechaFiltroInput' value='" + fechaFiltro + " (" + barberoFiltro + ")' readonly>");
            out.println("</div>");

            out.println("<label>Selecciona tu franja horaria:</label>");
            out.println("<div class='schedule-grid'>");

            for (String h : horasBase) {
                boolean ocupada = horasOcupadas.contains(h);
                String rangoHora = formatearRangoHora(h);

                if (ocupada) {
                    out.println("<div class='time-slot disabled'><div>" + rangoHora + "</div><span class='status-badge occupied'>Ocupado</span></div>");
                } else {
                    out.println("<div class='time-slot' data-time='" + h + "'><div>" + rangoHora + "</div><span class='status-badge'>Disponible</span></div>");
                }
            }

            out.println("</div>");
            out.println("<button type='submit'>Confirmar Reserva</button>");
            out.println("<p class='note'>* Selecciona un bloque de horario disponible para confirmar.</p>");
            out.println("</div>");

            out.println("</form>");

            out.println("<script src='https://cdn.jsdelivr.net/npm/flatpickr'></script>");
            out.println("<script src='https://npmcdn.com/flatpickr/dist/l10n/es.js'></script>");
            out.println("<script>");

            // LÓGICA DE EXCLUSIÓN MUTUA DE SERVICIOS
            out.println("function validarExclusionServicios() {");
            out.println("  const chkCorte = document.querySelector(\"input[name='servicio'][value='Corte de Cabello']\");");
            out.println("  const chkBarba = document.querySelector(\"input[name='servicio'][value='Arreglo de Barba']\");");
            out.println("  const chkCombo = document.querySelector(\"input[name='servicio'][value='Corte + Barba']\");");

            // Regla: Si selecciona Corte o Barba, se bloquea Corte + Barba
            out.println("  if (chkCorte.checked || chkBarba.checked) {");
            out.println("    chkCombo.disabled = true;");
            out.println("    chkCombo.checked = false;");
            out.println("    chkCombo.parentElement.classList.add('disabled-option');");
            out.println("  } else {");
            out.println("    chkCombo.disabled = false;");
            out.println("    chkCombo.parentElement.classList.remove('disabled-option');");
            out.println("  }");

            // Regla: Si selecciona Corte + Barba, se bloquean Corte y Barba
            out.println("  if (chkCombo.checked) {");
            out.println("    chkCorte.disabled = true; chkCorte.checked = false;");
            out.println("    chkBarba.disabled = true; chkBarba.checked = false;");
            out.println("    chkCorte.parentElement.classList.add('disabled-option');");
            out.println("    chkBarba.parentElement.classList.add('disabled-option');");
            out.println("  } else if (!chkCorte.checked && !chkBarba.checked) {");
            out.println("    chkCorte.disabled = false;");
            out.println("    chkBarba.disabled = false;");
            out.println("    chkCorte.parentElement.classList.remove('disabled-option');");
            out.println("    chkBarba.parentElement.classList.remove('disabled-option');");
            out.println("  }");
            out.println("}");

            out.println("document.querySelectorAll(\"input[name='servicio']\").forEach(chk => {");
            out.println("  chk.addEventListener('change', validarExclusionServicios);");
            out.println("});");
            out.println("validarExclusionServicios();");

            out.println("function obtenerServiciosSeleccionados() {");
            out.println("  const marcados = Array.from(document.querySelectorAll(\"input[name='servicio']:checked\")).map(c => c.value);");
            out.println("  return encodeURIComponent(marcados.join(','));");
            out.println("}");

            out.println("function actualizarDisponibilidad(nuevaFecha = null, nuevoBarbero = null) {");
            out.println("  const barbero = nuevoBarbero || document.getElementById('barbero').value;");
            out.println("  const fecha = nuevaFecha || document.getElementById('fecha').value;");
            out.println("  const cliente = encodeURIComponent(document.getElementById('cliente').value);");
            out.println("  const telefono = encodeURIComponent(document.getElementById('telefono').value);");
            out.println("  const servicio = obtenerServiciosSeleccionados();");
            out.println("  if (fecha) {");
            out.println("    window.location.href = 'HomeServlet?fechaFiltro=' + fecha + '&barberoFiltro=' + encodeURIComponent(barbero) + '&cliente=' + cliente + '&telefono=' + telefono + '&servicio=' + servicio;");
            out.println("  }");
            out.println("}");

            out.println("function seleccionarBarbero(nombreBarbero, elemento) {");
            out.println("  document.querySelectorAll('.barber-card').forEach(c => c.classList.remove('selected'));");
            out.println("  elemento.classList.add('selected');");
            out.println("  document.getElementById('barbero').value = nombreBarbero;");
            out.println("  actualizarDisponibilidad(null, nombreBarbero);");
            out.println("}");

            out.println("const festivosColombia = ['2026-01-01','2026-01-12','2026-03-23','2026-04-02','2026-04-03','2026-05-01','2026-05-18','2026-06-08','2026-06-15','2026-06-29','2026-07-20','2026-08-07','2026-08-17','2026-10-12','2026-11-02','2026-11-16','2026-12-08','2026-12-25'];");
            out.println("const inputFecha = document.getElementById('fecha');");
            out.println("const inputHora = document.getElementById('hora');");
            out.println("const inputTel = document.getElementById('telefono');");
            out.println("const timeSlots = document.querySelectorAll('.time-slot:not(.disabled)');");

            out.println("flatpickr('#fecha', {");
            out.println("  locale: 'es', dateFormat: 'Y-m-d', minDate: 'today', maxDate: new Date().fp_incr(15),");
            out.println("  disable: [ function(date) {");
            out.println("    const esDomingo = (date.getDay() === 0);");
            out.println("    const year = date.getFullYear();");
            out.println("    const month = String(date.getMonth() + 1).padStart(2, '0');");
            out.println("    const day = String(date.getDate()).padStart(2, '0');");
            out.println("    return esDomingo || festivosColombia.includes(`${year}-${month}-${day}`);");
            out.println("  }],");
            out.println("  onChange: function(selectedDates, dateStr) {");
            out.println("    actualizarDisponibilidad(dateStr);");
            out.println("  }");
            out.println("});");

            out.println("timeSlots.forEach(slot => {");
            out.println("  slot.addEventListener('click', function() {");
            out.println("    timeSlots.forEach(s => s.classList.remove('selected'));");
            out.println("    this.classList.add('selected');");
            out.println("    inputHora.value = this.getAttribute('data-time');");
            out.println("  });");
            out.println("});");

            out.println("document.getElementById('bookingForm').addEventListener('submit', function(e) {");
            out.println("  const serviciosMarcados = document.querySelectorAll(\"input[name='servicio']:checked\");");
            out.println("  if (serviciosMarcados.length === 0) { alert('Por favor, selecciona al menos un servicio.'); e.preventDefault(); return; }");
            out.println("  if (!inputTel.value.trim()) { alert('Por favor, ingresa tu número de WhatsApp.'); e.preventDefault(); return; }");
            out.println("  if (!inputFecha.value) { alert('Por favor, selecciona una fecha.'); e.preventDefault(); return; }");
            out.println("  if (!inputHora.value) { alert('Por favor, selecciona una franja horaria haciendo clic en uno de los bloques.'); e.preventDefault(); return; }");
            out.println("});");
            out.println("</script>");

            out.println("</body>");
            out.println("</html>");
        }
    }

    private void imprimirCheckbox(PrintWriter out, String nombreServicio, List<String> serviciosPrevios) {
        boolean checked = serviciosPrevios.contains(nombreServicio);
        out.println("<label class='checkbox-label'>");
        out.println("<input type='checkbox' name='servicio' value='" + nombreServicio + "' " + (checked ? "checked" : "") + ">");
        out.println("<span>" + nombreServicio + "</span>");
        out.println("</label>");
    }

    private String formatearRangoHora(String hora24) {
        switch (hora24) {
            case "10:00": return "10:00 AM - 11:00 AM";
            case "11:00": return "11:00 AM - 12:00 PM";
            case "12:00": return "12:00 PM - 01:00 PM";
            case "13:00": return "01:00 PM - 02:00 PM";
            case "14:00": return "02:00 PM - 03:00 PM";
            case "15:00": return "03:00 PM - 04:00 PM";
            case "16:00": return "04:00 PM - 05:00 PM";
            case "17:00": return "05:00 PM - 06:00 PM";
            case "18:00": return "06:00 PM - 07:00 PM";
            case "19:00": return "07:00 PM - 08:00 PM";
            case "20:00": return "08:00 PM - 09:00 PM";
            default: return hora24;
        }
    }
}