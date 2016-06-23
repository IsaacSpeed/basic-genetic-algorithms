package com.isaacspeed;

public class Main {

    public static void main(String[] args) {
        GeneticAlgorithm genAlg = new GeneticAlgorithm(40, 41.6);

        while (genAlg.getSolution() == null) {
            genAlg.printPopulation();
            //genAlg.printPopulationCSV();
            genAlg.nextGeneration();
        }

        System.out.println("Found solution! " + genAlg.getSolution());
    }
}
