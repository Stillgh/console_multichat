import java.io.*;
import java.net.Socket;
import java.util.List;

class ClientHandler implements Runnable{

    Socket clientSocket;
    Server server;
    PrintWriter out;

    public ClientHandler(Socket s, Server server) {
        this.clientSocket = s;
        this.server = server;
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handle() {

        System.out.println("got new client");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String request;
            while ((request = in.readLine()) != null) {
                System.out.println(request);
                broadcast(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void broadcast(String message){
        List<ClientHandler> clientHandlerList = server.getClientHandlerList();
        for (ClientHandler ch: clientHandlerList) {
            if (ch != this) {
                //ch.out.println(clientSocket.toString() + " from serv " + message);
                ch.out.println(message);

            } else {
                //message = message.substring(message.indexOf(']'));
                ch.out.println("[You" + message.substring(message.indexOf(']')));
            }
        }
    }

    @Override
    public void run() {
            handle();
    }
}