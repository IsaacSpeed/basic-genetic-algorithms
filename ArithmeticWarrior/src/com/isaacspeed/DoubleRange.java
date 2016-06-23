package com.isaacspeed;

/**
 * Created by Isaac Speed on 6/23/2016.
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
