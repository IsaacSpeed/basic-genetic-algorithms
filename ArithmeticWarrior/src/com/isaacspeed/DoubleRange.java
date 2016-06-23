package com.isaacspeed;

/**
 * Created by IS046078 on 6/23/2016.
 */
public class DoubleRange {
    double lowerBound;
    double upperBound;

    public DoubleRange(double begin, double end) {
        lowerBound = begin;
        upperBound = end;
    }

    public boolean containsDouble(double num) {
        return (num >= lowerBound && num < upperBound);
    }
}
