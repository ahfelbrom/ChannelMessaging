package alexis.boulet.channelmessaging;

import java.io.IOException;

/**
 * Created by bouleta on 22/03/2017.
 */
public interface OnUploadFileListener {

        public void onResponse(String result);
        public void onFailed(IOException error);
}

