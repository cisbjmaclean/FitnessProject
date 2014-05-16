<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>  
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%--

This is the base layout.  It is used as a base tile jsp which controls 
where the header, body and footers go.  
--%>

<%request.getSession().setAttribute("jspName", "base.jsp");%>

  <%if (request.getProtocol().equals("HTTP/1.1")) {
	    response.setHeader("Cache-Control", "no-cache");
    }
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0);
   %>

<html:html>

 <body style="background-color: #efefef;">
 
    <!-- Header page information -->
     <tiles:insert attribute="header" />
  
     <!-- Main body information -->
     <tiles:insert attribute="body" />
   
     <!-- Footer information -->
     <tiles:insert attribute="footer" />
     
 </body>

</html:html>
