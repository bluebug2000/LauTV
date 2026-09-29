package controlador;

import utilidades.Util;
import modelo.Pelicula;
import modelo.Usuario;
import modelo.Watchlist;
import modelo.Genero;
import java.time.LocalDate;
import java.util.List;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Sachin.Baruwal
 */
public class Controlador {

    private static Dao dao = DaoImplementacion.getInstancia();

    public static void iniciar() {
        int opc;
        do {
            opc = menu();
            switch (opc) {
                case 1:
                    
                    registrarPelicula();
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 3:
                    crearWatchlist();
                    break;
                case 4:
                    anadirPeliculaAWatchlist();
                    break;
                case 5:
                    consultarPeliculasAdultos();
                    break;
                case 6:
                    consultarWatchlistUsuario();
                    break;
                case 7:
                    verHistorialWatchlistPelicula();
                    break;
                case 8:
                    System.out.println("Saliendo.....");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } while (opc != 8);
    }

    //---------------------------------------------------------------------------------------------
    // Menu en consola
    //---------------------------------------------------------------------------------------------
    private static int menu() {
        System.out.println("\n-------MENU PLATAFORMA DE STREAMING------");
        System.out.println("1. Registrar una pelicula");
        System.out.println("2. Registrar un usuario");
        System.out.println("3. Crear watchlist");
        System.out.println("4. Anadir pelicula a watchlist");
        System.out.println("5. Consultar peliculas para adultos");
        System.out.println("6. Consultar watchlist de un usuario");
        System.out.println("7. Ver historial de watchlist de una pelicula");
        System.out.println("8. Salir");
        return Util.leerInt("Seleccione una opcion (1-8): ", 1, 8);
    }
    
    //---------------------------------------------------------------------------------------------
    // Parametros para registrar una pelicula en la BD
    //---------------------------------------------------------------------------------------------
    private static void registrarPelicula() {
    String titulo = Util.leerString("Titulo: ");
    String director = Util.leerString("Director: ");
    Genero genero = Util.leerGenero("Genero: ");
    boolean adulto = Util.esBoolean("Es adulto? (S/N): ");
    String ruta = Util.leerString("Ruta: ");
    
    Pelicula pelicula = new Pelicula(titulo, director, genero, adulto, ruta);
    
    try {
        if (dao.registrarPelicula(pelicula)) {
            System.out.println("Pelicula registrada correctamente.");
        } else {
            System.out.println("No se pudo registrar la pelicula.");
        }
    } catch (SQLException e) {
        System.err.println("Error al registrar pelicula: " + e.getMessage());
    }
    
    }
    
    //---------------------------------------------------------------------------------------------
    // Metodo para registrar un usuario en la BD
    //---------------------------------------------------------------------------------------------
    private static void registrarUsuario() {
    String nombre = Util.leerString("Nombre: ");
    String email = Util.leerEmail("Email: ");
    String telefono = Util.leerTelefono("Telefono: ");

    Usuario usuario = new Usuario(nombre,email,telefono);

    try {
        if (dao.registrarUsuario(usuario)) {
            System.out.println("Usuario registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el usuario.");
        }
    } catch (SQLException e) {
        System.err.println("Error al registrar usuario: " + e.getMessage());
    }
    }
    
    //---------------------------------------------------------------------------------------------
    // Metodo para crear una WatchList en el fichero
    //---------------------------------------------------------------------------------------------
    private static void crearWatchlist() {
        System.out.println("\n--- CREAR WATCHLIST ---");
        int idWatchlist = Util.leerInt("ID de la nueva Watchlist:");
        String nombre = Util.leerString("Nombre de la lista:");
        int idUsuario = Util.leerInt("ID del Usuario propietario:");

        Watchlist w = new Watchlist();
        w.setId(idWatchlist);
        w.setNombre(nombre);
        w.setFechaCreacion(LocalDate.now());
        w.setNum_pel(0);

        try {
            dao.crearWatchlist(w, idUsuario);
            System.out.println("Watchlist creada y guardada en el fichero correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------------
    // Metodo para anadir una pelicula a una WatchList
    //---------------------------------------------------------------------------------------------
    private static void anadirPeliculaAWatchlist() {
        System.out.println("\n--- ANADIR PELiCULA ---");
        int idPelicula = Util.leerInt("ID de la pelicula:");
        int idWatchlist = Util.leerInt("ID de la Watchlist destino:");

        try {
            dao.anadirPeliculaAWatchlist(idPelicula, idWatchlist);
            System.out.println("Pelicula anadida a la lista.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------------
    // Listar peliculas para adultos
    //---------------------------------------------------------------------------------------------
    private static void consultarPeliculasAdultos() {
        System.out.println("\n--- CONSULTAR PELICULAS PARA ADULTOS ---");
        try {
            List<Pelicula> peliculasAdultos = dao.consultarPeliculasAdultos();

            if (peliculasAdultos == null || peliculasAdultos.isEmpty()) {
                System.out.println("No se encontraron películas para adultos registradas.");
            } else {
                System.out.println("Se han encontrado " + peliculasAdultos.size() + " pelicula(s):");
                for (Pelicula p : peliculasAdultos) {
                    System.out.println(p);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al consultar peliculas de adultos: " + e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------------
    // Metodo para consultar la WatchList de un usuario
    //---------------------------------------------------------------------------------------------
    private static void consultarWatchlistUsuario() {
    System.out.println("\n--- CONSULTAR WATCHLIST DE UN USUARIO ---");
    int idUsuario = Util.leerInt("ID del usuario: ");

    try {
        List<Watchlist> listas = dao.consultarWatchlistUsuario(idUsuario);

        if (listas.isEmpty()) {
            System.out.println("El usuario no tiene ninguna watchlist.");
        } else {
            System.out.println("Watchlists del usuario " + idUsuario + ":");

            for (Watchlist w : listas) {
                System.out.println("\nID Watchlist: " + w.getId());
                System.out.println("Nombre Watchlist: " + w.getNombre());
                System.out.println("Fecha de creacion: " + w.getFechaCreacion());
                
                if (w.getPeliculasIds() == null || w.getPeliculasIds().isEmpty()) {
                    System.out.println("Peliculas: ninguna");
                    
                } else {
                    System.out.println("Peliculas:");
                    for (Integer idPelicula : w.getPeliculasIds()) {
                        String titulo = dao.obtenerTituloPelicula(idPelicula);
                        if (titulo != null) {
                            System.out.println("  - " + titulo);
                        } else {
                            System.out.println("  - Pelicula no encontrada");
                        }
                    }
                }
            }
        }

    } catch (Exception e) {

        System.out.println("Error: " + e.getMessage());

    }
    }

    //---------------------------------------------------------------------------------------------
    // Metodo para consultar el historial de una pelicula en una WatchList
    //---------------------------------------------------------------------------------------------
    private static void verHistorialWatchlistPelicula() {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\n--- VER HISTORIAL DE WATCHLIST DE UNA PELICULA ---");
    System.out.print("Introduce el ID de la pelicula: ");
    
    try {
        int idPelicula = Integer.parseInt(scanner.nextLine().trim());
        
        // Llamada al DAO a traves del Singleton
        Dao dao = DaoImplementacion.getInstancia();
        List<Watchlist> historial = dao.verHistorialWatchlistPelicula(idPelicula);
        
        if (historial.isEmpty()) {
            System.out.println("No se encontro ningun historial: la pelicula con ID " + idPelicula + " no esta en ninguna Watchlist.");
        } else {
            System.out.println("\nLa pelicula con ID " + idPelicula + " se encuentra en las siguientes Watchlists (" + historial.size() + "):");
            System.out.println("------------------------------------------------------------------");
            
            for (Watchlist w : historial) {
                System.out.println("• ID Watchlist: " + w.getId() 
                        + " | Nombre: " + w.getNombre() 
                        + " | Usuario ID: " + w.getIdUsuario() 
                        + " | Fecha creacion: " + w.getFechaCreacion() 
                        + " | Total peliculas en la lista: " + w.getNum_pel());
            }
            System.out.println("------------------------------------------------------------------");
        }
        
    } catch (NumberFormatException e) {
        System.err.println("Error: Debes introducir un número entero valido para el ID.");
    } catch (Exception e) {
        System.err.println("Error al consultar el historial: " + e.getMessage());
    }
}
}