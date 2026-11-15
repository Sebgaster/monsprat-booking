/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.barberia.servlet;

import com.barberia.dao.TurnoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/CancelarTurnoServlet")
public class CancelarTurnoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Validar autenticación de administrador
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect("LoginAdminServlet");
            return;
        }

        // 2. Capturar parámetros del formulario
        String turnoId = request.getParameter("turnoId");
        String fechaFiltro = request.getParameter("fechaFiltro");

        // 3. Eliminar el turno directamente en PostgreSQL (Render)
        if (turnoId != null && !turnoId.isEmpty()) {
            TurnoDAO turnoDAO = new TurnoDAO();
            turnoDAO.eliminarTurno(turnoId);
        }

        // 4. Redirigir al panel conservando la fecha consultada
        if (fechaFiltro != null && !fechaFiltro.isEmpty()) {
            response.sendRedirect("AdminServlet?fechaFiltro=" + fechaFiltro);
        } else {
            response.sendRedirect("AdminServlet");
        }
    }
}