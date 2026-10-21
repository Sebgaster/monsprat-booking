/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
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
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/ReservarTurnoServlet")
public class ReservarTurnoServlet extends HttpServlet {

    // Lista de Festivos Oficiales de Colombia 2026
    private static final List<String> FESTIVOS_COLOMBIA = Arrays.asList(
        "2026-01-01", "2026-01-12", "2026-03-23", "2026-04-02", "2026-04-03", 
        "2026-05-01", "2026-05-18", "2026-06-08", "2026-06-15", "2026-06-29", 
        "2026-07-20", "2026-08-07", "2026-08-17", "2026-10-12", "2026-11-02", 
        "2026-11-16", "2026-12-08", "2026-12-25"
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String cliente = request.getParameter("cliente");
        String telefono = request.getParameter("telefono");

        String[] serviciosSeleccionados = request.getParameterValues("servicio");
        String servicio = (serviciosSeleccionados != null && serviciosSeleccionados.length > 0)
                ? String.join(", ", serviciosSeleccionados)
                : "Sin servicio especificado";

        String fecha = request.getParameter("fecha");
        String hora = request.getParameter("hora");

        // Validar límite de fecha (Hoy hasta 15 días) y no permitir Domingos ni Festivos
        boolean esFechaInvalida = false;
        boolean esDomingoOFestivo = false;

        try {
            LocalDate hoy = LocalDate.now();
            LocalDate maxFecha = hoy.plusDays(15);
            LocalDate fechaReserva = LocalDate.parse(fecha);

            if (fechaReserva.isBefore(hoy) || fechaReserva.isAfter(maxFecha)) {
                esFechaInvalida = true;
            }

            if (fechaReserva.getDayOfWeek() == DayOfWeek.SUNDAY || FESTIVOS_COLOMBIA.contains(fecha)) {
                esDomingoOFestivo = true;
            }

        } catch (Exception e) {
            esFechaInvalida = true;
        }

        ServletContext context = getServletContext();
        List<Turno> listaTurnos = (List<Turno>) context.getAttribute("listaTurnos");
        if (listaTurnos == null) {
            listaTurnos = new ArrayList<>();
        }

        boolean yaOcupado = false;
        if (!esFechaInvalida && !esDomingoOFestivo) {
            for (Turno t : listaTurnos) {
                if (t.getFecha().equals(fecha) && t.getHora().equals(hora)) {
                    yaOcupado = true;
                    break;
                }
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Confirmación - Monsprat Barber Studio</title>");
            out.println("<style>");
            out.println("* { box-sizing: border-box; }");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #0b0b0b; color: #ffffff; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; padding: 20px; }");
            out.println(".card { background-color: #141414; padding: 35px; border-radius: 12px; box-shadow: 0 8px 30px rgba(0, 0, 0, 0.9); border: 1px solid #d4af37; max-width: 460px; width: 100%; text-align: center; }");
            out.println("h2 { color: #d4af37; margin-top: 0; text-transform: uppercase; border-bottom: 1px solid #333333; padding-bottom: 12px; letter-spacing: 1.5px; font-weight: 400; font-size: 1.25rem; }");
            out.println(".info-group { text-align: left; background: #1e1e1e; padding: 18px; border-radius: 8px; margin: 20px 0; border: 1px solid #d4af37; box-shadow: inset 0 0 5px rgba(0,0,0,0.5); }");
            out.println(".info-group p { margin: 10px 0; font-size: 14px; color: #ffffff; }");
            out.println(".info-group strong { color: #d4af37; text-transform: uppercase; font-size: 12px; letter-spacing: 0.5px; }");
            out.println("a.btn { display: inline-block; width: 100%; padding: 14px; color: #000000; text-decoration: none; font-weight: bold; border-radius: 6px; text-transform: uppercase; margin-top: 10px; font-size: 14px; transition: all 0.3s ease; text-align: center; }");
            out.println("a.btn-home { background: linear-gradient(135deg, #d4af37 0%, #aa820a 100%); border: 1px solid #ffffff; }");
            out.println("a.btn-home:hover { background: linear-gradient(135deg, #ffffff 0%, #d4af37 100%); }");
            out.println("a.btn-wsp { background: #25d366; color: #ffffff; border: 1px solid #25d366; font-size: 14px; }");
            out.println("a.btn-wsp:hover { background: #1eb854; box-shadow: 0 0 15px rgba(37, 211, 102, 0.4); }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='card'>");

            if (esDomingoOFestivo) {
                out.println("<h2 style='color: #e74c3c;'>Día No Laboral</h2>");
                out.println("<p style='color: #e0e0e0; font-size: 14px;'>Los <strong>domingos y días festivos</strong> no hay servicio en Monsprat Barber Studio. Por favor, selecciona un día hábil.</p>");
                out.println("<a href='HomeServlet' class='btn btn-home'>Volver al Inicio</a>");
            } else if (esFechaInvalida) {
                out.println("<h2 style='color: #e74c3c;'>Fecha No Permitida</h2>");
                out.println("<p style='color: #e0e0e0; font-size: 14px;'>Solo puedes agendar citas desde la fecha de hoy hasta un máximo de <strong>15 días en el futuro</strong>.</p>");
                out.println("<a href='HomeServlet' class='btn btn-home'>Volver al Inicio</a>");
            } else if (yaOcupado) {
                out.println("<h2 style='color: #e74c3c;'>Horario No Disponible</h2>");
                out.println("<p style='color: #e0e0e0; font-size: 14px;'>El horario seleccionado (<strong>" + hora + "</strong>) ya ha sido reservado para la fecha <strong>" + fecha + "</strong>.</p>");
                out.println("<a href='HomeServlet?fechaFiltro=" + fecha + "' class='btn btn-home'>Seleccionar Otro Horario</a>");
            } else {
                Turno nuevoTurno = new Turno(cliente, "N/A", telefono, servicio, fecha, hora);
                listaTurnos.add(nuevoTurno);
                context.setAttribute("listaTurnos", listaTurnos);

                // Limpiar teléfono y asegurar indicativo de Colombia (57)
                String telefonoLimpio = (telefono != null) ? telefono.replaceAll("[^0-9]", "") : "";
                if (telefonoLimpio.length() == 10) {
                    telefonoLimpio = "57" + telefonoLimpio;
                }

                // Formatear mensaje para WhatsApp
                String mensajeTexto = "💈 *Confirmación de Reserva - Monsprat Barber Studio*\n\n"
                        + "Hola " + cliente + ", aquí tienes el comprobante de tu cita:\n"
                        + "📅 *Fecha:* " + fecha + "\n"
                        + "⏰ *Hora:* " + hora + "\n"
                        + "✂️ *Servicio:* " + servicio + "\n\n"
                        + "¡Te esperamos!";

                String urlWhatsAppCliente = "https://wa.me/" + telefonoLimpio + "?text=" + URLEncoder.encode(mensajeTexto, "UTF-8");

                out.println("<h2>Reserva Confirmada</h2>");
                out.println("<p style='color: #2ecc71; font-size: 15px; font-weight: 500;'>¡Tu turno ha sido agendado con éxito!</p>");

                out.println("<div class='info-group'>");
                out.println("<p><strong>Cliente:</strong> " + cliente + "</p>");
                out.println("<p><strong>WhatsApp:</strong> " + telefono + "</p>");
                out.println("<p><strong>Servicio:</strong> " + servicio + "</p>");
                out.println("<p><strong>Fecha:</strong> " + fecha + "</p>");
                out.println("<p><strong>Hora:</strong> " + hora + "</p>");
                out.println("</div>");

                out.println("<a href='" + urlWhatsAppCliente + "' target='_blank' class='btn btn-wsp'>📱 Recibir comprobante en WhatsApp</a>");
                out.println("<a href='HomeServlet?fechaFiltro=" + fecha + "' class='btn btn-home'>Volver al Inicio</a>");
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}