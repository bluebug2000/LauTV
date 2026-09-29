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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
    private static final String ALTA_PELICULA = "INSERT INTO Pelicula (titulo, director, genero, adultos, ruta) VALUES (?, ?, ?, ?, ?)";
    private static final String ALTA_USUARIO = "INSERT INTO Usuario (nombre, email, telefono) VALUES (?, ?, ?)";
    private static final String CONSULTAR_PELIS_ADULTOS = "SELECT * FROM Pelicula WHERE adultos = ?";
    
    private static final String CONSULTAR_PELICULA = "SELECT id FROM Pelicula WHERE id = ?";
    private static final String CONSULTAR_USUARIO = "SELECT id FROM Usuario WHERE id = ?";
    

    // Atributo de Ficheros
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

    
    // Metodos auxiliares de base de datos
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
    
    //---------------------------------------------------------------------------------------------
    // Se da de alta a una nueva pelicula en la BD 
    //---------------------------------------------------------------------------------------------
    @Override
    public boolean registrarPelicula(Pelicula pelicula) throws SQLException {
        try {
            openConnection();

            stmt = con.prepareStatement(ALTA_PELICULA);

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

    //---------------------------------------------------------------------------------------------
    // Se da de alta a un nuevo usuario en la BD
    //---------------------------------------------------------------------------------------------
    @Override
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        try {
            openConnection();

            stmt = con.prepareStatement(ALTA_USUARIO);

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefono());

            return stmt.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
    }
    
    //---------------------------------------------------------------------------------------------
    // Se crea una WatchList a traves de la ID de un usuario para luego guardarla en el fichero watchlists.dat 
    //---------------------------------------------------------------------------------------------
    
    @Override
    public void crearWatchlist(Watchlist watchlist, Integer idUsuario) throws Exception {
        if (!existeUsuarioBD(idUsuario)) {
            throw new Exception("No existe ningun usuario con el ID " + idUsuario);
        }

        List<Watchlist> todasLasListas = leerWatchlistsDelFichero();
        watchlist.setIdUsuario(idUsuario); 
        todasLasListas.add(watchlist);
        guardarWatchlistsEnFichero(todasLasListas);
    }
     
    //---------------------------------------------------------------------------------------------
    // Se añade una pelicula a traves de su ID a una WatchList existente para luego guardarla en el fichero watchlists.dat
    //---------------------------------------------------------------------------------------------
    
    @Override
    public void anadirPeliculaAWatchlist(Integer idPelicula, Integer idWatchlist) throws Exception {
        if (!existePeliculaBD(idPelicula)) {
            throw new Exception("No existe ninguna pelicula con el ID " + idPelicula);
        }

        List<Watchlist> todasLasListas = leerWatchlistsDelFichero();
        boolean encontrada = false;

        for (Watchlist w : todasLasListas) {
            if (w.getId().equals(idWatchlist)) {
                w.getPeliculasIds().add(idPelicula); 
                w.setNum_pel(w.getNum_pel() + 1); 
                encontrada = true;
                break;
            }
        }

        if (encontrada) {
            guardarWatchlistsEnFichero(todasLasListas);
        } else {
            throw new Exception("No existe ninguna Watchlist con el ID " + idWatchlist);
        }
    }
   
    //---------------------------------------------------------------------------------------------
    // Se listan las peliculas de adultos de la base de datos a traves de su estado
    //---------------------------------------------------------------------------------------------
    @Override
    public List<Pelicula> consultarPeliculasAdultos() throws Exception {
        List<Pelicula> listaAdultos = new ArrayList<>();
        ResultSet rs = null;

        try {
            openConnection();

            stmt = con.prepareStatement(CONSULTAR_PELIS_ADULTOS);
            stmt.setBoolean(1, true); // Filtramos por adultos = true

            rs = stmt.executeQuery();

            System.out.println("----PELICULAS ENCONTRADAS PARA ADULTOS-----");
            while (rs.next()) {
                Pelicula p = new Pelicula();

                p.setId(rs.getInt("id"));
                p.setTitulo(rs.getString("titulo"));
                p.setDirector(rs.getString("director"));
                p.setAdultos(rs.getBoolean("adultos"));
                p.setRuta(rs.getString("ruta"));

                String generoStr = rs.getString("genero");
                if (generoStr != null) {
                    p.setGenero(Genero.valueOf(generoStr)); // Convierte la cadena al Enum Genero
                }

                listaAdultos.add(p);

                System.out.println("ID: " + p.getId() + " | Título: " + p.getTitulo() + " | Director: " + p.getDirector() + " | Género: " + p.getGenero() + " | Ruta: " + p.getRuta());
            }

            if (rs != null) rs.close();
            closeConnection();

        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
            closeConnection();
        }

        return listaAdultos;
    }

    //---------------------------------------------------------------------------------------------
    // Se listan las peliculas de la WatchList del usuario a traves de su ID
    //---------------------------------------------------------------------------------------------
    @Override
    public List<Watchlist> consultarWatchlistUsuario(Integer idUsuario) throws Exception {
        if (!existeUsuarioBD(idUsuario)) {
            throw new Exception("No existe ningun usuario con el ID " + idUsuario);
        }

        List<Watchlist> todasLasListas = leerWatchlistsDelFichero();
        List<Watchlist> listasUsuario = new ArrayList<>();

        for (Watchlist w : todasLasListas) {
            if (w != null && idUsuario.equals(w.getIdUsuario())) {
                listasUsuario.add(w);
            }
        }

        return listasUsuario;
    }
    
    @Override
    public List<Watchlist> verHistorialWatchlistPelicula(Integer idPelicula) throws Exception {
        if (!existePeliculaBD(idPelicula)) {
            throw new Exception("No existe ninguna pelicula con el ID " + idPelicula);
        }

        List<Watchlist> todasLasListas = leerWatchlistsDelFichero();
        List<Watchlist> historial = new ArrayList<>();

        for (Watchlist w : todasLasListas) {
            if (w != null && w.getPeliculasIds() != null) {
                if (w.getPeliculasIds().contains(idPelicula)) {
                    historial.add(w);
                }
            }
        }
        return historial;
    }
    
    //---------------------------------------------------------------------------------------------
    // Comprobacion de usuarios y peliculas en la base de datos
    //---------------------------------------------------------------------------------------------
    private boolean existeUsuarioBD(int idUsuario) throws SQLException {
        boolean existe = false;
        try {
            openConnection();
            stmt = con.prepareStatement(CONSULTAR_USUARIO);
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                existe = rs.next();
            }
        } finally {
            closeConnection();
        }
        return existe;
    }

    private boolean existePeliculaBD(int idPelicula) throws SQLException {
        boolean existe = false;
        try {
            openConnection();
            stmt = con.prepareStatement(CONSULTAR_PELICULA);
            stmt.setInt(1, idPelicula);
            try (ResultSet rs = stmt.executeQuery()) {
                existe = rs.next();
            }
        } finally {
            closeConnection();
        }
        return existe;
    }
    
    
    
    
    //---------------------------------------------------------------------------------------------
    // Manejo de ficheros
    //---------------------------------------------------------------------------------------------
    private List<Watchlist> leerWatchlistsDelFichero() throws Exception {
    List<Watchlist> listas = new ArrayList<>();
    File fichero = new File("watchlists.dat");

        if (!fichero.exists()) {
            return listas;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
            listas = (List<Watchlist>) ois.readObject();

        } catch (EOFException e) {
            listas = new ArrayList<>();
        }

    return listas;
    }

    private void guardarWatchlistsEnFichero(List<Watchlist> listas) throws Exception {
    File fichero = new File("watchlists.dat");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {
            oos.writeObject(listas);
        }
    }
}