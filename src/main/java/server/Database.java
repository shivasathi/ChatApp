package server;

import java.io.*;
import java.util.HashMap;

public class Database {

    public String filePath;

    HashMap<String,String> users = null;

    public Database(String filePath) throws IOException {
        this.filePath = filePath;
        readUsersFile();
    }

    public boolean userExists(String username){


        if(users==null){
            try{
                readUsersFile();
            }catch(Exception e){
                return false;
            }
        }

        return users.containsKey(username);

    }

    private void readUsersFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String newline = br.readLine();
        users = new HashMap<String, String>();
        while(newline!=null){
            String[] toks = newline.split("\\s+");
            users.put(toks[0],toks[1]);
            newline = br.readLine();
        }
    }

    public boolean checkLogin(String username, String password){

        if(!userExists(username)){
            return false;
        }

        try{
            if(users.get(username).equals(password))
                return true;
            return false;
        }
        catch(Exception ex){
            System.out.println("Database exception : userExists()");
            return false;
        }
    }

    public void addUser(String username, String password) {


        if(userExists(username))
            return;
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(filePath, true));
            br.write("\n"+username+"\t"+password);
            br.close();
        }catch (Exception e){

        }
        users.put(username,password);
    }

}
