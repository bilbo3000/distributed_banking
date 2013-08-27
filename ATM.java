import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException; 
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The ATM client. 
 * It performance various of operations supported by 
 * the services provided by the bank server.  
 * 
 * Author: Dongpu Jin
 * Date: 2/4/2013
 */
public class ATM{
    // reference of the remote object 
    static BankInterface obj = null; 
    
    /**
     * rpc to deposit operation 
     */ 
    public void deposit(int account, int amount) throws RemoteException{
        System.out.println(obj.deposit(account, amount));
    }
    
    /**
     * rpc to widthdraw operation
     */
    public void widthdraw(int account, int amount) throws RemoteException{
        System.out.println(obj.widthdraw(account, amount));
    }
    
    /**
     * rpc to inquiry operation
     */ 
    public void inquiry(int account) throws RemoteException{ 
        System.out.println(obj.inquiry(account)); 
    }

    public static void main (String args[]) throws Exception{
        String server_address = null; 
        int server_port = 0; 
        String operation = null; 
        int account = 0; 
        int amount = 0; 
        
        // check arguments
        if (args.length == 4) { // inquiry
            if (!args[2].equals("inquiry")){
                System.out.println("Invalid arguments. Exit.");
                System.exit(1); 
            } 
            
            // get user's input
            try {
                server_address = args[0]; 
                server_port = Integer.parseInt(args[1]); 
                operation = args[2]; 
                account = Integer.parseInt(args[3]);  
            } catch (Exception e){
                System.out.println("Invalid arguments"); 
                e.printStackTrace(); 
            }
        } 
        else if (args.length == 5) { // deposit or widthdraw
            if(args[2].equals("inquiry")){
                System.out.println("Invalid arguments. Exit."); 
                System.exit(1); 
            }
            
            // get user's input
            try {
                server_address = args[0]; 
                server_port = Integer.parseInt(args[1]); 
                operation = args[2]; 
                account = Integer.parseInt(args[3]);
                amount = Integer.parseInt(args[4]); 
            }
            catch (Exception e) {
                System.out.println("Invalid arguments"); 
                e.printStackTrace();
            }
        } 
        else { // invalid arguments 
            System.out.println("Invalid arguments. Exit."); 
            System.exit(1); 
        } 
        
        // create and install a security manager
        // if (System.getSecurityManager() == null){
        //    System.setSecurityManager(new RMISecurityManager()); 
        // }

        // instantiate a client obj
        ATM ATMClient = new ATM();
        
        // look up server 
        try {
            String name = "rmi://" + server_address + ":" + server_port + "/Bank"; 
			obj = (BankInterface)Naming.lookup(name);
        } catch (Exception e){ 
            System.out.println("ATM client exception: " + e); 
            e.printStackTrace(); 
        }
        
        if (operation.equals("inquiry")) {
            ATMClient.inquiry(account);
        } 
        else if (operation.equals("deposit")) {
            ATMClient.deposit(account, amount); 
        } 
        else if (operation.equals("widthdraw")) {
            ATMClient.widthdraw(account, amount); 
        }
         
    }
}
