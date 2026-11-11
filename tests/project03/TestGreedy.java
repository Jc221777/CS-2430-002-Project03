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
/**
 * A test class that tests the functionality of the greedy method
 * 
 * Team 03
 * Jasey Christensen, Thaddeus Schlep & Tristan Taj
 * CS 2430, section 002
 *  Programming Project 3: Optimal selection
 * 
 * @author Jasey Christensen
 */
class TestGreedy {
/**
 * tests to make sure that the greedy strategy calculates weight correctly 
 */
	 @Test
	    void testGreedySortsByWeightCorrectly() {
	          Main.Experiment[] input = new Main.Experiment[] {
	        		Main.Experiment.CLOUD_PATTERNS,
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER
	        };
	          Main.Experiment[] expected = new Main.Experiment[] {
		        		Main.Experiment.CLOUD_PATTERNS,
		        		Main.Experiment.SOLAR_POWER,
		        		Main.Experiment.SOLAR_FLARES
		        		
		        };
	          Main.Experiment[] output = Main.greedy(input, Comparator.comparingInt(Experiment :: weight), 700);
	       
	       
	       assertArrayEquals(expected,output);
   }
	 /**
	  * Makes sure that the Greedy subset never exceeds 700
	  */
	 @Test
	 void neverExceedsWeight() {
		 Main.Experiment[] input = new Main.Experiment[] {
			Main.Experiment.SOLAR_FLARES,
			Main.Experiment.BINARY_STARS,
			Main.Experiment.SOLAR_POWER,
			Main.Experiment.RELATIVITY
			
		 };
		 Main.Experiment[] expected = new Main.Experiment[] {
					Main.Experiment.RELATIVITY,
					Main.Experiment.SOLAR_POWER,
					Main.Experiment.BINARY_STARS,
					
					
				 };
		 Main.Experiment[] output = Main.greedy(input, Comparator.comparingInt(Experiment :: weight), 700);
		 
		 assertArrayEquals(expected,output);
		 
		 
	 }
	 /**
	  * Makes sure that the Greedy subset can be sorted by rating correctly
	  */
	 @Test 
	 void testGreedySortsByRatingCorrectly () {
		  Main.Experiment[] input = new Main.Experiment[] {
	        		Main.Experiment.CLOUD_PATTERNS,
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER
	        };
		  Main.Experiment[] output =  Main.greedy(input, Comparator.comparingInt(Experiment :: rating).reversed(), 700);
		  
		  Main.Experiment[] expected = new Main.Experiment[] {
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER,
	        		Main.Experiment.CLOUD_PATTERNS
	        };
		  
		  assertArrayEquals(expected,output);
	 }
	 /**
	  * Makes sure that the Greedy subset can be sorted by ratio correctly
	  */
	 @Test
	 void testGreedySortsByRatioCorrectly () {
		 Main.Experiment[] input = new Main.Experiment[] {
	        		Main.Experiment.CLOUD_PATTERNS,
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER
	        };
		 Main.Experiment[] output =  Main.greedy(input, Comparator.comparingDouble(Experiment :: ratio).reversed(), 700);
		 
		 Main.Experiment[] expected = new Main.Experiment[] {
	        		Main.Experiment.CLOUD_PATTERNS,
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER
	        };
		 assertArrayEquals(expected,output);
	 }
	 /**
	  * Ensures not all three solar experiments can be in a subset together
	  */
@Test
void testNoMoreThanThreeSolar () {
	 Main.Experiment[] input = new Main.Experiment[] {
     		Main.Experiment.SOLAR_FLARES,
     		Main.Experiment.SOLAR_POWER,
     		Main.Experiment.SUN_SPOTS
     };
	 Main.Experiment[] output = Main.greedy(input, Comparator.comparingInt(Experiment :: weight), 700);
	 
	 Main.Experiment[] expected = new Main.Experiment[] {
      		Main.Experiment.SUN_SPOTS,
      		Main.Experiment.SOLAR_POWER,
      };
	 assertArrayEquals(expected,output);
}

/**
 * 	Tests to make sure that if secondary band is included but not primary band than to take out secondary band
 */
@Test
void testsToMakeSureIfPrimaryBandIsNotIncludedButSecondaryIsRemoveSecondary() {
	  Main.Experiment[] input = new Main.Experiment[]{
              Main.Experiment.CLOUD_PATTERNS,
              Main.Experiment.COSMIC_RAYS_SECONDARY_BAND
      };
	
	  Main.Experiment[] output = Main.greedy(input, Comparator.comparingInt(Experiment :: weight), 700);
	
	  Main.Experiment[] expected = new Main.Experiment[]{
               Main.Experiment.CLOUD_PATTERNS,
             
      };
	  
	  assertArrayEquals(expected,output);
}
/**
 * Tests to make sure that Yeast Fermentation is subtracted by 7 when seed viability is included
 */
	@Test
	void testToEnsureGreedyReducesYeastFermintationWhenSeedViabilityIsIncluded () {
		 Main.Experiment[] input = new Main.Experiment[]{
				 Main.Experiment.SEED_VIABILITY,   
				 Main.Experiment.YEAST_FERMENTATION
	               
	        };
		
		 Main.Experiment[] output = Main.greedy(input, Comparator.comparingInt(Experiment :: weight), 27);
		
		 Main.Experiment[] expected = new Main.Experiment[]{
				 Main.Experiment.SEED_VIABILITY, 
				 new Main.Experiment(Main.Experiment.YEAST_FERMENTATION.name(), 20, Main.Experiment.YEAST_FERMENTATION.rating())
	              
	        };
		 
		  assertArrayEquals(expected,output);
	}
	
	
	
	    
}
   
    
    
