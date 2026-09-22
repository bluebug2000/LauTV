/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Sachin.Baruwal
 */
public class Pelicula {

    private Integer id;
    private String titulo;
    private String director;
    private Genero genero;
    private boolean Adultos;
    private String ruta;

    public Pelicula() {
    }

    public Pelicula(Integer id, String titulo, String director, Genero genero, boolean Adultos, String ruta) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.Adultos = Adultos;
        this.ruta = ruta;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public boolean isAdultos() {
        return Adultos;
    }

    public void setAdultos(boolean Adultos) {
        this.Adultos = Adultos;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
