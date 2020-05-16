package JUnit;

import Modelo.DAO.Modelo_ClubDAO;
import Modelo.DAO.Modelo_FutbolistasDAO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Jose
 */
public class XYZT {
    
    
    public XYZT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    

    @Test
    public void testCreacion (){
        Modelo_ClubDAO club = new Modelo_ClubDAO();
        assertEquals(true, club.validarCreacion(2019) );
    }
    
    @Test
    public void validarFecha (){
        Modelo_FutbolistasDAO fut = new Modelo_FutbolistasDAO();
        assertEquals(true, fut.validarNif("77814742S"));
    }
    
}
