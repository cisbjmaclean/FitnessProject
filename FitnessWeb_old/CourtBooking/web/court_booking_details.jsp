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
    <br/>

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

    <div style="text-align: center; height: 300px; background-color: #efefef; padding: 10px; -webkit-border-radius: 12px; -moz-border-radius: 12px; margin-right: 10px">
        <h3 style="text-align: center; -webkit-border-radius: 10px; -moz-border-radius: 10px; margin-top: 0px; margin-bottom: 20px; padding: 8px">
            <strong><bean:message key="label.additional.details" /></strong>

        </h3>    

        <table align="center" style="text-align: left; border-style: outset">
            <tr>
                <td>
                    <label class="alignCenter" for="memberId">
                        <strong><bean:message key="label.member.id" /></strong></label>
                </td>
                <td>
                    <bean:write name="mainForm" property="memberId"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="courtNumber">
                        <strong><bean:message key="label.court.number" /></strong></label>
                </td>
                <td>
                    <bean:write name="mainForm" property="courtNumber"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="startTime">
                        <strong><bean:message key="label.start.time" /></strong></label>
                </td>
                <td>
                    <bean:write name="mainForm" property="startTime"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="memberIdOpponent">
                        <strong><bean:message key="label.opponent.id" /></strong></label>
                </td>
                <td>
                    <input type="text" name="memberIdOpponent" id="memberIdOpponent" size="20" value="" />
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="notes">
                        <strong><bean:message key="label.notes" /></strong></label>
                </td>
                <td>
                    <input type="text" name="notes" id="notes" size="20" value="" />
                </td>
            </tr>
        </table>

        <div>
            <br/>
            <input type="submit" value="submit"/>
            <input type="reset" value="reset"/>
        </div>
    </div>
</body>

</html:form>
