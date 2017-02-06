package alexis.boulet.channelmessaging;


import java.util.ArrayList;

/**
 * Created by bouleta on 30/01/2017.
 */
public class Messages {

    private ArrayList<Message> messages;

    public ArrayList<Message> getMessagesList() {
        return messages;
    }

    public Messages(ArrayList<Message> messagesList) {
        this.messages = messagesList;
    }

    @Override
    public String toString() {
        String result = "";
        for(Message mess:messages)
        {
            result += mess.toString() + "\n";
        }
        return result;
    }
}
