package Controlador;

import Modelo.Futbolistas;
import Modelo.DAO.Modelo_FutbolistasDAO;
import Vista.Asociar;
import Vista.TodosFutbolistas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Controlador_Futbolista implements ActionListener, MouseListener {

    public TodosFutbolistas futb;
    public Asociar asociar;
    Modelo_FutbolistasDAO mfutb = new Modelo_FutbolistasDAO();
    Futbolistas objFut = new Futbolistas();
    int respuesta, filasel;

    public Controlador_Futbolista(TodosFutbolistas futb) {
        this.futb = futb;
    }

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
        this.futb.tablaFutbolista.addMouseListener(this);
        this.futb.tablaFutbolista.setModel(new DefaultTableModel());
        //ponemos el panel centrado
        this.futb.setLocationRelativeTo(null);
        //bloqueamos en campo id para que no se pueda rellenar
        this.futb.txtId.setEnabled(false);
        this.futb.comboFutbolistas.setEditable(true);
      
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (Controlador_Futbolista.AccionMVC.valueOf(ae.getActionCommand())) {
            case __VER_FUTBOLISTA:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                break;

            case __NUEVO_FUTBOLISTA:

                //añade un nuevo registro
                if (this.futb.txtNif.getText().length() == 0
                        || this.futb.txtNombre.getText().length() == 0
                        || this.futb.txtApellidos.getText().length() == 0
                        || this.futb.txtFechaNac.getText().length() == 0
                        || this.futb.txtNacionalidad.getText().length() == 0) {
                    JOptionPane.showMessageDialog(futb, "Error: Campos vacios.");
                } else {
                    respuesta = JOptionPane.showConfirmDialog(null, "Desea insertar al futbolista");
                    if (respuesta == 0) {
                        objFut.setNif(this.futb.txtNif.getText());
                        objFut.setNombre(this.futb.txtNombre.getText());
                        objFut.setApellidos(this.futb.txtApellidos.getText());
                        objFut.setFecha_nacimiento(LocalDate.parse(this.futb.txtFechaNac.getText()));
                        objFut.setNacionalidad(this.futb.txtNacionalidad.getText());
                        if (this.mfutb.NuevoFutbolista(objFut)) {
                            this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                            JOptionPane.showMessageDialog(futb, "Exito: Nuevo registro agregado.");
                            limpiar();
                        } else //ocurrio un error
                        {
                            JOptionPane.showMessageDialog(futb, "Error: Nif existente o mal introducido.");
                        }
                    }
                }
                break;

            case __ELIMINAR_FUTBOLISTA:
                int filasel = futb.tablaFutbolista.getSelectedRow();
                if (filasel != -1) {
                    if (this.futb.txtNif.getText().length() == 0
                            || this.futb.txtNombre.getText().length() == 0
                            || this.futb.txtApellidos.getText().length() == 0
                            || this.futb.txtFechaNac.getText().length() == 0
                            || this.futb.txtNacionalidad.getText().length() == 0) {
                        JOptionPane.showMessageDialog(futb, "Error: Campos vacios.");
                    } else {
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea eliminar al futbolista");
                        if (respuesta == 0) {
                            if (this.mfutb.EliminarFutbolista(Integer.parseInt(this.futb.txtId.getText()))) {
                                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                                JOptionPane.showMessageDialog(futb, "Exito: Registro eliminado.");
                                limpiar();
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(futb, "No hay ninguna fila seleccionada.");
                }
                break;

            case __MODIFICAR_FUTBOLISTA:

                filasel = futb.tablaFutbolista.getSelectedRow();
                //modifico un nuevo registro
                if (filasel != -1) {
                    if (this.futb.txtNif.getText().length() == 0
                            || this.futb.txtNombre.getText().length() == 0
                            || this.futb.txtApellidos.getText().length() == 0
                            || this.futb.txtFechaNac.getText().length() == 0
                            || this.futb.txtNacionalidad.getText().length() == 0) {
                        JOptionPane.showMessageDialog(futb, "Error: Campos vacios.");
                    } else {
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea modificar al futbolista");
                        if (respuesta == 0) {
                            objFut.setId_futbolista(Integer.parseInt(this.futb.txtId.getText()));
                            objFut.setNif(this.futb.txtNif.getText());
                            objFut.setNombre(this.futb.txtNombre.getText());
                            objFut.setApellidos(this.futb.txtApellidos.getText());
                            objFut.setFecha_nacimiento(LocalDate.parse(this.futb.txtFechaNac.getText()));
                            objFut.setNacionalidad(this.futb.txtNacionalidad.getText());
                            if (this.mfutb.ModificarFutbolista(objFut)) {

                                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                                JOptionPane.showMessageDialog(futb, "Exito: Nuevo registro modificado.");
                                limpiar();

                            } else //ocurrio un error
                            {
                                JOptionPane.showMessageDialog(futb, "Error: NIF ya existe o mal introducido.");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(futb, "No hay ninguna fila seleccionada.");
                }
                break;

            case __ASOCIAR:
                new Controlador_Historico(new Asociar(futb, false)).iniciar();
                break;

            case __VOLVER:
                this.futb.dispose();
                Controlador_ventanaprincipal.ventana.setVisible(true);
                break;

            case __BUSCADOR:
                /*       if (   ) {
                    JOptionPane.showMessageDialog(futb, "Error: Campos vacios.");
                } else {
                    this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistasPersonalizada(
                            this.futb.comboEquipos.;
                }*/
                break;

            case __LIMPIAR:
                limpiar();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        if (me.getButton() == 1)//boton izquierdo
        {
            int fila = this.futb.tablaFutbolista.rowAtPoint(me.getPoint());
            if (fila > -1) {
                this.futb.txtId.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 0)));
                this.futb.txtNif.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 1)));
                this.futb.txtNombre.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 2)));
                this.futb.txtApellidos.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 3)));
                this.futb.txtFechaNac.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 4)));
                this.futb.txtNacionalidad.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 5)));

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

    public void limpiar () {
        this.futb.txtId.setText("");
        this.futb.txtNif.setText("");
        this.futb.txtNombre.setText("");
        this.futb.txtApellidos.setText("");
        this.futb.txtFechaNac.setText("");
        this.futb.txtNacionalidad.setText("");
    }
    
}
