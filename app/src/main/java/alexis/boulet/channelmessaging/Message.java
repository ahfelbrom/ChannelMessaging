package alexis.boulet.channelmessaging;

/**
 * Created by bouleta on 30/01/2017.
 */
public class Message {
    private int userID;
    private String username;
    private String message;
    private String date;
    private String imageUrl;

    public Message(int userID, String username, String message, String date, String imageUrl) {
        this.userID = userID;
        this.username = username;
        this.message = message;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "userID=" + userID + ", message='" + message + '\'' + ", date='" + date + '\'' + ", imageUrl='" + imageUrl + '\'';
    }
}
