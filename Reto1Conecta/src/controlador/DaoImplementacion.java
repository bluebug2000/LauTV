package controlador;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import modelo.Genero;
import modelo.Pelicula;
import modelo.Usuario;
import modelo.Watchlist;

import modelo.Pelicula;
import modelo.Usuario;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Sachin.Baruwal
 */
public class DaoImplementacion implements Dao {

    private static DaoImplementacion instancia;
    
    //  Atributos de Base de Datos 
    private ResourceBundle configFile;
    private String urlBD;
    private String userBD;
    private String passwordBD;
    private Connection con;
    private PreparedStatement stmt;

    // Consultas SQL 
    final String ALTA_PELICULA = "INSERT INTO Pelicula (id, titulo, director, genero, adultos, ruta) VALUES (?, ?, ?, ?, ?, ?)";
    final String ALTA_USUARIO = "INSERT INTO Usuario (id, nombre, email, telefono) VALUES (?, ?, ?, ?)";
    final String CONSULTAR_PELIS_ADULTOS = "SELECT * FROM Pelicula WHERE adultos = true";

    // --- Atributo de Ficheros ---
    private final String FICHERO_WATCHLIST = "watchlists.dat";

    // Constructor Privado (Singleton)
    private DaoImplementacion() {
        // Leemos la configuración de configclass.properties
        this.configFile = ResourceBundle.getBundle("modelo.configClass"); 
        this.urlBD = this.configFile.getString("Conn");
        this.userBD = this.configFile.getString("DBUser");
        this.passwordBD = this.configFile.getString("DBPass");
    }

    public static DaoImplementacion getInstancia() {
        if (instancia == null) {
            instancia = new DaoImplementacion();
        }
        return instancia;
    }

    
    // MÉTODOS AUXILIARES DE BASE DE DATOS
    private void openConnection() throws SQLException {
        con = DriverManager.getConnection(urlBD, userBD, passwordBD);
    }

    private void closeConnection() {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    @Override
    public boolean registrarPelicula(Pelicula pelicula) throws SQLException {
    String sql = "INSERT INTO Pelicula (titulo, director, genero, adultos, ruta) " + "VALUES (?, ?, ?, ?, ?)";

        try {
            openConnection();

            stmt = con.prepareStatement(sql);

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getDirector());
            stmt.setString(3, pelicula.getGenero() != null ? pelicula.getGenero().toString(): null);
            stmt.setBoolean(4, pelicula.isAdultos());
            stmt.setString(5, pelicula.getRuta());

            return stmt.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
}

    @Override
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (nombre, email, telefono) "+ "VALUES (?, ?, ?)";

        try {
            openConnection();

            stmt = con.prepareStatement(sql);

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefono());

            return stmt.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
    }
    
    @Override
    public void crearWatchlist(Watchlist watchlist, Integer idUsuario) throws Exception {
        List<Watchlist> todasLasListas = leerWatchlistsDelFichero();
        watchlist.setIdUsuario(idUsuario); 
        todasLasListas.add(watchlist);
        guardarWatchlistsEnFichero(todasLasListas);
    }

    @Override
    public void anadirPeliculaAWatchlist(Integer idPelicula, Integer idWatchlist) throws Exception {
        List<Watchlist> todasLasListas = leerWatchlistsDelFichero();
        boolean encontrada = false;
        
        for (Watchlist w : todasLasListas) {
            if (w.getId().equals(idWatchlist)) {
                w.getPeliculasIds().add(idPelicula); // Añadimos ID
                w.setNum_pel(w.getNum_pel() + 1); // Sumamos contador
                encontrada = true;
                break;
            }
        }
        
        if (encontrada) {
            guardarWatchlistsEnFichero(todasLasListas);
        } else {
            throw new Exception("Error: No existe ninguna Watchlist con el ID " + idWatchlist);
        }
    }

    private List<Watchlist> leerWatchlistsDelFichero() throws Exception {
    List<Watchlist> listas = new ArrayList<>();

    File fichero = new File("watchlists.dat");

    if (!fichero.exists()) {
        return listas;
    }

    try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(fichero))) {

        listas = (List<Watchlist>) ois.readObject();

    } catch (EOFException e) {
        listas = new ArrayList<>();
    }

    return listas;
    }

    private void guardarWatchlistsEnFichero(List<Watchlist> listas) throws Exception {
    File fichero = new File("watchlists.dat");

    try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(fichero))) {

        oos.writeObject(listas);
    }
    }

    @Override
    public List<Pelicula> consultarPeliculasAdultos() throws Exception {
        
        return null ;
    }

    

    @Override
    public List<Watchlist> consultarWatchlistUsuario(Integer idUsuario) throws Exception {

    List<Watchlist> todasLasListas = leerWatchlistsDelFichero();
    List<Watchlist> listasUsuario = new ArrayList<>();

    for (Watchlist w : todasLasListas) {

        if (w.getIdUsuario().equals(idUsuario)) {
            listasUsuario.add(w);
        }
    }

    return listasUsuario;
    }

    @Override
    public List<Watchlist> verHistorialWatchlistPelicula(Integer idPelicula) throws Exception {
        
        return null;
    }
}