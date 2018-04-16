package solution.pack;

import java.io.File;  
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.List;  
import java.util.Random;  


public class Knapsack {
	public ArrayList<ResultInfo> list = new ArrayList<ResultInfo>();
    private Random random = null;   
  
    private float[] weight = null; 
    //every item has two properties, this is the weight of every item
    
    private float[] profit = null; //the price of every item 
    private int len; // the length of chromosomes
  
    private float capacity; //the capacity of knapsack 
    private int scale; //size of the population 
    private int maxgen; //maximum generation 
    
    private float irate; 
    //rate of crossover(this means when crossover happens, the rate of crossover of every digit of gene)
    
    private float arate1; 
    //rate of mutation(rate of mutation of every individual)
    
    private float arate2; 
    //rate of crossover of every digit of gene(for those individuals who is sure to have mutation)
    
    private File data = null; //the file storing the data of items(includes the weight and price of every item)  
  
    boolean[][] population = null; //the last generation of the population  
    float[] fitness = null; //fitness of the population  
  
    float bestFitness; //the price of the item who has the best fitness  
    private boolean[] bestUnit = null; //the strategy of how to choose the best item  
  
    class SortFitness implements Comparable<SortFitness>{  
        int index;  
        float fitness;  
        public int compareTo(SortFitness c) {  
            float cfitness = c.fitness;  
            if(fitness > cfitness) {  
                return -1;  
            } else if(fitness < cfitness) {  
                return 1;  
            } else {  
                return 0;  
            }  
        }  
    }  
  
    /** 
     * @param capacity : capacity of the knapsack
     * @param scale ： size of the population 
     * @param maxgen ： maximum generation 
     * @param irate ： rate of crossover(this means when crossover happens, the rate of crossover of every digit of gene) 
     * @param arate1 ：rate of mutation(rate of mutation of every individual)
     * @param arate2 ：rate of crossover of every digit of gene(for those individuals who is sure to have mutation)
     * @param file : the file storing the data of items(includes the weight and price of every item)
     */  
    public Knapsack(float capacity, int scale, int maxgen, float irate, float arate1, float arate2, File data) {  
        this.capacity = capacity;  
        this.scale = scale;  
        this.maxgen = maxgen;  
        this.irate = irate;  
        this.arate1 = arate1;  
        this.arate2 = arate2;  
        this.data = data;  
        random = new Random(System.currentTimeMillis());  
    }  
  
    //read the weight and profit information of every item
    public void readDate() {  
        List<Object> tmp = Reader.read(data);  
        weight = (float[])tmp.get(0);  
        profit = (float[])tmp.get(1);  
        len = weight.length;  
    }  
  
    //initialize the population  
    public void initPopulation() {  
        fitness = new float[scale];  
        population = new boolean[scale][len];  
        //Taking the shortcomings of the randomly initialization into account, here we have some optimization
        //For each individual, randomly generate a capacity value(between 0.5*capacity and 1.5*capacity)
        //and then randomly put every item into the knapsack until it exceeds the capacity of the knapsack 
        for(int i = 0; i < scale; i++) {  
            float tmp = (float)(0.5 + Math.random()) * capacity;  
            int count = 0; //in case of the heavy comsumption of computer resource by initialization 
            for(int j = 0; j < tmp;) {  
                int k = random.nextInt(len);  
                if(population[i][k]) {  
                    if(count == 3) {  
                        break;  
                    }  
                    count++;  
                    continue;  
                } else {  
                    population[i][k] = true;  
                    j += weight[k];  
                    count = 0;  
                }  
            }  
        }  
    }  
  
    //evaluate the fitness of each individual  
    private float evaluate(boolean[] unit) {  
        float profitSum = 0;  
        float weightSum = 0;  
        for (int i = 0; i < unit.length; i++) {  
            if (unit[i]) {  
                weightSum += weight[i];  
                profitSum += profit[i];  
            }  
        }  
        if (weightSum > capacity) {  
            //the weight of all items exceeds the capacity of the knapsack 
            return 0;  
        } else {  
            return profitSum;  
        }  
    }  
    private float evaluateweight(boolean[] unit){
        float weightSum = 0;
        for(int i = 0; i < unit.length; i++){
            if(unit[i]){
                weightSum += weight[i];
            }
        }
        if(weightSum > capacity){
            return 0;
        }
        else{
            return weightSum;
        }
    } 
  
    //calculate the fitness of each individual in the population  
    public void calcFitness() {  
        for(int i = 0; i < scale; i++) {  
            fitness[i] = evaluate(population[i]);  
        }  
    }  
  
    //to find the best individual which has the best fitness  
    public void recBest(int gen) {  
        for(int i = 0; i < scale; i++) {  
            if(fitness[i] > bestFitness) {  
                bestFitness = fitness[i];  
                bestUnit = new boolean[len];  
                for(int j = 0; j < len; j++) {  
                    bestUnit[j] = population[i][j];  
                }   
            }  
        }  
    }  
  
    //about how to select the best individuals  
    //selection method: bring the 10% individuals with the best fitness to the next loop, and then
    //select the 90% of individuals from the newly generated population
    private void select() {  
        SortFitness[] sortFitness = new SortFitness[scale];  
        for(int i = 0; i < scale; i++) {  
            sortFitness[i] = new SortFitness();  
            sortFitness[i].index = i;  
            sortFitness[i].fitness = fitness[i];  
        }  
        Arrays.sort(sortFitness);  
  
        boolean[][] tmpPopulation = new boolean[scale][len];  
  
        //reserve the top 10% individuals with the best fitness  
        int reserve = (int)(scale * 0.1);  
        for(int i = 0; i < reserve; i++) {  
            for(int j = 0; j < len; j++) {  
                tmpPopulation[i][j] = population[sortFitness[i].index][j];  
            }  
            //generate individuals randomly  
            for(int j = 0; j < len; j++) {  
                population[sortFitness[i].index][j] = false;  
            }  
            float tmpc = (float)(0.5 + Math.random()) * capacity;  
            int count = 0;  
            for(int j = 0; j < tmpc;) {  
                int k = random.nextInt(len);  
                if(population[sortFitness[i].index][k]) {  
                    if(count == 3) {  
                        break;  
                    }  
                    count++;  
                    continue;  
                } else {  
                    population[sortFitness[i].index][k] = true;  
                    j += weight[k];  
                    count = 0;  
                }  
            }//  
        }  
  
        //select 90% of the population  
        List<Integer> list = new ArrayList<Integer>();  
        for(int i = 0; i < scale; i++) {  
            list.add(i);  
        }  
        for(int i = reserve; i < scale; i++) {  
            int selectid = list.remove((int)(list.size()*Math.random()));  
            for(int j = 0; j < len; j++) {  
                tmpPopulation[i][j] = population[selectid][j];  
            }  
        }  
        population = tmpPopulation;  
    }  
  
    //crossover  
    private void intersect() {  
        for(int i = 0; i < scale; i = i + 2)  
            for(int j = 0; j < len; j++) {  
                if(Math.random() < irate) {  
                    boolean tmp = population[i][j];  
                    population[i][j] = population[i + 1][j];  
                    population[i + 1][j] = tmp;  
                }  
            }  
    }  
  
    //mutation
    private void aberra() {  
        for(int i = 0; i < scale; i++) {  
            if(Math.random() > arate1) {  
                continue;  
            }  
            for(int j = 0; j < len; j++) {  
                if(Math.random() < arate2) {  
                    population[i][j] = Math.random() > 0.5 ? true : false;  
                }  
            }  
        }  
    }  
    
    //Convert ArrayList in Java into array in JS
//    public static String toJavascriptArray(String[] arr){
//        StringBuffer sb = new StringBuffer();
//        sb.append("[");
//        for(int i=0; i<arr.length; i++){
//            sb.append("\"").append(arr[i]).append("\"");
//            if(i+1 < arr.length){
//                sb.append(",");
//            }
//        }
//        sb.append("]");
//        return sb.toString();
//    }
      
    //Genetic Algorithm  
    public void solve() {  
        readDate();  
        initPopulation();  
//        ArrayList<ResultInfo> list = new ArrayList<ResultInfo>();
        
        for(int i = 0; i < maxgen; i++) {  
            //calculate the fitness 
            calcFitness();  
            //record the best individual  
            recBest(i); 
//            System.out.println("The best unit of the "+i+" generation "+
//            " Total Profit: "+evaluate(bestUnit)+" Total Weight: " +evaluateweight(bestUnit));
            
            list.add(new ResultInfo(i,evaluate(bestUnit),evaluateweight(bestUnit)));
            
            //to select population  
            select();  
            //crossover  
            intersect();  
            //mutation 
            aberra();  
        }  
          
        int totalWeight = 0;   
        for(int i = 0; i < bestUnit.length; i++) {  
            if(bestUnit[i]){  
                totalWeight += weight[i];  
            }  
        }  
        System.out.println("total profit:" + bestFitness);  
        System.out.println("total weight:" + totalWeight);  
    }  
      
//    public static void main(String[] args) {  
//        File data = new File("src/main/java/solution/pack/data.txt");  
//        //capacity of the knapsack 
//        //size of the population
//        //maximum generation 
//        //rate of crossover(this means when crossover happens, the rate of crossover of every digit of gene)  
//        //rate of mutation(rate of mutation of every individual)  
//        //rate of crossover of every digit of gene(for those individuals who is sure to have mutation) 
//        //the file storing the weight and price information of every item  
//        Knapsack gaKnapsack = new Knapsack(2000, 200, 200, 0.5f, 0.05f, 0.1f, data);  
//        gaKnapsack.solve();  
//    }  
}