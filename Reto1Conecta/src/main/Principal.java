/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import controlador.Controlador;
import javax.security.auth.login.LoginException;

/**
 *
 * @author Sachin.Baruwal
 */
public class Principal {

    public static void main(String[] args) throws LoginException {
        Controlador controlador = new Controlador();
        controlador.iniciar();

    }
}
