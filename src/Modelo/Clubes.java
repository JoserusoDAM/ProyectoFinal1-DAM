package Modelo;

/**
 *
 * @author Jose
 */
public class Clubes {
    
    //Atributos
    private int id_club;
    private int fecha_creacion;
    private String nombre;
    private String nom_estadio;
   
    /**
     * Constructor por defecto
     */
    public Clubes () {}

    /**
     * Metodo get id_club
     * @return id_club id del club
     */
    public int getId_club() {
        return id_club;
    }

    /**
     * Metodo set id_club
     * @param id_club id del club
     */
    public void setId_club(int id_club) {
        this.id_club = id_club;
    }

    /**
     * Metodo get fecha_creacion
     * @return fecha_creacion Devuelve el anio de creacion
     */
    public int getFecha_creacion() {
        return fecha_creacion;
    }

    /**
     * Metodo set anio de creacion
     * @param fecha_creacion  Anio de creacion
     */
    public void setFecha_creacion(int fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    /**
     * Metodo get Nombre
     * @return nombre Nombre del equipo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo set Nombre
     * @param nombre  Nombre del equipo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo get Nombre_estadio
     * @return nombre_estadio Nombre del estadio
     */
    public String getNom_estadio() {
        return nom_estadio;
    }

    /**
     * Metodo set nombre_estadio
     * @param nom_estadio Nombre del estadio
     */
    public void setNom_estadio(String nom_estadio) {
        this.nom_estadio = nom_estadio;
    }

    /**
     * Metodo toString
     * @return Devuelve todos los atributos del Club 
     */
    @Override
    public String toString() {
        return "Clubes{" + "id_club=" + id_club + ", fecha_creacion=" + fecha_creacion + ", nombre=" + nombre + ", nom_estadio=" + nom_estadio + '}';
    }
    
}
