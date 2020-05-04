package Controlador;


import Modelo.Modelo_Futbolistas;
import Vista.Asociar;
import Vista.FiltraFutbolista;
import Vista.TodosFutbolistas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose
 */
public class Controlador_Futbolista implements ActionListener,MouseListener {

    public  TodosFutbolistas futb;
    public  FiltraFutbolista filtraFutb;
    public  Asociar asociar;
    Modelo.Modelo_Futbolistas mfutb = new Modelo_Futbolistas();
    
    public Controlador_Futbolista (TodosFutbolistas futb) {
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
        this.futb.tablaFutbolista.addMouseListener(this);
        this.futb.tablaFutbolista.setModel(new DefaultTableModel());
        //ponemos el panel centrado
        this.futb.setLocationRelativeTo(null);
        //bloqueamos en campo id para que no se pueda rellenar
        this.futb.txtId.setEnabled(false);    
    }
    
    
    
    /*
    case __BUSCADOR:
                asociar = new Asociar(club, true);
                asociar.setVisible(true);
                break;
    */
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        switch (Controlador_Futbolista.AccionMVC.valueOf(ae.getActionCommand())) {
            case __VER_FUTBOLISTA:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.futb.tablaFutbolista.setModel(this.mfutb.getTablaFutbolistas());
                break;
           /* case __NUEVO_FUTBOLISTA:
                //añade un nuevo registro
                if (
                        this.futb.txtNombre.getText().length() == 0
                        || this.futb.txtFecha.getText().length() == 0
                        || this.futb.txtCampo.getText().length() == 0) {
                    JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                } else {
                    if (this.mfutb.NuevoFutbolista(
                            this.futb.txtNombre.getText(),
                            Integer.parseInt(this.futb.txtFecha.getText()),
                            this.futb.txtCampo.getText())) {
                        this.futb.tablaClub.setModel(this.mclub.getTablaClub());
                        JOptionPane.showMessageDialog(club, "Exito: Nuevo registro agregado.");
                        this.futb.txtId.setText("");
                        this.futb.txtNombre.setText("");
                        this.futb.txtFecha.setText("");
                        this.futb.txtCampo.setText("");
                    } else //ocurrio un error
                    {
                        JOptionPane.showMessageDialog(club, "Error: ID o Nombre ya registrado.");
                    }
                }
                break;
            case __ELIMINAR_FUTBOLISTA:
                int filasel = club.tablaClub.getSelectedRow();
                if (filasel != -1) {
                    if (this.futb.EliminarClub(Integer.parseInt(this.club.txtId.getText()))) {
                        this.futb.tablaClub.setModel(this.mclub.getTablaClub());
                        JOptionPane.showMessageDialog(club, "Exito: Registro eliminado.");
                        this.futb.txtId.setText("");
                        this.futb.txtNombre.setText("");
                        this.futb.txtFecha.setText("");
                        this.futb.txtCampo.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(club, "No hay ninguna fila seleccionada.");
                }
                break;
            case __MODIFICAR_FUTBOLISTA:
                filasel = club.tablaClub.getSelectedRow();
                //modifico un nuevo registro
                if (filasel != -1) {
                    if (this.mfutb.ModificarFutbolista(nif, nombre, apellidos, LocalDate.MIN, nacionalidad)
                            Integer.parseInt(this.futb.txtId.getText()),
                            this.futb.txtNombre.getText(),
                            Integer.parseInt(this.futb.txtFecha.getText()),
                            this.futb.txtCampo.getText())) {
                        this.futb.tablaFutbolista.setModel(this.mclub.getTablaClub());
                        JOptionPane.showMessageDialog(club, "Exito: Nuevo registro modificado.");
                        this.futb.txtId.setText("");
                        this.futb.txtNombre.setText("");
                        this.futb.txtFecha.setText("");
                        this.futb.txtCampo.setText("");
                    } else //ocurrio un error
                    {
                        JOptionPane.showMessageDialog(club, "Error: Nombre ya registrado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(club, "No hay ninguna fila seleccionada.");
                }
                break;
            case __VOLVER:
                this.futb.dispose();
                Controlador_ventanaprincipal.ventana.setVisible(true);
                break;
            case __BUSCADOR:
                fclub = new FiltraClub();
                fclub.setVisible(true);
                break;
            case __LIMPIAR:
                this.futb.txtId.setText("");
                this.futb.txtNombre.setText("");
                this.futb.txtFecha.setText("");
                this.futb.txtCampo.setText("");
                break;
                */
        }
        
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
                if (me.getButton() == 1)//boton izquierdo
        {
            int fila = this.futb.tablaFutbolista.rowAtPoint(me.getPoint());
            if (fila > -1) {
                this.futb.txtId.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 0)));
                this.futb.txtNombre.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 1)));
                this.futb.txtFecha.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 2)));
                this.futb.txtCampo.setText(String.valueOf(this.futb.tablaFutbolista.getValueAt(fila, 3)));
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
    
}
