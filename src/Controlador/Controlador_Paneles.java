package Controlador;

import Vista.Panel_principal;
import java.awt.Color;
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
public class Controlador_Paneles implements ActionListener,MouseListener {

    /**Instancia nuestro panel principal */
    Panel_principal vista;
    
    /** Constrcutor de clase
     * @param vista Instancia de clase Panel_principal
     */
    public Controlador_Paneles (Panel_principal vista) {
        this.vista = vista;
    }
    
    /** Inicia el skin y las diferentes variables que se utilizan */
    public void iniciar () {
    
         // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
        } catch (UnsupportedLookAndFeelException ex) {}
          catch (ClassNotFoundException ex) {}
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}
    
        
        
        
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        if (me.getSource() == this.vista.pInicio) {
            this.vista.panelInicio.setVisible(true);
            this.vista.panelClubs.setVisible(false);
            this.vista.panelFutbolistas.setVisible(false);
            this.vista.panelAsociar.setVisible(false);
        }
        
        if (me.getSource() == this.vista.pClubs) {
            this.vista.panelInicio.setVisible(false);
            this.vista.panelClubs.setVisible(true);
            this.vista.panelFutbolistas.setVisible(false);
            this.vista.panelAsociar.setVisible(false);
        }
        
        if (me.getSource() == this.vista.pFutbolistas) {
            this.vista.panelInicio.setVisible(false);
            this.vista.panelClubs.setVisible(false);
            this.vista.panelFutbolistas.setVisible(true);
            this.vista.panelAsociar.setVisible(false);
        }
        
        if (me.getSource() == this.vista.pAsociar) {
            this.vista.panelInicio.setVisible(false);
            this.vista.panelClubs.setVisible(false);
            this.vista.panelFutbolistas.setVisible(false);
            this.vista.panelAsociar.setVisible(true);
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
        if (me.getSource () == this.vista.pInicio) {
            this.vista.pInicio.setBackground (new Color (135,135,135));
        }
        
        if (me.getSource () == this.vista.pClubs) {
            this.vista.pClubs.setBackground (new Color (135,135,135));
        }
        
        if (me.getSource () == this.vista.pFutbolistas) {
            this.vista.pFutbolistas.setBackground (new Color (135,135,135));
        }
        
        if (me.getSource () == this.vista.pAsociar) {
            this.vista.pAsociar.setBackground (new Color (135,135,135));
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
        if (me.getSource () == this.vista.pInicio) {
            this.vista.pInicio.setBackground (new Color (0,0,255));
        }
        if (me.getSource () == this.vista.pClubs) {
            this.vista.pClubs.setBackground (new Color (0,0,255));
        }
        if (me.getSource () == this.vista.pFutbolistas) {
            this.vista.pFutbolistas.setBackground (new Color (0,0,255));
        }
        if (me.getSource () == this.vista.pAsociar) {
            this.vista.pAsociar.setBackground (new Color (0,0,255));
        }
        
    }
    
  
}
