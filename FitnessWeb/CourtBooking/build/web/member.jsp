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


<html:form action="/memberEdit">


    <%-- The hidden fields are used to make sure that the properties that are not displayed on the form
         are passed through to the next action.  These will not be passed through if they are not contained 
         on the form.
    --%>

    <%--
    
    This form displays the information that is already obtained but also asks the use to enter the
    additional information that is needed to complete the court booking.
    
    --%>

    <div style="text-align: center; height: 300px; background-color: #efefef; padding: 10px; -webkit-border-radius: 12px; -moz-border-radius: 12px; margin-right: 10px">

        <h3 style="text-align: center; -webkit-border-radius: 10px; -moz-border-radius: 10px; margin-top: 0px; margin-bottom: 20px; padding: 8px">
            <strong><bean:message key="label.member.details" /></strong>
        </h3>    



        <table align="center" style="text-align: left;  border-style: ridge">
            <html:hidden name="memberForm" property="updateFlag"/>
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
            <tr >
                <td style="border-bottom:1pt solid black;">
                    <label class="alignCenter" for="memberId">
                        <strong><bean:message key="label.members" /></strong></label>                </td>
                <td style="border-bottom:1pt solid black;">

                    <html:select name="memberForm" property="memberId">
                        <html:optionsCollection name="memberForm" property="members" label="lastNameFirstName" value="memberId" />
                    </html:select>
                    <input type="submit" name="action" value="load" onclick="updateMode()"/>
                </td>
            </tr>
            <logic:notEqual name="memberForm" property="updateFlag" value="true">
                <tr>
                    <td>
                        <label class="alignCenter" for="newMemberId">
                            <strong><bean:message key="label.new.member.id" /></strong></label>
                    </td>
                    <td>
                        <input type="text" id="forUpdate" name="memberForm" property="newMemberId" size="20" />
                    </td>
                </tr>
            </logic:notEqual>

            <tr>
                <td><label class="alignCenter" for="userType">
                        <strong><bean:message key="label.user.type" /></strong></label></td>
                <td>
                    <html:radio name="memberForm" property="userType" value="1">Member</html:radio><br/>
                    <html:radio name="memberForm" property="userType" value="2">Administration</html:radio><br/>
                    <html:radio name="memberForm" property="userType" value="3">Management</html:radio><br/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label class="alignCenter" for="lastName">
                            <strong><bean:message key="label.last.name" /></strong></label>
                </td>
                <td>
                    <html:text name="memberForm"  property="lastName" size="20" />
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="firstName">
                        <strong><bean:message key="label.first.name" /></strong></label>
                </td>
                <td>
                    <html:text name="memberForm"  property="firstName" size="20" />
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="password">
                        <strong><bean:message key="label.password" /></strong></label>
                </td>
                <td>
                    <html:password name="memberForm"  property="password" size="20" />
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="email">
                        <strong><bean:message key="label.email" /></strong></label>
                </td>
                <td>
                    <html:text name="memberForm"  property="email" size="20" />
                </td>
            </tr>


            <tr>
                <td>
                    <label class="alignCenter" for="phoneCell">
                        <strong><bean:message key="label.phone.cell" /></strong></label>
                </td>
                <td>
                    <html:text name="memberForm"  property="phoneCell" size="20" />
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="phoneHome">
                        <strong><bean:message key="label.phone.home" /></strong></label>
                </td>
                <td>
                    <html:text name="memberForm"  property="phoneHome" size="20" />
                </td>
            </tr>

            <tr>
                <td>
                    <label class="alignCenter" for="phoneWork">
                        <strong><bean:message key="label.phone.work" /></strong></label>
                </td>
                <td>
                    <html:text name="memberForm"  property="phoneWork" size="20" />
                </td>
            </tr>


            <tr>
                <td>
                    <label class="alignCenter" for="address">
                        <strong><bean:message key="label.address" /></strong></label>
                </td>
                <td>
                    <html:text name="memberForm"  property="address" size="20" />
                </td>
            </tr>


            <tr>
                <td><label class="alignCenter" for="status">
                        <strong><bean:message key="label.status" /></strong></label>
                </td>
                <td>
                    <html:radio name="memberForm" property="status" value="1">Active</html:radio><br/>
                    <html:radio name="memberForm" property="status" value="0">Inactive</html:radio><br/>
                        <br/>
                    </td>
                </tr>

                <tr>
                    <td><label class="alignCenter" for="memberShipType">
                            <strong><bean:message key="label.membership.type" /></strong></label></td><td>
                    <html:radio name="memberForm" property="memberShipType" value="1">Annual</html:radio><br>
                    <html:radio name="memberForm" property="memberShipType" value="2">6 Month</html:radio><br>
                    </td>
                </tr>


                <tr>
                    <td>
                        <label class="alignCenter" for="memberShipDate">
                            <strong><bean:message key="label.membership.date" /></strong></label>
                </td>
                <td>
                    <%-- <input type="text" name="memberShipDate" id="memberShipDate" size="20" value="" /> --%>
                </td>
            </tr>

        </table>

        <div>
            <br/>
            <input type="submit" value="submit"/>
            <input type="submit" name="action" value="reset"/>
        </div>
    </div>
</body>
</html:form>
