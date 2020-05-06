package Controlador;

import Modelo.Clubes;
import Modelo.DAO.Modelo_ClubDAO;
import Modelo.DAO.Modelo_HistoricoDAO;
import Vista.Asociar;
import Vista.TodosClubs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Jose
 */
public class Controlador_Club implements ActionListener, MouseListener {

    public TodosClubs club;
    Modelo_ClubDAO mclub = new Modelo_ClubDAO();
    Modelo_HistoricoDAO mHis = new Modelo_HistoricoDAO();
    Clubes objClub = new Clubes();
    int respuesta, filasel;
    
    public Controlador_Club(TodosClubs club) {
        this.club = club;
    }

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
        __COMBOCLUB
    }

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
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.comboClubs.setActionCommand("__COMBOCLUB");
        this.club.comboClubs.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.tablaClub.addMouseListener(this);
        this.club.tablaClub.setModel(new DefaultTableModel());
        //ponemos el panel centrado
        this.club.setLocationRelativeTo(null);
        //bloqueamos en campo id para que no se pueda rellenar
        this.club.txtId.setEnabled(false);

        this.club.comboClubs.setEditable(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (AccionMVC.valueOf(ae.getActionCommand())) {
            case __VER_CLUB:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.club.tablaClub.setModel(this.mclub.getTablaClub());
                break;

            case __NUEVO_CLUB:
                //añade un nuevo registro
                //  Clubes objClub;
                if (this.club.txtNombre.getText().length() == 0
                        || this.club.txtFecha.getText().length() == 0
                        || this.club.txtCampo.getText().length() == 0) {
                    JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                } else {

                    respuesta = JOptionPane.showConfirmDialog(null, "Desea insertar al club");

                    if (respuesta == 0) {
                        objClub.setNombre(this.club.txtNombre.getText());
                        objClub.setFecha_creacion(Integer.parseInt(this.club.txtFecha.getText()));
                        objClub.setNom_estadio(this.club.txtCampo.getText());
                        if (this.mclub.NuevoClub(objClub)) {
                            this.club.tablaClub.setModel(this.mclub.getTablaClub());
                            JOptionPane.showMessageDialog(club, "Exito: Nuevo registro agregado.");
                            limpiar ();
                        } else //ocurrio un error
                        {
                            JOptionPane.showMessageDialog(club, "Error: ID o Nombre ya registrado.");
                        }
                    }
                }
                break;

            case __ELIMINAR_CLUB:
                filasel = club.tablaClub.getSelectedRow();
                if (filasel != -1) {
                    if (this.club.txtNombre.getText().length() == 0
                            || this.club.txtFecha.getText().length() == 0
                            || this.club.txtCampo.getText().length() == 0) {
                        JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                    } else {
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea eliminar el club");
                        if (respuesta == 0) {
                            if (this.mclub.EliminarClub(Integer.parseInt(this.club.txtId.getText()))) {
                                this.club.tablaClub.setModel(this.mclub.getTablaClub());
                                JOptionPane.showMessageDialog(club, "Exito: Registro eliminado.");
                                limpiar ();
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(club, "No hay ninguna fila seleccionada.");
                }
                break;

            case __MODIFICAR_CLUB:
                filasel = club.tablaClub.getSelectedRow();
                //modifico un nuevo registro
                if (filasel != -1) {
                    if (this.club.txtNombre.getText().length() == 0
                            || this.club.txtFecha.getText().length() == 0
                            || this.club.txtCampo.getText().length() == 0) {
                        JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                    } else {
                        respuesta = JOptionPane.showConfirmDialog(null, "Desea modificar los datos del club");
                        if (respuesta == 0) {
                            objClub.setId_club(Integer.parseInt(this.club.txtId.getText()));
                            objClub.setNombre(this.club.txtNombre.getText());
                            objClub.setFecha_creacion(Integer.parseInt(this.club.txtFecha.getText()));
                            objClub.setNom_estadio(this.club.txtCampo.getText());
                            if (this.mclub.ModificarClub(objClub)) {
                                this.club.tablaClub.setModel(this.mclub.getTablaClub());
                                JOptionPane.showMessageDialog(club, "Exito: Nuevo registro modificado.");
                                limpiar ();
                            } else //ocurrio un error
                            {
                                JOptionPane.showMessageDialog(club, "Error: Nombre ya registrado.");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(club, "No hay ninguna fila seleccionada.");
                }
                break;

            case __ASOCIAR:
                new Controlador_Historico(new Asociar(club, false)).iniciar();
                break;

            case __VOLVER:
                this.club.dispose();
                Controlador_ventanaprincipal.ventana.setVisible(true);
                break;

            case __BUSCADOR:
                

                break;

            case __LIMPIAR:
                limpiar ();
                break;

            case __COMBOCLUB:

 
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        if (me.getButton() == 1)//boton izquierdo
        {
            int fila = this.club.tablaClub.rowAtPoint(me.getPoint());
            if (fila > -1) {
                this.club.txtId.setText(String.valueOf(this.club.tablaClub.getValueAt(fila, 0)));
                this.club.txtNombre.setText(String.valueOf(this.club.tablaClub.getValueAt(fila, 1)));
                this.club.txtFecha.setText(String.valueOf(this.club.tablaClub.getValueAt(fila, 2)));
                this.club.txtCampo.setText(String.valueOf(this.club.tablaClub.getValueAt(fila, 3)));
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
        this.club.txtId.setText("");
        this.club.txtNombre.setText("");
        this.club.txtFecha.setText("");
        this.club.txtCampo.setText("");
    }
}
