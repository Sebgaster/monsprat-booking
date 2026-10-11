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

    private static final List<String> HORARIOS_BASE = Arrays.asList(
        "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
        "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
        "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
        "18:00", "18:30", "19:00", "19:30"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String fechaFiltro = request.getParameter("fechaFiltro");
        if (fechaFiltro == null || fechaFiltro.isEmpty()) {
            fechaFiltro = LocalDate.now().toString();
        }

        ServletContext context = getServletContext();
        List<Turno> listaTurnos = (List<Turno>) context.getAttribute("listaTurnos");
        if (listaTurnos == null) {
            listaTurnos = new ArrayList<>();
        }

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
            out.println("<title>Monsprat Barber Studio - Reserva de Turnos</title>");
            out.println("<style>");
            out.println("* { box-sizing: border-box; }");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #0b0b0b; color: #ffffff; margin: 0; padding: 30px 20px; display: flex; justify-content: center; align-items: center; min-height: 100vh; }");
            out.println(".main-wrapper { display: flex; flex-wrap: wrap; gap: 30px; max-width: 1050px; width: 100%; justify-content: center; }");
            out.println(".card { background-color: #141414; padding: 30px; border-radius: 12px; box-shadow: 0 8px 30px rgba(0, 0, 0, 0.9); border: 1px solid #d4af37; flex: 1 1 400px; max-width: 460px; }");
            out.println(".header-logo { text-align: center; margin-bottom: 20px; }");
            out.println(".header-logo img { max-width: 130px; height: auto; border-radius: 8px; border: 2px solid #d4af37; padding: 4px; background: #000000; box-shadow: 0 0 10px rgba(212, 175, 55, 0.3); }");
            out.println("h2 { text-align: center; color: #d4af37; margin-top: 0; margin-bottom: 20px; font-weight: 400; letter-spacing: 1.5px; text-transform: uppercase; font-size: 1.25rem; border-bottom: 1px solid #333333; padding-bottom: 10px; }");
            out.println(".form-group { margin-bottom: 16px; }");
            out.println("label { display: block; margin-bottom: 6px; font-size: 12px; color: #e0e0e0; text-transform: uppercase; letter-spacing: 1px; font-weight: 600; }");
            out.println("input, select { width: 100%; padding: 12px; border: 1px solid #d4af37; border-radius: 6px; background-color: #1e1e1e; color: #ffffff; font-size: 14px; transition: all 0.3s ease; }");
            out.println("input::placeholder { color: #888888; }");
            out.println("input:focus, select:focus { outline: none; border-color: #ffffff; box-shadow: 0 0 8px rgba(212, 175, 55, 0.6); }");
            out.println("button { width: 100%; padding: 14px; background: linear-gradient(135deg, #d4af37 0%, #aa820a 100%); border: 1px solid #ffffff; border-radius: 6px; color: #000000; font-weight: bold; font-size: 15px; cursor: pointer; margin-top: 10px; text-transform: uppercase; letter-spacing: 1.5px; transition: all 0.3s ease; }");
            out.println("button:hover { background: linear-gradient(135deg, #ffffff 0%, #d4af37 100%); color: #000000; box-shadow: 0 0 12px rgba(255, 255, 255, 0.4); }");
            out.println(".schedule-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 10px; margin-top: 15px; max-height: 300px; overflow-y: auto; padding-right: 5px; }");
            out.println(".time-slot { background: #1e1e1e; padding: 10px; border-radius: 6px; text-align: center; border: 1px solid #333333; font-size: 13px; color: #ffffff; font-weight: 500; }");
            out.println(".status-badge { display: inline-block; padding: 3px 8px; border-radius: 4px; font-size: 10px; font-weight: bold; margin-top: 4px; text-transform: uppercase; letter-spacing: 0.5px; }");
            out.println(".badge-available { background-color: #1b3820; color: #2ecc71; border: 1px solid #2ecc71; }");
            out.println(".badge-busy { background-color: #3e1b1b; color: #e74c3c; border: 1px solid #e74c3c; }");
            out.println(".filter-box { background: #1e1e1e; padding: 12px; border-radius: 6px; margin-bottom: 15px; border: 1px solid #d4af37; }");
            out.println("optgroup { background-color: #141414; color: #d4af37; font-weight: bold; }");
            out.println("option { background-color: #1e1e1e; color: #ffffff; font-weight: normal; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='main-wrapper'>");

            // FORMULARIO DE RESERVA
            out.println("<div class='card'>");
            out.println("<div class='header-logo'><img src='images/logo.jpg' alt='Monsprat Barber Studio Logo'></div>");
            out.println("<h2>Reserva de Turno</h2>");
            out.println("<form action='ReservarTurnoServlet' method='POST'>");

            out.println("<div class='form-group'><label for='cliente'>Nombre Completo</label><input type='text' id='cliente' name='cliente' required placeholder='Ej. Carlos Mendoza'></div>");
            out.println("<div class='form-group'><label for='email'>Correo Electrónico</label><input type='email' id='email' name='email' required placeholder='Ej. cliente@email.com'></div>");
            out.println("<div class='form-group'><label for='telefono'>Teléfono / WhatsApp</label><input type='tel' id='telefono' name='telefono' required placeholder='Ej. +57 300 123 4567'></div>");

            out.println("<div class='form-group'><label for='servicio'>Servicio</label><select id='servicio' name='servicio' required>");
            out.println("<option value=''>-- Selecciona un servicio --</option>");

            out.println("<optgroup label='Cortes y Barba'>");
            out.println("<option value='Corte de Cabello'>Corte de Cabello (30 min)</option>");
            out.println("<option value='Arreglo de Barba'>Arreglo de Barba (30 min)</option>");
            out.println("<option value='Corte + Barba'>Corte + Barba (30 min)</option>");
            out.println("<option value='Pigmentación de Barba'>Pigmentación de Barba (30 min)</option>");
            out.println("</optgroup>");

            out.println("<optgroup label='Color'>");
            out.println("<option value='Tintura / Color'>Tintura / Color (30 min)</option>");
            out.println("</optgroup>");

            out.println("<optgroup label='Facial y Depilación'>");
            out.println("<option value='Arreglo de Cejas'>Arreglo de Cejas (30 min)</option>");
            out.println("<option value='Depilación de Nariz'>Depilación de Nariz (30 min)</option>");
            out.println("<option value='Depilación de Oídos'>Depilación de Oídos (30 min)</option>");
            out.println("<option value='Mascarilla Puntos Negros'>Mascarilla Puntos Negros (30 min)</option>");
            out.println("<option value='Mascarilla de Colágeno'>Mascarilla de Colágeno (30 min)</option>");
            out.println("<option value='Exfoliación Facial'>Exfoliación Facial (30 min)</option>");
            out.println("<option value='Mascarilla Oro'>Mascarilla Oro (30 min)</option>");
            out.println("</optgroup>");

            out.println("</select></div>");

            out.println("<div class='form-group'><label for='fecha'>Fecha</label><input type='date' id='fecha' name='fecha' value='" + fechaFiltro + "' required></div>");

            out.println("<div class='form-group'><label for='hora'>Horarios Disponibles (30 min)</label><select id='hora' name='hora' required>");
            out.println("<option value=''>-- Selecciona un horario libre --</option>");

            for (String hora : HORARIOS_BASE) {
                if (horasOcupadas.contains(hora)) {
                    out.println("<option value='" + hora + "' disabled style='color: #777777;'> " + hora + " (OCUPADO)</option>");
                } else {
                    out.println("<option value='" + hora + "'>" + hora + " (Disponible)</option>");
                }
            }

            out.println("</select></div>");
            out.println("<button type='submit'>Confirmar Reserva</button>");
            out.println("</form>");
            out.println("</div>");

            // PANEL DERECHO: DISPONIBILIDAD
            out.println("<div class='card'>");
            out.println("<h2>Disponibilidad en Tiempo Real</h2>");

            out.println("<form method='GET' action='HomeServlet' class='filter-box'>");
            out.println("<label for='fechaFiltro'>Ver disponibilidad para la fecha:</label>");
            out.println("<input type='date' id='fechaFiltro' name='fechaFiltro' value='" + fechaFiltro + "' onchange='this.form.submit()'>");
            out.println("</form>");

            out.println("<div class='schedule-grid'>");
            for (String hora : HORARIOS_BASE) {
                boolean estaOcupado = horasOcupadas.contains(hora);
                out.println("<div class='time-slot'>");
                out.println("<div>" + hora + "</div>");
                if (estaOcupado) {
                    out.println("<span class='status-badge badge-busy'>Ocupado</span>");
                } else {
                    out.println("<span class='status-badge badge-available'>Disponible</span>");
                }
                out.println("</div>");
            }
            out.println("</div>");

            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}