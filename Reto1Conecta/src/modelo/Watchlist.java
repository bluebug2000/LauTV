package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sachin.Baruwal
 */
// Implementamos Serializable para poder guardar los objetos en el fichero .dat
public class Watchlist implements Serializable {

    private Integer id;
    private String nombre;
    private LocalDate fechaCreacion;
    private Integer num_pel;

    // ATRIBUTOS NUEVOS NECESARIOS:
    private Integer idUsuario; // Para saber de quién es la lista
    private List<Integer> peliculasIds; // Para guardar los IDs de las películas añadidas

    public Watchlist() {
        this.peliculasIds = new ArrayList<>(); // Inicializamos la lista para evitar NullPointerException
    }

    public Watchlist(Integer id, String nombre, LocalDate fechaCreacion, Integer num_pel, Integer idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.num_pel = num_pel;
        this.idUsuario = idUsuario;
        this.peliculasIds = new ArrayList<>();
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getNum_pel() {
        return num_pel;
    }

    public void setNum_pel(Integer num_pel) {
        this.num_pel = num_pel;
    }

    // Getters y Setters de los nuevos atributos
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Integer> getPeliculasIds() {
        return peliculasIds;
    }

    public void setPeliculasIds(List<Integer> peliculasIds) {
        this.peliculasIds = peliculasIds;
    }

    @Override
    public String toString() {
        return "Watchlist [ID=" + id + ", Nombre=" + nombre + ", Fecha=" + fechaCreacion + ", Peliculas=" + num_pel + "]";
    }
}
