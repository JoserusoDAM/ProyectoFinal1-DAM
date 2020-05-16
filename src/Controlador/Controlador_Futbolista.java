package Controlador;

import Modelo.Futbolistas;
import Modelo.DAO.Modelo_FutbolistasDAO;
import Modelo.DAO.Modelo_HistoricoDAO;
import Modelo.Historico;
import Vista.Asociacion;
import Vista.TodosFutbolistas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Jose
 */
public class Controlador_Futbolista implements ActionListener, MouseListener, KeyListener {

    public TodosFutbolistas futb;
    Modelo_FutbolistasDAO mfutb = new Modelo_FutbolistasDAO();
    Modelo_HistoricoDAO mHis = new Modelo_HistoricoDAO();
    Futbolistas objFut = new Futbolistas();
    Historico hist = new Historico();
    int respuesta, filasel;

    public Controlador_Futbolista(TodosFutbolistas futb) {
        this.futb = futb;
    }

    // enumeramos las acciones que realizaremos con el controlador
    public enum AccionMVC {
        __VER_FUTBOLISTA,
        __NUEVO_FUTBOLISTA,
        __ELIMINAR_FUTBOLISTA,
        __MODIFICAR_FUTBOLISTA,
        __VOLVER,
        __BUSCADOR,
        __ASOCIAR,
        __LIMPIAR,
        __CREAR_PDF,
        __COMBOFUT,
        __ELIMINARASOC
    }

    public void iniciar() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(futb);
            futb.setVisible(true);
        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnMostrar.setActionCommand("__VER_FUTBOLISTA");
        this.futb.btnMostrar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnNuevo.setActionCommand("__NUEVO_FUTBOLISTA");
        this.futb.btnNuevo.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnEliminar.setActionCommand("__ELIMINAR_FUTBOLISTA");
        this.futb.btnEliminar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnModificar.setActionCommand("__MODIFICAR_FUTBOLISTA");
        this.futb.btnModificar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnVolver.setActionCommand("__VOLVER");
        this.futb.btnVolver.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnBuscar.setActionCommand("__BUSCADOR");
        this.futb.btnBuscar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnLimpiar.setActionCommand("__LIMPIAR");
        this.futb.btnLimpiar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnPdf.setActionCommand("__CREAR_PDF");
        this.futb.btnPdf.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnAsociar.setActionCommand("__ASOCIAR");
        this.futb.btnAsociar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.btnEliminarAsoc.setActionCommand("__ELIMINARASOC");
        this.futb.btnEliminarAsoc.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.futb.tablaFutbolista.addMouseListener(this);
        //ponemos el panel centrado
        this.futb.setLocationRelativeTo(null);
        //bloqueamos en campo id para que no se pueda rellenar
        this.futb.txtId.setEnabled(false);
        //ponemos el label en opaco para mostrar el color
        this.futb.labelTitulo.setOpaque(true);
        this.futb.labelClubs.setOpaque(true);
        //Asignamos el icono a la vista
        this.futb.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo_lfp.png")).getImage());
        // configuro el combobox con sus action comand y listener
        this.futb.comboFutbolistas.setActionCommand("__COMBOFUT");
        this.futb.comboFutbolistas.addActionListener(this);
        this.futb.comboFutbolistas.addMouseListener(this);
        this.futb.comboFutbolistas.setModel(this.mfutb.llenarCombo());
        //ocultamos los campos que no necesitamos ver
        this.futb.txtId.setVisible(false);
        //le damos al escucha al texto de filtro
        this.futb.txtFiltro.addKeyListener(this);
        this.futb.txtNif.addKeyListener(this);
        this.futb.txtFechaNac.addKeyListener(this);
        this.futb.txtNombre.addKeyListener(this);
        this.futb.txtApellidos.addKeyListener(this);
        this.futb.txtNacionalidad.addKeyListener(this);
        //deshabilito el campo de temporada
        this.futb.txtTemporada.setEnabled(false);
        //precargo la tabla
        this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
        //elimina la columna del id
        TableColumnModel tCol = this.futb.tablaFutbolista.getColumnModel();
        tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (Controlador_Futbolista.AccionMVC.valueOf(ae.getActionCommand())) {
            case __VER_FUTBOLISTA:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                //elimina la columna del id
                TableColumnModel tCol = this.futb.tablaFutbolista.getColumnModel();
                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                break;

            case __NUEVO_FUTBOLISTA:
                //comprobamos que los campos no esten vacios
                if (camposVacios()) {
                    JOptionPane.showMessageDialog(futb, "Error: Campos vacíos.");
                } else {
                    respuesta = JOptionPane.showConfirmDialog(null, "Desea insertar al futbolista");
                    if (respuesta == 0) {
                        //asignamos los valores al objeto
                        objFut.setNif(this.futb.txtNif.getText());
                        objFut.setNombre(this.futb.txtNombre.getText());
                        objFut.setApellidos(this.futb.txtApellidos.getText());
                        objFut.setFecha_nacimiento(LocalDate.parse(this.futb.txtFechaNac.getText()));
                        objFut.setNacionalidad(this.futb.txtNacionalidad.getText());
                        //creamos el objeto
                        if (this.mfutb.NuevoFutbolista(objFut)) {
                            this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                            tCol = this.futb.tablaFutbolista.getColumnModel();
                            tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                            JOptionPane.showMessageDialog(futb, "Éxito: Nuevo registro agregado.");
                            limpiar();
                        } else //ocurrio un error
                        {
                            JOptionPane.showMessageDialog(futb, "Error: Datos mal introducidos.");
                        }
                    }
                }
                break;

            case __ELIMINAR_FUTBOLISTA:
                //seleccionamos una fila
                filasel = futb.tablaFutbolista.getSelectedRow();
                if (filasel != -1) {
                    //comprobamos que los campos no esten vacios
                    if (camposVacios()) {
                        JOptionPane.showMessageDialog(futb, "Error: Campos vacíos.");
                    } else {
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea eliminar al futbolista");
                        if (respuesta == 0) {
                            //asignamos el valor al objeto futbolista
                            objFut.setId_futbolista(Integer.parseInt(this.futb.txtId.getText()));
                            //eliminamos al futbolista
                            if (this.mfutb.EliminarFutbolista(objFut)) {
                                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                                tCol = this.futb.tablaFutbolista.getColumnModel();
                                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                                JOptionPane.showMessageDialog(futb, "Éxito: Registro eliminado.");
                                limpiar();
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(futb, "No hay ninguna fila seleccionada.");
                }
                break;

            case __MODIFICAR_FUTBOLISTA:
                // seleccionamos una fila
                filasel = futb.tablaFutbolista.getSelectedRow();
                // da un aviso de error si se intenta modificar sin seleccionar ninguna fila
                if (filasel != -1) {
                    // se verifica que ningun campo este vacio
                    if (camposVacios()) {
                        JOptionPane.showMessageDialog(futb, "Error: Campos vacíos.");
                    } else {
                        // se pide confirmacion para realizar el paso
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea modificar al futbolista");
                        if (respuesta == 0) {
                            // mediante los setters se ponen los datos de de los campos de texto al objeto futbolista 
                            objFut.setId_futbolista(Integer.parseInt(this.futb.txtId.getText()));
                            objFut.setNif(this.futb.txtNif.getText());
                            objFut.setNombre(this.futb.txtNombre.getText());
                            objFut.setApellidos(this.futb.txtApellidos.getText());
                            objFut.setFecha_nacimiento(LocalDate.parse(this.futb.txtFechaNac.getText()));
                            objFut.setNacionalidad(this.futb.txtNacionalidad.getText());
                            // se realiza la modificación del futbolista metiendo el objeto por parametro
                            if (this.mfutb.ModificarFutbolista(objFut)) {
                                // ponemos el modelo de la tabla 
                                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                                // eliminamos la columna de la id ya que no queremos que se muestre
                                tCol = this.futb.tablaFutbolista.getColumnModel();
                                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                                JOptionPane.showMessageDialog(futb, "Éxito: Nuevo registro modificado.");
                                limpiar();
                            } else //ocurrio un error
                            {
                                JOptionPane.showMessageDialog(futb, "Error: Datos mal introducidos.");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(futb, "No hay ninguna fila seleccionada.");
                }
                break;

            case __ASOCIAR:
                // inciamos el controlador y la vista de la asociación
                new Controlador_Historico(new Asociacion()).iniciar();
                break;

            case __VOLVER:
                // cerramos la ventana y volvemos al panel principal
                this.futb.dispose();
                Controlador_ventanaprincipal.ventana.setVisible(true);
                break;

            case __BUSCADOR:
                // ponemos el nuevo modelo de la tabla personalizada que muestra otros campos
                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistasPersonalizada(String.valueOf(this.futb.comboFutbolistas.getSelectedItem())));
                // eliminamos la columna que no queremos
                tCol = this.futb.tablaFutbolista.getColumnModel();
                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                break;

            case __LIMPIAR:
                // limpiamos los campos de texto
                limpiar();
                break;

            case __CREAR_PDF:
                try {
                    MessageFormat Header = new MessageFormat("Lista de Futbolistas de la LFP");
                    MessageFormat Footer = new MessageFormat("Página {0, number, integer} ");
                    //con el metodo .print imprimimos la tabla que necesitamos, pudiendola guardar en pdf
                    this.futb.tablaFutbolista.print(JTable.PrintMode.NORMAL, Header, Footer);
                    JOptionPane.showMessageDialog(futb, "Pdf creado correctamente");
                } catch (PrinterException ex) {
                    Logger.getLogger(Controlador_Club.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case __ELIMINARASOC:
                //seleccionamos una fila
                filasel = futb.tablaFutbolista.getSelectedRow();
                if (filasel != -1) {
                    //comprobamos que los campos no esten vacios
                    if (camposVacios() || this.futb.txtTemporada.getText().length() == 0
                            || this.futb.txtTemporada.getText().equals("null")) {
                        JOptionPane.showMessageDialog(futb, "Error: Campos vacíos.");
                    } else {
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea eliminar la asociación");
                        if (respuesta == 0) {
                            hist.setId_futbolista(Integer.parseInt(this.futb.txtId.getText()));
                            hist.setTemporada(Integer.parseInt(this.futb.txtTemporada.getText()));
                            //eliminamos la asociacion
                            if (this.mHis.EliminarHistorico(hist)) {
                                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistasPersonalizada(String.valueOf(this.futb.comboFutbolistas.getSelectedItem())));
                                tCol = this.futb.tablaFutbolista.getColumnModel();
                                tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                                JOptionPane.showMessageDialog(futb, "Éxito: Asociación eliminada.");
                                limpiar();
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(futb, "No hay ninguna fila seleccionada.");
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //indico que estoy sobre la tabla futbolistas
        if (me.getSource() == this.futb.tablaFutbolista) {
            if (me.getButton() == 1)//boton izquierdo
            {
                int fila = this.futb.tablaFutbolista.rowAtPoint(me.getPoint());
                if (fila > -1) {
                    this.futb.txtId.setText(String.valueOf(this.futb.tablaFutbolista.getModel().getValueAt(fila, 0)));
                    this.futb.txtNif.setText(String.valueOf(this.futb.tablaFutbolista.getModel().getValueAt(fila, 1)));
                    this.futb.txtNombre.setText(String.valueOf(this.futb.tablaFutbolista.getModel().getValueAt(fila, 2)));
                    this.futb.txtApellidos.setText(String.valueOf(this.futb.tablaFutbolista.getModel().getValueAt(fila, 3)));
                    this.futb.txtFechaNac.setText(String.valueOf(this.futb.tablaFutbolista.getModel().getValueAt(fila, 4)));
                    this.futb.txtNacionalidad.setText(String.valueOf(this.futb.tablaFutbolista.getModel().getValueAt(fila, 5)));
                    // si no existe la fila 6 caputra la excepcion  
                    try {
                        this.futb.txtTemporada.setText(String.valueOf(this.futb.tablaFutbolista.getModel().getValueAt(fila, 6)));
                    } catch (Exception e) {
                    }
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

    //variables para limitar los campos de texto
    int limiteNif = 9;
    int limiteFecha = 10;
    int limitetexto = 45;
    TableRowSorter trs = null;

    @Override
    public void keyTyped(KeyEvent ke) {
        //le doy el source a cada campo
        if (ke.getSource() == this.futb.txtFiltro) {
            //creo un rowsorter con el modelo de la tabla
            trs = new TableRowSorter(this.futb.tablaFutbolista.getModel());
            //le asigno ese row sorter a la tabla
            this.futb.tablaFutbolista.setRowSorter(trs);
            //limito el numero de caracteres en el nif
        }

        if (ke.getSource() == this.futb.txtNif) {
            if (this.futb.txtNif.getText().length() == limiteNif) {
                ke.consume();
            }
        }

        if (ke.getSource() == this.futb.txtFechaNac) {
            //fuerzo que no se pueda escribir numeros en el campo
            if (Character.isLetter(ke.getKeyChar())
                    || this.futb.txtFechaNac.getText().length() == limiteFecha) {
                ke.consume();
            }
        }

        if (ke.getSource() == this.futb.txtNombre) {
            //fuerzo que no se pueda escribir numeros en el campo
            if (!Character.isLetter(ke.getKeyChar())
                    && !(ke.getKeyChar() == KeyEvent.VK_SPACE)
                    && !(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                    || this.futb.txtNombre.getText().length() == limitetexto) {
                ke.consume();
            }
        }

        if (ke.getSource() == this.futb.txtApellidos) {
            //fuerzo que no se pueda escribir numeros en el campo
            if (!Character.isLetter(ke.getKeyChar())
                    && !(ke.getKeyChar() == KeyEvent.VK_SPACE)
                    && !(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                    || this.futb.txtApellidos.getText().length() == limitetexto) {
                ke.consume();
            }
        }

        if (ke.getSource() == this.futb.txtNacionalidad) {
            //fuerzo que no se pueda escribir numeros en el campo
            if (!Character.isLetter(ke.getKeyChar())
                    && !(ke.getKeyChar() == KeyEvent.VK_SPACE)
                    && !(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                    || this.futb.txtNacionalidad.getText().length() == limitetexto) {
                ke.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == this.futb.txtFiltro) {
            //realizo el filtro de la columna deseada
            trs.setRowFilter(RowFilter.regexFilter("(?i)" + this.futb.txtFiltro.getText(), 1, 2, 3, 4, 5));
        }

    }

    /**
     * Vacio los campos de texto
     */
    public void limpiar() {
        this.futb.txtId.setText("");
        this.futb.txtNif.setText("");
        this.futb.txtNombre.setText("");
        this.futb.txtApellidos.setText("");
        this.futb.txtFechaNac.setText("");
        this.futb.txtNacionalidad.setText("");
        this.futb.txtTemporada.setText("");
        this.futb.txtFiltro.setText("");
    }

    /**
     * Compruebo que los campos esten vacios
     *
     * @return true/false
     */
    public boolean camposVacios() {
        return (this.futb.txtNif.getText().length() == 0
                || this.futb.txtNombre.getText().length() == 0
                || this.futb.txtApellidos.getText().length() == 0
                || this.futb.txtFechaNac.getText().length() == 0
                || this.futb.txtNacionalidad.getText().length() == 0
                || this.futb.txtNif.getText().contains("null")
                || this.futb.txtNombre.getText().contains("null")
                || this.futb.txtApellidos.getText().contains("null")
                || this.futb.txtFechaNac.getText().contains("null")
                || this.futb.txtNacionalidad.getText().contains("null"));
    }
}
