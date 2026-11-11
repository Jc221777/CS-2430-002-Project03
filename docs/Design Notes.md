# Project 3: System Design Notes & Decisions

### Data Structures & Architecture
* We are using the data structure (records) to ensure that the data within the universal set cannot be changed
* In the Brute force method we use a 2d array alongside a simple int array that will store the best rated out of all generated subsets.   
### System Invariants (Rules that must never be broken)
* The combined weight of any experiment subset must nver exceed 700 kg
* There can be no mre than 2 solar experiments in a subset generated from the records
* The original records elements such as weight and rating must never be changed and remain final
### Core Assumptions
* Since the amount of subsets generated for brute force will always be 2^13 or 8,192 we know that the number of comparisons will be small enough to not cause very much performance issues
### Design Tradeoffs
* The Bruteforce method will garentee finding the global most optimal experiment combination. However It may scale poorly if more elements where to be added to records table
* The greedy method will provide results very fast but it may not provide accurate results across the whole records table

