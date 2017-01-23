package alexis.boulet.channelmessaging;

import java.util.ArrayList;

/**
 * Created by bouleta on 23/01/2017.
 */
public class Channels {
    private ArrayList<Channel> channels;

    public Channels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    @Override
    public String toString() {
        String result = "";
        for(Channel chan:channels)
        {
            result += chan.toString();
        }
        return result;
    }
}
