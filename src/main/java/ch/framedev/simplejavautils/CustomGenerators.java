package ch.framedev.simplejavautils;

import java.util.PrimitiveIterator;
import java.util.Random;

/**
 * A class that provides utility methods for generating random integers and doubles.
 */
public class CustomGenerators {

    /**
     * Generates a random integer within the specified range.
     *
     * @param min the minimum value inclusive
     * @param max the maximum value inclusive
     * @return a random integer within the specified range
     */
    public int randomInt(int min, int max) {
        return new IntRandomNumberGenerator(min, max).nextInt();
    }

    /**
     * Generates a random double within the specified range.
     *
     * @param min the minimum value inclusive
     * @param max the maximum value inclusive
     * @return a random double within the specified range
     */
    public double randomDouble(double min, double max) {
        return new DoubleRandomNumberGenerator(min, max).nextDouble();
    }

    /**
     * A nested class that generates random integers within a specified range.
     */
    public static final class IntRandomNumberGenerator {

        private PrimitiveIterator.OfInt randomIterator;

        private int min;
        private int max;

        /**
         * Initializes a new instance of the {@code IntRandomNumberGenerator} class.
         */
        public IntRandomNumberGenerator() {
        }

        /**
         * Sets the minimum value for the random integer generation.
         *
         * @param min the minimum value inclusive
         * @return this instance for method chaining
         */
        public IntRandomNumberGenerator setMin(int min) {
            this.min = min;
            return this;
        }

        /**
         * Sets the maximum value for the random integer generation.
         *
         * @param max the maximum value inclusive
         * @return this instance for method chaining
         */
        public IntRandomNumberGenerator setMax(int max) {
            this.max = max;
            return this;
        }

        /**
         * Gets the minimum value for the random integer generation.
         *
         * @return the minimum value inclusive
         */
        public int getMin() {
            return min;
        }

        /**
         * Gets the maximum value for the random integer generation.
         *
         * @return the maximum value inclusive
         */
        public int getMax() {
            return max;
        }

        /**
         * Initializes a new random number generator that generates random integers within the specified range.
         *
         * @param min the minimum value inclusive
         * @param max the maximum value inclusive
         */
        public IntRandomNumberGenerator(int min, int max) {
            randomIterator = new Random().ints(min, max + 1).iterator();
        }

        /**
         * Generates a random integer within the specified range.
         *
         * @return a random integer within the specified range
         */
        public int nextInt() {
            if (randomIterator == null) randomIterator = new Random().ints(min, max + 1).iterator();
            return randomIterator.nextInt();
        }
    }

    /**
     * A nested class that generates random doubles within a specified range.
     */
    public static final class DoubleRandomNumberGenerator {

        private PrimitiveIterator.OfDouble randomIterator;

        private double min;
        private double max;


        /**
         * Initializes a new instance of the {@code DoubleRandomNumberGenerator} class.
         */
        public DoubleRandomNumberGenerator() {
        }

        /**
         * Sets the minimum value for the random double generation.
         *
         * @param min the minimum value inclusive
         * @return this instance for method chaining
         */
        public DoubleRandomNumberGenerator setMin(double min) {
            this.min = min;
            return this;
        }

        /**
         * Sets the maximum value for the random double generation.
         *
         * @param max the maximum value inclusive
         * @return this instance for method chaining
         */
        public DoubleRandomNumberGenerator setMax(double max) {
            this.max = max;
            return this;
        }

        /**
         * Gets the minimum value for the random double generation.
         *
         * @return the minimum value inclusive
         */
        public double getMin() {
            return min;
        }

        /**
         * Gets the maximum value for the random double generation.
         *
         * @return the maximum value inclusive
         */
        public double getMax() {
            return max;
        }

        /**
         * Initializes a new random number generator that generates random doubles within the specified range.
         *
         * @param min the minimum value inclusive
         * @param max the maximum value inclusive
         */
        public DoubleRandomNumberGenerator(double min, double max) {
            randomIterator = new Random().doubles(min, max + 1).iterator();
        }

        /**
         * Generates a random double within the specified range.
         *
         * @return a random double within the specified range
         */
        public double nextDouble() {
            if (randomIterator == null) randomIterator = new Random().doubles(min, max + 1).iterator();
            return randomIterator.nextDouble();
        }
    }
}