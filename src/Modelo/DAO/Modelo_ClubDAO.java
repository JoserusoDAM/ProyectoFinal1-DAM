
package Modelo.DAO;

import Modelo.Clubes;
import Modelo.Conexion;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Modelo_ClubDAO extends Conexion {

    
    public Modelo_ClubDAO() {
    }

    /**
     * Metodo que genera la tabla de los cubles
     * @return tablemodel
     */
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
            close(res);
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
            close(res);
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    /**
     * Metodo para generar la tabla personalizada
     * @param nombre Nombre del futbolista
     * @return tablemodel
     */
    public DefaultTableModel getTablaClubPersonalizada(String nombre) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Id", "Nombre", "Fecha Creacion", "Nombre Estadio", "Temporada"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT count(*) as total FROM Clubs");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            close(res);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][6];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            CallableStatement call = this.getConnection().prepareCall("{call buscarEquipos(?)}");
            call.setString(1, nombre);
            ResultSet res = call.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("id_club");
                data[i][1] = res.getString("nombre");
                data[i][2] = res.getString("fecha_creacion");
                data[i][3] = res.getString("nom_estadio");
                data[i][4] = res.getString("temporada");
                i++;
            }
            close(res);
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
     * @param club Club
     * @return true/false
     */
    public boolean NuevoClub(Clubes club) {
        //LLamamos previamente a la validacion
        if (validarCreacion(club.getFecha_creacion())) {
            try {
                // realizamos la llamada al procedimiento almacenado en la base de datos
                CallableStatement call = this.getConnection().prepareCall("{call insertClub (?,?,?)}");
                // se le pasan los tres parametros 
                call.setString(1, club.getNombre());
                call.setInt(2, club.getFecha_creacion());
                call.setString(3, club.getNom_estadio());
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
     * Metodo para eliminar un club
     *
     * @param club Club
     * @return true/false
     */
    public boolean EliminarClub(Clubes club ) {
        try {
            // realizamos la llamada al procedimiento guardado en la base de datos
            CallableStatement call = this.getConnection().prepareCall("{call deleteClub (?, ?)}");
            // se le pasan los dos parametros 
            call.setInt(1, club.getId_club());
            call.setString(2, club.getNombre());
            call.execute();
            close(call);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Metodo para modificar los parametros de un club
     * @param club Club
     * @return true/false
     */
    public boolean ModificarClub(Clubes club) {
        // llamamos previamente a la validacion
        if (validarCreacion(club.getFecha_creacion())) {
            try {
                // se realiza el procedimiento almacenado en la BD 
                CallableStatement call = this.getConnection().prepareCall("{call updateClub (?,?,?,?)}");
                // pasamos los 4 parametros
                call.setInt(1, club.getId_club());
                call.setString(2, club.getNombre());
                call.setInt(3, club.getFecha_creacion());
                call.setString(4, club.getNom_estadio());
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
     * Metodo para realizar la consulta que llenara el combobox
     * @param sql Consulta sql sobre los datos de los futbolistas
     * @return res Datos de los futbolistas
     */
    
    public ResultSet consulta (String sql) {
        ResultSet res = null;
        // realizamos esta consulta para llenarlo en el combobox
        try {
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT * FROM Futbolistas");
            res = pstm.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return res;
    }
    
    /**
     * Metodo para llenar el llenar el combobox de clubs
     * @return comboClub Combobox de clubes 
     */
    public DefaultComboBoxModel llenarCombo () {
        DefaultComboBoxModel comboClub = new DefaultComboBoxModel();
        comboClub.addElement("Seleccione una futbolista");
        
        ResultSet res = this.consulta("SELECT * FROM Futbolistas");
        
        try {
            while(res.next()) {
                // metemos el dato nombre en el combobox
                comboClub.addElement(res.getString("nombre"));
            }
            close(res);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return comboClub;
    }
    
    
    
    /**
     * Metodo por el que validamos la fecha de creacion
     * @param n Fecha de freacion
     * @return true/false
     */
    public boolean validarCreacion(int n) {
        LocalDate fechaActual = LocalDate.now();
        // se comprueba que el anio sea inferior al actual
        return n <= fechaActual.getYear();
    }
    
    /**
     * Metodo para conseguir la ID que usaremos en la lista de clubes
     * @param nombreclub
     * @return r El id del club
     */
    public int getIdClub (String nombreclub) {
        try {
            ResultSet res;
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT id_club as id FROM Clubs WHERE nombre = ?");
            pstm.setString(1, nombreclub);
            res = pstm.executeQuery();
            res.next();
            int r = res.getInt("id");
            close(res);
            close(pstm);
            return r;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  
        }   
    }

}
