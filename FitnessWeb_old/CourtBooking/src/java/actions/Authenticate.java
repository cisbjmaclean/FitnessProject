package actions;

import beans.Member;
import business.AuthenticateBO;
import forms.LogonForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * This class will process the authentication from the logon information provided.
 *
 * @author bjmaclean
 * @since 20131129
 */
public class Authenticate extends Action {

    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        /*
         Get the logonForm from the request.  Note that the code knows from the struts-config
         that logonForm is the form associated with the authenticate action
         */

        LogonForm logonForm = (LogonForm) request.getAttribute("logonForm");
        System.out.println("Username entered=" + logonForm.getUserName());

        /*
         * create a new Member instance to be used in processing.
         */ 
        Member member = new Member(logonForm.getUserName(), logonForm.getPassword());


        boolean successfulLogin = true;

                /*
         Call Business Object (BO) to perform validation.  This should go to a business 
         object to do the actual login.
         */

        
        if (!AuthenticateBO.authenticate(member)) {
            successfulLogin = false;
        }

        /*
         * If successful forward to the main forward (see struts-config).  If not 
         * successful provide an error message and go back to the logon.
         */
        
        if (successfulLogin) {
            member.setAuthenticated(true);
            request.getSession().setAttribute("member", member);
            return mapping.findForward("main");
        } else {
            member.setAuthenticated(false);
            request.getSession().setAttribute("member", member);
            ActionMessages messages = new ActionMessages();
            messages.add("error", (new ActionMessage("label.login.failed")));
            saveMessages(request, messages);
            return mapping.findForward("welcome");
        }

    }
}
