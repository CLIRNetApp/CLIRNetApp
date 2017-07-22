package app.clirnet.com.clirnetapp.models;

/**
 * Created by Ashish on 8/28/2016.
 */
//model class
public class LoginModel {

    private String act_followupdate;
    private String userName;
    private String passowrd;
    private String visit_date;
    private String added_on;
    private String id;
    private String name;
    private String speciality;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    private String visit_id;

    public LoginModel(String id, String name, String speciality) {
    }

    public String getVisit_id() {
        return visit_id;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public String getAdded_on() {
        return added_on;
    }


    public String getAct_followupdate() {
        return act_followupdate;
    }


    public String getUserName() {
        return userName;
    }


    public String getPassowrd() {
        return passowrd;
    }


    public LoginModel(String name, String password) {
        this.userName = name;
        this.passowrd = password;
    }

    public LoginModel(String visit_id, String visit_date, String added_on, String actual_follow_up_date) {
        this.visit_id = visit_id;
        this.visit_date = visit_date;
        this.added_on = added_on;
        this.act_followupdate = actual_follow_up_date;
    }
}
