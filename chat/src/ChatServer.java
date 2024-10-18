import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ChatServer implements ChatService {

    private List<String> messages = new ArrayList<>();
    private Semaphore mutex = new Semaphore(1);

    @Override
    public void sendMessage(String sender, String message) throws RemoteException, InterruptedException{
        mutex.acquire();
        String s;
        if(message.equals("salir")){
            s = "--- " + sender + " ha salido del chat ---\n";
        }else if(message.equals("00entrada00")) {
            s = "--- " + sender + " ha entrado en el chat ---\n";

        }else {
                s = sender + ": " + message + "\n";
        }
        messages.add(s);
        System.out.println("Mensaje recibido");
        mutex.release();
    }

    @Override
    public List<String> receiveMessage() throws RemoteException, InterruptedException{
        mutex.acquire();
        try{
            System.out.println("Se van a enviar los mensajes");
            return messages;
        } finally {
            mutex.release();
        }

    }

    public ChatServer() {}


    public static void main(String args[]) {

        try {
            ChatServer server = new ChatServer(); //Instancia del servidor

            // El servidor se registra en el RMI como un objeto remoto y se obtiene su referencia remota
            ChatService ServidorChat = (ChatService) UnicastRemoteObject.exportObject(server, 0);

            // El servidor se registra en el registry con un nombre
            Registry registry = LocateRegistry.createRegistry(1013);

            registry.rebind("Servidor Chat", ServidorChat);
            System.err.println("Servidor Chat instalado"); // Servidor instalado con exito
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }


    }
}

