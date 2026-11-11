package project03;



/**
 *A class for bit mask scanning.
 * SuperMask provides and efficient way to check if any bitset previously added is present in a match bitset. Constant
 * time matches are achieved by moving the work to the add time, a function called much less frequently in our use case.
 * 
 * Team 03
 * Jasey Christensen, Thaddeus Schlep & Tristan Taj
 * CS 2430, section 002
 * Programming Project 3: Optimal selection
 * 
 * @author Thaddeus
 */

/**
 * A class for bit mask scanning.
 * SuperMask provides and efficient way to check if any bitset previously added is present in a match bitset. Constant
 * time matches are achieved by moving the work to the add time, a function called much less frequently in our use case.
 */
public class SuperMask {
    byte[] matches;

    /**
     * Builds a SuperMask capable of processing numbers up to the given value.
     * @param maxSize The largest number that will be added or matched.
     */
    SuperMask(final int maxSize) {
        matches = new byte[Math.ceilDivExact(maxSize, 8)];
    }

    /**
     * Attempts to match the given key with a previously added bitset.
     * @param key The key to match.
     * @return true if add was called with a bitset which is a subset of the key bitset.
     */
    boolean match(final int key) {
        return (matches[Math.floorDivExact(key, 8)] & (1 << (key % 8))) > 0;
    }

    /**
     * Adds a bitset which can be matched against later.
     * @param bitset The bitset to add.
     */
    void add(final int bitset) {
        int index = bitset;
        while (index < matches.length * 8) {
            matches[Math.floorDivExact(index, 8)] |= (byte)(1 << (index % 8));
            index = (index + 1) | bitset;
        }
    }
}
