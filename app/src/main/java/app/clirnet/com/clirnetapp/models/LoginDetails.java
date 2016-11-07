package app.clirnet.com.clirnetapp.models;

public class LoginDetails {

	private  String mActive;
	private  String mUnicode;
	private String mEmailId;
	private String mPassword;




	public LoginDetails(String emailId,String password,String unicode,String active) {
		
		this.mEmailId = emailId;
		this.mPassword = password;
		this.mUnicode =unicode;
		this.mActive =active;
		
	}

	public String getEmailId() {
		return mEmailId;
	}

	public String getPassword() {
		return mPassword;
	}
	public String getUnicode() {
		return mUnicode;
	}
	public String getActive() {
		return mActive;
	}
}
