package forms;


import org.apache.struts.validator.ValidatorForm;


public class FutureForm extends ValidatorForm {

String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
