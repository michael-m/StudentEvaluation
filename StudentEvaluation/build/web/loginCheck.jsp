<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="%{#session.loggedin != 'true'}">
<jsp:forward page="/studenteval.jsp" />
</s:if>
