package client;

import server.MessageProtocol;
import server.MessageType;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientThread  extends Thread{
//this i amethios
    public String serverAddress,username;
    public int serverPort;
    Socket serverSocket;
    ObjectOutputStream socketWriter;
    ObjectInputStream socketReader;
    public ClientUI ui;
    ArrayList<String> blockedUsers = new ArrayList<String>();


    public ClientThread(ClientUI clientUI, String serverAddress, int serverPort) throws IOException {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        serverSocket = new Socket(InetAddress.getByName(serverAddress), serverPort);
        this.socketWriter = new ObjectOutputStream(serverSocket.getOutputStream());
        this.socketReader  = new ObjectInputStream(serverSocket.getInputStream());
        this.ui = clientUI;
    }

    public void loginServer(String username,String password) throws IOException {
        HashMap<String,String> attr = new HashMap<String, String>();
        this.username = username;
        attr.put("password",password);
        MessageProtocol msg = new MessageProtocol(username, MessageType.LOGIN, "Trying to Login", attr);
        send(msg);
    }

    public void sendBroadCastMessage(String username,String message) throws IOException {
        HashMap<String,String> attr = new HashMap<String, String>();
        MessageProtocol msg = new MessageProtocol(username, MessageType.BROADCAST, message, attr);
        send(msg);
    }

    public void sentPrivateMessage(String username,String recepient, String message) throws IOException {
        HashMap<String,String> attr = new HashMap<String, String>();
        attr.put("recepient",recepient);
        MessageProtocol msg = new MessageProtocol(username, MessageType.PRIVATECHAT, message, attr);
        send(msg);
    }

    public void signUp(String username, String password) throws IOException {
        HashMap<String,String> attr = new HashMap<String, String>();
        attr.put("password",password);
        this.username = username;
        MessageProtocol msg = new MessageProtocol(username, MessageType.SIGNUP, "Signup process", attr);
        send(msg);
    }

    public void send(MessageProtocol message) throws IOException {
        socketWriter.writeObject(message);
    }

    public void run()  {
        while(true){
            MessageProtocol message = null;
            try {
                message = (MessageProtocol)socketReader.readObject();
                processMessage(message);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    public void processMessage(MessageProtocol message) throws IOException {
        MessageType type = message.type;
        if(type.equals(MessageType.LOGINSUCCESS)){
            ui.proceedToMainScreen(this.username);
        }else if(type.equals(MessageType.LOGINFAILED)){
            JOptionPane.showMessageDialog(null, "Login Failed");
        }else if(type.equals(MessageType.USEREXISTS)){
            JOptionPane.showMessageDialog(null, "Username exists");
        }else if(type.equals(MessageType.SIGNUPSUCCESS)){
            JOptionPane.showMessageDialog(null, "Congratulations...You have signed up successfully..");
            ui.proceedToMainScreen(this.username);
        }
        else if(type.equals(MessageType.BROADCAST)){
            ui.mainTextBox.append(message.sender + "--> All" + " >>> " + message.message + "\n");
        }else if(type.equals(MessageType.PRIVATECHAT)){
            if(!blockedUsers.contains(message.sender))
                ui.mainTextBox.append(message.sender+"--> " +username+" >> "+ message.message+"\n");
        }
        else if(type.equals(MessageType.USERLIST)){
            String[] toks = message.message.split("@@@");
            ui.model.clear();
            ui.model.addElement("All");
            ui.userList.setSelectedIndex(0);
            for(int i=0;i<toks.length;i++){
                if(username.equals(toks[i]))continue;
                System.out.println(toks[i]);
                ui.model.addElement(toks[i]);
            }
        }

    }


}
