import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

class AssignmentCollector {
	
	// Define constants and constraints
	public static enum Roles {Chef, Assistant, Cook}
	public static final int MIN_NUM_CASES = 0;
	public static final int MAX_NUM_CASES = 50;
	public static final int MIN_NUM_JOBS = 0;
	public static final int MAX_NUM_JOBS = 1000;
	public static final int INVALID_VALUE = -2345; // Sentinel
	
	// Define instance variables
	private BufferedReader reader = null;
	private int numCases = INVALID_VALUE;
	private List<JobAssignment> assignments = null;
	
	public AssignmentCollector() {
		
		reader = new BufferedReader(new InputStreamReader(System.in));
		setup();
		
	} // end no-arg constructor
	
	
	public AssignmentCollector(String input) {
		
		reader = new BufferedReader(new StringReader(input));
		setup();
		
	} // end String input constructor
	
	
	/**
	 * This method is used to do any initial setup for the collector, making it ready to process test cases.
	 */
	private void setup() {
		
		assignments = new ArrayList<JobAssignment>();
		int tempValue;
		
		try {
			
			tempValue = Integer.parseInt(getNextLineFromBuffer());
				
			if(tempValue >= MIN_NUM_CASES && tempValue <= MAX_NUM_CASES) {
					
				numCases = tempValue;
					
			}
			
		} catch (NumberFormatException nfe) {
			
			// Catch all exception, would normally handle each individually but...
			System.out.println("Error retrieving number of test cases!");
			
		} // end try-catch block
		
	} // end setup
	
	
	/**
	 * Use this method to process all test cases.
	 */
	public void processCases() {
		
		String line1;
		String line2;
		
		for(int i = 0 ; i < numCases ; i++) {
			
			line1 = getNextLineFromBuffer();
			line2 = getNextLineFromBuffer();
			
			// If more test cases stated than in input String/Stream, if so, stop processing.
			if(line1 == null || line2 == null) {
				
				break;
				
			} else {
				
				assignments.add(new JobAssignment(line1, line2));
								
			}
			
		}
		
	} // end processCases
	
	
	/**
	 * This is a private method used to get the next input from the BufferedReader.
	 */
	private String getNextLineFromBuffer() {
		
		String tempString = "";
		
		try {
			
			if(reader.ready()) {
				
				tempString = reader.readLine();
				
			}
			
		} catch (IOException e) {
			
			// Catch all exception, would normally handle differently but for codechef...
			System.out.println("Error retrieving next line from input!");
		
		}
		
		return tempString;
		
	} // end getNextLineFromBuffer
	
	
	/**
	 * This method will output results of each test case, two lines each. The first line contains the Chef's jobs and 
	 * second the Assistant's.
	 */
	public void outputResults() {
		
		Iterator<JobAssignment> iterator = assignments.iterator();
		JobAssignment tempAssignment;
		
		while(iterator.hasNext()) {
			
			tempAssignment = iterator.next();
			
			System.out.println(tempAssignment.chefJobs);
			System.out.println(tempAssignment.assistantJobs);
			
		}
		
	} // end outputResults
	
	
 	private class JobAssignment {
		
		// Create class variables with default values.
		private int numJobs = AssignmentCollector.INVALID_VALUE;
		private int numFinishedJobs = AssignmentCollector.INVALID_VALUE;
		private int[] finishedJobs;
		private String chefJobs = "";
		private String assistantJobs = "";
		
		public JobAssignment(String jobTotals, String finishedJobsList) {
				
			parseJobTotals(jobTotals);
			finishedJobs = new int[numFinishedJobs];
			parseFinishedJobsList(finishedJobsList);
			assignJobs();
			
		} // end JobAssignment constructor
		
		
		/**
		 * This method is used to parse the total number of jobs (N) and number of already completed jobs (M) from 
		 * the provided string.
		 * 
		 * @param jobTotals - A string containing the total job count (N), as well as the number of those already 
		 * completed (M) in the format "N M".
		 * 
		 * @throws InputMismatchException - If too few values were provided, values are out of bounds, or in the 
		 * wrong order.
		 * 
		 * @throws NumberFormatException - If the values provided cannot be interpreted as integers.
		 */
		private void parseJobTotals(String jobTotals) throws InputMismatchException, NumberFormatException {
			
			int tempNumJobs;
			int tempNumFinishedJobs;
			String[] totals = jobTotals.split(" ");
			
			// Outer if-else
			if(totals.length >= 2) {
				
				// These may throw NumberFormatExceptions
				tempNumJobs = Integer.parseInt(totals[0]);
				tempNumFinishedJobs = Integer.parseInt(totals[1]);
					
				// Middle if-else
				if(isValidValue(tempNumJobs) && isValidValue(tempNumFinishedJobs)) {
						
					// Inner if-else
					if(tempNumJobs >= tempNumFinishedJobs) {
						
						numJobs = tempNumJobs;
						numFinishedJobs = tempNumFinishedJobs;
							
					} else {
							
						throw new InputMismatchException("Job Totals: Finished jobs cannot exceed total jobs!");
							
					} // end Inner if-else
						
				} else {
						
					throw new InputMismatchException("Job Totals: One or more values are invalid!");
						
				} // end Middle if-else
					
			} else {
				
				throw new InputMismatchException("Job Totals: Too few values provided!");
				
			} // end Outer if-else
			
		} // end parseJobNumbers
		
		
		/**
		 * This method is used to parse the list of completed jobs from the provided string. Make sure to parse total 
		 * job numbers first!
		 * 
		 * @param finishedJobsList - A space separated list of finished job numbers.
		 * 
		 * @throws NumberFormatException - If a string value cannot be interpreted as an integer.
		 * 
		 * @throws InputMismatchException - if the provided job ID's are duplicates, out of bounds or don't match the 
		 * specified amount.
		 */
		private void parseFinishedJobsList(String finishedJobsList) throws NumberFormatException, 
																				InputMismatchException {
			
			String[] finishedJobsSTRINGS = finishedJobsList.split(" ");
			HashSet<Integer> finishedJobsSET = new HashSet<Integer>();
			
			if(finishedJobsSTRINGS.length >= numFinishedJobs) {
				
				// Only want to grab the specified number of values, no more.
				for(int i = 0 ; i < numFinishedJobs ; i++ ) {
					
					finishedJobsSET.add(Integer.parseInt(finishedJobsSTRINGS[i])); // May throw NumberFormatException
					
				}
				
				
				// If there were Duplicates, the set should have ignored them but now too few values found.
				if(finishedJobsSET.size() < numFinishedJobs) {
					
					throw new InputMismatchException("Finished Jobs: No duplicate job ID's allowed!");
					
				}
				
				
				// Now that we have all unique values, make sure they are valid... if so, save them.
				Iterator<Integer> iterator = finishedJobsSET.iterator();
				for(int i = 0 ; iterator.hasNext() ; i++) {
					
					int tempValue = iterator.next();
					
					if(isValidValue(tempValue) && tempValue < numJobs + 1) {
						
						finishedJobs[i] = tempValue;
						
					} else {
						
						throw new InputMismatchException("Finished Jobs: Value in finished jobs list out of bounds!");
						
					}
					
				}
				
			} else {
				
				throw new InputMismatchException("Finished Jobs: List is too short!");
				
			}
			
		} // end parseFinishedJobsList
		
		
		/**
		 * This method is used to determine the job assignments. Make sure number of jobs, finished jobs and finished 
		 * jobs list have been created prior to calling this method.
		 */
		private void assignJobs() {
			
			Roles[] assignments = new Roles[numJobs];
			boolean chefJob = true;
			
			// Fill in the jobs already finished by the line cooks.
			for(int i = 0 ; i < finishedJobs.length ; i++) {
				
				assignments[finishedJobs[i] - 1] = Roles.Cook;
				
			}
			
			// Assign jobs to the Chef and Assistant.
			for(int i = 0 ; i < assignments.length ; i++) {
				
				if(assignments[i] != Roles.Cook) {
					
					if(chefJob) {
						
						assignments[i] = Roles.Chef;
						chefJob = false;
						
					} else {
						
						assignments[i] = Roles.Assistant;
						chefJob = true;
						
					}
					
				}
				
			} // end for loop
			
			// Create the lists
			for(int i = 0 ; i < assignments.length ; i++) {
				
				if(assignments[i] == Roles.Chef) {
					
					chefJobs += (i + 1) + " ";
					
				} else if(assignments[i] == Roles.Assistant) {
					
					assistantJobs += (i + 1) + " ";
					
				}
				
			}
			
			chefJobs = chefJobs.trim();
			assistantJobs = assistantJobs.trim();
			
		} // end assignJobs
		
		
		/**
		 * This method is used to validate a value by checking it against constraints (set in the outer class).
		 * 
		 * @param value - The value to be tested.
		 * 
		 * @return - A boolean true if valid, otherwise false.
		 */
		private boolean isValidValue(int value) {
			
			// Value must fall within the specified range
			if(value >= AssignmentCollector.MIN_NUM_JOBS && value <= AssignmentCollector.MAX_NUM_JOBS) {
				
				return true;
				
			} else {
				
				return false;
				
			}
			
		} // end validateValue
		
		
		/**
		 * This method will provide a list of jobs assigned to the Chef.
		 * 
		 * @return - The Chef's jobs as a string.
		 */
		public String getChefJobs() {
			
			return chefJobs;
			
		} // end getChefJobs
		
		
		/**
		 * This method will provide a list of jobs assigned to the Assistant.
		 * 
		 * @return - The Assistant's jobs as a string.
		 */
		public String getAssistantJobs() {
			
			return assistantJobs;
			
		} // end getAssistantJobs
		
	} // end inner-class JobAssignment
	
	
	public static void main(String[] args) {

		
		// Test JobAssignment inner class... make it static for tests.
		
		// should be fine
//		String line1 = "7 3";
//		String line2 = "5 2 6";
//		JobAssignment ja1 = new JobAssignment(line1, line2);
		
		// value at min
//		String line3 = "0 7";
//		String line4 = "5 2 6";
//		JobAssignment ja2 = new JobAssignment(line3, line4);
		
		// value too low
//		String line5 = "-1 7";
//		String line6 = "5 2 6";
//		JobAssignment ja3 = new JobAssignment(line5, line6);
		
		// value at max
//		String line7 = "1000 1000";
//		String line8 = "5 2 6";
//		JobAssignment ja5 = new JobAssignment(line7, line8);
		
		// value too high
//		String line9 = "1000 1001";
//		String line10 = "5 2 6";
//		JobAssignment ja6 = new JobAssignment(line9, line10);
		
		// Too few totals provided
//		String line11 = "7";
//		String line12 = "5 2 6";
//		JobAssignment ja7 = new JobAssignment(line11, line12);
		
		// Duplicate finished tests
//		String line13 = "7 3";
//		String line14 = "5 2 2 6";
//		JobAssignment ja8 = new JobAssignment(line13, line14);
		
		// Invalid input String
//		String line15 = "A7 3";
//		String line16 = "5 6";
//		JobAssignment ja9 = new JobAssignment(line15, line16);
		
		// 6 total jobs, 7 is out of bounds.
//		String line17 = "6 3";
//		String line18 = "2 4 7";
//		JobAssignment ja10 = new JobAssignment(line17, line18);
		
//		3
//		6 3
//		2 4 1
//		3 2
//		3 2
//		8 2
//		3 8
		
		
//		String input = "7\n6 3\n2 4 1\n3 2\n3 2\n8 2\n3 8";
//		AssignmentCollector collector = new AssignmentCollector(input);
//		collector.processCases();
//		collector.outputResults();
		
		AssignmentCollector collector = new AssignmentCollector();
		collector.processCases();
		collector.outputResults();
		
	} // end main

} // end Class AssignmentCollector
