package app.clirnet.com.clirnetapp.helper;

/**
 * Created by Ashish on 01-11-2016.
 */

public class ClirNetAppException extends Exception {

    public ClirNetAppException() {
        super();
    }

    public ClirNetAppException(String detailMessage) {
        super(detailMessage);
    }

    public ClirNetAppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ClirNetAppException(Throwable throwable) {
        super(throwable);
    }
}
