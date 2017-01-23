package alexis.boulet.channelmessaging;

/**
 * Created by bouleta on 23/01/2017.
 */
public class Channels {

    private int channelID;
    private String name;
    private int connectedusers;

    public Channels(int channelID, String name, int connectedusers) {
        this.channelID = channelID;
        this.name = name;
        this.connectedusers = connectedusers;
    }
}
