package Controlador;

import Modelo.DAO.Modelo_ClubDAO;
import Modelo.DAO.Modelo_FutbolistasDAO;
import Modelo.DAO.Modelo_HistoricoDAO;
import Modelo.Historico;
import Vista.Asociacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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
public class Controlador_Historico implements ActionListener, MouseListener, KeyListener {

    public Asociacion asoc;
    Modelo_HistoricoDAO mHisto = new Modelo_HistoricoDAO();
    Modelo_FutbolistasDAO mfutb = new Modelo_FutbolistasDAO();
    Modelo_ClubDAO mclub = new Modelo_ClubDAO();
    Historico hist = new Historico();
    int respuesta, filasel;

    public Controlador_Historico(Asociacion asoc) {
        this.asoc = asoc;
    }

    // enumeramos las acciones que realizaremos con el controlador
    public enum AccionMVC {
        __ASOCIAR,
        __ELIMINAR,
        __VOLVER,
        __LIMPIAR,
    }

    public void iniciar() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(asoc);
            asoc.setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.labelTitulo.setOpaque(true);
        this.asoc.setLocationRelativeTo(null);
        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.btnAsociar.setActionCommand("__ASOCIAR");
        this.asoc.btnAsociar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.btnVolver.setActionCommand("__VOLVER");
        this.asoc.btnVolver.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.btnLimpiar.setActionCommand("__LIMPIAR");
        this.asoc.btnLimpiar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.tablaFutbolistas.addMouseListener(this);
        // declaro el listener para la lista
        this.asoc.listaClubs.addMouseListener(this);
        //desactivo los campos que no quiero modificar
        this.asoc.txtIdClub.setEnabled(false);
        this.asoc.txtIdFut.setEnabled(false);
        this.asoc.txtNomFut.setEnabled(false);
        this.asoc.txtClub.setEnabled(false);
        //le doy a la lista el modelo que quiero
        this.asoc.listaClubs.setModel(this.mHisto.listaClubes());
        //pongo un icono a la interfaz
        this.asoc.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo_lfp.png")).getImage());
        // oculto los campos
        this.asoc.txtIdFut.setVisible(false);
        this.asoc.txtIdClub.setVisible(false);
        //asigno las escucha de teclado
        this.asoc.txtFiltroFut.addKeyListener(this);
        this.asoc.txtTemporada.addKeyListener(this);
        //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
        this.asoc.tablaFutbolistas.setModel(this.mfutb.getTablaFutbolistas());
        TableColumnModel tCol = this.asoc.tablaFutbolistas.getColumnModel();
        tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (Controlador_Historico.AccionMVC.valueOf(ae.getActionCommand())) {

            case __ASOCIAR:
                if (this.asoc.txtTemporada.getText().length() == 0
                        || this.asoc.txtIdFut.getText().length() == 0
                        || this.asoc.txtIdClub.getText().length() == 0) {
                    JOptionPane.showMessageDialog(asoc, "Error: Campos vacíos.");
                } else {
                    respuesta = JOptionPane.showConfirmDialog(null, "Desea crear la asociaciín");
                    if (respuesta == 0) {
                        //asignamos los valores al objeto
                        hist.setId_club(Integer.parseInt(this.asoc.txtIdClub.getText()));
                        hist.setId_futbolista(Integer.parseInt(this.asoc.txtIdFut.getText()));
                        hist.setTemporada(Integer.parseInt(this.asoc.txtTemporada.getText()));
                        //creamos 
                        if (this.mHisto.NuevoHistorico(hist)) {
                            //se realiza la asociacion de un futbolista a un club, en una determinada temporada
                            this.asoc.tablaFutbolistas.setModel(this.mfutb.getTablaFutbolistas());
                            TableColumnModel tCol = this.asoc.tablaFutbolistas.getColumnModel();
                            tCol.removeColumn(tCol.getColumn(tCol.getColumnIndex("Id")));
                            limpiar();
                            JOptionPane.showMessageDialog(asoc, "Éxito: Nuevo registro agregado.");
                        } else {
                            JOptionPane.showMessageDialog(asoc, "Error: Datos mal introducidos.");
                        }
                    }
                }
                break;

            case __LIMPIAR:
                //limpiamos los cajones
                limpiar();
                break;

            case __VOLVER:
                //cerramos la ventana
                this.asoc.dispose();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //indico que estoy clicando en la tabla futbolistas
        if (me.getSource() == this.asoc.tablaFutbolistas) {
            //indico que estoy cilicando con el clic izquierdo
            if (me.getButton() == 1) {
                int fila = this.asoc.tablaFutbolistas.rowAtPoint(me.getPoint());
                if (fila > -1) {
                    //asigno a los campos de texto el valor de la columna 0 y 2
                    this.asoc.txtIdFut.setText(String.valueOf(this.asoc.tablaFutbolistas.getModel().getValueAt(fila, 0)));
                    this.asoc.txtNomFut.setText(String.valueOf(this.asoc.tablaFutbolistas.getModel().getValueAt(fila, 2)));
                }
            }
        }
        //indico que estoy clicando en la lista clubs
        if (me.getSource() == this.asoc.listaClubs) {
            //indico que 
            if (me.getButton() == 1) {
                //asigno a los cajones el valor de la lista
                this.asoc.txtIdClub.setText(String.valueOf(this.mclub.getIdClub(this.asoc.listaClubs.getSelectedValue())));
                this.asoc.txtClub.setText(String.valueOf(this.asoc.listaClubs.getSelectedValue()));
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

    int limitetemporada = 4;
    TableRowSorter trs = null;

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getSource() == this.asoc.txtFiltroFut) {
            //creo un rowsorter con el modelo de la tabla
            trs = new TableRowSorter(this.asoc.tablaFutbolistas.getModel());
            //le asigno ese row sorter a la tabla
            this.asoc.tablaFutbolistas.setRowSorter(trs);
        }

        if (ke.getSource() == this.asoc.txtTemporada) {
            if (Character.isLetter(ke.getKeyChar())
                    || this.asoc.txtTemporada.getText().length() == limitetemporada) {
                ke.consume();
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //le damos el source solo a este campo de texto
        if (ke.getSource() == this.asoc.txtFiltroFut) {
            //realizo el filtro de la columna deseada
            trs.setRowFilter(RowFilter.regexFilter("(?i)" + this.asoc.txtFiltroFut.getText(), 1, 2, 3, 4, 5));
        }

    }

    /**
     * Metodo para vaciar los campos
     */
    public void limpiar() {
        this.asoc.txtIdClub.setText("");
        this.asoc.txtTemporada.setText("");
        this.asoc.txtNomFut.setText("");
        this.asoc.txtIdFut.setText("");
        this.asoc.txtClub.setText("");
    }
}
