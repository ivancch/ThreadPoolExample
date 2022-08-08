
package com.ivanch.ThreadPoolExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ManagerAndWorkersMain {

    
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        
        
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    	
    	System.out.print("Number of running threads to work with: ");
    	int workers = Integer.parseInt(reader.readLine());
    	
    	System.out.print("The number of completed tasks in one iteration: ");
    	int tasks = Integer.parseInt(reader.readLine());
    	
    	System.out.print("Number of iterations: ");
    	int howManyRepeatWork = Integer.parseInt(reader.readLine());
    	
        PullManagerAndWorkers man1 = new PullManagerAndWorkers(workers, tasks, howManyRepeatWork);
        
        man1.startNewWorkingDay();
        man1.joinWorkingDay();
        
        System.out.println("End of main");
    }
}
