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
 * This action will execute when the user selects a hyperlink from the list 
 * of courts that are available.  It will put the information in a mainForm to be used on the 
 * getCourtDetails page.  
 * 
 * @author bjmaclean
 * @since 20131129
 */
public class GetCourtBookingDetails extends Action {

    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        //First check if the user is logged in
        Member loggedIn = (Member) request.getSession().getAttribute("member");
        if (loggedIn == null || !loggedIn.isAuthenticated()){
                        ActionMessages messages = new ActionMessages();
            messages.add("error", (new ActionMessage("label.session.expired")));
            saveMessages(request, messages);
            return mapping.findForward("welcome");
        }
        
        /*
         * Get the values provided as parameters in the hyperlink.
         */
        
        String courtNumber = request.getParameter("courtNumber");
        String startTime = request.getParameter("startTime");
        String bookingDate = request.getParameter("bookingDate");
        
        /*
         * Setup the form to be used on the next page displayed to the user.
         */
        
        MainForm mainForm = new MainForm();
        mainForm.setCourtNumber(courtNumber);
        mainForm.setMemberId(loggedIn.getMemberId());
        System.out.println("memberId="+loggedIn.getMemberId());
        mainForm.setBookingDate(bookingDate);
        mainForm.setStartTime(startTime);
        request.setAttribute("mainForm", mainForm);
        return mapping.findForward("courtBookingDetails");
    }
}
