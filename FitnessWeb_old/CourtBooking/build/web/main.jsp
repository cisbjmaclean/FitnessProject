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


<script type="text/javascript">
//<![CDATA[
    function formFocus() {
        document.forms[0].elements["bookingDate"].focus();
    }

    window.onload = formFocus;
//]]>
</script>

<%--

This jsp will provide the user with the main form for the application.  This will provide
the place to choose a date to select a court.  A jsp calendar is provided to make it easy for them
to pick their date.  This calendar was taken from the prototype javascript site.

--%>

<html:form action="/main">

    <head>
        <meta charset="UTF-8">

        <link rel="stylesheet" href="./styles/calendarview.css">
        <style>
            body {
                font-family: Trebuchet MS;
            }
            div.calendar {
                max-width: 240px;
                margin-left: auto;
                margin-right: auto;
            }
            div.calendar table {
                width: 100%;
            }
            div.dateField {
                width: 140px;
                padding: 6px;
                -webkit-border-radius: 6px;
                -moz-border-radius: 6px;
                color: #555;
                background-color: white;
                margin-left: auto;
                margin-right: auto;
                text-align: center;
            }
            div#popupDateField:hover {
                background-color: #cde;
                cursor: pointer;
            }
        </style>
        <script src="./javascript/prototype.js"></script>
        <script src="./javascript/calendarview.js"></script>
        <script>
    function setupCalendars() {
        // Embedded Calendar
        Calendar.setup(
                {
                    dateField: 'bookingDate',
                    parentElement: 'embeddedCalendar'
                }
        )

        // Popup Calendar
        Calendar.setup(
                {
                    dateField: 'popupDateField',
                    triggerElement: 'popupDateField'
                }
        )
    }

    Event.observe(window, 'load', function() {
        setupCalendars()
    })
        </script>
    </head>
    <body>



        <div style="text-align: center; height: 300px; background-color: #efefef; padding: 10px; -webkit-border-radius: 12px; -moz-border-radius: 12px; margin-right: 10px">

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
            
            <h3 style="text-align: center; -webkit-border-radius: 10px; -moz-border-radius: 10px; margin-top: 0px; margin-bottom: 20px; padding: 8px">
                <bean:message key="label.pick.date" />
            </h3>

            <%--
                This is the embedded calendar code.  This set the value for the bookingDate.
            --%>

            <div id="embeddedExample" style="">
                <div id="embeddedCalendar" style="margin-left: auto; margin-right: auto">
                </div>
                <br />
                <div id="bookingDateDiv" class="dateField" style="background-color: #efefef;">
                    <label class="alignCenter" for="bookingDate">
                        <bean:message key="label.court.booking.date" /></label>
                    <input align="center" type="text" name="bookingDate" id="bookingDate" size="20" value="" />
                    <input type="submit" value="Book"/>
                </div>
                <br />
            </div>


        </div>


    </body>

</html:form>
