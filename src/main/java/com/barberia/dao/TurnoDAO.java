/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.barberia.dao;

import com.barberia.model.Turno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {

    public boolean guardarTurno(Turno turno) {
        String sql = "INSERT INTO turnos (id, cliente, telefono, fecha, hora, servicio, barbero) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, turno.getId());
            ps.setString(2, turno.getCliente());
            ps.setString(3, turno.getTelefono());
            ps.setString(4, turno.getFecha());
            ps.setString(5, turno.getHora());
            ps.setString(6, turno.getServicio());
            ps.setString(7, turno.getBarbero());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Turno> listarTurnos() {
        List<Turno> lista = new ArrayList<>();
        String sql = "SELECT id, cliente, telefono, servicio, fecha, hora, barbero FROM turnos ORDER BY fecha ASC, hora ASC";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Turno t = new Turno(
                    rs.getString("id"),
                    rs.getString("cliente"),
                    rs.getString("telefono"),
                    rs.getString("servicio"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("barbero")
                );
                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminarTurno(String id) {
        String sql = "DELETE FROM turnos WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.trim());
            int filas = ps.executeUpdate();
            System.out.println(">>> INTENTO DE BORRADO ID: [" + id + "] | FILAS BORRADAS: " + filas);
            return filas > 0;
        } catch (SQLException e) {
            System.err.println(">>> ERROR SQL AL BORRAR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}