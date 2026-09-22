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
public class Controlador {
    
    private final Dao dao;
    
    public Controlador() {
        this.dao = new DaoImplementacion();
    }
    
    public boolean registrarPelicula(Pelicula pelicula) {
        try{
            return dao.registrarPelicula(pelicula);
        }catch(SQLException e) {
            System.err.println("Error al registrar película" + e.getMessage());
            return false;
        }
    }
    
    
    public boolean registrarUsuario(Usuario usuario) {
        try{
            return dao.registrarUsuario(usuario);
        } catch (SQLException e){
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
    
    
}
