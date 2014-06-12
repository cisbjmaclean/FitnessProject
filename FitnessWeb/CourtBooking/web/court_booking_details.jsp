<%-- 
    Document   : main
    Created on : Nov 13, 2013, 2:51:24 PM
    Author     : bjmaclean
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/InsertCourtBooking">


    <%-- The hidden fields are used to make sure that the properties that are not displayed on the form
         are passed through to the next action.  These will not be passed through if they are not contained 
         on the form.
    --%>


    <html:hidden property="bookingDate"/>
    <html:hidden property="startTime"/>
    <html:hidden property="memberId"/>
    <html:hidden property="courtNumber"/>

    <%--
    
    This form displays the information that is already obtained but also asks the use to enter the
    additional information that is needed to complete the court booking.
    
    --%>

    <div>
        <h3>
            <strong><bean:message key="label.additional.details" /></strong>

        </h3>    

        <table>

            <tr>
                <td>
                    <%--  These messages can be used to display either messages, warnings, or errors from your
actions.  You'll notice that once a court is booked a message is put out as an 
information message.  --%>


                    <logic:messagesPresent message="true">
                        <html:messages id="msg2" message="true" property="message1"><div class="infoMessageCheck" style="color: green"><bean:write name="msg2"/></div><br/></html:messages>				  		
                        <html:messages id="msg2" message="true" property="warn"><div class="warnExclaim"  style="color: yellow"><bean:write name="msg2"/></div><br/></html:messages>
                        <html:messages id="msg2" message="true" property="error"><div class="errorX"  style="color: red"><bean:write name="msg2"/></div><br/></html:messages>				  		
                    </logic:messagesPresent>
                    <div style="color:red">
                        <html:errors />
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="memberId">
                        <strong><bean:message key="label.member.id" /></strong></label>
                    <bean:write name="mainForm" property="memberId"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="courtNumber">
                        <strong><bean:message key="label.court.number" /></strong></label>
                    <bean:write name="mainForm" property="courtNumber"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="startTime">
                        <strong><bean:message key="label.start.time" /></strong></label>
                    <bean:write name="mainForm" property="startTime"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="notes">
                        <strong><bean:message key="label.notes" /></strong></label>
                    <input type="text" name="notes" id="notes" size="20" value="" />
                </td>
            </tr>
        </table>

        <div>
            <input type="submit" value="submit"/>
            <input type="reset" value="reset"/>
        </div>
    </div>
</body>

</html:form>
