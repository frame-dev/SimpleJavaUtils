package ch.framedev.simplejavautils;

import java.util.PrimitiveIterator;
import java.util.Random;

public class CustomGenerators {


    /**
     * @param min the Min Value inclusive
     * @param max the Max Value inclusive
     * @return a Random Integer
     */
    public int randomInt(int min, int max) {
        return new IntRandomNumberGenerator(min, max).nextInt();
    }

    /**
     * @param min the Min Value inclusive
     * @param max the Max Value inclusive
     * @return a Random Double
     */
    public double randomDouble(double min, double max) {
        return new DoubleRandomNumberGenerator(min, max).nextDouble();
    }

    public static final class IntRandomNumberGenerator {

        private PrimitiveIterator.OfInt randomIterator;

        private int min;
        private int max;

        public IntRandomNumberGenerator() {
        }

        public IntRandomNumberGenerator setMin(int min) {
            this.min = min;
            return this;
        }

        public IntRandomNumberGenerator setMax(int max) {
            this.max = max;
            return this;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        /**
         * Initialize a new random number generator that generates as Integer random
         * numbers in the range [min, max]
         *
         * @param min - the min value (inclusive)
         * @param max - the max value (inclusive)
         */
        public IntRandomNumberGenerator(int min, int max) {
            randomIterator = new Random().ints(min, max + 1).iterator();
        }

        /**
         * Returns a random number in the range (min, max)
         *
         * @return a random number in the range (min, max)
         */
        public int nextInt() {
            if (randomIterator == null) randomIterator = new Random().ints(min, max + 1).iterator();
            return randomIterator.nextInt();
        }
    }

    public static final class DoubleRandomNumberGenerator {

        private PrimitiveIterator.OfDouble randomIterator;

        private double min;
        private double max;

        public DoubleRandomNumberGenerator() {
        }

        public DoubleRandomNumberGenerator setMin(double min) {
            this.min = min;
            return this;
        }

        public DoubleRandomNumberGenerator setMax(double max) {
            this.max = max;
            return this;
        }

        public double getMax() {
            return max;
        }

        public double getMin() {
            return min;
        }

        /**
         * Initialize a new random number generator that generates as Double random
         * numbers in the range [min, max]
         *
         * @param min - the min value (inclusive)
         * @param max - the max value (inclusive)
         */
        public DoubleRandomNumberGenerator(double min, double max) {
            randomIterator = new Random().doubles(min, max + 1).iterator();
        }

        /**
         * Returns a random number in the range (min, max)
         *
         * @return a random number in the range (min, max)
         */
        public double nextDouble() {
            if (randomIterator == null) randomIterator = new Random().doubles(min, max + 1).iterator();
            return randomIterator.nextDouble();
        }
    }
}
