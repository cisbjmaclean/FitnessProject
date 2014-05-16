package actions;

import beans.CourtBooking;
import beans.Member;
import business.CourtBookingBO;
import forms.FutureForm;
import forms.MainForm;
import forms.MemberForm;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *
 * @author bjmaclean
 */
public class MainMenu extends Action {

    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        //First check if the user is logged in
        Member loggedIn = (Member) request.getSession().getAttribute("member");
        if (loggedIn == null || !loggedIn.isAuthenticated()) {
            ActionMessages messages = new ActionMessages();
            messages.add("error", (new ActionMessage("label.session.expired")));
            saveMessages(request, messages);
            return mapping.findForward("welcome");
        }

        //get the main form from the request.
        FutureForm mainForm = (FutureForm) request.getAttribute("futureForm");
        System.out.println("action" + mainForm.getAction());

        //if the user clicked a button, forward to forward specified in the button.
        if (mainForm.getAction().equalsIgnoreCase("Court Bookings")) {
            return mapping.findForward("main");
        } else if (mainForm.getAction().equalsIgnoreCase("Members")) {
            MemberForm thisMemberForm = new MemberForm();
            thisMemberForm.setMembers((ArrayList<MemberForm>) request.getSession().getAttribute("MemberList"));
            request.setAttribute("memberForm", thisMemberForm);
            return mapping.findForward("member");
        } else if (mainForm.getAction().equalsIgnoreCase("Logout")) {
            loggedIn.setAuthenticated(false);
            request.getSession().setAttribute("member", loggedIn);
            return mapping.findForward("welcome");
        } else {
            return mapping.findForward("welcome");
        }
    }
}
