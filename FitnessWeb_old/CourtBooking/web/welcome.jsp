<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<script type="text/javascript">
//<![CDATA[
    function formFocus() {
        document.forms[0].elements["userName"].focus();
    }

    window.onload = formFocus;
//]]>
</script>

<html:form action="/authenticate">
    <body onload="formFocus()" 

    <%--  These messages can be used to display either messages, warnings, or errors from your
          actions.  You'll notice that once a court is booked a message is put out as an 
          information message.  --%>

        <logic:messagesPresent message="true">
            <html:messages id="msg2" message="true" property="message1"><div class="infoMessageCheck" style="color: green"><bean:write name="msg2"/></div><br/></html:messages>				  		
            <html:messages id="msg2" message="true" property="warn"><div class="warnExclaim"  style="color: yellow"><bean:write name="msg2"/></div><br/></html:messages>
            <html:messages id="msg2" message="true" property="error"><div class="errorX"  style="color: red"><bean:write name="msg2"/></div><br/></html:messages>				  		
        </logic:messagesPresent>
        
        <%-- the html:errors is populated if the validator is used. --%>    
        <div style="color:red">
            <html:errors />
        </div>
        
        
    <%--
    
    This form displays the text fields to get the username and password.  This form will submit to the
    authenticate action.
    
    --%>
        
        <div style="height: 100px; text-align: center; background-color: #efefef; padding: 10px; -webkit-border-radius: 12px; -moz-border-radius: 12px; margin-right: 10px">
            <table align="center" >
                <tr>
                    <td>
                        <label class="alignCenter" for="userName">
                            <strong><bean:message key="login.username.label" /></strong></label>
                    </td>
                    <td>
                        <input type="text" name="userName" id="userName" size="20" value="" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="alignCenter"  for="password">
                            <strong><bean:message key="login.password.label" /></strong></label>
                    </td>
                    <td>
                        <input type="password" id="password" name="password" size="20" value="" />
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="2">
                        <input type="submit" value="submit"/>
                        <input type="reset" value="reset"/>
                    </td>
                </tr>
            </table>
        </div>        

    </body>

</html:form>
