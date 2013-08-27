import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.Vector; 

/**
 * The bank server. 
 * It has the actual implementation of the methods. 
 * 
 * Author: Dongpu Jin
 * Date: 2/4/2013
 */
public class Bank extends UnicastRemoteObject implements BankInterface
{
    private Vector<Integer> userAccount; //users’ account
    private Vector<Integer> userBalance; //users’ balance

    public Bank() throws RemoteException{
        userAccount = new Vector<Integer>(); 
        userBalance = new Vector<Integer>(); 

        // initialization
        userAccount.addElement(100);
        userBalance.addElement(1000);  
    }

    /**
     * Deposit to the given account with the specific amount
     */
    public String deposit(int account, int amount) throws RemoteException{
        // get user account index
        int index = userAccount.indexOf(account); 
        
        if (index == -1){ // user account not found
            userAccount.addElement(account); 
            userBalance.addElement(amount); 
            return "Account not found. Account created.";
        }
        else { // add to existing amount
            int newBalance = userBalance.get(index) + amount; 
            userBalance.setElementAt(newBalance, index);
            return "Successfully deposit $" + amount + " to account " + account;  
        }
    }

    /**
     * Widthdraw from the given account with the specific amount
     */ 
    public String widthdraw(int account, int amount) throws RemoteException{
        // get user account index 
        int index = userAccount.indexOf(account);
        
        if (index == -1) { // user account not found 
            return "Account not found"; 
        } 
        else { // widthdraw from user account
            // get current balance 
            int balance = userBalance.get(index); 
            
            if (balance < amount){ // balance not enough
                return "Balance not enough. Action canceled."; 
            } 
            else { // widthdraw
                int newBalance = balance - amount; 
                userBalance.setElementAt(newBalance, index); 
                return "Successfully widthdraw $" + amount + " from account " + account;
            }
        } 
    }

    public String inquiry(int account) throws RemoteException{
        // get user account index 
        int index = userAccount.indexOf(account);
        
        if (index == -1) { // account not found 
            return "Account not found"; 
        }
        else {
            // get current balance 
            int balance = userBalance.get(index); 
            return "The current balance for account " + account + " is $" + balance; 
        } 
    }

    public static void main(String args[]) throws Exception{
        // default server port number
        int serverport = 1099;
        
        //get port number of argument
        if (args.length == 1){
            try {
                serverport = Integer.parseInt(args[0]);
				System.out.println("Using port: " + serverport); 
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        } 
        else{
            System.out.println("No port provided. Using default port: 1099"); 
        }
        
        // create and install a security manager 
        //if(System.getSecurityManager() == null){
        //    System.setSecurityManager(new RMISecurityManager());
        //}
        
		Registry registry = null; 
        try {
            registry = LocateRegistry.createRegistry(serverport); 
        } catch (RemoteException e) {
            System.out.println("java RMI registry on port " + serverport + " already exists");
			registry = LocateRegistry.getRegistry(); 
        }
        
        //init Bank server
		Bank bankobj  = new Bank();
		
		// bind this obj instance to the name "Bank"
        try {
            String name = "rmi://localhost" + ":" + serverport + "/Bank"; 
            Naming.rebind(name, bankobj);
            System.out.println("Server ready"); 
        } catch (Exception e){
            e.printStackTrace();  
        }
    }
}
