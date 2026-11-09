package project03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project03.Main.Experiment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestGreedy {

    private Main mainApp;
/**
 * create an instance of the main class before each test
 *  
 * This is so that we can have each test have its own isolated occurrence of the class so that data messed with 
 * in one test does not effect a different test
 */
    @BeforeEach
    void setUp() {
        mainApp = new Main();
    }
    /**
     * Verifies that the greedy algorithm successfully selects and organizes payload items 
     * purely by ascending weight when provided a lowest-weight-first comparator.
     * 
     *   The results array must not be null or empty.
     *   The total weight of selected experiments must not exceed the 700kg payload cap.
     *   High-priority lightweight candidate experiments are reliably selected.
     * 
     * 
     */
    @Test
    @DisplayName("Should sort and pick experiments purely by lowest weight first")
    void testGreedyByWeight() {
    	// Create a comparator that prioritizes experiments with the smallest physical weight
        Comparator<Experiment> byWeight = Comparator.comparingInt(Experiment::weight);

        // Execute the greedy algorithm using the weight-based sorting strategy
        Experiment[] result = mainApp.greedy(byWeight);

        // Ensure the algorithm returned a valid, populated array of selections
        assertNotNull(result);
        assertTrue(result.length > 0);
        
        // Verify total weight constraint (<= 700) is honored
        int totalWeight = calculateTotalWeight(result);
        assertTrue(totalWeight <= 700, "Total weight exceeded 700: " + totalWeight);

        // Seed Viability (7) and Yeast Fermentation (27) should be at the front
        List<Experiment> list = Arrays.asList(result);
        assertTrue(list.contains(Experiment.SEED_VIABILITY));
        assertTrue(list.contains(Experiment.YEAST_FERMENTATION));
    }
    
    /**
     * Verifies that the greedy algorithm strictly enforces the maximum threshold rule 
     * for solar-related projects.
     * 
     *  Rule: No more than 2 solar experiments are allowed on the payload.
     * This test forcefully pushes solar items to the front of the queue to verify that 
     * the algorithm terminates solar selections once the ceiling is reached.
     */
    @Test
    @DisplayName("Should cap solar experiments to a maximum of 2")
    void testSolarExperimentLimit() {
    	// Create a comparator that artificially pushes all solar experiments to the top of the queue
        // e2 compared to e1 returns a positive integer if e2 is solar and e1 is not, reversing natural order
        Comparator<Experiment> prioritizeSolar = (e1, e2) -> Boolean.compare(e2.isSolar(), e1.isSolar());

        // Run the greedy selection under high solar priority demand
        Experiment[] result = mainApp.greedy(prioritizeSolar);

        // Count how many solar experiments were actually accepted into the final selection
        long solarCount = Arrays.stream(result)
                .filter(Experiment::isSolar)
                .count();
        // Enforce the explicit business cap rule
        assertTrue(solarCount <= 2, "Greedy algorithm selected more than 2 solar experiments!");
    }
    
    /**
     * Verifies that the 7kg joint weight reduction rule is correctly applied when both 
     * the Seed Viability and Yeast Fermentation experiments are bundled together.
     * 
     *  Rule: If both Seed Viability and Yeast Fermentation are chosen, 
     * their total shared footprint receives a 7kg weight discount. The test cross-checks 
     * the algorithm's tracking math against an independent calculation loop.
     */
    @Test
    @DisplayName("Should apply the 7kg discount when both Seed Viability and Yeast Fermentation are chosen")
    void testYeastAndSeedWeightDiscount() {
    	 // Force both required bundle items to the top of the stream to guarantee selection
        Comparator<Experiment> prioritizeDiscountPair = (e1, e2) -> {
            boolean isPair1 = (e1 == Experiment.SEED_VIABILITY || e1 == Experiment.YEAST_FERMENTATION);
            boolean isPair2 = (e2 == Experiment.SEED_VIABILITY || e2 == Experiment.YEAST_FERMENTATION);
            return Boolean.compare(isPair2, isPair1);
        };

        // Execute greedy selection with the targeted bundle prioritized
        Experiment[] result = mainApp.greedy(prioritizeDiscountPair);
        List<Experiment> list = Arrays.asList(result);

        // Ensure both elements of the target bundle were successfully selected
        assertTrue(list.contains(Experiment.SEED_VIABILITY));
        assertTrue(list.contains(Experiment.YEAST_FERMENTATION));

        // Manually compute the expected weight mimicking the system's discount laws
        int expectedWeight = 0;
        boolean hasSeed = false;
        boolean hasYeast = false;
        
        for (Experiment e : result) {
        	  // Skip the secondary band calculation during the initial standard item iteration
            if (e == Experiment.COSMIC_RAYS_SECONDARY_BAND) continue;
            int w = e.weight();
            // Apply the 7kg offset discount rule if the counterpart item has already been checked
            if ((e == Experiment.YEAST_FERMENTATION && hasSeed) || (e == Experiment.SEED_VIABILITY && hasYeast)) {
                w -= 7;
            }
            expectedWeight += w;
            // Track presence flags as we loop through the array
            if (e == Experiment.SEED_VIABILITY) hasSeed = true;
            if (e == Experiment.YEAST_FERMENTATION) hasYeast = true;
        }
        // Handle conditional addition of Cosmic Rays Primary Band weight if capacity allows
        if (list.contains(Experiment.COSMIC_RAYS_PRIMARY_BAND) && expectedWeight + 10 <= 700) {
            expectedWeight += 10;
        }
     // Compare our manual tracking math with the output of the dedicated helper method
        int actualCalculatedWeight = calculateTotalWeight(result);
        assertEquals(expectedWeight, actualCalculatedWeight, "Weight discount math discrepancy discovered.");
    }
    
    /**
     * Verifies the mandatory dependency chain rule linking cosmic ray testing nodes.
     * 
     *  Rule: If the Cosmic Rays Primary Band is added to the payload, 
     * the Cosmic Rays Secondary Band must be appended to the very end of the selection list, 
     * provided there is sufficient remaining weight capacity.
     */
    @Test
    @DisplayName("Should automatically append Secondary Band if Primary Band is selected and space permits")
    void testCosmicRayDependency() {
    	 // Create a compounding comparator to manage item ordering safely:
        // 1. Force the Primary Band to the absolute front of the line (index priority).
        // 2. Sort all remaining experiments by weight (lowest first) so space is preserved.
        Comparator<Experiment> prioritizePrimaryThenWeight = (e1, e2) -> {
        	
        	  // If e1 is the primary band, move it ahead of e2
            if (e1 == Experiment.COSMIC_RAYS_PRIMARY_BAND) return -1;
            
            // If e2 is the primary band, move it ahead of e1
            if (e2 == Experiment.COSMIC_RAYS_PRIMARY_BAND) return 1;
            
            // If neither is the primary band, fallback to sorting by weight (ascending)
            return Integer.compare(e1.weight(), e2.weight());
        };

        // Execute the greedy algorithm under this specific structural priority layout
        Experiment[] result = mainApp.greedy(prioritizePrimaryThenWeight);
        
     // Convert the returned array into a List for scannable assertions
        List<Experiment> list = Arrays.asList(result);

        // 1. Confirm that our custom comparator successfully forced the selection of the Primary Band
        assertTrue(list.contains(Experiment.COSMIC_RAYS_PRIMARY_BAND), "Primary band should have been chosen.");
        
        // 2. Confirm that the algorithm automatically triggered the dependency rule and appended the Secondary Band
        assertTrue(list.contains(Experiment.COSMIC_RAYS_SECONDARY_BAND), 
                "Secondary band must be automatically appended when Primary band fits and space is available.");
        
        // 3. Confirm structural placement: The Secondary Band must reside at the final array index
        assertEquals(Experiment.COSMIC_RAYS_SECONDARY_BAND, result[result.length - 1], 
                "Secondary Band should be appended to the end of the selected list.");
    }
   

    /**
     * Helper method to calculate total weight accounting for conditional rule offsets
     */
    private int calculateTotalWeight(Experiment[] array) {
        int totalWeight = 0;
        boolean hasSeed = false;
        boolean hasYeast = false;
        boolean hasPrimary = false;

        for (Experiment e : array) {
            if (e == Experiment.COSMIC_RAYS_SECONDARY_BAND) {
                continue; 
            }
            int w = e.weight();
            if ((e == Experiment.YEAST_FERMENTATION && hasSeed) || (e == Experiment.SEED_VIABILITY && hasYeast)) {
                w -= 7;
            }
            totalWeight += w;
            if (e == Experiment.SEED_VIABILITY) hasSeed = true;
            if (e == Experiment.YEAST_FERMENTATION) hasYeast = true;
            if (e == Experiment.COSMIC_RAYS_PRIMARY_BAND) hasPrimary = true;
        }
        int secondaryWeight = Experiment.COSMIC_RAYS_SECONDARY_BAND.weight(); 
        if (hasPrimary && totalWeight +  secondaryWeight <= 700) {
            totalWeight +=  secondaryWeight;
        }
        return totalWeight;
    }
}