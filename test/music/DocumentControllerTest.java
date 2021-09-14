/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author root
 */
public class DocumentControllerTest {
    
    public DocumentControllerTest() {
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

    /**
     * Test of hide method, of class DocumentController.
     */
    @Test
    public void testHide() {
        System.out.println("hide");
        DocumentController instance = new DocumentController();
        instance.hide();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fees method, of class DocumentController.
     */
    @Test
    public void testFees() {
        System.out.println("fees");
        DocumentController instance = new DocumentController();
        instance.fees();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of search method, of class DocumentController.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        ActionEvent event = null;
        DocumentController instance = new DocumentController();
        instance.search(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextFD method, of class DocumentController.
     */
    @Test
    public void testNextFD() throws Exception {
        System.out.println("nextFD");
        DocumentController instance = new DocumentController();
        Date expResult = null;
        Date result = instance.nextFD();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class DocumentController.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        DocumentController instance = new DocumentController();
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class DocumentController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        DocumentController instance = new DocumentController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
