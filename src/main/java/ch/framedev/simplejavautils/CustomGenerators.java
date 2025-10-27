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

        private static final int DEFAULT_MIN = 120;
        private static final int DEFAULT_MAX = 420;

        /**
         * Initializes a new instance of the {@code IntRandomNumberGenerator} class with defaults.
         */
        public IntRandomNumberGenerator() {
            this.min = DEFAULT_MIN;
            this.max = DEFAULT_MAX;
        }

        /**
         * Initializes a new random number generator that generates random integers within the specified range.
         *
         * @param min the minimum value inclusive
         * @param max the maximum value inclusive
         */
        public IntRandomNumberGenerator(int min, int max) {
            this.min = min;
            this.max = max;
            this.randomIterator = new Random().ints(min, max + 1).iterator();
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
         * Generates a random integer within the specified range.
         *
         * @return a random integer within the specified range
         */
        public int nextInt() {
            if (randomIterator == null) {
                randomIterator = new Random().ints(min, max + 1).iterator();
            }
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
            // keep defaults 0.0 .. 1.0 unless changed via setMin/setMax
            this.min = 0.0;
            this.max = 1.0;
        }

        /**
         * Initializes a new random number generator that generates random doubles within the specified range.
         *
         * @param min the minimum value inclusive
         * @param max the maximum value inclusive
         */
        public DoubleRandomNumberGenerator(double min, double max) {
            this.min = min;
            this.max = max;
            // use Math.nextUp(max) so that the upper bound can be effectively inclusive
            this.randomIterator = new Random().doubles(min, Math.nextUp(max)).iterator();
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
         * Generates a random double within the specified range.
         *
         * @return a random double within the specified range
         */
        public double nextDouble() {
            if (randomIterator == null) {
                randomIterator = new Random().doubles(min, Math.nextUp(max)).iterator();
            }
            return randomIterator.nextDouble();
        }
    }
}
