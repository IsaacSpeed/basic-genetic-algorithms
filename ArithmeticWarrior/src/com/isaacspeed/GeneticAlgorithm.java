package com.isaacspeed;

import java.util.Random;

/**
 * Created by IS046078 on 6/23/2016.
 * A genetic algorithm class
 */
public class GeneticAlgorithm {
    private Chromosome[] population;
    private DoubleRange[] populationChances;
    private double targetValue;
    private static final double SOLUTION_THRESHOLD = 0.000001;
    int generation;
    private Chromosome solution = null;


    public GeneticAlgorithm(int populationSize, double target) {
        population = new Chromosome[populationSize];
        generation = 1;

        for (int i = 0; i < populationSize; i++) {
            population[i] = new Chromosome();
        }

        targetValue = target;
    }

    public void nextGeneration() {
        generation++;
        int newPopulationSize = 0;
        Random randGenerator = new Random();
        Chromosome[] newPopulation = new Chromosome[population.length];
        double maxIndex = setPopulationChances();

        while (newPopulationSize < population.length) {
            double indexOne = randGenerator.nextDouble() * maxIndex;
            double indexTwo = randGenerator.nextDouble() * maxIndex;
            Chromosome chromosomeOne = null;
            Chromosome chromosomeTwo = null;
            Chromosome newChromosome;

            int indexOneInt = binarySearchPopulationChances(indexOne, 0, populationChances.length);
            int indexTwoInt = binarySearchPopulationChances(indexTwo, 0, populationChances.length);
            chromosomeOne = population[indexOneInt];
            chromosomeTwo = population[indexTwoInt];

            if (chromosomeOne != null && chromosomeTwo != null) {
                newChromosome = chromosomeOne.breedOne(chromosomeTwo);

                if (newChromosome.isValid()) {
                    newPopulation[newPopulationSize] = newChromosome;

                    if (Math.abs(newChromosome.getFitnessScore(targetValue)) < SOLUTION_THRESHOLD) {
                        System.out.println("Found solution. " + newChromosome.evaluate() + " with " + newChromosome.getFitnessScore(targetValue));
                        solution = newChromosome;
                    }

                    newPopulationSize++;
                }
            }
        }

        population = newPopulation;

        for (Chromosome chromo : population) {
            if (!chromo.isValid()) System.out.println("aw crap");
        }
    }

    public void printPopulation() {
        System.out.println("Generation " + generation);
        for (Chromosome chromosome : population) {
            System.out.printf("Chromosome %s has a value of %.2f and a fitness score of %.4f.\n", chromosome, chromosome.evaluate(), chromosome.getFitnessScore(targetValue));
        }
        System.out.println();
    }

    private double setPopulationChances() {
        double currentValue = 0.0;
        int size = population.length;
        populationChances = new DoubleRange[size];

        for (int i = 0; i < size; i++) {
            // make absolute
            double fitnessScore = Math.abs(population[i].getFitnessScore(targetValue));

            populationChances[i] = new DoubleRange(currentValue, currentValue + fitnessScore);
            currentValue += fitnessScore;
        }

        return currentValue;
    }

    public Chromosome getSolution() {
        if (solution != null) {
            solution.getFitnessScore(targetValue);
        }
        return solution;
    }

    private int binarySearchPopulationChances(double key, int minIndex, int maxIndex) {
        int currentIndex = (maxIndex - minIndex) / 2 + minIndex;

        if (populationChances[currentIndex].containsDouble(key)) return currentIndex;

        if (currentIndex == 0)  {
            return -1;
        }

        if (populationChances[currentIndex].lowerBound > key) return binarySearchPopulationChances(key, minIndex, currentIndex);

        if (populationChances[currentIndex].upperBound < key) return binarySearchPopulationChances(key, currentIndex, maxIndex);

        return -1;
    }

    public void printPopulationCSV() {
        for (Chromosome chromo : population) {
            System.out.printf("%d, %f\n", generation, chromo.evaluate());
        }
    }
}
