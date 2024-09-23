/* Name: Axel Alvarado
Course: CNT 4714 Spring 2024
Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

//BankAccount synchronizes access to a single bank account
package developmentalVersion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TheBankAccount implements TheBank
{

	//Define the lock to control mutually exclusive access to the bank account
	private int bal = 0;
	private Lock accLock = new ReentrantLock();
	
	//Define any condition variables for this lock
	private Condition sufficentFCondition = accLock.newCondition();
	
	//Define the variables and constants for the bank account
	private int transNum = 0;
	
	
	
	//method used to log flagged transactions made against the bank account
	public void flagged_transaction(int transAmount, String threadName, String threadType)
	{
		double extraAmount = 0.0;
		
		//generate a Date object for timestamping 
		LocalDateTime now = LocalDateTime.now();
		
		ZoneId zoneID = ZoneId.systemDefault();
		String sZone = zoneID.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		
		//set the Date object format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		String formatDate = now.format(format) + " " + sZone;
		
		
		//create an output transaction file
		File file = new File("transactions.csv");
		
		
			if(threadType.equals("Deposit"))
			{
				extraAmount = 350.00;
			}
			else //transactino type is a withdrawal
			{
				extraAmount = 75.00;
			}
			
			System.out.println("* * * Flagged Transaction - " + threadType + " " + threadName + " Made a " + threadType + " In Excess of $" + extraAmount + " USD - See Flagged Transaction Log" + "\n");
			
			//write the string to the transaction file
			try (FileWriter write = new FileWriter(file, true))
			{
				if(threadType.equals("Deposit"))
				{
				write.write(threadType + " " + threadName + " issued a " + threadType + " of $" + transAmount + " at " + formatDate + " Transaction Number : " + transNum + System.lineSeparator()+"\n");
				}
				else
				{
				write.write("\t" + threadType + " " + threadName + " issued a " + threadType + " of $" + transAmount + " at " + formatDate + " Transaction Number : " + transNum + System.lineSeparator()+"\n");
				}
			} catch (IOException e) {
				System.out.println("Error writing to the file ");
			}
			
		}//end method flagged_transaction
	
	
	//method used to make a deposit into the bank account
	
	public void deposit(int depAmount, String threadName)
	{
		//lock the bank account
		accLock.lock();
		//wait for auditor to complete
		//output thread information, deposit amount and account balance then signal any waiting withdrawal threads
		try
		{
			//make deposit
			bal += depAmount;
			
			//handle transaction logging for flagged transaction
			transNum += 1;
			System.out.println(threadName + " deposits $" + depAmount + "\t\t\t\t\t\t" + "(+) Balance is $" + bal + "\t\t\t\t" + transNum);
			
			if(depAmount > 350)
			{
				System.out.println();
				flagged_transaction(depAmount, threadName, "Deposit");
			}
			
			//signal all waiting withdrawal threads waiting to make a withdrawal 
			sufficentFCondition.signalAll();
			
		}//end try block
		catch(Exception e)
		{
			System.out.print("Exception thrown depositing");
		}//end catch block
		finally
		{
			accLock.unlock();//unlock the bank account
		}//end finally block
	}//end method deposit
	
	
	
	//method used to make a withdrawal from the bank account
	public void withdrawal(int withAmount, String threadName)
	{
		//lock the bank account
		accLock.lock();
		//wait for auditor to complete
		//output thread information, withdrawal amount, and account balance. Block on insufficient funds
		try
		{
			//check balance
			//if balance too low
			//block for deposit to occur
			if(bal < withAmount)
			{
				System.out.println("\t\t\t\t" + threadName + " withdraws $" + withAmount + "\t\t(******) WITHDRAWAL BLOCKED - INSUFFICENT FUNDS!!!");
				sufficentFCondition.await();
			}
			//else
			//make withdrawal
			//handle transaction logging for flagged transaction
			else
			{
				bal -= withAmount;
				transNum += 1;
				System.out.println("\t\t\t\t" + threadName + " withdraws $" + withAmount + "\t\t(-) Balance is $" + bal + "\t\t\t\t" + transNum);
				if(withAmount> 75)
				{
					System.out.println();
					flagged_transaction(withAmount, threadName, "Withdrawal");
				}
			}

		} //end try block
		catch(Exception e)
			{System.out.println("An Exception was thrown getting the withdrawal.");}
		finally
		{
			accLock.unlock();//unlock the bank account
		}//end finally block
		}//end method internalAudit
	
	public int getBal()
	{
		return bal;
	}
	
	public int getTransNum()
	{
		return transNum;
	}


	@Override
	public void internalAudit() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void treasuryAudit() {
		// TODO Auto-generated method stub
		
	}
	

	
	
}//end class TheBankAccount
	

