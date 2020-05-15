package Controlador;

import Modelo.Clubes;
import Modelo.DAO.Modelo_ClubDAO;
import Modelo.DAO.Modelo_HistoricoDAO;
import Vista.Asociacion;
import Vista.TodosClubs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Jose
 */
public class Controlador_Club implements ActionListener, MouseListener, KeyListener {

    public TodosClubs club;
    Modelo_ClubDAO mclub = new Modelo_ClubDAO();
    Modelo_HistoricoDAO mHis = new Modelo_HistoricoDAO();
    Clubes objClub = new Clubes();
    int respuesta, filasel;

    public Controlador_Club(TodosClubs club) {
        this.club = club;
    }

    // enumeramos las acciones que realizaremos con el controlador
    public enum AccionMVC {
        __VER_CLUB,
        __NUEVO_CLUB,
        __ELIMINAR_CLUB,
        __MODIFICAR_CLUB,
        __ASOCIAR,
        __VOLVER,
        __BUSCADOR,
        __LIMPIAR,
        __CREAR_PDF,
        __COMBOCLUB,
    }

    /**
     * Metodo que inicia nuestra vista, poniendo visible la vista y asignando
     * todas las escuchas
     */
    public void iniciar() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(club);
            club.setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnMostrar.setActionCommand("__VER_CLUB");
        this.club.btnMostrar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnNuevo.setActionCommand("__NUEVO_CLUB");
        this.club.btnNuevo.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnEliminar.setActionCommand("__ELIMINAR_CLUB");
        this.club.btnEliminar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnModificar.setActionCommand("__MODIFICAR_CLUB");
        this.club.btnModificar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnVolver.setActionCommand("__VOLVER");
        this.club.btnVolver.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnBuscar.setActionCommand("__BUSCADOR");
        this.club.btnBuscar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnLimpiar.setActionCommand("__LIMPIAR");
        this.club.btnLimpiar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnAsociar.setActionCommand("__ASOCIAR");
        this.club.btnAsociar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.btnPdf.setActionCommand("__CREAR_PDF");
        this.club.btnPdf.addActionListener(this);
        //añade un escucha al evento producido por el componente
        this.club.tablaClub.addMouseListener(this);
        // asigna un modelo a la tabla
        this.club.tablaClub.setModel(new DefaultTableModel());
        //ponemos el panel centrado
        this.club.setLocationRelativeTo(null);
        //bloqueamos en campo id para que no se pueda rellenar
        this.club.txtId.setEnabled(false);
        //ponemos opacos los titulos para la interfaz
        this.club.labelTituloClub.setOpaque(true);

        this.club.labelConsulta.setOpaque(true);
        //asignamos un icono a la vista
        this.club.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo_lfp.png")).getImage());
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.comboClubs.setActionCommand("__COMBOCLUB");
        this.club.comboClubs.addActionListener(this);
        this.club.comboClubs.addMouseListener(this);
        // asignamos un modelo y lo llenamos con su metodo
        this.club.comboClubs.setModel(this.mclub.llenarCombo());
        // ocultamos dos campos que usaremos pero no queremos que sean visible
        this.club.txtId.setVisible(false);
        //le doy la escucha al texto filtro
        this.club.txtFiltro.addKeyListener(this);
        this.club.txtNombre.addKeyListener(this);
        this.club.txtCampo.addKeyListener(this);
        this.club.txtFecha.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (AccionMVC.valueOf(ae.getActionCommand())) {
            case __VER_CLUB:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.club.tablaClub.setModel(this.mclub.getTablaClub());
                // eliminamos la colunma de la id
                TableColumnModel tCol = this.club.tablaClub.getColumnModel();
                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                break;

            case __NUEVO_CLUB:
                //comprobamos que los campos no esten vacios
                if (camposVacios()) {
                    JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                } else {
                    // preguntamos confirmacion previa
                    respuesta = JOptionPane.showConfirmDialog(null, "Desea insertar al club");
                    if (respuesta == 0) {
                        // asignamos todos los valores al objeto club
                        objClub.setNombre(this.club.txtNombre.getText());
                        objClub.setFecha_creacion(Integer.parseInt(this.club.txtFecha.getText()));
                        objClub.setNom_estadio(this.club.txtCampo.getText());
                        // introducimos el nuevo club
                        if (this.mclub.NuevoClub(objClub)) {
                            this.club.tablaClub.setModel(this.mclub.getTablaClub());
                            tCol = this.club.tablaClub.getColumnModel();
                            tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                            JOptionPane.showMessageDialog(club, "Exito: Nuevo registro agregado.");
                            limpiar();
                        } else //ocurrio un error
                        {
                            JOptionPane.showMessageDialog(club, "Error: Datos mal introducidos.");
                        }
                    }
                }
                break;

            case __ELIMINAR_CLUB:
                // seleccionamos una fila de la tabla
                filasel = club.tablaClub.getSelectedRow();
                if (filasel != -1) {
                    // comprobamos que los campos no esten vacios
                    if (camposVacios()) {
                        JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                    } else {
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea eliminar el club");
                        if (respuesta == 0) {
                            //asignamos los valores al objeto
                            objClub.setId_club(Integer.parseInt(this.club.txtId.getText()));
                            objClub.setNombre(this.club.txtNombre.getText());
                            //eliminamos el club
                            if (this.mclub.EliminarClub(objClub)) {
                                this.club.tablaClub.setModel(this.mclub.getTablaClub());
                                tCol = this.club.tablaClub.getColumnModel();
                                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                                JOptionPane.showMessageDialog(club, "Exito: Registro eliminado.");
                                limpiar();
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(club, "No hay ninguna fila seleccionada.");
                }
                break;

            case __MODIFICAR_CLUB:
                //seleccionamos la fila de la tabla a modificar
                filasel = club.tablaClub.getSelectedRow();
                if (filasel != -1) {
                    //se comprueban que los campos no esten vacios
                    if (camposVacios()) {
                        JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                    } else {
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea modificar los datos del club");
                        if (respuesta == 0) {
                            //se asignan los valores al objeto
                            objClub.setId_club(Integer.parseInt(this.club.txtId.getText()));
                            objClub.setNombre(this.club.txtNombre.getText());
                            objClub.setFecha_creacion(Integer.parseInt(this.club.txtFecha.getText()));
                            objClub.setNom_estadio(this.club.txtCampo.getText());
                            //se modifica el objeto
                            if (this.mclub.ModificarClub(objClub)) {
                                this.club.tablaClub.setModel(this.mclub.getTablaClub());
                                tCol = this.club.tablaClub.getColumnModel();
                                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                                JOptionPane.showMessageDialog(club, "Exito: Nuevo registro modificado.");
                                limpiar();
                            } else //ocurrio un error
                            {
                                JOptionPane.showMessageDialog(club, "Error: Datos mal introducidos..");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(club, "No hay ninguna fila seleccionada.");
                }
                break;

            case __ASOCIAR:
                //vamos a la ventana de asociar
                new Controlador_Historico(new Asociacion()).iniciar();
                break;

            case __VOLVER:
                //cerramos la ventana actual
                this.club.dispose();
                Controlador_ventanaprincipal.ventana.setVisible(true);
                break;

            case __BUSCADOR:
                //asigna al modelo de la tabla la nuvea tabla personalizada
                //en función al valor del combobox
                this.club.tablaClub.setModel(this.mclub.getTablaClubPersonalizada(String.valueOf(this.club.comboClubs.getSelectedItem())));
                tCol = this.club.tablaClub.getColumnModel();
                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                break;

            case __LIMPIAR:
                //vaciamos los campos
                limpiar();
                break;

            case __CREAR_PDF:
                try {
                    //mensajes que estaran en el pdf cuando se genere
                    MessageFormat Header = new MessageFormat("Lista de Clubes de la LFP");
                    MessageFormat Footer = new MessageFormat("Página {0, number, integer} ");
                    //con el metodo .print imprimimos la tabla que necesitamos, pudiendola guardar en pdf   
                    this.club.tablaClub.print(JTable.PrintMode.NORMAL, Header, Footer);
                    JOptionPane.showMessageDialog(club, "Pdf creado correctamente");
                } catch (PrinterException ex) {
                    Logger.getLogger(Controlador_Club.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //indico que estoy sobre la tabla clubs
        if (me.getSource() == this.club.tablaClub) {
            if (me.getButton() == 1)//boton izquierdo
            {
                int fila = this.club.tablaClub.rowAtPoint(me.getPoint());
                if (fila > -1) {
                    //le damos los valores a los campos de la tabla a cada campo de texto
                    this.club.txtId.setText(String.valueOf(this.club.tablaClub.getModel().getValueAt(fila, 0)));
                    this.club.txtNombre.setText(String.valueOf(this.club.tablaClub.getModel().getValueAt(fila, 1)));
                    this.club.txtFecha.setText(String.valueOf(this.club.tablaClub.getModel().getValueAt(fila, 2)));
                    this.club.txtCampo.setText(String.valueOf(this.club.tablaClub.getModel().getValueAt(fila, 3)));
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }
    
    int limitefecha = 4;
    int limiteTexto = 30;
    TableRowSorter trs=null;
    @Override
    public void keyTyped(KeyEvent ke) {
            
        if (ke.getSource()==this.club.txtFiltro) {
        //creo un rowsorter con el modelo de la tabla
        trs = new TableRowSorter(this.club.tablaClub.getModel());
        //le asigno ese row sorter a la tabla
        this.club.tablaClub.setRowSorter(trs); 
        }
        
        if (ke.getSource()==this.club.txtNombre) {
             if (this.club.txtNombre.getText().length() == limiteTexto) {
                ke.consume();
            }
        }
        
        if (ke.getSource()==this.club.txtCampo) {
             if (this.club.txtCampo.getText().length() == limiteTexto) {
                ke.consume();
            }
        }
        
        if (ke.getSource()==this.club.txtFecha) {
             if (this.club.txtFecha.getText().length() == limitefecha) {
                ke.consume();
            }
        }
        
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //asigno el source solo a ese campo de texto
        if (ke.getSource()==this.club.txtFiltro) {
        //realizo el filtro de la columna deseada
        trs.setRowFilter(RowFilter.regexFilter("(?i)"+this.club.txtFiltro.getText(), 1));
        }
    }
    
    
    /**
     * Metodo para vaciar los campos
     */
    public void limpiar() {
        this.club.txtId.setText("");
        this.club.txtNombre.setText("");
        this.club.txtFecha.setText("");
        this.club.txtCampo.setText("");
        this.club.txtFiltro.setText("");
    }

    /**
     * Metodo que comprueba que los campos no esten vacios o sean nulos
     *
     * @return true/false
     */
    public boolean camposVacios() {
        return (this.club.txtId.getText().length() == 0
                || this.club.txtNombre.getText().length() == 0
                || this.club.txtFecha.getText().length() == 0
                || this.club.txtCampo.getText().length() == 0
                || this.club.txtId.getText().equals("null")
                || this.club.txtNombre.getText().equals("null")
                || this.club.txtFecha.getText().equals("null")
                || this.club.txtCampo.getText().equals("null"));
    }
}
