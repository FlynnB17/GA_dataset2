package geneticalgorithms;

import java.util.Arrays;

/**
 *
 * @author Flynn
 * object for the individual, with all the getters and setter and the tostring method
 */
public final class Individual {

    int numberofrules = 10;
    String[] gene;
    int fitness;

    public int getNumberofrules() {
        return numberofrules;
    }

    public void setNumberofrules(int numberofrules) {
        this.numberofrules = numberofrules;
    }

    public Individual() {
        gene = new String[numberofrules];
    }

    public String getGene(int i) {
        return gene[i];
    }

    public void setGene(String[] gene) {
        this.gene = gene;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int size() {
        return gene.length;
    }

    @Override
    public String toString() {
        return "Individual{" + ", gene=" + Arrays.toString(gene) + ", fitness=" + fitness + '}';
    }

}
