package app.clirnet.com.clirnetapp.models;

/**
 * Created by ${Ashish} on 8/28/2016.
 */
//model class
public class LoginModel {

    private String act_followupdate;
    private String userName;
    private String passowrd;



    private String docId;
    private String doctor_login_id;
    private String membership_id;
    private String firsttName;
    private String lastName;
    private String middleName;
    private String phoneNo;
    private String visit_id;

    public String getVisit_id() {
        return visit_id;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public String getAdded_on() {
        return added_on;
    }

    private String visit_date;
    private String  added_on ;



    public LoginModel(String id, String doctor_login_id, String membership_id, String first_name, String middle_name, String last_name, String email_id,String phNo,String email) {
        this.docId = id;
        this.doctor_login_id = doctor_login_id;

        this.membership_id = membership_id;

        this.firsttName = first_name;
        this.lastName = last_name;
        this.middleName = middle_name;
        this.emailId=email;
        this.phoneNo=phNo;
    }
    public String getAct_followupdate() {
        return act_followupdate;
    }
    public String getDocId() {
        return docId;
    }

    public String getDoctor_login_id() {
        return doctor_login_id;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public String getFirsttName() {
        return firsttName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    private String mobileNumber;
    private String emailId;

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

    public LoginModel(String visit_id, String visit_date,String added_on,String actual_follow_up_date) {
        this.visit_id = visit_id;
        this.visit_date=visit_date;
        this.added_on = added_on;
        this.act_followupdate=actual_follow_up_date;
    }
}
