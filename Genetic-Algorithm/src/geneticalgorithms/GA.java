package geneticalgorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GA {

    /*
   Author: Flynn Bastin
    *intialise each population and set the file location to read and write into
    *also the main class that calculates the average and max fitness of an individual.
     */
    public static void main(String[] args) {
        Individual rules = new Individual();
        final int popsize = 50, numberofrules = rules.getNumberofrules(), data = 64, maxGen = 150;
        Calculate calc = new Calculate();
        String dataFile = "data2.txt";//data1
        String filename = "F:Genetic-Algorithm\\src\\geneticalgorithms\\datatxt2.txt";
        Population pop = new Population(popsize, numberofrules);
        String[] dataset = calc.grabResource(dataFile, data);
        int maxIndividualfitness = 0, maxfitness = 0, average = 1;
        Calculate.getFitness(numberofrules, pop.individual, dataset);
        //System.out.println(Arrays.toString(dataset));
        FileWriter fw;
        BufferedWriter bw;
        PrintWriter out;
        String a;
        int genCount = 0;
        while (genCount != maxGen) {//while gen count is not reached
            pop = Calculate.evolvepop(pop); // evolve the population
            Calculate.getFitness(numberofrules, pop.individual, dataset); // check fitness
            for (Individual individual : pop.individual) {
                average = Calculate.getSumofPop(pop) / pop.popsize; //calc the average
                maxIndividualfitness = getMaxIndividualFitness(individual, maxIndividualfitness); // calc the max individuals fitness
                maxfitness = getMaxFitness(pop, maxfitness); //calc max pop fitness
                                //   System.out.println(individual.toString());

            }
            try { // file writing 
                a = maxIndividualfitness + "," + average;
                fw = new FileWriter(filename, true);
                bw = new BufferedWriter(fw);
                out = new PrintWriter(bw);
                out.println(a);
                out.close();
            } catch (IOException e) {
                System.out.println("nowork");
            }
            genCount++;
            System.out.println("__Generations Completed: " + genCount);
            System.out.println("Total fitness:" + Calculate.getSumofPop(pop));
            System.out.println("Max fitness: " + maxfitness);
            System.out.println("Max Individual Fitness: " + maxIndividualfitness);
            System.out.println("_Average fitness " + average);

            if (genCount == maxGen) {// prints out the last population
                System.out.println("Completed");
                for (Individual individual : pop.individual) {
                    System.out.println(individual.toString());
                }
            }
        }
    } // Main     
//function for get max pop fitness and max individual fitness
    private static int getMaxFitness(Population pop, int maxfitness) {
        if (Calculate.getSumofPop(pop) > maxfitness) {
            maxfitness = Calculate.getSumofPop(pop);
        }
        return maxfitness;
    }

    private static int getMaxIndividualFitness(Individual individual, int maxIndividualfitness) {
        if (individual.getFitness() > maxIndividualfitness) {
            maxIndividualfitness = individual.getFitness();

        }
        return maxIndividualfitness;
    }
} // Class
