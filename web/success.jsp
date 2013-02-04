<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:include page="/loginCheck.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign in Success</title>
    </head>
    <body>
        <h1>You have successfully signed in via your Google account!</h1>
        <s:iterator value="authenticatedUser" var="authUser" />
        <p><s:label>Name: </s:label><s:text name="name">
            ${authUser.firstName} ${authUser.lastName}
        </s:text>
        <p><s:label>E-mail: </s:label><s:text name="email">${authUser.email}</s:text>
        <p><s:label>Language: </s:label><s:text name="language">${authUser.language}</s:text>
        <p><s:url id="logoutUrl" action="doLogout" />
           <s:a href="%{logoutUrl}">Logout</s:a>
    </body>
</html>
