import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Server class
public class Server
{


    private List<ClientHandler> clientHandlerList = new ArrayList<>();

    public List<ClientHandler> getClientHandlerList() {
        return clientHandlerList;
    }

    public  void execute()  {

        try {
            ServerSocket serverSocket = new ServerSocket(5555);
            System.out.println("serv is up");
            while (true){
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlerList.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}