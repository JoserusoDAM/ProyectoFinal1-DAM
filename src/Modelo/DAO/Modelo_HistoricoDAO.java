package Modelo.DAO;


import Modelo.Conexion;
import Modelo.Historico;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.DefaultListModel;

/**
 *
 * @author Jose
 */
public class Modelo_HistoricoDAO  extends Conexion{

    public Modelo_HistoricoDAO() {
    }

    /**
     * Metodo para añadir un registro historico
     *
     * @param id_club
     * @param id_futbolista
     * @param temporada
     * @return
     */
    public boolean NuevoHistorico(Historico hist) {
        if (validarTemporada(hist.getTemporada())) {
            try {
            CallableStatement call = Modelo_ClubDAO.getConnection().prepareCall("{call insertHistorico (?,?,?)}");
            call.setInt(1, hist.getId_club());
            call.setInt(2, hist.getId_futbolista());
            call.setInt(3, hist.getTemporada());
            call.execute();
            close(call);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
        } else {
            return false;
        }    
    }

    /**
     * Metodo para eliminar un registro Historico
     *
     * @param Historico Historico
     * @return true/false
     */
    public boolean EliminarHistorico(Historico hist) {
        try {
            CallableStatement call = Modelo_ClubDAO.getConnection().prepareCall("{call deleteHistorico (?, ?)}");
            call.setInt(1, hist.getId_futbolista());
            call.setInt(2, hist.getTemporada());
            call.execute();
            close(call);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
    
    /**
     * Metodo que crea la tabla de la lista de clubes
     * @return lista
     */
    public DefaultListModel listaClubes () {
        
        DefaultListModel lista = new DefaultListModel();
        String datos;
        try {
            PreparedStatement pstm = Modelo_ClubDAO.getConnection().prepareStatement("SELECT * FROM Clubs");
            ResultSet res = pstm.executeQuery();
             while (res.next()) {                
                datos = res.getString(2);
                lista.addElement(datos);
            }
            close(res);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
        
    }
 
        /**
     * Metodo por el que validamos la fecha de creacion
     * @param n Fecha de freacion
     * @return true/false
     */
    public boolean validarTemporada (int n) {
        LocalDate fechaActual = LocalDate.now();
        return n <= fechaActual.getYear();
    }
}
