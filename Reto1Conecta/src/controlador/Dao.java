package controlador;

import java.util.List;
import modelo.Pelicula;
import modelo.Usuario;
import modelo.Watchlist;

/**
 * Interfaz que define los métodos de acceso a la Base de Datos. Todos lanzan
 * Exception para que el Controlador maneje los errores de SQL.
 *
 * @author Sachin.Baruwal
 */
public interface Dao {

    // 1. Registrar una película en la tabla Pelicula
    public void registrarPelicula(Pelicula pelicula) throws Exception;

    // 2. Registrar un usuario en la tabla Usuario
    public void registrarUsuario(Usuario usuario) throws Exception;

    // 3. Crear watchlist vinculada a un usuario (tabla WatchList)
    public void crearWatchlist(Watchlist watchlist, Integer idUsuario) throws Exception;

    // 4. Añadir película a watchlist (inserción en tabla intermedia y actualización de num_pel)
    public void anadirPeliculaAWatchlist(Integer idPelicula, Integer idWatchlist) throws Exception;

    // 5. Consultar películas para adultos (SELECT donde adultos = true)
    public List<Pelicula> consultarPeliculasAdultos() throws Exception;

    // 6. Consultar watchlist de un usuario mediante su ID
    public List<Watchlist> consultarWatchlistUsuario(Integer idUsuario) throws Exception;

    // 7. Ver historial de watchlist de una película (en qué listas está guardada)
    public List<Watchlist> verHistorialWatchlistPelicula(Integer idPelicula) throws Exception;

}
