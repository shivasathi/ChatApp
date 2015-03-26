package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{

    ObjectOutputStream socketWriter;
    ObjectInputStream socketReader;
    int port;
    Server server;
    String username;


    public ServerThread(Socket newConnection, Server server) throws IOException {
        this.socketWriter = new ObjectOutputStream(newConnection.getOutputStream());
        this.socketReader  = new ObjectInputStream(newConnection.getInputStream());
        this.port = newConnection.getPort();
        this.server = server;
    }

    @Override
    public void run() {
        while(true){
            MessageProtocol message = null;
            try {
                message = (MessageProtocol)socketReader.readObject();
                username = message.sender;
                System.out.println(message);
                server.processMessage(message,this);
            } catch (Exception e) {
                server.removeClient(this);
                stop();
            }
        }
    }

    public void send(MessageProtocol message) throws IOException {
        socketWriter.writeObject(message);
    }


}
