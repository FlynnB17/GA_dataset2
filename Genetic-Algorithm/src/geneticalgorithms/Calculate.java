/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithms;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Flynn
 */
public class Calculate {

    /* These are the rates that I've set for crossover and mutation, 
    the values that are commented next to them are alternate 
    values that worked well with my program*/
    final static double CROSSOVERRATE = 0.1;//0.85//0.315//375
    final static double MUTATIONRATE = 0.01;//0.007/

    /*The requirement for the fitness function for data set 1 only required to */
    public static void getFitness(int numberofrules, Individual[] individual, String[] dataset) {
        int fitness;
        String[] datasetcomp;
        char[] a, b;
        for (Individual individual1 : individual) {
            fitness = 0;
            datasetcomp = dataset;
            //  System.out.println(Arrays.toString(dataset));
            for (String dataset1 : datasetcomp) {
                for (String gene : individual1.gene) {
                    a = dataset1.toCharArray();
                    b = gene.toCharArray();
                    if (wildcards(a, b)) {
                        if (a[a.length - 1] == b[b.length - 1]) {
                            individual1.setFitness(fitness++);
                            //   System.out.println("matched a: " + Arrays.toString(a) + " to b: " + Arrays.toString(b));
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    /* This is the function where I evolve my population, using tournament selection to select individuals
    then using uniform crossover and mutation*/
    public static Population evolvepop(Population pop) {
        Population evolvedPopulation;
        evolvedPopulation = mutation(crossover(tournamentSelection(pop)));
        return evolvedPopulation;
    }

    /* The mutation class take a population does a check to see if it should mutate a gene if so mutate the gene
    then add it back to the population and continue searching for individuals to 
    mutate till it reaches the end of the population*/
    private static Population mutation(Population pop) {
        Random random = new Random();
        char[] arr;
        char[] comp;
        Population mut = pop;
        for (Individual individual : mut.individual) {
                                          

            for (int i = 0; i < individual.gene.length; i++) {
                arr = individual.gene[i].toCharArray();
                for (int j = 0; j < arr.length; j++) {
                    if (Math.random() <= MUTATIONRATE) {
                        comp = Integer.toString(random.nextInt(2)).toCharArray();
                        if (arr[j] == comp[comp.length - 1]) { // if the random generaed mutation is the same as the one thats in the gene then set it to wildcard
                            if (j == individual.gene[0].length()) { //but if its the 6th or 7th for data set 2 then mutate it to 0 or 1
                                comp = Integer.toString(random.nextInt(2)).toCharArray();
                                arr[j] = comp[comp.length - 1];
                            } else {
                                arr[j] = '2';
                            }

                        } else {
                            arr[j] = comp[comp.length - 1];
                        }
                        individual.gene[i] = String.valueOf(arr);
                    }
                }
            }
        }
        return mut;

    }

    /*Tournament selection was used because the data was not large enough to use a selection such as roulette wheel.
    I found that tournament was a better choice as the performance speaks for itself.
    After each generation completed there was a higher chance for stronger individuals to form as tournament 
    would randomnly selection 50 individuals most of which could have been (strong) duplicates and was improved upon after crossover and mutation    
     */
    private static Population tournamentSelection(Population population) {
        int parentidx1;
        int parentidx2;
        int r = population.popsize;
        Population offspring = population;
        Random random = new Random();
        for (Individual individual : offspring.individual) {
            parentidx1 = random.nextInt(r);
            parentidx2 = random.nextInt(r);
            if (population.individual[parentidx1].fitness >= population.individual[parentidx2].fitness) {
                System.arraycopy(population.individual[parentidx1].gene, 0, individual.gene, 0, individual.gene.length);
            } else {
                System.arraycopy(population.individual[parentidx2].gene, 0, individual.gene, 0, individual.gene.length);
            }
        }
        return offspring;
    }

    // Get optimum fitness
    static int getMaxFitness() {
        int maxFitness = 0;
        return maxFitness;
    }

    //takes a population and retuns a population that has been crossover 
    //This is an implementation of uniform crossover
    private static Population crossover(Population pop) {
        Population crossPop = pop;
        Individual offspring;
        char[] a, b;
        for (int i = 0; i < pop.popsize; i++) {
            for (int j = 1; j < pop.popsize; j++) {
                for (int k = 0; k < pop.individual[0].gene.length; k++) {
                    if (Math.random() <= CROSSOVERRATE) {
                        a = pop.individual[i].gene[k].toCharArray();
                        b = pop.individual[j].gene[k].toCharArray();
                        offspring = flipBits(a, b);
                        crossPop.individual[i].gene[k] = offspring.getGene(0);
                        crossPop.individual[j].gene[k] = offspring.getGene(1);
                    }
                }
            }
        }
        return crossPop;
    }
//returns two child genes after it gets called in crossover
    private static Individual flipBits(char[] arr, char[] arr2) {
        Random random = new Random();
        Individual offsping = new Individual();
        String a = "", b = "";
        char comp;
        int counter = 0;
        boolean tmp;
        // System.out.println("A BEFORE: "+Arrays.toString(arr));
        //         System.out.println("B BEFORE: "+Arrays.toString(arr2));

        for (int i = 0; i < arr.length; i++) {
            tmp = random.nextBoolean();
            if (tmp) {
                comp = arr[i];
                arr[i] = arr2[i];
                arr2[i] = comp;
            }
            a += String.valueOf(arr[i]);
            b += String.valueOf(arr2[i]);
            counter++;
        }
        if (counter == arr.length) {
            offsping.gene[0] = a;
            offsping.gene[1] = b;
        }
        // System.out.println("A AFTER: "+a);
        //        System.out.println("B AFTER: "+b);
        return offsping;
    }
// implementation of roulette wheel selection
    private static int rouletteWheelIndex(Population pop) {
        int sum = getSumofPop(pop);
        Random random = new Random();
        int randaf = random.nextInt((sum - 0) + 1) + 0;
        int partialSum = 0;

        for (int i = pop.popsize - 1; i >= 0; i--) {
            partialSum += getSumofPop(pop);
            if (partialSum >= randaf) {
                return i;
            }
        }
        return -1;
    }
//gets the sum of the population, required to calculate average fitness
    static int getSumofPop(Population pop) {
        int totalFitness = 0;
        for (int i = 0; i < pop.popsize; i++) {
            totalFitness += pop.individual[i].fitness;
        }
        return totalFitness;
    }

    /* 
   boolean function that takes in two char arrays 
and it values to match
   and return a true or false if it matches
     */
    static boolean wildcards(char[] arr, char[] arr2) {
        boolean a = false;
        char comp = '2';
        int counter = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == arr2[i] || comp == arr2[i]) {
                counter++;
            }
        }
        if (counter == arr.length - 1) {
            a = true;
        }
        return a;
    }
//grabs the data file and adds it into an array
    public String[] grabResource(String dataFile, int data) {
        String[] dataset = new String[data];
        Scanner sc = new Scanner(Calculate.class
                .getResourceAsStream(dataFile));
        //  System.out.println("file found");
        Pattern reg = Pattern.compile("[0-1]+.[0-1]");
        Matcher matcher = reg.matcher("");
        for (int i = 0; i < data;) {
            String next = sc.nextLine();

            //   System.out.println(next);
            matcher.reset(next);
            if (matcher.find()) {

                next = next.replaceAll("\\s+", "");
                //  System.out.println(next);
                dataset[i] = next;
                i++;

            }
            //System.out.println(dataset[i]);
        }

        return dataset;
    }

}
