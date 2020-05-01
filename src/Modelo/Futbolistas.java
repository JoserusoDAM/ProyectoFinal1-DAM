package Modelo;

import java.time.LocalDate;

/**
 *
 * @author Jose
 */
public class Futbolistas {
    
    //Atributos
    private int id_futbolista;
    private String nif;
    private String nombre;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private String nacionalidad;

    /**
     * Constructor parametrizado
     * @param id_futbolista id del futbolista
     * @param nif NIF
     * @param nombre Nombre
     * @param apellidos Apellidos
     * @param fecha_nacimiento Fecha de nacimiento
     * @param nacionalidad Nacionalidad
     */
    public Futbolistas(int id_futbolista, String nif, String nombre, String apellidos, LocalDate fecha_nacimiento, String nacionalidad) {
        this.id_futbolista = id_futbolista;
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nacionalidad = nacionalidad;
    }
    
    /**
     * Constructor por defecto
     */
    public Futbolistas () {}

    //Getters y Setters
    
    public int getId_futbolista() {
        return id_futbolista;
    }

    public void setId_futbolista(int id_futbolista) {
        this.id_futbolista = id_futbolista;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

     /**
     * Metodo toString
     * @return Todos los datos de los futbolistas
     */
    @Override
    public String toString() {
        return "Futbolistas{" + "id_futbolista=" + id_futbolista + ", nif=" + nif + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fecha_nacimiento=" + fecha_nacimiento + ", nacionalidad=" + nacionalidad + '}';
    }
    
}
