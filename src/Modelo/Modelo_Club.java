package Modelo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Modelo_Club extends Conexion {

    public Modelo_Club() {
    }

    public DefaultTableModel getTablaClub() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Id_Club", "Nombre", "Fecha Creacion", "Nombre Estadio"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = Modelo_Club.getConnection().prepareStatement("SELECT count(*) as total FROM Clubs");
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
            PreparedStatement pstm = Modelo_Club.getConnection().prepareStatement("SELECT * FROM Clubs");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("id_club");
                data[i][1] = res.getString("nombre");
                data[i][2] = res.getString("fecha_creacion");
                data[i][3] = res.getString("nom_estadio");
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
     * Metodo para la creacion de un club
     *
     * @param nombre Nombre del club
     * @param fecha_creacion Fecha de creacion del club
     * @param nom_estadio Nombre del estadio
     * @return true/false Club
     */
    public boolean NuevoClub(String nombre, int fecha_creacion, String nom_estadio) {
        try {
            CallableStatement call = Modelo_Club.getConnection().prepareCall("{call insertClub (?,?,?)}");
            call.setString(1, nombre);
            call.setInt(2, fecha_creacion);
            call.setString(3, nom_estadio);
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Metodo para eliminar un club
     *
     * @param id_club
     * @return true/false
     */
    public boolean EliminarClub(int id_club) {
        try {
            CallableStatement call = Modelo_Club.getConnection().prepareCall("{call deleteClub (?)}");
            call.setInt(1, id_club);
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Metodo para modificar los parametros de un club
     *
     * @param id_club
     * @param nombre
     * @param fecha_creacion
     * @param nom_estadio
     * @return true/false
     */
    public boolean ModificarClub(int id_club, String nombre, int fecha_creacion, String nom_estadio) {
        try {
            CallableStatement call = Modelo_Club.getConnection().prepareCall("{call updateClub (?,?,?,?)}");
            call.setInt(1, id_club);
            call.setString(2, nombre);
            call.setInt(3, fecha_creacion);
            call.setString(4, nom_estadio);
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
