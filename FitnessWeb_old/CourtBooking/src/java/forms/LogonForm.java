package forms;


import org.apache.struts.validator.ValidatorForm;


public class LogonForm extends ValidatorForm {

	private String userName = "";

	private String password = "";
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
