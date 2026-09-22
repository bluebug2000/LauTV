package controlador;

import utilidades.Util;
import modelo.Pelicula;
import modelo.Usuario;
import modelo.Watchlist;
import modelo.Genero;
import java.time.LocalDate;
import java.util.List;

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
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opc != 8);
    }

    private static int menu() {
        System.out.println("\n______MENÚ PLATAFORMA DE STREAMING_____");
        System.out.println("1. Registrar una pelicula");
        System.out.println("2. Registrar un usuario");
        System.out.println("3. Crear watchlist");
        System.out.println("4. Anadir pelicula a watchlist");
        System.out.println("5. Consultar peliculas para adultos");
        System.out.println("6. Consultar watchlist de un usuario");
        System.out.println("7. Ver historial de watchlist de una pelicula");
        System.out.println("8. Salir");
        return Util.leerInt("Seleccione una opción (1-8): ", 1, 8);
    }

    private static void registrarPelicula() {
        
    }

    private static void registrarUsuario() {
        
    }

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

    private static void anadirPeliculaAWatchlist() {
        System.out.println("\n--- AÑADIR PELÍCULA ---");
        int idPelicula = Util.leerInt("ID de la película:");
        int idWatchlist = Util.leerInt("ID de la Watchlist destino:");

        try {
            dao.anadirPeliculaAWatchlist(idPelicula, idWatchlist);
            System.out.println("Película añadida a la lista.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void consultarPeliculasAdultos() {
        
    }

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
                System.out.println(w);
            }
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    private static void verHistorialWatchlistPelicula() {
        
    }
}
