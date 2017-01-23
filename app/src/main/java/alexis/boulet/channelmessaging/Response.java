package alexis.boulet.channelmessaging;

/**
 * Created by bouleta on 20/01/2017.
 */
public class Response {
    private String response;

    public String getCode() {
        return code;
    }

    private String code;
    private String accesstoken;

    public String getAccessToken() {
        return accesstoken;
    }

    public Response(String result, String code, String acces) {
        this.response = result;
        this.code = code;
        this.accesstoken = acces;
    }

    @Override
    public String toString() {
        return "res = " + response + " + code = " + code + " acces token = " + accesstoken;
    }
}
