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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Control de Seguridad / Autenticación de Sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect("LoginAdminServlet");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        // 2. Manejo de Filtro por Fecha
        String fechaFiltro = request.getParameter("fechaFiltro");
        if (fechaFiltro == null || fechaFiltro.isEmpty()) {
            fechaFiltro = LocalDate.now().toString();
        }

        // 3. Recuperar Turnos de la Memoria (ServletContext)
        ServletContext context = getServletContext();
        List<Turno> listaTurnos = (List<Turno>) context.getAttribute("listaTurnos");
        if (listaTurnos == null) {
            listaTurnos = new ArrayList<>();
        }

        // 4. Renderizar Interfaz HTML
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Panel de Administración - Monsprat Barber Studio</title>");
            out.println("<style>");
            out.println("* { box-sizing: border-box; }");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #0d0d0d; color: #f1f1f1; margin: 0; padding: 30px 20px; display: flex; justify-content: center; min-height: 100vh; }");
            out.println(".admin-container { max-width: 1000px; width: 100%; background-color: #1a1a1a; padding: 30px; border-radius: 12px; border: 1px solid #c5a059; box-shadow: 0 8px 30px rgba(0,0,0,0.9); }");
            out.println(".header-bar { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #2a2a2a; padding-bottom: 15px; margin-bottom: 25px; flex-wrap: wrap; gap: 10px; }");
            out.println("h2 { color: #c5a059; margin: 0; text-transform: uppercase; letter-spacing: 1px; font-size: 1.3rem; font-weight: 400; }");
            out.println(".btn-logout { background: #4a1e1e; color: #e74c3c; padding: 8px 14px; border-radius: 6px; text-decoration: none; font-size: 12px; font-weight: bold; border: 1px solid #e74c3c; transition: 0.3s; }");
            out.println(".btn-logout:hover { background: #e74c3c; color: #fff; }");
            out.println(".top-tools { display: flex; justify-content: space-between; align-items: center; background: #222; padding: 15px; border-radius: 8px; border: 1px solid #333; margin-bottom: 25px; flex-wrap: wrap; gap: 15px; }");
            out.println("label { color: #aaa; font-size: 12px; text-transform: uppercase; font-weight: bold; letter-spacing: 0.5px; }");
            out.println("input[type='date'] { padding: 9px 12px; border: 1px solid #c5a059; border-radius: 6px; background: #0d0d0d; color: #fff; font-size: 14px; }");
            out.println(".stat-box { background: #0d0d0d; padding: 8px 16px; border-radius: 6px; border: 1px solid #333; color: #c5a059; font-size: 13px; font-weight: bold; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
            out.println("th, td { padding: 14px 12px; text-align: left; border-bottom: 1px solid #2a2a2a; font-size: 13px; }");
            out.println("th { background-color: #222; color: #c5a059; text-transform: uppercase; font-size: 11px; letter-spacing: 1px; }");
            out.println("tr:hover { background-color: #252525; }");
            out.println(".badge-hora { background: #c5a059; color: #0d0d0d; font-weight: bold; padding: 4px 8px; border-radius: 4px; font-size: 12px; }");
            out.println(".btn-wa { color: #2ecc71; text-decoration: none; font-weight: bold; display: inline-flex; align-items: center; gap: 4px; }");
            out.println(".btn-wa:hover { text-decoration: underline; }");
            out.println(".btn-cancel { background: #e74c3c; color: #fff; border: none; padding: 6px 12px; border-radius: 4px; font-size: 11px; font-weight: bold; cursor: pointer; text-transform: uppercase; }");
            out.println(".btn-cancel:hover { background: #c0392b; }");
            out.println(".empty-msg { text-align: center; padding: 40px; color: #888; font-style: italic; }");
            out.println(".btn-public { color: #888; font-size: 12px; text-decoration: none; margin-left: 10px; }");
            out.println(".btn-public:hover { color: #c5a059; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='admin-container'>");

            // Encabezado
            out.println("<div class='header-bar'>");
            out.println("<div>");
            out.println("<h2>Panel Administrativo</h2>");
            out.println("<a href='HomeServlet' class='btn-public' target='_blank'> Ver Sitio Público ↗</a>");
            out.println("</div>");
            out.println("<a href='LogoutAdminServlet' class='btn-logout'>Cerrar Sesión</a>");
            out.println("</div>");

            // Herramientas y Filtro
            int totalCitasHoy = 0;
            for (Turno t : listaTurnos) {
                if (t.getFecha().equals(fechaFiltro)) {
                    totalCitasHoy++;
                }
            }

            out.println("<div class='top-tools'>");
            out.println("<form method='GET' action='AdminServlet'>");
            out.println("<label for='fechaFiltro'>Consultar Fecha: </label>");
            out.println("<input type='date' id='fechaFiltro' name='fechaFiltro' value='" + fechaFiltro + "' onchange='this.form.submit()'>");
            out.println("</form>");
            out.println("<div class='stat-box'>Citas Agendadas: " + totalCitasHoy + "</div>");
            out.println("</div>");

            // Tabla de Reservas
            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Hora</th>");
            out.println("<th>Cliente</th>");
            out.println("<th>Contacto</th>");
            out.println("<th>Servicio</th>");
            out.println("<th>Acción</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            int contador = 0;
            for (Turno t : listaTurnos) {
                if (t.getFecha().equals(fechaFiltro)) {
                    contador++;
                    String telefonoLimpio = t.getTelefono() != null ? t.getTelefono().replaceAll("[^0-9]", "") : "";
                    
                    out.println("<tr>");
                    out.println("<td><span class='badge-hora'>" + t.getHora() + "</span></td>");
                    out.println("<td><strong>" + t.getCliente() + "</strong></td>");
                    out.println("<td>");
                    out.println("<a href='https://wa.me/" + telefonoLimpio + "' target='_blank' class='btn-wa'>📲 " + t.getTelefono() + "</a><br>");
                    out.println("<small style='color:#888;'>" + t.getEmail() + "</small>");
                    out.println("</td>");
                    out.println("<td>" + t.getServicio() + "</td>");
                    out.println("<td>");
                    out.println("<form action='CancelarTurnoServlet' method='POST' style='margin:0;' onsubmit=\"return confirm('¿Seguro que deseas cancelar esta cita?');\">");
                    out.println("<input type='hidden' name='turnoId' value='" + t.getId() + "'>");
                    out.println("<input type='hidden' name='fechaFiltro' value='" + fechaFiltro + "'>");
                    out.println("<button type='submit' class='btn-cancel'>Cancelar</button>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            }

            if (contador == 0) {
                out.println("<tr><td colspan='5' class='empty-msg'>No hay reservas registradas para el día " + fechaFiltro + ".</td></tr>");
            }

            out.println("</tbody>");
            out.println("</table>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}