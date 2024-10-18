import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HebraEscritora extends Thread{

    private ChatService service;
    String sender;
    HebraLectora lee;


    public HebraEscritora(ChatService service, String sender, HebraLectora lee) {
        this.service = service;
        this.sender = sender;
        this.lee = lee;
    }

    @Override
    public void run() {
        try{
            //Preparamos para leer de consola
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String message = "";
            System.out.println("Bienvenido al chat. Escribe \"salir\" para terminar. ");
            //escritor
            while(!message.equals("salir")){
                //System.out.println("Mensaje:");
                message = reader.readLine();

                service.sendMessage(sender, message); // Se invoca el servicio remoto
                //System.out.println("Mensaje enviado");

                //sleep(1);
            }
            lee.finish();


        } catch(Exception e){

            System.err.println("Excepcion del escritor: " + e.getMessage());
        }

    }
}
