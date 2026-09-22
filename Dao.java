/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Pelicula;
import modelo.Usuario;
import java.sql.SQLException;
/**
 *
 * @author Eneko.Plaza
 */
public interface Dao {
    boolean registrarPelicula(Pelicula pelicula) throws SQLException;
    boolean registrarUsuario(Usuario usuario) throws SQLException;

}
