package Modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Modelo_Futbolistas {

    public Modelo_Futbolistas() {
    }

    public DefaultTableModel getTablaFutbolistas() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"id_futbolista", "nif", "nombre", "apellidos", "fecha_nacimiento", "nacionalidad"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = Modelo_Club.getConnection().prepareStatement("SELECT count(*) as total FROM Futbolistas");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][7];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = Modelo_Club.getConnection().prepareStatement("SELECT * FROM Futbolistas");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("id_futbolista");
                data[i][1] = res.getString("nif");
                data[i][2] = res.getString("nombre");
                data[i][3] = res.getString("apellidos");
                data[i][4] = res.getString("fecha_nacimiento");
                data[i][5] = res.getString("nacionalidad");
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
     * Metodo para insertar un futbolista
     *
     * @param nif
     * @param nombre
     * @param apellidos
     * @param fecha_nacimiento
     * @param nacionalidad
     * @return true/false
     */
    public boolean NuevoFutbolista(int id, String nif, String nombre, String apellidos, LocalDate fecha_nacimiento, String nacionalidad) {
        try {
            CallableStatement call = Modelo_Club.getConnection().prepareCall("{call insertClub (?,?,?,?,?}");
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Metodo para eliminar un futbolista
     *
     * @param id_futbolista
     * @return true/false
     */
    public boolean EliminarFutbolista(String id_futbolista) {
        try {
            CallableStatement call = Modelo_Club.getConnection().prepareCall("{call deleteFutbolista (?}");
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Metodo para modificar un futbolista
     *
     * @param nif
     * @param nombre
     * @param apellidos
     * @param fecha_nacimiento
     * @param nacionalidad
     * @return
     */
    public boolean ModificarFutbolista(String nif, String nombre, String apellidos, LocalDate fecha_nacimiento, String nacionalidad) {
        try {
            CallableStatement call = Modelo_Club.getConnection().prepareCall("{call updateFutbolista (?,?,?,?}");
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
