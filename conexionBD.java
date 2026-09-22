/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Eneko.Plaza
 */
public class conexionBD {
   
    private static final String url = "jdbc:mysql://localhost:3306/streamingdb?useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String pass = "abcd*123";
    
    
    
    public static Connection getConexion() throws SQLException {
        try{
           Class.forName("com.mysql.cj.jdbc.Driver"); 
        }catch(ClassNotFoundException e){
            throw new SQLException("Driver MySQL no encontrado.", e);
        }
        
        return DriverManager.getConnection(url, user, pass);
    }
}
