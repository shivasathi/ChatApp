package server;

import java.io.Serializable;
import java.util.HashMap;


public class MessageProtocol  implements Serializable {

    private static final long serialVersionUID = 1L;
    public String sender;
    public String message;
    public MessageType type;
    HashMap<String,String> attributes=new HashMap<String, String>();


    public  MessageProtocol(String sender,MessageType type, String message,HashMap<String,String> attributeMap){
        this.sender = sender;
        this.type = type;
        this.message = message;
        for(String attr : attributeMap.keySet()){
            attributes.put(attr,attributeMap.get(attr));
        }
    }

    public String toString(){
        String ans =  "{sender='"+this.sender+"',type='"+this.type+"',message='"+message+"',attr='"+attributes+"'";
        return ans;
    }


}
