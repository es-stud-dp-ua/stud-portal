<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page isELIgnored="false" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page contentType="text/html" %>

<% ImageService imageService = (ImageService) pageContext.findAttribute("imageService"); %>
