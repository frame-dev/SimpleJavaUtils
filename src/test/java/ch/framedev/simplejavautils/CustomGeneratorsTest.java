package ch.framedev.simplejavautils;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomGeneratorsTest {

    @Test
    public void testRandomInt() {
        CustomGenerators generators = new CustomGenerators();
        int min = 10;
        int max = 20;
        for (int i = 0; i < 100; i++) {
            int randomInt = generators.randomInt(min, max);
            assertTrue("Random integer is out of bounds", randomInt >= min && randomInt <= max);
        }
    }

    @Test
    public void testRandomDouble() {
        CustomGenerators generators = new CustomGenerators();
        double min = 5.5;
        double max = 15.5;
        for (int i = 0; i < 100; i++) {
            double randomDouble = generators.randomDouble(min, max);
            assertTrue("Random double is out of bounds", randomDouble >= min && randomDouble <= max);
        }
    }

    @Test
    public void testIntRandomNumberGenerator() {
        CustomGenerators.IntRandomNumberGenerator intGen = new CustomGenerators.IntRandomNumberGenerator()
                .setMin(1)
                .setMax(100);
        for (int i = 0; i < 100; i++) {
            int randomInt = intGen.nextInt();
            assertTrue("Random integer is out of bounds", randomInt >= 1 && randomInt <= 100);
        }
    }

    @Test
    public void testDoubleRandomNumberGenerator() {
        CustomGenerators.DoubleRandomNumberGenerator doubleGen = new CustomGenerators.DoubleRandomNumberGenerator()
                .setMin(1.0)
                .setMax(100.0);
        for (int i = 0; i < 100; i++) {
            double randomDouble = doubleGen.nextDouble();
            assertTrue("Random double is out of bounds", randomDouble >= 1.0 && randomDouble <= 100.0);
        }
    }

    @Test
    public void testIntRandomNumberGeneratorDefaults() {
        CustomGenerators.IntRandomNumberGenerator intGen = new CustomGenerators.IntRandomNumberGenerator();
        for (int i = 0; i < 100; i++) {
            int randomInt = intGen.nextInt();
            assertTrue("Random integer is out of default bounds", randomInt >= 120 && randomInt <= 420);
        }
    }

    @Test
    public void getters() {
        CustomGenerators.IntRandomNumberGenerator intGen = new CustomGenerators.IntRandomNumberGenerator()
                .setMin(5)
                .setMax(15);
        assertEquals(5, intGen.getMin());
        assertEquals(15, intGen.getMax());

        CustomGenerators.DoubleRandomNumberGenerator doubleGen = new CustomGenerators.DoubleRandomNumberGenerator()
                .setMin(2.5)
                .setMax(7.5);
        assertEquals(2.5, doubleGen.getMin(), 0.0001);
        assertEquals(7.5, doubleGen.getMax(), 0.0001);
    }
}