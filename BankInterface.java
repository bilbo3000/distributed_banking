import java.rmi.Remote; 
import java.rmi.RemoteException; 

/**
 * The interface for the distributed banking system. 
 * This interface provides method interfaces that 
 * are to be implemented by the server. 
 * 
 * Author: Dongpu Jin
 * Date: 2/1/2013
 */
public interface BankInterface extends Remote{
    public String deposit(int account, int amount) throws RemoteException; 
    public String widthdraw(int account, int amount) throws RemoteException; 
    public String inquiry(int account) throws RemoteException; 
}
