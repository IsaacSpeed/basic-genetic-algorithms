package com.isaacspeed;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        GeneticAlgorithm genAlg = new GeneticAlgorithm(40, 158);

        while (genAlg.getSolution() == null) {
            genAlg.printPopulation();
            //genAlg.printPopulationCSV();
            genAlg.nextGeneration();
        }

        System.out.println("Found solution! " + genAlg.getSolution());
    }
}
