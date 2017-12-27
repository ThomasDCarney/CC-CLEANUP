# Design Document
A simple one page design/process overview.

## PseudoCode

- Parse the input for T
- For Each T
  - Parse line 1 for N and M
  - Create a collection to represent total jobs
  - Parse line 2 and mark jobs already finished
  - Process the collection and assign jobs to either the Chef or Assistant.
  - Output results, one line for the Chef and another for Assistant.
	
## Errors 

I'm being strict since there is no one to ask about odd situations...

* Each line must start with the specified format, if anything remains/follows said values (eg, comments), ignore/discard them.
* Ignore extra values (eg, more test cases available than specified). 

Error when...

- Unexpected Input
  - T, N or M are outside their respective ranges.
  - Values provided are of incorrect type.
	
## UML
