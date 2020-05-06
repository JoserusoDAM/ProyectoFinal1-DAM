package Controlador;

import Modelo.DAO.Modelo_FutbolistasDAO;
import Modelo.DAO.Modelo_HistoricoDAO;
import Vista.Asociar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Jose
 */
public class Controlador_Historico implements ActionListener, MouseListener {

    public Asociar asoc;
    Modelo_HistoricoDAO mHisto = new Modelo_HistoricoDAO();
    Modelo_FutbolistasDAO mfutb = new Modelo_FutbolistasDAO();
    
    public Controlador_Historico (Asociar asoc) {
        this.asoc = asoc;
    }
    
    public enum AccionMVC {
        __BUSCADOR,
        __ASOCIAR,
        __TEMPORADA,
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
        this.asoc.btnBuscarAsc.setActionCommand("__BUSCADOR");
        this.asoc.btnBuscarAsc.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.btnAsociar.setActionCommand("__ASOCIAR");
        this.asoc.btnAsociar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.comboTemporada.setActionCommand("__TEMPORADA");
        this.asoc.comboTemporada.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.btnVolver.setActionCommand("__VOLVER");
        this.asoc.btnVolver.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.asoc.btnLimpiar.setActionCommand("__LIMPIAR");
        this.asoc.btnLimpiar.addActionListener(this);
        
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
    
        switch (Controlador_Historico.AccionMVC.valueOf(ae.getActionCommand())) {
            case __BUSCADOR:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.asoc.tablaFutbolistas.setModel(this.mfutb.getTablaFutbolistas());
                break;
                
            case __LIMPIAR:
                this.asoc.txtNifFutb.setText("");
                
            case __VOLVER:
                this.asoc.dispose();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
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
