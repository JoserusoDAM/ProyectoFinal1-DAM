package Modelo.DAO;

import Modelo.Clubes;
import Modelo.Conexion;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Modelo_ClubDAO extends Conexion {

    public Modelo_ClubDAO() {
    }

    public DefaultTableModel getTablaClub() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Id", "Nombre", "Fecha Creacion", "Nombre Estadio"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT count(*) as total FROM Clubs");
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
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT * FROM Clubs");
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

    
    public DefaultTableModel getTablaClubPersonalizada(int id, int temporada) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Id", "Nombre", "Fecha Creacion", "Nombre Estadio"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT count(*) as total FROM Clubs");
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
            CallableStatement call = this.getConnection().prepareCall("{call buscarEquipos(?, ?)}");
            call.setInt(1, id);
            call.setInt(2, temporada);
            ResultSet res = call.executeQuery();
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
     * @param club
     * @return true/false
     */
    public boolean NuevoClub(Clubes club) {
        try {
            CallableStatement call = this.getConnection().prepareCall("{call insertClub (?,?,?)}");
            call.setString(1, club.getNombre());
            call.setInt(2, club.getFecha_creacion());
            call.setString(3, club.getNom_estadio());
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
            CallableStatement call = this.getConnection().prepareCall("{call deleteClub (?)}");
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
    public boolean ModificarClub(Clubes club) {
        try {
            CallableStatement call = this.getConnection().prepareCall("{call updateClub (?,?,?,?)}");
            call.setInt(1, club.getId_club());
            call.setString(2, club.getNombre());
            call.setInt(3, club.getFecha_creacion());
            call.setString(4, club.getNom_estadio());
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
