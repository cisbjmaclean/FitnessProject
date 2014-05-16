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
<html>
<html:form action="/mainMenu">

<table>
<tr style="text-align: center;">
    <td>
    Menu
  </td>
</tr>
<tr>
  <td width="120" valign="top">
    <font size="-1"> <input type="submit" name="action" value="<bean:message key='label.court.bookings'/>" /></font>
  </td>
</tr>

<%-- This will filter the member button for the member user types --%>
<logic:notEqual name="member" property="memberType" value="1" scope="session">
<tr>
  <td width="120" valign="top">
      <font size="-1"> <input type="submit" name="action" value="<bean:message key='label.members'/>"/></font>
  </td>
</tr>
</logic:notEqual>


<tr>
  <td width="120" valign="top">
    <font size="-1"> <input type="submit" name="action" value="<bean:message key='label.logout'/>"/></font>
  </td>
</tr>
</table>
</html:form>
</html>