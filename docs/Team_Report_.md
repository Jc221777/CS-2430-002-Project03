Team members:

Jasey Christensen

Thaddeus Schlep

Tristan Taj

Team Name: Team 03

Course: CS 2430

Semester: Fall

Author: Jasey C,Tristan T,Thaddeus Schlep

# Programming Project 3: Optimal Selection

 ## **Part 01 Introduction** 

The goal of this project is to find which combination of payloads within a table of 12 gives the highest rating. The table of 12 contains different types of payloads for use in NASA testing. These elements have three attributes, the name, the weight and the rating. The shuttle that we are loading these experiments on cannot have more than 700kg of weight inside it. So the goal is to find the most optimal way to build a subset that has the highest rating to load onto the shuttle while remaining beneath 700kg.

This problem is what is known as a knapsack problem where we have a list of things we want in a space but that space has limitations so we need to find the most optimal way to get as close as we can to our desired payload within the sack. This problem showcases how algorithm design can be used to solve abstract mathematical reasoning as well as showcase different ways to tackle optimization issues in programming. We will be using three different strategies to solve this problem and we will evaluate each one's effectiveness. 

## **Methods / Approach**

We used the Java programming language to solve this problem. We used the `java.util` library for `Array` functions and for `Comparator`s. Furthermore, we used the `junit` library for testing.

### Greedy Algorithms

All three of the greedy algorithms were implemented using the same function. This function takes a comparator by which to sort the input experiments. This simplifies our project significantly by reducing code duplication, the amount of work required to produce code that meets the requirements, and makes it easier to instrument changes when the change request comes around.

### Brute Force and Dynamic Programming

Our brute force code is very straight forward, and our dynamic programming code is built on top of it. The brute force algorithm is designed to iterate over all possible subsets of the given experiment set. Each subset is tested to see if it is under or equal to the weight limit. If it is, then it is compared with the best known subsets and added to the list if it beats any of them. If not, then it is thrown out.

The dynamic solution uses a very similar approach, except that it is capable of eliminating whole subtrees of the problem at once. Whenever a set is rejected, it is added to the list of rejected sets. Whenever a new set is tested if it should be checked or not, we first check if any subset of it has already been rejected in constant time. If so, we know this one is part of that one's tree and will also be rejected, so we throw it out immediately. If not, then we continue with the same path that brute force does.

### Correctness Verification

We verified the correctness of our algorithms by writing tests that checked specific cases. For example, one corner case that we were careful to check for is the case in which the weight of the optimal set of experiments exactly matches the weight limit. We also made sure to include extra tests to verify that the change request was being accounted for in the algorithms after it was issued.

## Results

### Greedy - Rating

- Solar Flares
- Micrometeorites
- Binary Stars
- Cloud Patterns
- Seed Viability
- Yeast Fermentation

**Total Weight:** 700

**Total Rating:** 39

### Greedy - Weight

- Seed Viability
- Yeast Fermentation
- Cloud Patterns
- Mice Tumors
- Microgravity Plant Growth
- Cosmic Rays (Primary Band)
- Sun Spots
- Relativity
- Micrometeorites
- Cosmic Rays (Secondary Band)

**Total Weight:** 657

**Total Rating:** 57

### Greedy - Ratio

- Seed Viability
- Yeast Fermentation
- Cloud Patterns
- Mice Tumors
- Cosmic Rays (Primary Band)
- Relativity
- Microgravity Plant Growth
- Micrometeorites
- Sun Spots
- Cosmic Rays (Secondary Band)

**Total Weight:** 657

**Total Rating:** 57

### Brute Force

#### Optimal Set

- Seed Viability
- Cosmic Rays (Secondary Band)
- Yeast Fermentation
- Cloud Patterns
- Mice Tumors
- Cosmic Rays (Primary Band)
- Relativity
- Micrometeorites
- Binary Stars

**Total Weight:** 695

**Total Rating:** 58

#### 2nd Best Set

- Seed Viability
- Cosmic Rays (Secondary Band)
- Yeast Fermentation
- Cloud Patterns
- Mice Tumors
- Cosmic Rays (Primary Band)
- Relativity
- Microgravity Plant Growth
- Micrometeorites
- Sun Spots

**Total Weight:** 657

**Total Rating:** 57

#### 3rd Best Set

- Seed Viability
- Cosmic Rays (Secondary Band)
- Yeast Fermentation
- Cloud Patterns
- Mice Tumors
- Cosmic Rays (Primary Band)
- Relativity
- Micrometeorites
- Solar Power

**Total Weight:** 680

**Total Rating:** 56

### Dynamic

- Seed Viability
- Cosmic Rays (Secondary Band)
- Yeast Fermentation
- Cloud Patterns
- Mice Tumors
- Cosmic Rays (Primary Band)
- Relativity
- Micrometeorites
- Binary Stars

**Total Weight:** 695

**Total Rating:** 58

### Comparison Table

| Strategy        | Weight | Rating | Optimal |
|-----------------|--------|--------|---------|
| Greedy - Rating | 700    | 39     | NO      |
| Greedy - Weight | 657    | 57     | NO      |
| Greedy - Ratio  | 657    | 57     | NO      |
| Brute Force     | 695    | 58     | YES     |
| Dynamic         | 695    | 58     | YES     |

## Discussion / Analysis

### Greedy vs Brute Force

  Based on the results of our program the greedy strategies were very un optimal as the target rating  of  58 was never reached in any of the results and either packed things that left much too much dead space or loaded on way too many heavy items. However, each solution came very close to the optimal missing by only 1 number in one case. The brute force strategy would guarantee an optimal result however this does come with the downside of potentially taking much longer to execute depending on the length of the list it is searching through. 

### Why is Greedy Suboptimal

  Greedy strategies create a subset based on only one rule, This leads to a very fast algorithm however it also leads to many potentially better options being passed over. For example for our weight greedy strategy our algorithm first sorts the entire list of 12 by lowest weight to highest. It then takes in elements as it goes down the newly sorted list until we reach an element that would make our subset exceed 700. This leads to many potentially better options for reaching the optimal rating being left behind at the end of the list. 

### Brute Force and Dynamic Programming Scalability

  While the Brute force strategy will always give us the most optimal answer it will get much slower as the list it is handling gets bigger to the point where it will quickly become unusable. However, dynamic programming takes a very large problem and simplifies it down to smaller issues that are solved in the interest of the bigger problem. So in our example if the table of 12 became something like 1,000,000 elements the brute force algorithm would eventually get the right answer, but it will be very slow to attain. But with the dynamic approach you can split the 1,000,000 into smaller lists sort them, make subsets of them, and then combine them all to have the optimized subset of the original 1,000,000 in a very timely fashion

### Another Approach

  One other plausible approach could be genetic (or heuristic) programming. Genetic programming works by taking three random elements from the list and compares each one and gets rid of the losing element and includes the other two. This is done until the constraint is reached. For our example it would be taking three elements from our list of 12 and comparing the elements against each other. The loser of this test would be left behind and the other two are put into the new subset. This would be a decent solution as it takes a large problem and solves it through solving many smaller comparisons.




1. **Weight-based selection:** Which experiments were selected? Total weight? Total rating? **(This will be answered by Tristan)**


2. **Rating-based selection:** Which experiments were selected? Total weight? Total rating **(This will be answered by Tristan)**


3. **Ratio-based selection:** Which experiments were selected? Total weight? Total rating? **(This will be done by Tristan)**


### Brute Force Optimal

The brute force algorithm produced the following combination.

- Binary Stars
- Cloud Patterns
- Cosmic Rays (Primary Band)
- Cosmic Rays (Secondary Band)
- Mice Tumors
- Micrometeorites
- Relativity
- Seed Viability
- Yeast Fermentation

Total Weight: 695kg

Total Rating: 58 science units

5. **Greedy vs optimal:** Which greedy strategy performed best in this dataset? Why do the others miss the optimal subset? **(This will be done by Tristan)**


### Dynamic Programming

Our dynamic programming approach stores subproblems in a `SuperMask`. Any subproblem stored there is invalid, and all of its children are ignored. At each step we decide if the current subproblem is the best we have seen so far, or if it is invalid. In the case of invalid subproblems, we add them the `supermask` so that we never have to look at any of their children. This is significantly more efficient than brute force, because we can quickly cull huge branches of the tree that involve cases that will never work. Brute force requires looking at every possible set of experiments regardless of whether that set could feasibly fly. As the problem space gets larger, the effectiveness of the dynamic programming approach with respect to brute force becomes more and more apparent as the difference in number of checked sets increases.

## **Change Impact Analysis**

1.  **What changed:**

    The first thing that changed was that our set of records had to have the element `COSMIC_RAYS_SECONDARY_BAND` added to it. Next we made a boolean function `isSolar` and included the three solar experiments within to track how many solar experiments are included within our subset. Next was to change how the greedy method functioned. It still would sort items based on the `Comparator` passed into it but now the items would pass through filters to ensure the rules of the change request were enforced before being added to the subset. for the `bruteForce` function we did much of the same by adding filters that will prune out items according to the change request as well as change attributes of elements when required. the `dynamic` approach works the exact same as the `bruteForce`

2.  **What did not change:**

    We were able to reuse all of the data types within the methods themselves as they were used for very broad things such as tracking total weights and the attributes of each element. We were also able to reuse our printResult method as all that does is print the resulting subsets that come from our three methods.

3.  **Refactoring:**

    There was no real restructuring due to the change request, All that was done was add things to our existing methods but visibilities and keywords did not change for the change request.

4.  **Design strengths and weaknesses:**

    Our decision to have everything handled in three big methods instead of smaller helper methods actually made implementing the changes easy as all that needed to be changed was add some filtering rules so that the code adheres to the change requests parameters.

5.  **Lessons learned:**

    We would probably communicate how we are implementing the changes to each other better as coming up with ways to test that the code adheres to the change request was a little difficult.**

## Conclusion

We learned  that it is a good idea to really think about how one should optimize their problem. It can depend on the size, but it seems like coming up with a solution that utilizes dynamic programming generally seems like the way to go when trying to optimize stuff. However, if the data you are trying to optimize is not very large it seems that using a brute force strategy is a pretty nice way to get an optimized solution fast. However, if you don’t need your optimization to be meet the goal 100% the greedy strategies are a great way to get your optimization fast as well. It really depends on what you are optimizing what strategy you should use.

## Summary of Work Done / Lessons Learned

When deciding what to store our 12 values as in our code we almost used enumerations, However this may have led to the code not being that testable since there could not have been new enumerations created of the same type so we decided to use records instead so learning about how those work was certainly interesting. Also, we decided to keep all of our code mostly in one class, although we did end up with one helper class for the dynamic solution. This probably could have been split up into multiple classes but at the end of the day it may have just ended up making our code more bloated than it needed to be. Overall we are all happy with how our project turned out.

## Sources
“Brute Force Approach and Its Pros and Cons.” GeeksforGeeks, May 2021, www.geeksforgeeks.org/dsa/brute-force-approach-and-its-pros-and-cons/. Accessed 11 Nov. 2026.

“Dynamic Programming or DP.” GeeksforGeeks, 9 Feb. 2024, www.geeksforgeeks.org/dsa/dynamic-programming/. Accessed 11 Nov. 2026.

“Greedy Algorithms.” GeeksforGeeks, 7 Feb. 2024, www.geeksforgeeks.org/dsa/greedy-algorithms/. Accessed 11 Nov. 2026.
