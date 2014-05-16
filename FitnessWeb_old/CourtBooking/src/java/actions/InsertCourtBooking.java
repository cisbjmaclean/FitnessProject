package actions;

import beans.CourtBooking;
import beans.Member;
import business.AuthenticateBO;
import business.CourtBookingBO;
import forms.LogonForm;
import forms.MainForm;
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
 * This action will take the information submitted by the user and insert the court booking.
 *
 * @author bjmaclean
 * @since 20131129
 */
public class InsertCourtBooking extends Action {

    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        ActionMessages messages = new ActionMessages();

        //First check if the user is logged in
        Member loggedIn = (Member) request.getSession().getAttribute("member");
        if (loggedIn == null || !loggedIn.isAuthenticated()) {
            messages.add("error", (new ActionMessage("label.session.expired")));
            saveMessages(request, messages);
            return mapping.findForward("welcome");
        }

        /*
         * Get the information from the main form.  
         */

        MainForm mainForm = (MainForm) request.getAttribute("mainForm");
        if (mainForm.getMemberIdOpponent() == null || mainForm.getMemberIdOpponent().equals("")) {
            mainForm.setMemberIdOpponent("0");
        }

        /*
         * Call the method to book the court.  Not sure why I didn't just pass in the mainForm...
         */

        CourtBookingBO.bookCourt(mainForm.getBookingDate(),
                mainForm.getStartTime(),
                Integer.parseInt(mainForm.getCourtNumber()),
                Integer.parseInt(mainForm.getMemberId()),
                Integer.parseInt(mainForm.getMemberIdOpponent()),
                mainForm.getNotes());
        messages.add("message1", (new ActionMessage("label.court.booked.success")));
        saveMessages(request, messages);

        //forward the user back to the main form.
        request.setAttribute("mainForm", mainForm);
        return mapping.findForward("runMain");
    }
}
