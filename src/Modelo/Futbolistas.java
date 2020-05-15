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
    
    /**
     * Metodo para comprobar que existen numeros en el string
     * @param cadena
     * @return true/false
     */
    private static boolean tieneNumeros(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
}
    
}
