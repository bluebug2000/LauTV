package controlador;

import Conexion.conexionBD;
import modelo.Pelicula;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoImplementacion implements Dao {
    
    @Override
    public boolean registrarPelicula(Pelicula pelicula) throws SQLException {
        String sql = "INSERT INTO pelicula (titulo, director, genero, adultos, ruta) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = conexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setString(3, pelicula.getGenero() != null ? pelicula.getGenero().toString() : null);
            ps.setBoolean(4, pelicula.isAdultos());
            ps.setString(5, pelicula.getRuta());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, email, telefono) VALUES (?, ?, ?)";
        
        try (Connection conexion = conexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelefono());
            
            return ps.executeUpdate() > 0;
        }
    }
}