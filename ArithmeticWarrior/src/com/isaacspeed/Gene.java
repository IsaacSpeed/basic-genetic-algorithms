package com.isaacspeed;

import java.util.Random;

/**
 * Created by IS046078 on 6/23/2016.
 * Gene for chromosome
 */
public class Gene {
    byte geneByte;
    private Random randGenerator;

    public Gene(Random generator) {
        randGenerator = generator;
        geneByte = (byte) (randGenerator.nextInt(13) + 1);
        randGenerator = generator;
    }

    public Gene(Gene gene, boolean shouldMutate) {
        geneByte = gene.geneByte;
        randGenerator = gene.randGenerator;

        if (shouldMutate) mutate();
    }

    public Gene(boolean isOperator, Random generator) {
        randGenerator = generator;

        if (isOperator) {
            geneByte = (byte) (randGenerator.nextInt(4) + 10);
        } else {
            geneByte = (byte) (randGenerator.nextInt(9) + 1);
        }
    }

    public Gene(byte gene, Random generator) {
        if (gene > 13 || gene < 1) {
            throw new IllegalArgumentException();
        } else {
            geneByte = gene;
        }
        randGenerator = generator;
    }

    public Gene(Gene gene) {
        this.geneByte = gene.geneByte;
        this.randGenerator = gene.randGenerator;
    }

    public void mutate() {
        byte position = (byte) randGenerator.nextInt(4);

        // if the bit is zero, make it a one
        if (((geneByte >> position) & 1) == 1) {
            geneByte = (byte) (geneByte & ~(1 << position));
        } else {
            geneByte = (byte) (geneByte | (1 << position));
        }
    }

    public boolean isValid() {
        return getCharValue() != 'x';
    }

    public boolean isOperator() {
        char value = getCharValue();

        switch (value) {
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }

    public String toString() {
        return "" + getCharValue();
    }

    public char getCharValue() {
        switch (geneByte) {
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            case 9:
                return '9';
            case 10:
                return '+';
            case 11:
                return '-';
            case 12:
                return '*';
            case 13:
                return '/';
            default:
                return 'x';
        }
    }
}