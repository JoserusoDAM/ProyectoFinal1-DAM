package Controlador;

import Modelo.Clubes;
import Modelo.DAO.Modelo_ClubDAO;
import Vista.FiltraClub;
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

/**
 *
 * @author Jose
 */
public class Controlador_Club implements ActionListener, MouseListener {

    public TodosClubs club;
    public FiltraClub fclub;
    Modelo_ClubDAO mclub = new Modelo_ClubDAO();
    Clubes objClub = new Clubes();

    public Controlador_Club(TodosClubs club) {
        this.club = club;
    }

    public enum AccionMVC {
        __VER_CLUB,
        __NUEVO_CLUB,
        __ELIMINAR_CLUB,
        __MODIFICAR_CLUB,
        __VOLVER,
        __BUSCADOR,
        __LIMPIAR,
        __CREAR_PDF,
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
        this.club.btnPdf.setActionCommand("__CREAR_PDF");
        this.club.btnPdf.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.club.tablaClub.addMouseListener(this);
        this.club.tablaClub.setModel(new DefaultTableModel());
        //ponemos el panel centrado
        this.club.setLocationRelativeTo(null);
        //bloqueamos en campo id para que no se pueda rellenar
        this.club.txtId.setEnabled(false);
        //cargamos la tabla (opcional)
        this.club.tablaClub.setModel(this.mclub.getTablaClub());
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (AccionMVC.valueOf(ae.getActionCommand())) {
            case __VER_CLUB:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.club.tablaClub.setModel(this.mclub.getTablaClub());
                break;
                
            case __NUEVO_CLUB:
                objClub.setNombre(this.club.txtNombre.getText());
                objClub.setFecha_creacion(Integer.parseInt(this.club.txtFecha.getText()));
                objClub.setNom_estadio(this.club.txtCampo.getText());
                //añade un nuevo registro
                //  Clubes objClub;
                if (this.club.txtNombre.getText().length() == 0
                        || this.club.txtFecha.getText().length() == 0
                        || this.club.txtCampo.getText().length() == 0) {
                    JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                } else {
                    if (this.mclub.NuevoClub(objClub)) {
                        this.club.tablaClub.setModel(this.mclub.getTablaClub());
                        JOptionPane.showMessageDialog(club, "Exito: Nuevo registro agregado.");
                        this.club.txtId.setText("");
                        this.club.txtNombre.setText("");
                        this.club.txtFecha.setText("");
                        this.club.txtCampo.setText("");
                    } else //ocurrio un error
                    {
                        JOptionPane.showMessageDialog(club, "Error: ID o Nombre ya registrado.");
                    }
                }
                break;
                
            case __ELIMINAR_CLUB:
                int filasel = club.tablaClub.getSelectedRow();
                if (filasel != -1) {
                    if (this.club.txtNombre.getText().length() == 0
                            || this.club.txtFecha.getText().length() == 0
                            || this.club.txtCampo.getText().length() == 0) {
                        JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                    } else {
                        if (this.mclub.EliminarClub(Integer.parseInt(this.club.txtId.getText()))) {
                            this.club.tablaClub.setModel(this.mclub.getTablaClub());
                            JOptionPane.showMessageDialog(club, "Exito: Registro eliminado.");
                            this.club.txtId.setText("");
                            this.club.txtNombre.setText("");
                            this.club.txtFecha.setText("");
                            this.club.txtCampo.setText("");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(club, "No hay ninguna fila seleccionada.");
                }
                break;
                
            case __MODIFICAR_CLUB:
                objClub.setId_club(Integer.parseInt(this.club.txtId.getText()));
                objClub.setNombre(this.club.txtNombre.getText());
                objClub.setFecha_creacion(Integer.parseInt(this.club.txtFecha.getText()));
                objClub.setNom_estadio(this.club.txtCampo.getText());
                filasel = club.tablaClub.getSelectedRow();
                //modifico un nuevo registro
                if (filasel != -1) {
                    if (this.club.txtNombre.getText().length() == 0
                            || this.club.txtFecha.getText().length() == 0
                            || this.club.txtCampo.getText().length() == 0) {
                        JOptionPane.showMessageDialog(club, "Error: Campos vacios.");
                    } else {
                        if (this.mclub.ModificarClub(objClub)) {
                            this.club.tablaClub.setModel(this.mclub.getTablaClub());
                            JOptionPane.showMessageDialog(club, "Exito: Nuevo registro modificado.");
                            this.club.txtId.setText("");
                            this.club.txtNombre.setText("");
                            this.club.txtFecha.setText("");
                            this.club.txtCampo.setText("");
                        } else //ocurrio un error
                        {
                            JOptionPane.showMessageDialog(club, "Error: Nombre ya registrado.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(club, "No hay ninguna fila seleccionada.");
                }
                break;
                
            case __VOLVER:
                this.club.dispose();
                Controlador_ventanaprincipal.ventana.setVisible(true);
                break;
                
            case __BUSCADOR:    
                this.club.tablaClub.setModel(this.mclub.getTablaClubPersonalizada(Integer.parseInt(this.club.txtIdBuscador.getText())));
                break;
                
            case __LIMPIAR:
                this.club.txtId.setText("");
                this.club.txtNombre.setText("");
                this.club.txtFecha.setText("");
                this.club.txtCampo.setText("");
                break;
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

}
