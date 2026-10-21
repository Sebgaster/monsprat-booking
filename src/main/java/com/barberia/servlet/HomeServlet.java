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
import java.util.List;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        LocalDate hoy = LocalDate.now();
        LocalDate maxFecha = hoy.plusDays(15);

        String fechaFiltro = request.getParameter("fechaFiltro");
        if (fechaFiltro == null || fechaFiltro.isEmpty()) {
            fechaFiltro = hoy.toString();
        } else {
            try {
                LocalDate fechaParsed = LocalDate.parse(fechaFiltro);
                if (fechaParsed.isBefore(hoy) || fechaParsed.isAfter(maxFecha)) {
                    fechaFiltro = hoy.toString();
                }
            } catch (Exception e) {
                fechaFiltro = hoy.toString();
            }
        }

        ServletContext context = getServletContext();
        List<Turno> listaTurnos = (List<Turno>) context.getAttribute("listaTurnos");
        if (listaTurnos == null) {
            listaTurnos = new ArrayList<>();
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Monsprat Barber Studio - Reserva</title>");
            out.println("<style>");
            out.println("* { box-sizing: border-box; }");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #0d0d0d; color: #f1f1f1; margin: 0; padding: 30px 20px; display: flex; justify-content: center; align-items: center; min-height: 100vh; }");
            out.println(".main-wrapper { display: flex; flex-wrap: wrap; gap: 30px; max-width: 950px; width: 100%; justify-content: center; }");
            out.println(".card { background-color: #1a1a1a; padding: 30px; border-radius: 12px; box-shadow: 0 6px 25px rgba(0, 0, 0, 0.8); border: 1px solid #c5a059; flex: 1 1 420px; max-width: 460px; display: flex; flex-direction: column; justify-content: space-between; }");
            out.println(".header-logo { text-align: center; margin-bottom: 20px; }");
            out.println(".header-logo img { max-width: 120px; height: auto; border-radius: 8px; border: 1px solid #c5a059; padding: 4px; background: #0d0d0d; }");
            out.println("h2 { text-align: center; color: #c5a059; margin-top: 0; margin-bottom: 25px; font-weight: 300; letter-spacing: 1.5px; text-transform: uppercase; font-size: 1.1rem; border-bottom: 1px solid #2a2a2a; padding-bottom: 10px; }");
            out.println(".form-group { margin-bottom: 16px; }");
            out.println("label { display: block; margin-bottom: 6px; font-size: 11px; color: #aaa; text-transform: uppercase; letter-spacing: 0.5px; font-weight: bold; }");
            out.println("input[type='text'], input[type='tel'], input[type='date'], select { width: 100%; padding: 11px; border: 1px solid #333; border-radius: 6px; background-color: #252525; color: #fff; font-size: 14px; transition: border 0.3s; }");
            out.println("input:focus, select:focus { outline: none; border-color: #c5a059; }");
            
            out.println(".services-container { background-color: #252525; border: 1px solid #333; border-radius: 6px; padding: 12px; max-height: 180px; overflow-y: auto; }");
            out.println(".category-title { color: #c5a059; font-size: 11px; text-transform: uppercase; font-weight: bold; letter-spacing: 1px; margin-top: 10px; margin-bottom: 6px; border-bottom: 1px solid #333; padding-bottom: 2px; }");
            out.println(".category-title:first-child { margin-top: 0; }");
            out.println(".checkbox-item { display: flex; align-items: center; gap: 8px; margin-bottom: 6px; cursor: pointer; font-size: 13px; color: #ddd; }");
            out.println(".checkbox-item input[type='checkbox'] { width: auto; accent-color: #c5a059; cursor: pointer; }");
            out.println(".checkbox-item input[type='checkbox']:disabled + span { color: #555; text-decoration: line-through; cursor: not-allowed; }");

            out.println("button[type='submit'] { width: 100%; padding: 14px; background: linear-gradient(135deg, #c5a059 0%, #a38241 100%); border: none; border-radius: 6px; color: #0d0d0d; font-weight: bold; font-size: 14px; cursor: pointer; margin-top: 15px; text-transform: uppercase; letter-spacing: 1px; transition: 0.3s ease; }");
            out.println("button[type='submit']:hover { background: #c5a059; box-shadow: 0 0 15px rgba(197, 160, 89, 0.4); }");
            
            out.println(".schedule-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 12px; margin-top: 10px; max-height: 380px; overflow-y: auto; padding-right: 5px; }");
            out.println(".time-slot { background: #252525; padding: 12px 8px; border-radius: 8px; text-align: center; border: 1px solid #333; font-size: 14px; transition: all 0.2s ease; cursor: pointer; display: flex; flex-direction: column; align-items: center; gap: 4px; }");
            out.println(".time-slot.available:hover { border-color: #c5a059; background: #2d2a1e; transform: translateY(-2px); }");
            out.println(".time-slot.selected { border: 2px solid #c5a059 !important; background: #383013 !important; }");
            out.println(".time-slot.occupied { background: #1f1717; border-color: #4a2323; cursor: not-allowed; opacity: 0.6; }");
            
            out.println(".status-badge { display: inline-block; padding: 2px 8px; border-radius: 4px; font-size: 10px; font-weight: bold; text-transform: uppercase; }");
            out.println(".status-badge.avail { background-color: #1e3a29; color: #2ecc71; border: 1px solid #2ecc71; }");
            out.println(".status-badge.occ { background-color: #4a1e1e; color: #e74c3c; border: 1px solid #e74c3c; }");
            
            out.println(".admin-link-container { margin-top: 20px; text-align: center; border-top: 1px solid #2a2a2a; padding-top: 15px; }");
            out.println(".admin-link { color: #666; font-size: 11px; text-decoration: none; letter-spacing: 0.5px; transition: color 0.3s; }");
            out.println(".admin-link:hover { color: #c5a059; }");
            out.println("</style>");
            
            out.println("<script>");
            out.println("function cambiarFecha(fecha) {");
            out.println("    window.location.href = 'HomeServlet?fechaFiltro=' + fecha;");
            out.println("}");
            out.println("function seleccionarHorario(elemento, hora) {");
            out.println("    document.querySelectorAll('.time-slot').forEach(el => el.classList.remove('selected'));");
            out.println("    elemento.classList.add('selected');");
            out.println("    document.getElementById('hora').value = hora;");
            out.println("}");
            out.println("function validarServiciosExcluyentes(checkboxActual) {");
            out.println("    const cbCorte = document.querySelector('input[value=\"Corte de Cabello\"]');");
            out.println("    const cbBarba = document.querySelector('input[value=\"Arreglo de Barba\"]');");
            out.println("    const cbCombo = document.querySelector('input[value=\"Corte + Barba\"]');");
            out.println("    if (checkboxActual === cbCombo && cbCombo.checked) {");
            out.println("        cbCorte.checked = false; cbBarba.checked = false;");
            out.println("        cbCorte.disabled = true; cbBarba.disabled = true;");
            out.println("    } else if (checkboxActual === cbCombo && !cbCombo.checked) {");
            out.println("        cbCorte.disabled = false; cbBarba.disabled = false;");
            out.println("    } else if ((checkboxActual === cbCorte || checkboxActual === cbBarba) && (cbCorte.checked || cbBarba.checked)) {");
            out.println("        cbCombo.checked = false; cbCombo.disabled = true;");
            out.println("    } else if (!cbCorte.checked && !cbBarba.checked) {");
            out.println("        cbCombo.disabled = false;");
            out.println("    }");
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='main-wrapper'>");

            // TARJETA FORMULARIO
            out.println("<div class='card'>");
            out.println("<div>");
            out.println("<div class='header-logo'><img src='images/logo.jpg' alt='Monsprat Barber Studio'></div>");
            out.println("<h2>Reserva de Turno</h2>");
            out.println("<form action='ReservarTurnoServlet' method='POST'>");
            
            out.println("<div class='form-group'><label for='cliente'>Nombre Completo</label><input type='text' id='cliente' name='cliente' required placeholder='Ej. Carlos Mendoza'></div>");
            out.println("<div class='form-group'><label for='telefono'>Teléfono / WhatsApp</label><input type='tel' id='telefono' name='telefono' required placeholder='+57 300 123 4567'></div>");
            
            out.println("<div class='form-group'><label>Servicios (Puedes seleccionar varios)</label>");
            out.println("<div class='services-container'>");
            
            out.println("<div class='category-title'>Cortes y Barba</div>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Corte de Cabello' onchange='validarServiciosExcluyentes(this)'> <span>Corte de Cabello</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Arreglo de Barba' onchange='validarServiciosExcluyentes(this)'> <span>Arreglo de Barba</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Corte + Barba' onchange='validarServiciosExcluyentes(this)'> <span>Corte + Barba</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Pigmentación de Barba'> <span>Pigmentación de Barba</span></label>");
            
            out.println("<div class='category-title'>Color</div>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Tintura / Color'> <span>Tintura / Color</span></label>");
            
            out.println("<div class='category-title'>Facial y Depilación</div>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Arreglo de Cejas'> <span>Arreglo de Cejas</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Depilación de Nariz'> <span>Depilación de Nariz</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Depilación de Oídos'> <span>Depilación de Oídos</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Mascarilla Puntos Negros'> <span>Mascarilla Puntos Negros</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Mascarilla de Colágeno'> <span>Mascarilla de Colágeno</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Exfoliación Facial'> <span>Exfoliación Facial</span></label>");
            out.println("<label class='checkbox-item'><input type='checkbox' name='servicio' value='Mascarilla Oro'> <span>Mascarilla Oro</span></label>");
            
            out.println("</div></div>");

            out.println("<div class='form-group'><label for='fecha'>Fecha Seleccionada</label>");
            out.println("<input type='date' id='fecha' name='fecha' value='" + fechaFiltro + "' min='" + hoy.toString() + "' max='" + maxFecha.toString() + "' required onchange='cambiarFecha(this.value)'></div>");

            out.println("<div class='form-group'><label for='hora'>Horario Seleccionado</label>");
            out.println("<select id='hora' name='hora' required>");
            out.println("<option value=''>-- Selecciona o haz clic a la derecha --</option>");
            
            String[] horas = {"10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"};
            for (String h : horas) {
                out.println("<option value='" + h + "'>" + h + "</option>");
            }
            out.println("</select></div>");

            out.println("<button type='submit'>Confirmar Reserva</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");

            // TARJETA DISPONIBILIDAD
            out.println("<div class='card'>");
            out.println("<div>");
            out.println("<h2>Disponibilidad " + fechaFiltro + "</h2>");

            out.println("<div class='schedule-grid'>");
            for (String h : horas) {
                boolean ocupado = false;
                for (Turno t : listaTurnos) {
                    if (t.getFecha().equals(fechaFiltro) && t.getHora().equals(h)) {
                        ocupado = true;
                        break;
                    }
                }
                if (ocupado) {
                    out.println("<div class='time-slot occupied'>");
                    out.println("<div><strong>" + h + "</strong></div>");
                    out.println("<span class='status-badge status-occ'>Ocupado</span>");
                    out.println("</div>");
                } else {
                    out.println("<div class='time-slot available' onclick=\"seleccionarHorario(this, '" + h + "')\">");
                    out.println("<div><strong>" + h + "</strong></div>");
                    out.println("<span class='status-badge avail'>Disponible</span>");
                    out.println("</div>");
                }
            }
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='admin-link-container'>");
            out.println("<a href='AdminServlet' class='admin-link'>🔒 Acceso Administración Monsprat</a>");
            out.println("</div>");

            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}