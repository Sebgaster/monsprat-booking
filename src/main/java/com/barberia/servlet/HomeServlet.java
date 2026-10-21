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

        ServletContext context = getServletContext();
        List<Turno> listaTurnos = (List<Turno>) context.getAttribute("listaTurnos");
        if (listaTurnos == null) {
            listaTurnos = new ArrayList<>();
        }

        List<String> horasBase = Arrays.asList(
            "10:00", "11:00", "12:00", "13:00", 
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"
        );

        List<String> horasOcupadas = new ArrayList<>();
        for (Turno t : listaTurnos) {
            if (t.getFecha().equals(fechaFiltro)) {
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
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #0d0d0d; color: #f1f1f1; margin: 0; padding: 30px 20px; display: flex; justify-content: center; align-items: center; min-height: 100vh; }");
            out.println(".main-wrapper { display: flex; flex-wrap: wrap; gap: 30px; max-width: 900px; width: 100%; justify-content: center; }");
            out.println(".card { background-color: #1a1a1a; padding: 30px; border-radius: 12px; box-shadow: 0 6px 25px rgba(0, 0, 0, 0.8); border: 1px solid #2a2a2a; flex: 1 1 400px; max-width: 440px; }");
            out.println(".header-logo { text-align: center; margin-bottom: 20px; }");
            out.println(".header-logo img { max-width: 130px; height: auto; border-radius: 8px; border: 1px solid #c5a059; padding: 4px; background: #0d0d0d; }");
            out.println("h2 { text-align: center; color: #c5a059; margin-top: 0; margin-bottom: 25px; font-weight: 300; letter-spacing: 1px; text-transform: uppercase; font-size: 1.2rem; border-bottom: 1px solid #2a2a2a; padding-bottom: 10px; }");
            out.println(".form-group { margin-bottom: 18px; }");
            out.println("label { display: block; margin-bottom: 6px; font-size: 13px; color: #aaa; text-transform: uppercase; letter-spacing: 0.5px; }");
            out.println("input, select { width: 100%; padding: 12px; border: 1px solid #333; border-radius: 6px; background-color: #252525; color: #fff; font-size: 14px; transition: border 0.3s; }");
            out.println("input:focus, select:focus { outline: none; border-color: #c5a059; }");
            out.println("optgroup { color: #c5a059; font-style: normal; font-weight: bold; background-color: #1a1a1a; }");
            out.println("option { color: #fff; background-color: #252525; }");
            out.println("button { width: 100%; padding: 14px; background-color: #c5a059; border: none; border-radius: 6px; color: #0d0d0d; font-weight: bold; font-size: 15px; cursor: pointer; margin-top: 15px; text-transform: uppercase; letter-spacing: 1px; transition: background 0.3s ease; }");
            out.println("button:hover { background-color: #a38241; }");
            out.println(".filter-box { background: #222; padding: 12px; border-radius: 6px; margin-bottom: 15px; border: 1px solid #333; }");
            out.println(".schedule-grid { display: grid; grid-template-columns: 1fr; gap: 10px; margin-top: 15px; max-height: 280px; overflow-y: auto; padding-right: 5px; }");
            out.println(".time-slot { background: #252525; padding: 12px; border-radius: 6px; text-align: center; border: 1px solid #333; font-size: 14px; cursor: pointer; transition: all 0.2s ease; display: flex; justify-content: space-between; align-items: center; }");
            out.println(".time-slot:hover { border-color: #c5a059; background: #2e2e2e; }");
            out.println(".time-slot.selected { background: #c5a059; color: #0d0d0d; border-color: #ffffff; font-weight: bold; }");
            out.println(".time-slot.selected .status-badge { background-color: #0d0d0d; color: #c5a059; }");
            out.println(".time-slot.disabled { background-color: #1a1a1a; border-color: #333; color: #555; cursor: not-allowed; opacity: 0.6; }");
            out.println(".status-badge { display: inline-block; padding: 4px 8px; border-radius: 4px; font-size: 11px; font-weight: bold; background-color: #1e3a29; color: #2ecc71; }");
            out.println(".status-badge.occupied { background-color: #3a1e1e; color: #e74c3c; }");
            out.println(".note { font-size: 12px; color: #888; margin-top: 15px; text-align: center; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<form action='ReservarTurnoServlet' method='POST' id='bookingForm' class='main-wrapper'>");
            out.println("<input type='hidden' id='hora' name='hora' required>");

            // PRIMERA TARJETA: FORMULARIO
            out.println("<div class='card'>");
            out.println("<div class='header-logo'><img src='images/logo.jpg' alt='Monsprat Barber Studio Logo'></div>");
            out.println("<h2>Reserva de Turno</h2>");

            out.println("<div class='form-group'>");
            out.println("<label for='cliente'>Nombre Completo</label>");
            out.println("<input type='text' id='cliente' name='cliente' required placeholder='Ej. Carlos Mendoza'>");
            out.println("</div>");

            // CAMPO DE WHATSAPP / TELÉFONO
            out.println("<div class='form-group'>");
            out.println("<label for='telefono'>Número de WhatsApp</label>");
            out.println("<input type='tel' id='telefono' name='telefono' required placeholder='Ej. 3001234567'>");
            out.println("</div>");

            out.println("<div class='form-group'>");
            out.println("<label for='servicio'>Servicio</label>");
            out.println("<select id='servicio' name='servicio' required>");
            out.println("<option value='' disabled selected>-- Selecciona un servicio --</option>");
            
            out.println("<optgroup label='Cortes y Barba'>");
            out.println("<option value='Corte de Cabello'>Corte de Cabello</option>");
            out.println("<option value='Arreglo de Barba'>Arreglo de Barba</option>");
            out.println("<option value='Corte + Barba'>Corte + Barba</option>");
            out.println("<option value='Pigmentación de Barba'>Pigmentación de Barba</option>");
            out.println("</optgroup>");
            
            out.println("<optgroup label='Color'>");
            out.println("<option value='Tintura / Color'>Tintura / Color</option>");
            out.println("</optgroup>");
            
            out.println("<optgroup label='Facial y Depilación'>");
            out.println("<option value='Arreglo de Cejas'>Arreglo de Cejas</option>");
            out.println("<option value='Depilación de Nariz'>Depilación de Nariz</option>");
            out.println("<option value='Depilación de Oídos'>Depilación de Oídos</option>");
            out.println("<option value='Mascarilla Puntos Negros'>Mascarilla Puntos Negros</option>");
            out.println("<option value='Mascarilla de Colágeno'>Mascarilla de Colágeno</option>");
            out.println("<option value='Exfoliación Facial'>Exfoliación Facial</option>");
            out.println("<option value='Mascarilla Oro'>Mascarilla Oro</option>");
            out.println("</optgroup>");

            out.println("</select>");
            out.println("</div>");

            out.println("<div class='form-group'>");
            out.println("<label for='fecha'>Fecha</label>");
            out.println("<input type='text' id='fecha' name='fecha' value='" + fechaFiltro + "' required placeholder='Selecciona una fecha disponible'>");
            out.println("</div>");
            out.println("</div>");

            // SEGUNDA TARJETA: DISPONIBILIDAD Y BOTÓN FINAL
            out.println("<div class='card'>");
            out.println("<h2>Disponibilidad en Tiempo Real</h2>");

            out.println("<div class='filter-box'>");
            out.println("<label for='fechaFiltroInput'>Fecha seleccionada:</label>");
            out.println("<input type='text' id='fechaFiltroInput' value='" + fechaFiltro + "' readonly>");
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
            out.println("    if (dateStr) { window.location.href = 'HomeServlet?fechaFiltro=' + dateStr; }");
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
            out.println("  if (!inputTel.value.trim()) { alert('Por favor, ingresa tu número de WhatsApp.'); e.preventDefault(); return; }");
            out.println("  if (!inputFecha.value) { alert('Por favor, selecciona una fecha.'); e.preventDefault(); return; }");
            out.println("  if (!inputHora.value) { alert('Por favor, selecciona una franja horaria haciendo clic en uno de los bloques.'); e.preventDefault(); return; }");
            out.println("});");
            out.println("</script>");

            out.println("</body>");
            out.println("</html>");
        }
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
            default: return hora24;
        }
    }
}