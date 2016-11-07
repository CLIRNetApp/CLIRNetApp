package app.clirnet.com.clirnetapp.models;

/**
 * Created by ${Ashish} on 7/13/2016.
 */
public class CallAsynOnce {

    private static String value;
    private static String loginResult;

    public void setValue(String t) {
        value = t;
    }

    public String getValue() {
        return value;
    }

    public void setValue1(String t) {
        loginResult = t;
    }

    public String getValue1() {
        return loginResult;
    }



}
