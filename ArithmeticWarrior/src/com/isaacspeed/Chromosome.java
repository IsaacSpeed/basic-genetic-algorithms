package com.isaacspeed;

/**
 * Created by Isaac Speed on 6/23/2016.
 * A chromosome for a simple genetic algorithm to find an arithmetic representation of a number
 */
import java.util.Random;

class Chromosome {
    public static final int CHROMOSOME_LENGTH = 9;
    public static final double MUTATION_RATE = 0.01;
    public static final double CROSSOVER_RATE = 0.7;
    private Gene[] genes;
    private Random randGenerator;

    public Chromosome() {
        randGenerator = new Random();
        genes = new Gene[CHROMOSOME_LENGTH];

        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (i % 2 == 1) {
                genes[i] = new Gene(true, randGenerator);
            } else {
                genes[i] = new Gene(false, randGenerator);
            }
        }
    }

    public Chromosome(Chromosome parent) {
        randGenerator = new Random();
        genes = new Gene[CHROMOSOME_LENGTH];

        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            genes[i] = new Gene(parent.genes[i]);
        }
    }

    public Chromosome breedOne(Chromosome other) {
        return breed(other)[0];
    }

    public Chromosome[] breed(Chromosome other) {
        int position = randGenerator.nextInt(9) + 1;
        float crossoverChance = randGenerator.nextFloat();

        Chromosome[] children = {new Chromosome(this), new Chromosome(other)};

        if (crossoverChance > (1 - CROSSOVER_RATE)) {
            children[0].swap(children[1], position);
        }

        children[0].maybeMutate();
        children[1].maybeMutate();

        return children;
    }

    public double getFitnessScore(double targetValue) {
        double myValue = evaluate();

        double denominator = targetValue - myValue;

        return 1.0 / denominator;
    }

    public double evaluate() {
        double value;
        /*
        The previously found operator
        + = 1
        - = 2
        * = 3
        / = 4
         */
        int previousOperator = 0;
        int previousValue;
        value = Integer.parseInt(genes[0].toString());

        for (int i = 1; i < CHROMOSOME_LENGTH; i++) {
            switch (genes[i].getCharValue()) {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    previousValue = Integer.parseInt(genes[i].toString());
                    break;
                case '+':
                    previousOperator = 1;
                    previousValue = 0;
                    break;
                case '-':
                    previousOperator = 2;
                    previousValue = 0;
                    break;
                case '*':
                    previousOperator = 3;
                    previousValue = 0;
                    break;
                case '/':
                    previousOperator = 4;
                    previousValue = 0;
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            // only do the thing if we just hit a number
            if (previousValue > 0) {
                switch(previousOperator) {
                    case 0:
                        value = 125315;
                        break;
                    case 1:
                        value += previousValue;
                        break;
                    case 2:
                        value -= previousValue;
                        break;
                    case 3:
                        value *= previousValue;
                        break;
                    case 4:
                        value /= previousValue;
                        break;
                }
            }
        }

        return value;
    }

    public boolean isValid() {
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (!genes[i].isValid()) return false;

            if (i % 2 == 1) {
                if (!genes[i].isOperator()) return false;
            } else {
                if (genes[i].isOperator()) return false;
            }
        }

        return true;
    }

    public void maybeMutate() {
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            double chance =randGenerator.nextFloat();
            if (chance > (1 - MUTATION_RATE)) genes[i].mutate();
        }
    }

    public void swap(Chromosome other, int position) {
        if (position > CHROMOSOME_LENGTH) throw new IllegalArgumentException();

        // make a temporary deep copy of my genes
        Gene[] myNewGenes = new Gene[CHROMOSOME_LENGTH];
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            myNewGenes[i] = new Gene(genes[i]);
        }

        // swap their genes to me up to position
        for (int i = 0; i < position; i++) {
            myNewGenes[i] = other.genes[i];
        }

        // swap my genes to them, up to position
        for (int i = 0; i < position; i++) {
            other.genes[i] = genes[i];
        }

        genes = myNewGenes;
    }

    public String toString() {
        String strValue = "";

        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            Gene gene = genes[i];

            strValue = strValue + gene.getCharValue();
        }

        return strValue;
    }
}