package alexis.boulet.channelmessaging;

/**
 * Created by bouleta on 20/01/2017.
 */
public class Response {
    private String res;
    private String code;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public Response(String result, String code, String acces) {
        this.res = result;
        this.code = code;
        this.accessToken = acces;


    }
}
