/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.barberia.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginAdminServlet")
public class LoginAdminServlet extends HttpServlet {

    // Clave de acceso al panel (puedes cambiarla aquí)
    private static final String CLAVE_ADMIN = "monsprat2026";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        String error = request.getParameter("error");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Acceso Administrador - Monsprat Barber Studio</title>");
            out.println("<style>");
            out.println("* { box-sizing: border-box; }");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #0d0d0d; color: #f1f1f1; margin: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }");
            out.println(".login-card { background: #1a1a1a; padding: 40px; border-radius: 12px; border: 1px solid #c5a059; box-shadow: 0 8px 30px rgba(0,0,0,0.9); width: 100%; max-width: 380px; text-align: center; }");
            out.println("h2 { color: #c5a059; margin-top: 0; text-transform: uppercase; font-size: 1.1rem; letter-spacing: 1px; margin-bottom: 25px; }");
            out.println("input[type='password'] { width: 100%; padding: 12px; border: 1px solid #333; border-radius: 6px; background-color: #252525; color: #fff; font-size: 14px; margin-bottom: 20px; }");
            out.println("input[type='password']:focus { outline: none; border-color: #c5a059; }");
            out.println("button { width: 100%; padding: 12px; background-color: #c5a059; border: none; border-radius: 6px; color: #0d0d0d; font-weight: bold; cursor: pointer; text-transform: uppercase; letter-spacing: 1px; }");
            out.println("button:hover { background-color: #a38241; }");
            out.println(".error-msg { color: #e74c3c; font-size: 13px; margin-bottom: 15px; }");
            out.println(".back-link { display: inline-block; margin-top: 20px; color: #888; font-size: 12px; text-decoration: none; }");
            out.println(".back-link:hover { color: #c5a059; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='login-card'>");
            out.println("<h2>Acceso Administración</h2>");

            if ("true".equals(error)) {
                out.println("<div class='error-msg'>⚠️ Contraseña incorrecta</div>");
            }

            out.println("<form action='LoginAdminServlet' method='POST'>");
            out.println("<input type='password' name='password' placeholder='Ingresa la contraseña' required>");
            out.println("<button type='submit'>Ingresar</button>");
            out.println("</form>");
            out.println("<a href='HomeServlet' class='back-link'>← Volver al sitio público</a>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String password = request.getParameter("password");

        if (CLAVE_ADMIN.equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("adminLogueado", true);
            response.sendRedirect("AdminServlet");
        } else {
            response.sendRedirect("LoginAdminServlet?error=true");
        }
    }
}