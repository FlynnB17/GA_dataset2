package geneticalgorithms;

import java.util.Random;

/*
 * @author Flynn
    Class used to create each population
 */
class Population {

    int popsize = 50;
    Individual[] individual = new Individual[popsize];

    public Population(int popsize, int numberOfRules) {
        Random random = new Random();
        //Creating the Population
        for (int i = 0; i < popsize; i++) {
            individual[i] = new Individual();
            for (int j = 0; j < numberOfRules; j++) {
                individual[i].gene[j] = "";
                for (int k = 0; k < 6; k++) {
                    individual[i].gene[j] += Integer.toString(random.nextInt(3));
                }
                individual[i].gene[j] += Integer.toString(random.nextInt(2));
            }
            individual[i].fitness = 0;
        }//Creating the Population
    }

    public void setIndividual(Individual[] individual) {
        this.individual = individual;
    }

    public int getPopsize() {
        return popsize;
    }

    public void setPopsize(int popsize) {
        this.popsize = popsize;
    }

}
