import java.io.*;
import java.net.Socket;

public class Client
{


    private String userName;
    PrintWriter out;
    BufferedReader mesStream;
    BufferedReader in;


    public Client(){}


    public void execute()
    {



        try {
            Socket socket = new Socket("localhost", 5555);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            mesStream = new BufferedReader(new InputStreamReader(System.in));


            System.out.println("Enter your user name");
            userName = mesStream.readLine();
            //out.println(userName);


            Thread outThread = new Thread(() -> {
                while (true){
                    String mes;
                    try {
                        if ((mes = mesStream.readLine()) != null) {
                            out.println("[" + userName + "]: " + mes);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread inThread = new Thread(() -> {
                while (true) {
                    String input;
                    try {
                        if ((input = in.readLine()) != null) System.out.println(input);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            outThread.start();
            inThread.start();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
