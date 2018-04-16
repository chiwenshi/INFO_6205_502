
# Knapsack Problem
> The knapsack problem is a problem in combinatorial optimization: Given a set of items, each with a weight and a value, determine the number of each item to include in a collection so that the total weight is less than or equal to a given limit and the total weight is less than or equal to a given limit and the total value is as large as possible. In other words, we are trying to fill a fixed-size knapsack with the most valuable items. 

## Genotype
> In this problem we use the boolean array as the genotype. The length of the array is the number of the object read by the project at first. 

## Phenotype
> The boolean array represent each object is or not in the bag. For example, if the array[0] is true, it means that the first object put into the bag. On the contrary, if array[0] is false, it means that the first object doesn't put into the bag.

## Fintness Fuction
> According to the knapsack problem, it wants to output the max profit of the objects in the bag. So the fitness function is the sum of the object's profit which is in the bag.

## The logic of the evolution steps
### Initpopulation
> The population is a two-dimensional array. For example population[i][j], i represent it is which individual. In this project, we set a parameter called scale to define the size of the population. The rule to generate a population is that generate an individual until the number of the individual equals the scale. The rule to generate an individual is that insert the object into the original individual. The original individual is an array that all the elements are false. So using a random number generator to generate a number smaller than the number of the objects then set the value to true. Below is the code:

    public void initPopulation() {  
        fitness = new float[scale];  
        population = new boolean[scale][len];   
        for(int i = 0; i < scale; i++) {  
            float tmp = (float)(0.5 + Math.random()) * capacity;  
            int count = 0;    
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
> Specially, in order to avoid that insert a too large object at first and insert too many small objects into the bag we set the capacity from 0.5*capacity to 1.5*capacity.

### Calculate the fitness of each individual in the population
> The fitness function is the sum of all the objects' profit so it just to iterate all the individual and calculate the sum of each.
Below is the code:

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
    
    public void calcFitness() {  
        for(int i = 0; i < scale; i++) {  
            fitness[i] = evaluate(population[i]);  
        }  
    }
    
### Find the best individual which has the best fitness
> Find the individual whose fitness value is biggest. Below is the code:

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
    
### Select the best individuals
> selection method: bring the 10% individuals with the best fitness to the next loop, and then select the 90% of individuals from the newly generated population.Below is the code:

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
            } 
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
  
### Crossover 
> We define a parameter called irate to define the probability of the crossover. So we iterate every element of two individual if the random number is smaller than the irate it will exchange the two element in the two individuals.Below is the code:

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
    
### Mutation
> We define two parameters arate1 and arate2. Arate1 define the probability of the individual's mutation. Arate2  defines the probability of every single gen's mutation.Below is the code:

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
    
### The process of the evolution
> First initpopulation, then calculate the fitness and record the best fitness. Second, select the best individual. Third, intersect and mutate. Repeat those processes until it arrives the maximum generation.Below is the code:

     public void solve() {  
        readDate();  
        initPopulation();  
        for(int i = 0; i < maxgen; i++) {  
            //calculate the fitness  
            calcFitness();  
            //record the best individual 
            recBest(i); 
            System.out.println("The"+i+"generation's bestfitness"+"total profit"+evaluate(bestUnit)+"total weight" +evaluateweight(bestUnit));
            //to select population 
            select();  
            //crossover  
            intersect();  
           //mutation  
            aberra();  
        }  
    }
## UI
> URL:https://webapp-180415180624.azurewebsites.net/
