/* Name: Axel Alvarado
Course: CNT 4714 Spring 2024
Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

package developmentalVersion;

import java.util.Random;

public class Depositor implements Runnable {

	//define class variable and constants as needed
	private static final int MAX_DEPOSIT = 500;
	private static final int MAXSLEEPTIME = 5000;
	private static Random generator = new Random();
	private static Random sleepTime = new Random();
	private TheBankAccount jointAccount;
	String threadName;
	
	//constructor
	public Depositor(TheBankAccount joint, String name)
	{
		//create a depositor instance
		this.jointAccount = joint;
		this.threadName = name;
		
	}//end Depositor constructor
	
	
	//Add money to the bank account 
	public void run()
	{
		while(true) //run depositor in infinite loop
		{
			try//sleep random time for simulation, then add money 
			{
				//Thread.sleep(sleepTime.nextInt(MAXSLEEPING-1+1+1); //sleep thread - was 1500
				
				jointAccount.deposit(generator.nextInt(MAX_DEPOSIT) + 1, threadName);
				Thread.sleep(sleepTime.nextInt(MAXSLEEPTIME)+1);
				//add money to the bank account
			}//end try
			catch(InterruptedException e)
			{
				System.out.print("Exception thrown depositing !");
				e.printStackTrace();
			}//end catch
		}//end while
		
	}//end method run

}//end class Depositor 
