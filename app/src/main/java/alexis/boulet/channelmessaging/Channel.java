package alexis.boulet.channelmessaging;

/**
 * Created by bouleta on 23/01/2017.
 */
public class Channel {

    private int channelID;
    private String name;

    public int getConnectedusers() {
        return connectedusers;
    }

    public String getName() {
        return name;
    }

    public int getChannelID() {
        return channelID;
    }

    private int connectedusers;

    public Channel(int channelID, String name, int connectedusers) {
        this.channelID = channelID;
        this.name = name;
        this.connectedusers = connectedusers;
    }

    @Override
    public String toString() {
        return "id = " + channelID + " nom = " + name + " user connect = " + connectedusers + "\n";
    }
}
