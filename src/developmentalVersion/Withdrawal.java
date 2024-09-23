/* Name: Axel Alvarado
Course: CNT 4714 Spring 2024
Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

package developmentalVersion;

import java.util.Random;

public class Withdrawal implements Runnable
{

	//define variables and constants as needed
	private static final int MAX_WITHDRAWAL = 99;
	private static final int MAXSLEEPTIME = 1000;
	private static Random generator = new Random();
	private static Random sleepTime = new Random();
	private TheBankAccount jointAccount;
	String threadName;
	
	
	//constructor
	
	public Withdrawal(TheBankAccount joint, String name)
	{
		this.jointAccount = joint;
		this.threadName = name;
		//create a Withdrawal instance
	}//end Withdrawal constructor
	
	public void run()
	{
		while(true) //run instance in infinite loop
		{
			int amount = generator.nextInt(MAX_WITHDRAWAL) + 1;
			try //sleep random time for the simulation, then withdraw the money
			{
				jointAccount.withdrawal(amount, threadName);
				Thread.sleep(sleepTime.nextInt(MAXSLEEPTIME)+ 1);
				//sleep random time
				//withdraw money
			}
			catch (Exception e)
			{
				System.out.print("Exception throw withdrawing !");
				e.printStackTrace();
			}//end catch
		}//end while
	}//end method run
	
}//end class Withdrawal
