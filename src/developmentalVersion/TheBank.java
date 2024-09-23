
/* Name: Axel Alvarado
Course: CNT 4714 Spring 2024
Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

package developmentalVersion;
//Bank interface specifies abstract methods to define the allowed behaviors on the bank account

public interface TheBank 
{

	//deposit argument: deposit amount, thread name making the deposit 
	public abstract void deposit(int depAmount, String threadName);
	
	//Withdrawal argument: withdrawal amount, thread name making the withdrawal 
	public abstract void withdrawal(int withAmount, String threadName);
	
	//flaggedtransactions are logged independently into a log file 
	//flagged transactions arguments: transaction amount, thread name making the transaction , type of thread making the transaction 
	//use "D" for depositor thread type, and "W" for withdrawal thread type 
	public abstract void flagged_transaction (int transAmount, String threadName, String threadType);
	
	//internal banking audit - examine balance only 
	public abstract void internalAudit ();
	
	//external banking audit - Treasury Dept - examines balance only 
	public abstract void treasuryAudit ();
	

}
