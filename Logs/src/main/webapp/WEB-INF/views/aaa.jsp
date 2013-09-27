<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://liferay.com/tld/portlet" %>
<%--
  Created by IntelliJ IDEA.
  User: kostya
  Date: 05.09.13
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<portlet:actionURL var="actionLink"></portlet:actionURL>
<form action="${actionLink}" method="POST">
    <input name="linesNumber" type="text"     value=""/>
    <input type="submit" value="Submit"/>
</form>
<div>${lines}</div>