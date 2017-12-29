# CC-CLEANUP
This is a solution to the CodeChef problem [CLEANUP](https://www.codechef.com/problems/CLEANUP).

## Problem Statement
After a long and successful day of preparing food for the banquet, it is time to clean up. There is a list of n jobs to do before the kitchen can be closed for the night. These jobs are indexed from 1 to n.

Most of the cooks have already left and only the Chef and his assistant are left to clean up. Thankfully, some of the cooks took care of some of the jobs before they left so only a subset of the n jobs remain. The Chef and his assistant divide up the remaining jobs in the following manner. The Chef takes the unfinished job with least index, the assistant takes the unfinished job with the second least index, the Chef takes the unfinished job with the third least index, etc. That is, if the unfinished jobs were listed in increasing order of their index then the Chef would take every other one starting with the first job in the list and the assistant would take every other one starting with the second job on in the list.

The cooks logged which jobs they finished before they left. Unfortunately, these jobs were not recorded in any particular order. Given an unsorted list of finished jobs, you are to determine which jobs the Chef must complete and which jobs his assistant must complete before closing the kitchen for the evening.

## Input

### Values

* T = Num Test Cases
* N = Num Jobs Total
* M = Num Jobs Already Finished 

### Restrictions

* T <= 50
* 0 <= N <= M <= 1000

### Format

* T 
* Case1: N M
* Case1: M1 M2 ... MM
* Case2: N M
* Case2: M1 M2 ... MM
* ...
* ...
* CaseT: (N M (0 <= N <= M <= 1000))
* CaseT: (M1 M2 ... MM)

## Output

Each test case will produce two lines, the first is a list of jobs for the Chef, the second for the Assistant. Both lists are in ascending order with indices separated by spaces. If no jobs exist for a role, the line should exist but be blank, resulting in T * 2 lines total.

## Example

### Input:

* 3
* 6 3
* 2 4 1
* 3 2
* 3 2
* 8 2
* 3 8

### Output:

* 3 6
* 5
* 1
* 
* 1 4 6
* 2 5 7
