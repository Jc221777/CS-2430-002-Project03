package project03;

import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.api.Test;
import project03.Main.Experiment;
import project03.Main;
import project03.Main.Experiment;
import java.util.Comparator;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TestGreedy {
/**
 * tests to make sure we can sort the weight from lowest to highest
 */
	 @Test
	    void testGreedySortsByWeightAscending() {
	        //Comparator sorting by weight in ascending order (lightest first)
	        Comparator<Main.Experiment> sortByWeightAsc = Comparator.comparingInt(Main.Experiment::weight);

	        //Run the greedy method with the weight comparator
	        Main.Experiment[] selected = Main.greedy(sortByWeightAsc);

	        //  Ensure everything selected is ordered correctly by weight
	        int checkLength = selected.length;
	        if (selected.length > 0 && selected[selected.length - 1] == Main.Experiment.COSMIC_RAYS_SECONDARY_BAND) {
	            checkLength--; 
	        }

	        // Verify that the remaining elements strictly ascend or stay equal in weight
	        for (int i = 0; i < checkLength - 1; i++) {
	            assertTrue(
	                selected[i].weight() <= selected[i + 1].weight(),
	                String.format("Expected ascending weight, but %s (%d kg) came before %s (%d kg)",
	                    selected[i].name(), selected[i].weight(),
	                    selected[i + 1].name(), selected[i + 1].weight())
	            );
	        }
	    }
	 
	 /**
	  * Tests to make sure we can correctly sort the ratings from highest to lowest
	  */
	 @Test
	    void testGreedySortsByHighestRatingFirst() {
	        // Comparator sorting by rating in descending order (highest first)
	        Comparator<Main.Experiment> sortByRatingDesc = (e1, e2) -> Integer.compare(e2.rating(), e1.rating());

	        // Run the greedy method
	        Main.Experiment[] selected = Main.greedy(sortByRatingDesc);

	        // Exclude the forced secondary band tail element for strict sort verification
	        int checkLength = selected.length;
	        if (selected.length > 0 && selected[selected.length - 1] == Main.Experiment.COSMIC_RAYS_SECONDARY_BAND) {
	            checkLength--;
	        }

	        // Verify that the rating decreases or stays equal as the array moves
	        for (int i = 0; i < checkLength - 1; i++) {
	            assertTrue(
	                selected[i].rating() >= selected[i + 1].rating(),
	                String.format("Expected descending rating, but %s (rating: %d) came before %s (rating: %d)",
	                    selected[i].name(), selected[i].rating(),
	                    selected[i + 1].name(), selected[i + 1].rating())
	            );
	        }
	    }
	 
	  /**
	  * Test to make sure the weight can not reach over 700
	  */
	 @Test
	    void testTotalWeightNeverExceeds700() {
	        //insert the compare int comparator
	        Comparator<Main.Experiment> sortByWeight = Comparator.comparingInt(Main.Experiment::weight);

	       // Run the greedy method
	        Main.Experiment[] selected = Main.greedy(sortByWeight);

	        //  Sum up the basic weight of everything selected
	        int totalWeight = 0;
	        for (Main.Experiment experiment : selected) {
	            totalWeight += experiment.weight();
	        }

	        // Verify the sum stays within the 700 kg boundary
	        assertTrue(totalWeight <= 700, "The total selected weight exceeded 700 kg!");
	    }
/**
 * Tests that the greedy method can accurately sort the elements by thier rating/weight ratio
 */
	  @Test
	    void testGreedySortsByHighestRatioFirst() {
	        // Create a comparator that sorts by ratio() from highest to lowest
	        Comparator<Main.Experiment> sortByRatioDesc = (e1, e2) -> Double.compare(e2.ratio(), e1.ratio());

	        //Run the greedy method with our density ratio comparator
	        Main.Experiment[] selected = Main.greedy(sortByRatioDesc);

	        // Exclude the forced secondary band tail element for strict sort verification
	        int checkLength = selected.length;
	        if (selected.length > 0 && selected[selected.length - 1] == Main.Experiment.COSMIC_RAYS_SECONDARY_BAND) {
	            checkLength--;
	        }

	        // Verify that the ratio decreases or stays equal as you move down the array
	        for (int i = 0; i < checkLength - 1; i++) {
	            assertTrue(
	                selected[i].ratio() >= selected[i + 1].ratio(),
	                String.format("Expected descending ratio, but %s (ratio: %.4f) came before %s (ratio: %.4f)",
	                    selected[i].name(), selected[i].ratio(),
	                    selected[i + 1].name(), selected[i + 1].ratio())
	            );
	        }
	    }
	  
	  /**
	   * verifies that the no more than three solar experiment rule is not broken
	   */
	  @Test
	    void testSolarExperimentLimit() {
	        // Use a sorting strategy that favors solar items to try and force them in
	        // (e.g., sorting by highest rating, where Solar Flares has a 9)
	        Comparator<Main.Experiment> sortByRatingDesc = (e1, e2) -> Integer.compare(e2.rating(), e1.rating());

	        // Run the greedy method
	        Main.Experiment[] selected = Main.greedy(sortByRatingDesc);

	        // Assert: Count how many solar items were actually included
	        int solarCount = 0;
	        for (Main.Experiment experiment : selected) {
	            if (experiment.isSolar()) {
	                solarCount++;
	            }
	        }

	        // Verify the count strictly respects your code's cap of 2
	        assertTrue(solarCount <= 2, 
	            String.format("Rule broken! Selected %d solar experiments, but the limit is 2.", solarCount));
	    }
	  
	  /**
	   *Ensures that the weight reduction of the Yeast Fermentation experiment is applied when Seed Viability is
       * included.
	   */
	  @Test
	    void testYeastWeightReductionApplied() {
	        // Sort by highest rating first.
	        // This naturally processes Seed Viability and Yeast Fermentation early.
	        Comparator<Main.Experiment> sortByRatingDesc = (e1, e2) -> Integer.compare(e2.rating(), e1.rating());

	        // Run the greedy method
	        Main.Experiment[] selected = Main.greedy(sortByRatingDesc);
	        java.util.List<Main.Experiment> selectedList = java.util.Arrays.asList(selected);

	        // Verify both experiments were successfully selected together
	        assertTrue(selectedList.contains(Main.Experiment.SEED_VIABILITY), "Seed Viability must be included");
	        assertTrue(selectedList.contains(Main.Experiment.YEAST_FERMENTATION), "Yeast Fermentation must be included");

	        // Verify that the sum of the selected items' raw weights fits the 700kg limit plus the 7kg discount
	        int rawWeightSum = 0;
	        for (Main.Experiment experiment : selected) {
	            rawWeightSum += experiment.weight();
	        }
	        
	        assertTrue(rawWeightSum <= 707, "Total raw weight exceeded expected bounds; discount was not applied!");
	    }
	 
	  /**
	     * Ensures that if the Primary Cosmic Rays experiment is not included, then the Secondary Cosmic Rays experiment is
	     * also not included.
	     */
	  @Test
	    void testSecondaryCosmicRaysExcludedWithoutPrimary() {
	        //  Sort by heaviest weight first. 
	        // This ensures the budget fills up before Primary Cosmic Rays can be selected.
	        Comparator<Main.Experiment> sortByWeightDesc = (e1, e2) -> Integer.compare(e2.weight(), e1.weight());

	        // Run the greedy method
	        Main.Experiment[] selected = Main.greedy(sortByWeightDesc);
	        java.util.List<Main.Experiment> selectedList = java.util.Arrays.asList(selected);

	        // First, confirm our sorting trick successfully excluded the Primary Band
	        assertFalse(selectedList.contains(Main.Experiment.COSMIC_RAYS_PRIMARY_BAND), 
	            "Setup check: Primary Band should not be included in this scenario.");

	        // Ensure the Secondary Band was also correctly excluded as a result
	        assertFalse(selectedList.contains(Main.Experiment.COSMIC_RAYS_SECONDARY_BAND), 
	            "Rule broken! Secondary Cosmic Rays were included even though Primary was missing.");
	    }
}
   
    
    
