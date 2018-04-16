/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 84690
 */
public class ReaderTest {
    private Reader read;
    public ReaderTest() {
    }

    /**
     * Test of read method, of class Reader.
     */
    @Test
    public void testRead() {
        File file = new File("src/Main/test.txt");  
        List<Object> data = read.read(file);  
        float[] weight = (float[])data.get(0);  
        float[] profit = (float[])data.get(1);  
        assertTrue(weight[0]==1.0f);  
        assertTrue(weight[1]==3.0f);
        assertTrue(weight[2]==5.0f);
        assertTrue(profit[0]==2.0f);
        assertTrue(profit[1]==4.0f);
        assertTrue(profit[2]==6.0f);
    }
}
