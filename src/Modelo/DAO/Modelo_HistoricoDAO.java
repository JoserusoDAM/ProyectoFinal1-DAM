package Modelo.DAO;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Modelo_HistoricoDAO {

    public Modelo_HistoricoDAO() {
    }

    public DefaultTableModel getTablaHistoricos() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"id_club", "id_futbolista", "temporada"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = Modelo_ClubDAO.getConnection().prepareStatement("SELECT count(*) as total FROM Historico");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][4];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = Modelo_ClubDAO.getConnection().prepareStatement("SELECT * FROM Historico");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("id_club");
                data[i][1] = res.getInt("id_futbolista");
                data[i][2] = res.getString("temporada");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    /**
     * Metodo para añadir un registro historico
     *
     * @param id_club
     * @param id_futbolista
     * @param temporada
     * @return
     */
    public boolean NuevoHistorico(int id_club, int id_futbolista, int temporada) {
        try {
            CallableStatement call = Modelo_ClubDAO.getConnection().prepareCall("{call insertHistorico (?,?,?,?)}");
            call.setInt(1, id_club);
            call.setInt(2, id_futbolista);
            call.setInt(3, temporada);
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Metodo para eliminar un registro Historico
     *
     * @param id_club
     * @param id_futbolista
     * @param temporada
     * @return
     */
    public boolean EliminarHistorico(int id_club, int id_futbolista, int temporada) {
        try {
            CallableStatement call = Modelo_ClubDAO.getConnection().prepareCall("{call deleteHistorico (?, ?, ?)}");
            call.setInt(1, id_club);
            call.setInt(2, id_futbolista);
            call.setInt(3, temporada);
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Metodo para actualizar el historico
     *
     * @param id_club
     * @param id_futbolista
     * @param temporada
     * @return
     */
    public boolean ModificarHistorico(int id_club, int id_futbolista, int temporada) {
        try {
            CallableStatement call = Modelo_ClubDAO.getConnection().prepareCall("{call updateHistorico (?,?,?)}");
            call.setInt(1, id_club);
            call.setInt(2, id_futbolista);
            call.setInt(3, temporada);
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean BuscarJugadores(String nombre) {
        try {
            CallableStatement call = Modelo_ClubDAO.getConnection().prepareCall("{call buscarEquipos (?)}");
            call.setString(1, nombre);
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
    
}
