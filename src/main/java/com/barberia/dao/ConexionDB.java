 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.barberia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {

    // Reemplaza HOST, BASE_DE_DATOS y CONTRASEÑA con los de tu External Database URL de Render
    private static final String URL  = "jdbc:postgresql://dpg-dbe013hsrm7s73d6kp10-a.TU_REGION-postgres.render.com:5432/TU_BASE_DE_DATOS?sslmode=require";
    private static final String USER = "monsprat_user";
    private static final String PASS = "TU_CONTRASEÑA_DE_RENDER";

    // Crea la tabla 'turnos' automáticamente en Render al iniciar
    static {
        String sql = "CREATE TABLE IF NOT EXISTS turnos ("
                   + "id VARCHAR(36) PRIMARY KEY, "
                   + "cliente VARCHAR(100) NOT NULL, "
                   + "telefono VARCHAR(30) NOT NULL, "
                   + "fecha VARCHAR(15) NOT NULL, "
                   + "hora VARCHAR(10) NOT NULL, "
                   + "servicio VARCHAR(255) NOT NULL, "
                   + "barbero VARCHAR(100) NOT NULL)";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
            System.out.println("CONEXION EXITOSA A RENDER Y TABLA 'turnos' LISTA.");
        } catch (SQLException e) {
            System.err.println("ERROR CONECTANDO A RENDER: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Falta el driver JDBC de PostgreSQL", e);
        }
    }
}