package actions;

import beans.Member;
import business.MemberBO;
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
 * This action will take the information submitted by the user and update member
 * table
 *
 * @author bjmaclean
 * @since 20131129
 */
public class MemberEdit extends Action {

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
         * Get the information from the form.  
         */
        MemberForm memberForm = (MemberForm) request.getAttribute("memberForm");

        
        //check the action
        if (memberForm.getAction() != null && memberForm.getAction().equals("load")) {
            memberForm = MemberBO.loadDetails(memberForm);
        memberForm.setMembers((ArrayList<MemberForm>) request.getSession().getAttribute("MemberList"));
            request.setAttribute("memberForm", memberForm);
            return mapping.findForward("member");
        } else if (memberForm.getAction() != null && memberForm.getAction().equals("reset")) {
            //reset the form back to a new one
            MemberForm newMemberForm = new MemberForm();
            newMemberForm.setMembers((ArrayList<MemberForm>) request.getSession().getAttribute("MemberList"));
            request.setAttribute("memberForm", newMemberForm);
            return mapping.findForward("member");

        } else {
            
            MemberBO.updateMember(memberForm);
            ArrayList<MemberForm> members = MemberBO.getAllMembers();
            memberForm.setMembers(members);
            request.getSession().setAttribute("MemberList",members);
            request.setAttribute("memberForm", memberForm);
            messages.add("message1", (new ActionMessage("label.member.updated")));
            saveMessages(request, messages);
            return mapping.findForward("member");
        }
    }
}
