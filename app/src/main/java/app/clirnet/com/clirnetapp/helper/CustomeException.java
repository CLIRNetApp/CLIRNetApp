package app.clirnet.com.clirnetapp.helper;

/**
 * Created by ${Ashish} on 01-11-2016.
 */

public class CustomeException extends Exception {

    public CustomeException() {
        super();
    }

    public CustomeException(String detailMessage) {
        super(detailMessage);
    }

    public CustomeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CustomeException(Throwable throwable) {
        super(throwable);
    }
}
