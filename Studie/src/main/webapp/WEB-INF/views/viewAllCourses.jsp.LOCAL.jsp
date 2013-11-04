<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<portlet:defineObjects/>
<%
    Collection<Organization> orgs = (Collection) request.getAttribute("organisations");
    Integer pagesCount = (Integer) request.getAttribute("pagesCount");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    int leftPageNumb = (Integer) request.getAttribute("leftPageNumb");
    int rightPageNumb = (Integer) request.getAttribute("rightPageNumb");
    boolean skippedBeginning = (Boolean) request.getAttribute("skippedBeginning");
    boolean skippedEnding = (Boolean) request.getAttribute("skippedEnding");
    OrganizationType type = null;
    if (request.getAttribute("type") != null)
        type = (OrganizationType) request.getAttribute("type");
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    String imagePath = new StringBuilder(themeDisplay.getPortalURL()).append('/')
            .append(themeDisplay.getPathImage()).append("/image_gallery?img_id=").toString();
    Collection<String> allTypes = (Collection) (OrganizationType.allTypes());
    String temp;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
View all courses page
</body>
</html>