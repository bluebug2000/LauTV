package controlador;

import modelo.Pelicula;
import modelo.Usuario;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
public class DaoImplementacion implements Dao {
    private Connection obtenerConexion() throws SQLException {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("modelo/ConexionBD.properties")) {
            if (input == null) {
              throw new SQLException("No se pudo encontrar el archivo modelo/ConexionBD.properties");
            }
            props.load(input);
        } catch (Exception e) {
            throw new SQLException("Error al cargar la configuración de la BD", e);
        }
        String url = props.getProperty("Conexion");
        String user = props.getProperty("User");
        String pass = props.getProperty("Pass");
        String driver = props.getProperty("Driver");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado: " + driver, e);
        }

        return DriverManager.getConnection(url, user, pass);
    }
    @Override
    public boolean registrarPelicula(Pelicula pelicula) throws SQLException {
        String sql = "INSERT INTO pelicula (titulo, director, genero, adultos, ruta) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setString(3, pelicula.getGenero() != null ? pelicula.getGenero().toString() : null);
            ps.setBoolean(4, pelicula.isAdultos());
            ps.setString(5, pelicula.getRuta());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, email, telefono) VALUES (?, ?, ?)";
        
        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelefono());
            
            return ps.executeUpdate() > 0;
        }
    }


    @Override
    public List<Pelicula> consultarPeliculasAdultos() throws Exception {
    
    //Creamos la lista que pide el usuario
    List<Pelicula> peliculasAdultos=new ArrayList<>();
    try {
       ResultSet rs=stmt.executeQuery(CONSULTAR_PELIS_ADULTOS);
       while(rs.next()){
           
           //Rellenamos pelicula
            Pelicula p=new Pelicula();
            p.setId(rs.getInt("id"));             
            p.setTitulo(rs.getString("titulo"));   
            p.setDirector(rs.getString("director"));
            p.setGenero(Genero.valueOf(rs.getString("genero")));
            p.setAdultos(true);            
            p.setRuta(rs.getString("ruta"));

            
            //Añadimos la película a nuestra lista
            peliculasAdultos.add(p);
        }
          
       return peliculasAdultos; 
    }catch(SQLException  e){
        
        System.out.println(e.getErrorCode());
        return null ;
    }
    } 

     @Override
    public List<Watchlist> verHistorialWatchlistPelicula(Integer idPelicula) throws Exception {
        
        List<Watchlist> todasLasListas = leerWatchlistsDelFichero();
        List<Watchlist> WatchListDePelicula = new ArrayList<>();

    for (Watchlist w : todasLasListas) {

        if (w.getPeliculasIds().equals(idPelicula)) {
            WatchListDePelicula.add(w);
        }
    }
        return WatchListDePelicula;
    }
}
}
