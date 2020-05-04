package Modelo;

/**
 *
 * @author Jose
 */
public class Historico {
    
    private int id_club;
    private int id_futbolista;
    private int temporada;
    
    /**
     * Constructor parametrizado
     */
    public Historico () {}

    //Getters y sett
    
    public int getId_club() {
        return id_club;
    }

    public void setId_club(int id_club) {
        this.id_club = id_club;
    }

    public int getId_futbolista() {
        return id_futbolista;
    }

    public void setId_futbolista(int id_futbolista) {
        this.id_futbolista = id_futbolista;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    /**
     * Metodo toString
     * @return Datos historicos de jugador, equipo y temporada
     */
    @Override
    public String toString() {
        return "Historico{" + "id_club=" + id_club + ", id_futbolista=" + id_futbolista + ", temporada=" + temporada + '}';
    }
    
}
