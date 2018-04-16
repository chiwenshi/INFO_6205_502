package solution.pack;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import org.junit.Test;

public class KnapsackTest {

	private Knapsack gaKnapsack;
    public KnapsackTest() {
       File data = new File("src/main/java/solution/pack/test.txt");
       gaKnapsack = new Knapsack(2000, 2000, 200, 0.5f, 0.05f, 0.1f, data); 
    }
    
    /**
     * Test of solve method, of class Knapsack.
     */
   @Test
    public void testcalcFitness() {
       Map<Float,Integer> map = new HashMap();
       gaKnapsack.readDate();
       gaKnapsack.initPopulation();
       gaKnapsack.calcFitness();
       for(int i =0; i < gaKnapsack.fitness.length; i++){
           if(map.containsKey(gaKnapsack.fitness[i])){
               map.put(gaKnapsack.fitness[i],map.get(gaKnapsack.fitness[i])+1);
           }
           else map.put(gaKnapsack.fitness[i],1);
       }
       assertTrue(map.containsKey(12.0f)&&map.containsKey(2.0f)&&map.containsKey(4.0f)&&map.containsKey(6.0f)&&map.containsKey(10.0f));
    }
    @Test
    public void testrecBest() {
       gaKnapsack.readDate();
       gaKnapsack.initPopulation();
       gaKnapsack.calcFitness();
       gaKnapsack.recBest(2000);
       assertTrue(gaKnapsack.bestFitness==12.0f);
       
    }
     @Test
    public void testintersect() {
       gaKnapsack.population = new boolean[][] {{true,true,true},{false,false,false}};
       for(int i = 0; i < 2; i = i + 2){  
            for(int j = 0; j < 3; j++) {  
                    boolean tmp = gaKnapsack.population[i][j];  
                    gaKnapsack.population[i][j] = gaKnapsack.population[i + 1][j];  
                    gaKnapsack.population[i + 1][j] = tmp;    
            } 
       }
       for(int i = 0; i < 3; i++){
           assertTrue(gaKnapsack.population[1][i]);
       }
       for(int i = 0; i < 3; i++){
           assertFalse(gaKnapsack.population[0][i]);
       }
    }
    @Test
    public void testsolve() {
       gaKnapsack.readDate();
       gaKnapsack.initPopulation();
       gaKnapsack.solve();
       assertTrue(gaKnapsack.bestFitness == 12.0f);
    }

}
