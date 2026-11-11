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
 * @author Jasey
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
	        Main.greedy(input, Comparator.comparingInt(Experiment :: weight), 700);
	       
	       
	       assertArrayEquals(expected,input);
   }
	 
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
		 Main.greedy(input, Comparator.comparingInt(Experiment :: weight), 700);
		 
		 assertArrayEquals(expected,input);
		 
		 
	 }
	 
	 @Test 
	 void testGreedySortsByRatingCorrectly () {
		  Main.Experiment[] input = new Main.Experiment[] {
	        		Main.Experiment.CLOUD_PATTERNS,
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER
	        };
		   Main.greedy(input, Comparator.comparingInt(Experiment :: rating).reversed(), 700);
		  
		  Main.Experiment[] expected = new Main.Experiment[] {
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER,
	        		Main.Experiment.CLOUD_PATTERNS
	        };
		  
		  assertArrayEquals(expected,input);
	 }
	 
	 @Test
	 void testGreedySortsByRatioCorrectly () {
		 Main.Experiment[] input = new Main.Experiment[] {
	        		Main.Experiment.CLOUD_PATTERNS,
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER
	        };
		 Main.greedy(input, Comparator.comparingDouble(Experiment :: ratio).reversed(), 700);
		 
		 Main.Experiment[] expected = new Main.Experiment[] {
	        		Main.Experiment.CLOUD_PATTERNS,
	        		Main.Experiment.SOLAR_FLARES,
	        		Main.Experiment.SOLAR_POWER
	        };
		 assertArrayEquals(expected,input);
	 }
	 
	 
	
	
	    
}
   
    
    
