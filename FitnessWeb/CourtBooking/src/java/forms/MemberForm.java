package forms;

import java.sql.Date;
import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

public class MemberForm extends ValidatorForm {
    private  ArrayList<MemberForm> members;

    public ArrayList<MemberForm> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<MemberForm> members) {
        this.members = members;
    }
    private int memberId;
    private int newMemberId;
    private int userType;
    private String lastName;
    private String firstName;
    private String password;
    private String email;
    private String phoneCell;
    private String phoneHome;
    private String phoneWork;
    private String address;
    private int status;
    private int memberShipType;
    private Date memberShipDate;
    private String action;
    private String updateFlag = "false";

    public int getNewMemberId() {
        return newMemberId;
    }

    public void setNewMemberId(int newMemberId) {
        this.newMemberId = newMemberId;
    }

    public String getLastNameFirstName() {
        return lastName + ", "+ firstName;
    }
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneCell() {
        return phoneCell;
    }

    public void setPhoneCell(String phoneCell) {
        this.phoneCell = phoneCell;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMemberShipType() {
        return memberShipType;
    }

    public void setMemberShipType(int memberShipType) {
        this.memberShipType = memberShipType;
    }

    public Date getMemberShipDate() {
        return memberShipDate;
    }

    public void setMemberShipDate(Date memberShipDate) {
        this.memberShipDate = memberShipDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    
    
}
