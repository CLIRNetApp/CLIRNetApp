package app.clirnet.com.clirnetapp.models;

public class LoginResult {

	private String mMsg;
	private Boolean mIsSuccessfull;
	private String mUserId;
	public LoginResult(){

	}

	public LoginResult(String mMsg, Boolean mIsSuccessfull) {
		this.mMsg = mMsg;
		this.mIsSuccessfull = mIsSuccessfull;
	}

	public LoginResult(String msg,Boolean isSuccessfull,String userId) {
		
		this.mMsg = msg;
		this.mIsSuccessfull = isSuccessfull;
		this.mUserId = userId;
		
	}

	public String getMsg() {
		return mMsg;
	}

	public Boolean getIsSuccessfull() {
		return mIsSuccessfull;
	}

	public String getUserId() {
		return mUserId;
	}

	public void setmUserId(String mUserId) {
		this.mUserId = mUserId;
	}
	
	
}
