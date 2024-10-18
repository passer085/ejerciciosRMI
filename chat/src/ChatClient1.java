import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatClient1 {


    public ChatClient1() {}

    public static void main(String args[]) {

        String host = null;

        if (args.length >=1) host = args[0];

        try {
            // Se localiza el servidor en el registro por su nombre
            Registry registry =	LocateRegistry.getRegistry(1013);
            ChatService service = (ChatService) registry.lookup("Servidor Chat");

            //Preparamos para leer de consola
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //Pedimos nombre de usuario
            System.out.println("Ingrese su nombre de usuario");
            String sender = reader.readLine();
            service.sendMessage(sender, "00entrada00");


            //hebra lectora
            HebraLectora hebritaLee = new HebraLectora(service, sender);
            hebritaLee.start();

            HebraEscritora hebritaEscribe = new HebraEscritora(service, sender, hebritaLee);
            hebritaEscribe.start();

            hebritaEscribe.join();
            hebritaLee.join();
            //System.out.println(service.receiveMessage());
       } catch (Exception e) {
            System.err.println("Excepcion del cliente: " + e.getMessage());
       }


   }

}