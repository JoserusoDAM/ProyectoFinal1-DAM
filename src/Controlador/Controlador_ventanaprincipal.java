package Controlador;

import Vista.Portada;
import Vista.TodosClubs;
import Vista.TodosFutbolistas;
import Vista.Ventanaprincipal;
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
public class Controlador_ventanaprincipal implements ActionListener, MouseListener {

    public static Ventanaprincipal ventana;
    public static Portada portada;

    public Controlador_ventanaprincipal(Portada portada, Ventanaprincipal ventana) {
        this.portada = portada;
        this.ventana = ventana;
    }

    public enum AccionMVC {
        __ENTRAR,
        __ENTRAR_CLUB,
        __ENTRAR_FUTBOLISTAS,
    }

    public void iniciar() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(portada);
            portada.setVisible(true);
        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        //declara una acción y añade un escucha al evento producido por el componente
        this.portada.btnEntrar.setActionCommand("__ENTRAR");
        this.portada.btnEntrar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.ventana.btnEntrarClub.setActionCommand("__ENTRAR_CLUB");
        this.ventana.btnEntrarClub.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.ventana.btnEntrarFutbolista.setActionCommand("__ENTRAR_FUTBOLISTAS");
        this.ventana.btnEntrarFutbolista.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (AccionMVC.valueOf(ae.getActionCommand())) {
            case __ENTRAR:
                this.portada.dispose();
                this.ventana.setVisible(true);
                break;

            case __ENTRAR_CLUB:
                this.ventana.dispose();
                new Controlador_Club ( new TodosClubs()).iniciar();
                break;

            case __ENTRAR_FUTBOLISTAS:
                this.ventana.dispose();
                new Controlador_Futbolista(new TodosFutbolistas()).iniciar();
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
