package Modelo.DAO;

import Modelo.Conexion;
import Modelo.Futbolistas;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Modelo_FutbolistasDAO extends Conexion {

    public Modelo_FutbolistasDAO() {
    }

    /**
     * Metodo para generar la tabla con todos los futbolistas
     * @return tablemodel
     */
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
            close(res);
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
            close(res);
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    /**
     * Metodo que genera una tabla pesonalizada a partir de un nombre dado
     * @param nombre
     * @return tablemodel
     */
    public DefaultTableModel getTablaFutbolistasPersonalizada(String nombre) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Id", "NIF", "Nombre", "Apellidos", "Fecha nac.", "Nacionalidad", "Temporada"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT count(*) as total FROM Futbolistas");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            close(res);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][8];
        try {
            //realizamos la llamada al procedimiento de la bd y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConnection().prepareStatement("{call buscarJugadores(?)}");
            pstm.setString(1, nombre);
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("id_futbolista");
                data[i][1] = res.getString("nif");
                data[i][2] = res.getString("nombre");
                data[i][3] = res.getString("apellidos");
                data[i][4] = res.getString("fecha_nacimiento");
                data[i][5] = res.getString("nacionalidad");
                data[i][6] = res.getString("temporada");
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
     * Metodo para insertar futbolista
     *
     * @param fut Futbolista
     * @return true/false
     */
    public boolean NuevoFutbolista(Futbolistas fut) {
        //LLamamos previamente a los validadores
        if ((validarNif(fut.getNif())
                && (validarFecha(fut.getFecha_nacimiento())
                && (contieneSoloLetras(fut.getNombre())
                && (contieneSoloLetras(fut.getApellidos())
                && (contieneSoloLetras(fut.getNacionalidad()))))))) {
            try {
                CallableStatement call = this.getConnection().prepareCall("{call insertFutbolista (?,?,?,?,?)}");
                call.setString(1, fut.getNif());
                call.setString(2, fut.getNombre());
                call.setString(3, fut.getApellidos());
                call.setDate(4, Date.valueOf(fut.getFecha_nacimiento()));
                call.setString(5, fut.getNacionalidad());
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
     * Metodo para eliminar un futbolista
     *
     * @param fut Futbolista
     * @return true/false
     */
    public boolean EliminarFutbolista(Futbolistas fut) {
        try {
            CallableStatement call = this.getConnection().prepareCall("{call deleteFutbolista (?)}");
            call.setInt(1, fut.getId_futbolista());
            call.execute();
            close(call);
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
        //LLamamos previamente a los validadores
        if ((validarNif(fut.getNif())
                && (validarFecha(fut.getFecha_nacimiento())
                && (contieneSoloLetras(fut.getNombre())
                && (contieneSoloLetras(fut.getApellidos())
                && (contieneSoloLetras(fut.getNacionalidad()))))))) {
            try {
                CallableStatement call = this.getConnection().prepareCall("{call updateFutbolista (?,?,?,?,?,?)}");
                call.setInt(1, fut.getId_futbolista());
                call.setString(2, fut.getNif());
                call.setString(3, fut.getNombre());
                call.setString(4, fut.getApellidos());
                call.setDate(5, Date.valueOf(fut.getFecha_nacimiento()));
                call.setString(6, fut.getNacionalidad());
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
     * Metodo por el que validamos que el NIF tenga una estructura y contenido
     * valido
     *
     * @param nif NIF
     * @return true/false
     */
    public boolean validarNif(String nif) {
        boolean flag;
        // comprobamos que tegna 8 numeros y una letra determinada
        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher = pattern.matcher(nif);
        // si el patron coincide
        if (matcher.matches()) {
            String letra = matcher.group(2);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int index = Integer.parseInt(matcher.group(1));
            index = index % 23;
            String reference = letras.substring(index, index + 1);
            flag = reference.equalsIgnoreCase(letra);
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Metodo por el que validamos la fecha, que sea anterior a la actual
     *
     * @param fecha Fecha
     * @return true/false
     */
    public boolean validarFecha(LocalDate fecha) {      
        // se comprueba que la fecha sea anterior o igual a la actual
        return fecha.isBefore(LocalDate.now()) || fecha.isEqual(LocalDate.now());
    }

    /**
     * Metodo para realizar la consulta que llenara el combobox
     *
     * @param sql Consulta sql sobre los datos de los clubs
     * @return res Consulta sql
     */
    public ResultSet consulta(String sql) {
        ResultSet res = null;

        try {
            PreparedStatement pstm = this.getConnection().prepareStatement("SELECT * FROM Clubs");
            res = pstm.executeQuery();;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    /**
     * Metodo para llenar el combobox
     *
     * @return comboFut Combobox de futbolistas
     */
    public DefaultComboBoxModel llenarCombo() {
        DefaultComboBoxModel comboFut = new DefaultComboBoxModel();
        comboFut.addElement("Seleccione una club");
        ResultSet res = this.consulta("SELECT * FROM Clubs");
        try {
            while (res.next()) {
                // se inserta en el combo el nombre del club
                comboFut.addElement(res.getString("nombre"));
            }
            close(res);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return comboFut;
    }

    /**
     * Metodo para comprobar que la cadena de caracteres contiene numeros
     *
     * @param cadena
     * @return
     */
    public static boolean contieneSoloLetras(String cadena) {
        boolean flag = false;
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            flag = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ';           
        }
        return flag;
    }
}
