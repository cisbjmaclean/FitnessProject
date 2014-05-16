package actions;

import beans.CourtBooking;
import beans.Member;
import business.CourtBookingBO;
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
 *
 * @author bjmaclean
 */
public class Main extends Action {

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
        
        
        //get the main form from the request.
        MainForm mainForm = (MainForm) request.getAttribute("mainForm");
        System.out.println("Date entered=" + mainForm.getBookingDate());
        System.out.println("action"+mainForm.getAction());
        System.out.println("BOOKING DATE TEST ***"+mainForm.getBookingDate()+"***");
        mainForm.setBookingDate(mainForm.getBookingDate().trim());
        System.out.println("BOOKING DATE TEST ***"+mainForm.getBookingDate()+"***");
        
        //if the user clicked a button, forward to forward specified in the button.
        if ((mainForm.getAction() != null) && !mainForm.getAction().equals("") ){
            return mapping.findForward(mainForm.getAction());
        }

        //Call getCourtsForDate to populate the courts availabe for that date.
        ArrayList<CourtBooking> courtBookings = CourtBookingBO.getCourtsForDate(mainForm.getBookingDate());
        request.setAttribute("CourtBookings",courtBookings);
        
        return mapping.findForward("courtBooking");
    }
}
