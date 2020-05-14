package Controlador;


import Vista.Portada;
import Vista.Ventanaprincipal;

/**
 *
 * @author Jose
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Controlador_ventanaprincipal(new Portada(), new Ventanaprincipal()).iniciar();
        
    }
    
}
