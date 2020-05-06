package Modelo.DAO;

import Modelo.Conexion;
import Modelo.Futbolistas;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Modelo_FutbolistasDAO extends Conexion {

    public Modelo_FutbolistasDAO() {
    }

    public DefaultTableModel getTablaFutbolistas() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Id", "NIF", "Nombre", "Apellidos", "Fecha nac.", "Nacionalidad"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT count(*) as total FROM Futbolistas");
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
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT * FROM Futbolistas");
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

    public DefaultTableModel getTablaFutbolistasPersonalizada(String nombre, int temporada) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Id", "NIF", "Nombre", "Apellidos", "Fecha nac.", "Nacionalidad"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT count(*) as total FROM Futbolistas");
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
            CallableStatement call = this.getConnection().prepareCall("{call buscarJugadores(?, ?)}");
            call.setString(1, nombre);
            call.setInt(2, temporada);
            ResultSet res = call.executeQuery();
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
     * Metodo para insertar futbolista
     *
     * @param fut Futbolista
     * @return true/false
     */
    public boolean NuevoFutbolista(Futbolistas fut) {
        
        if (validarNif(fut.getNif())) {
            try {
            CallableStatement call = this.getConnection().prepareCall("{call insertFutbolista (?,?,?,?,?)}");
            call.setString(1, fut.getNif());
            call.setString(2, fut.getNombre());
            call.setString(3, fut.getApellidos());
            call.setDate(4, Date.valueOf(fut.getFecha_nacimiento()));
            call.setString(5, fut.getNacionalidad());
            call.execute();
            call.close();
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
     * Metodo para eliminar un futbolista
     *
     * @param id_futbolista
     * @return true/false
     */
    public boolean EliminarFutbolista(int id_futbolista) {
        try {
            CallableStatement call = this.getConnection().prepareCall("{call deleteFutbolista (?)}");
            call.setInt(1, id_futbolista);
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
     * @param fut Futbolista
     * @return true/false
     */
    public boolean ModificarFutbolista(Futbolistas fut) {
        
        if (validarNif(fut.getNif())) {
            try {
            CallableStatement call = this.getConnection().prepareCall("{call updateFutbolista (?,?,?,?,?,?)}");
            call.setInt(1, fut.getId_futbolista());
            call.setString(2, fut.getNif());
            call.setString(3, fut.getNombre());
            call.setString(4, fut.getApellidos());
            call.setDate(5, Date.valueOf(fut.getFecha_nacimiento()));
            call.setString(6, fut.getNacionalidad());
            call.execute();
            call.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
        } else {
            return false;
        }  
    }

    public boolean validarNif(String nif) {

        boolean correcto = false;
        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher = pattern.matcher(nif);
        if (matcher.matches()) {
            String letra = matcher.group(2);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int index = Integer.parseInt(matcher.group(1));
            index = index % 23;
            String reference = letras.substring(index, index + 1);
            if (reference.equalsIgnoreCase(letra)) {
                correcto = true;
            } else {
                correcto = false;
            }
        } else {
            correcto = false;
        }
        return correcto;
    }

}
