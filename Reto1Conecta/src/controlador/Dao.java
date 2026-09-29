package controlador;

import java.util.List;
import modelo.Pelicula;
import modelo.Usuario;
import modelo.Watchlist;
import java.sql.SQLException;


/**
 * Interfaz que define los métodos de acceso a la Base de Datos. Todos lanzan
 * Exception para que el Controlador maneje los errores de SQL.
 *
 * @author Sachin.Baruwal
 */
public interface Dao {

    // 1. Registrar una pelicula en la tabla Pelicula
    public boolean registrarPelicula(Pelicula pelicula) throws SQLException;

    // 2. Registrar un usuario en la tabla Usuario
    public boolean registrarUsuario(Usuario usuario) throws SQLException;

    // 3. Crear watchlist vinculada a un usuario
    public void crearWatchlist(Watchlist watchlist, Integer idUsuario) throws Exception;

    // 4. Añadir pelicula a watchlist
    public void anadirPeliculaAWatchlist(Integer idPelicula, Integer idWatchlist) throws Exception;

    // 5. Consultar peliculas para adultos (donde adultos = true)
    public List<Pelicula> consultarPeliculasAdultos() throws Exception;

    // 6. Consultar watchlist de un usuario mediante su ID
    public List<Watchlist> consultarWatchlistUsuario(Integer idUsuario) throws Exception;

    // 7. Ver historial de watchlist de una pelicula
    public List<Watchlist> verHistorialWatchlistPelicula(Integer idPelicula) throws Exception;
    
    // 8. Obtener el titulo de una pelicula mediante su ID
    public String obtenerTituloPelicula(Integer idPelicula) throws SQLException;

}
