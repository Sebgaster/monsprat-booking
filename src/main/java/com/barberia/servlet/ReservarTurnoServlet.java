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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ReservarTurnoServlet")
public class ReservarTurnoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Recepción de parámetros del formulario
        String cliente = request.getParameter("cliente");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String servicio = request.getParameter("servicio");
        String fecha = request.getParameter("fecha");
        String hora = request.getParameter("hora");

        ServletContext context = getServletContext();
        List<Turno> listaTurnos = (List<Turno>) context.getAttribute("listaTurnos");
        if (listaTurnos == null) {
            listaTurnos = new ArrayList<>();
        }

        // Validación para evitar reservas duplicadas en el mismo horario y fecha
        boolean yaOcupado = false;
        for (Turno t : listaTurnos) {
            if (t.getFecha().equals(fecha) && t.getHora().equals(hora)) {
                yaOcupado = true;
                break;
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
            out.println("a.btn { display: inline-block; width: 100%; padding: 14px; background: linear-gradient(135deg, #d4af37 0%, #aa820a 100%); color: #000000; text-decoration: none; font-weight: bold; border-radius: 6px; text-transform: uppercase; margin-top: 10px; border: 1px solid #ffffff; letter-spacing: 1.5px; font-size: 14px; transition: all 0.3s ease; }");
            out.println("a.btn:hover { background: linear-gradient(135deg, #ffffff 0%, #d4af37 100%); color: #000000; box-shadow: 0 0 12px rgba(255, 255, 255, 0.4); }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='card'>");

            if (yaOcupado) {
                out.println("<h2 style='color: #e74c3c;'>Horario No Disponible</h2>");
                out.println("<p style='color: #e0e0e0; font-size: 14px;'>El horario seleccionado (<strong>" + hora + "</strong>) ya ha sido reservado para la fecha <strong>" + fecha + "</strong>.</p>");
                out.println("<p style='color: #aaa; font-size: 13px;'>Por favor selecciona un horario libre en la pantalla principal.</p>");
            } else {
                Turno nuevoTurno = new Turno(cliente, email, telefono, servicio, fecha, hora);
                listaTurnos.add(nuevoTurno);
                context.setAttribute("listaTurnos", listaTurnos);

                out.println("<h2>Reserva Confirmada</h2>");
                out.println("<p style='color: #2ecc71; font-size: 15px; font-weight: 500;'>¡Tu turno ha sido agendado con éxito!</p>");

                out.println("<div class='info-group'>");
                out.println("<p><strong>Cliente:</strong> " + cliente + "</p>");
                out.println("<p><strong>Correo Electrónico:</strong> " + email + "</p>");
                out.println("<p><strong>Teléfono / WhatsApp:</strong> " + telefono + "</p>");
                out.println("<p><strong>Servicio:</strong> " + servicio + "</p>");
                out.println("<p><strong>Fecha:</strong> " + fecha + "</p>");
                out.println("<p><strong>Hora:</strong> " + hora + " hrs</p>");
                out.println("</div>");
            }

            out.println("<a href='HomeServlet?fechaFiltro=" + fecha + "' class='btn'>Volver al Inicio</a>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}