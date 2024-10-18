import java.util.ArrayList;
import java.util.List;

public class HebraLectora extends Thread{

    private ChatService service;
    private List<String> oldMessages = new ArrayList<>();
    String sender;
    boolean stop = false;

    public HebraLectora(ChatService service, String sender) {
        this.service = service;
        this.sender = sender;
    }

    public void finish(){
        stop = true;
    }

    @Override
    public void run() {
        try{
            List<String> newMessages = new ArrayList<>();
            while(!stop) {
                newMessages = service.receiveMessage();
                if (!oldMessages.equals(newMessages)) {
                    oldMessages = newMessages;
                    System.out.println(newMessages);

                }

                sleep(1000);

            }

        } catch(Exception e){

            System.err.println("Excepcion de lectura: " + e.getMessage());
        }

    }
}
