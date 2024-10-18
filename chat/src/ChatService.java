import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {

    void sendMessage(String sender, String message) throws RemoteException, InterruptedException;

    List<String> receiveMessage() throws RemoteException, InterruptedException;

}
