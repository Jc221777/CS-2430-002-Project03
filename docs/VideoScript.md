Hello, I am Thaddeus Schelp, and I will be talking about the brute force and dynamic programming approaches.

# Brute Force Strategies

The brute force strategy is to check every possible set of experiments, and keep a record of the best $n$ which are under the weight limit. After all possible sets of experiments have been checked, the best $n$ are returned.

# Dynamic Strategies

The dynamic strategy avoids checking whole subtrees of experiment sets. These trees are culled by looking at previous results. Any tree that is guaranteed not to contain the solution is culled.

# `SuperMask` Class

The `SuperMask` class is designed to keep track of the subsets that the dynamic algorithm culls. It keeps a big boolean array that is updated when a new item is added, and provides constant time matching.

# Tests

The brute force and dynamic tests ensure that the weight limit is respected. The edge case in which a solution matches the weight exactly is checked for. These tests also ensure that the algorithms respect the requirements of the change request.
