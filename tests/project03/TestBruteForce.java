package project03;
import org.junit.jupiter.api.*;

import project03.Main;
import project03.Main.Experiment;
/**
 * A test class that tests the functionality of the Bruteforce method
 * 
 * Team 03
 * Jasey Christensen, Thaddeus Schlep & Tristan Taj
 * CS 2430, section 002
 *  Programming Project 3: Optimal selection
 * 
 * @author Thaddeus
 */
public class TestBruteForce {
    /**
     * Typical case, ensures generally correct behavior.
     */
    @Test
    void hasCorrectOutput() {
        Main.Experiment[] expected = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.SOLAR_POWER
        };
        Main.Experiment[] input = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.SOLAR_FLARES,
                Main.Experiment.SOLAR_POWER
        };
        Assertions.assertArrayEquals(expected, Main.bruteForce(input, 250, 1)[0]);
    }

    /**
     * Ensures that no solutions cannot exceed the weight limit.
     */
    @Test
    void doesNotExceedWeightLimit() {
        for (Main.Experiment[] experimentSet : Main.bruteForce(Main.EXPERIMENTS, 700, 4096)) {
            int totalWeight = 0;
            if (experimentSet == null) continue;
            for (Main.Experiment experiment : experimentSet)
                totalWeight += experiment.weight();
            Assertions.assertFalse(totalWeight > 700);
        }

        for (Main.Experiment[] experimentSet : Main.bruteForce(Main.EXPERIMENTS, 234, 4096)) {
            int totalWeight = 0;
            if (experimentSet == null) continue;
            for (Main.Experiment experiment : experimentSet)
                totalWeight += experiment.weight();
            Assertions.assertFalse(totalWeight > 234);
        }
    }

    /**
     * Verifies that a solution which exactly matches the maximum weight can be generated.
     */
    @Test
    void canMatchWeightLimit() {
        Main.Experiment[] expected = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.SOLAR_POWER
        };
        int expectedWeight = Main.Experiment.CLOUD_PATTERNS.weight() + Main.Experiment.SOLAR_POWER.weight();
        Main.Experiment[] input = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.SOLAR_FLARES,
                Main.Experiment.SOLAR_POWER
        };
        Assertions.assertArrayEquals(expected, Main.bruteForce(input, expectedWeight, 1)[0]);
    }

    /**
     * Ensures that if the Primary Cosmic Rays experiment is not included, then the Secondary Cosmic Rays experiment is
     * also not included.
     */
    @Test
    void doesNotIncludeSecondaryCosmicIfNotIncludingPrimaryCosmic() {
        Main.Experiment[] expected = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
        };
        Main.Experiment[] input = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.COSMIC_RAYS_PRIMARY_BAND,
                Main.Experiment.COSMIC_RAYS_SECONDARY_BAND
        };
        int weight = Main.Experiment.COSMIC_RAYS_SECONDARY_BAND.weight() + Main.Experiment.CLOUD_PATTERNS.weight();
        Assertions.assertArrayEquals(expected, Main.bruteForce(input, weight, 1)[0]);
    }

    /**
     * Ensures that Solar Flares, Solar Power, and Sun Spots are not all included at the same time.
     */
    @Test
    void doesNotIncludeSolarFlaresSolarPowerAndSunSpots() {
        Main.Experiment[] expected = new Main.Experiment[]{
                Main.Experiment.SOLAR_FLARES,
                Main.Experiment.SOLAR_POWER,
        };
        Main.Experiment[] input = new Main.Experiment[]{
                Main.Experiment.SOLAR_FLARES,
                Main.Experiment.SOLAR_POWER,
                Main.Experiment.SUN_SPOTS
        };
        Assertions.assertArrayEquals(expected, Main.bruteForce(input, Integer.MAX_VALUE, 1)[0]);
    }

    /**
     * Ensures that the weight reduction of the Yeast Fermentation experiment is applied when Seed Viability is
     * included.
     */
    @Test
    void reducesYeastWeightIfIncludingSeed() {
        Main.Experiment[] expected = new Main.Experiment[]{
                Main.Experiment.SEED_VIABILITY,
                new Main.Experiment(Main.Experiment.YEAST_FERMENTATION.name(), 20, Main.Experiment.YEAST_FERMENTATION.rating()),
        };
        Main.Experiment[] input = new Main.Experiment[]{
                Main.Experiment.SEED_VIABILITY,
                Main.Experiment.YEAST_FERMENTATION
        };
        Assertions.assertArrayEquals(expected, Main.bruteForce(input, 27, 1)[0]);
    }
    
    
}
