
package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends Thread{
    public int serverPort;
    ServerSocket serverSocket;
    Database db;
    ArrayList<ServerThread> clients = new ArrayList<ServerThread>();
    private String userString;
    ServerUI ui;

    
    public Server(int port,String filename,ServerUI ui) throws IOException {
        this.serverPort = port;
        serverSocket  = new ServerSocket(this.serverPort,10);
        db = new Database(filename);
        this.ui = ui;
        ui.serverMsgBox.append("# SIGNED UP:"+db.users.size()+"\n");
    }

    public void run()  {
        ui.serverMsgBox.append("SERVER SUCCESSFULLY STARTED...\n");
        ui.serverMsgBox.append("WAITING FOR CONNECTIONS.......\n");
        while(true){
            try{
                Socket newConnection = serverSocket.accept();
                ui.serverMsgBox.append("REQUEST FOR A CONNECTION ::"+newConnection.getPort()+"\n");
                ui.serverMsgBox.append("NUMBER OF CONNECTIONS ::"+(clients.size()+1)+"\n");
                if(clients.size()<10000){
                    ServerThread thread = new ServerThread(newConnection,this);
                    clients.add(thread);
                    thread.start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void processMessage(MessageProtocol message, ServerThread serverThread) throws IOException {
        MessageType type = message.type;
        if(type==MessageType.LOGIN){
            processLogin(message,serverThread);            
        }else if(type==MessageType.BROADCAST){
            broadCastMessage(message,serverThread);
        }else if(type==MessageType.PRIVATECHAT){
            sendMessageToASingleUser(message,serverThread);
        }else if(type==MessageType.SIGNUP){
            signup(message, serverThread);
        }else if(type==MessageType.SIGNOUT){
            signOut(message,serverThread);
        }
        
    }

    private void signOut(MessageProtocol message, ServerThread serverThread) {
        removeClient(serverThread);
    }

    private void signup(MessageProtocol message, ServerThread serverThread) throws IOException {
        String username = message.sender;
        String password = message.attributes.get("password");
        if(!db.userExists(username)){
            db.addUser(username,password);
            serverThread.send(new MessageProtocol("server",MessageType.SIGNUPSUCCESS,"you are signed up :-)",new HashMap<String, String>()));
        }else{
            serverThread.send(new MessageProtocol("server",MessageType.USEREXISTS,"User already exists.Choose a different username.",new HashMap<String, String>()));
        }

    }

    private void sendMessageToASingleUser(MessageProtocol message, ServerThread serverThread) throws IOException {
        for(ServerThread client : clients){
            if(message.attributes.containsKey("recepient") && message.attributes.get("recepient").equals(client.username)){
                client.send(message);
                break;
            }
        }
    }

    private void broadCastMessage(MessageProtocol message, ServerThread serverThread) throws IOException {
        for(int i=0;i<clients.size();i++){
            clients.get(i).send(message);
        }
    }

    private synchronized void processLogin(MessageProtocol message,ServerThread serverThread) throws IOException {
        String username = message.sender;
        String password = message.attributes.get("password");
        //DB check
        if(db.checkLogin(username,password)){
            serverThread.send(new MessageProtocol("server",MessageType.LOGINSUCCESS,"Login Success",new HashMap<String, String>()));
            String users = getUserString();
            broadCastMessage(new MessageProtocol("server", MessageType.USERLIST, users, new HashMap<String, String>()), serverThread);
        }else{
            serverThread.send(new MessageProtocol("server",MessageType.LOGINFAILED,"Login Failed",new HashMap<String, String>()));
        }

    }
    
    public void removeClient(ServerThread serverThread) {
        int i=0;
        for(i=0;i<clients.size();i++){
            if(clients.get(i).port==serverThread.port){
                break;                
            }
        }
        System.out.println("Removing user::"+clients.get(i).username);
        clients.remove(i);
        ui.serverMsgBox.append("NUMBER OF CONNECTIONS ::"+clients.size()+"\n");

    }

    public String getUserString() {
        userString="";
        for(int i=0;i<clients.size();i++){
            userString+=clients.get(i).username+"@@@";
        }
        return userString;
    }
}
