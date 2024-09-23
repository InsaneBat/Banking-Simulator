/* Name: Axel Alvarado
Course: CNT 4714 Spring 2024
Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/
package developmentalVersion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ABankingSimulator 
{

	//10 withdrawal threads, 5 depositor threads, and 2 auditor threads
	public static final int MAX_AGENTS = 17;
	
	
	public static void main(String[] args) {
		//thread pool - size 17
		ExecutorService application = Executors.newFixedThreadPool(MAX_AGENTS); //executor object
		
		TheBankAccount jointAccount = new TheBankAccount(); //create a bank account to work with
		
		try {
			System.out.println("* * *  SIMULATION BEGINS...");
			System.out.println();
			
			//Start Threads = random order
			System.out.println("Deposit  Agents\t\t\t  Withdrawal Agents\t\t    Balance                        Transaction Number");
			System.out.println("---------------\t\t\t  -----------------\t\t-------------------\t\t\t------------");
			
			//start all threads running
			application.execute( new Withdrawal( jointAccount, "Agent WT1" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT2" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT3" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT4" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT5" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT6" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT7" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT8" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT9" ));
			application.execute( new Withdrawal( jointAccount, "Agent WT10" ));
			
			application.execute(new Depositor(jointAccount, "Agent DT1"));
			application.execute(new Depositor(jointAccount, "Agent DT2"));
			application.execute(new Depositor(jointAccount, "Agent DT3"));
			application.execute(new Depositor(jointAccount, "Agent DT4"));
			application.execute(new Depositor(jointAccount, "Agent DT5"));
			
			application.execute( new InternalAuditor (jointAccount));
			application.execute(new TreasuryDeptAuditor(jointAccount));
			
		}
		catch ( Exception exception)
		{
			exception.printStackTrace(); //something went wrong
		} //end catch break
		
		application.shutdown();
		

	}

}
