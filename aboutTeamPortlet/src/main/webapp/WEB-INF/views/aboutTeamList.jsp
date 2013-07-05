<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ page import="ua.dp.stud.aboutTeam.model.Human" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<portlet:defineObjects />

<%
  List<Human> bateam = (List) request.getAttribute("baTeam");
  List<Human> devteam = (List) request.getAttribute("devTeam");
  List<Human> testersteam = (List) request.getAttribute("testersTeam");
  List<Human> companyteam = (List) request.getAttribute("companyTeam");
  List<Human> tnxteam = (List) request.getAttribute("tnxTeam");
%>

<html>
<head>
</head>
<body>
<div id="about-wrapper">
<div class="aba">
<div class="asection"><spring:message code='head.ba'/></div>
<%for (int i=0; i<bateam.size();i++){%>
        <div class="ahum-wrapper">
                <div class="ainfo">
                        <div class="aphoto">
                            <img src="<%=bateam.get(i).getPhotoUrl()%>"/>
                        </div>
                        <div class="atitle">
                            <h1>
                            <a href="<%=bateam.get(i).getUrl()%>" target="_blank">
                                <p><%=bateam.get(i).getFirstName()%></p>
                                <p><%=bateam.get(i).getLastName()%></p>
                            </a>
                            <h1>
                            <div class="astatus">
                                <h3><%=bateam.get(i).getStatus()%><h3>
                            </div>
                        </div>
                </div>
        </div>
    <%}%>
</div>
<div class="adev">
    <div class="asection"><spring:message code='head.developers'/></div>
    <%for (int i=0; i<devteam.size();i++){%>
        <div class="ahum-wrapper">
                <div class="ainfo">
                        <div class="aphoto">
                            <img src="<%=devteam.get(i).getPhotoUrl()%>"/>
                        </div>
                        <div class="atitle">
                            <h1>
                            <a href="<%=devteam.get(i).getUrl()%>" target="_blank">
                                <p><%=devteam.get(i).getFirstName()%></p>
                                <p><%=devteam.get(i).getLastName()%></p>
                            </a>
                            <h1>
                            <div class="astatus">
                                <h3><%=devteam.get(i).getStatus()%><h3>
                            </div>
                        </div>
                </div>
        </div>
    <%}%>
</div>
<div class="atesters">
    <div class="asection"><spring:message code='head.testers'/></div>
    <%for (int i=0; i<testersteam.size();i++){%>
            <div class="ahum-wrapper">
                    <div class="ainfo">
                            <div class="aphoto">
                                <img src="<%=testersteam.get(i).getPhotoUrl()%>"/>
                            </div>
                            <div class="atitle">
                                <h1>
                                <a href="<%=testersteam.get(i).getUrl()%>" target="_blank">
                                    <p><%=testersteam.get(i).getFirstName()%></p>
                                    <p><%=testersteam.get(i).getLastName()%></p>
                                </a>
                                <h1>
                                <div class="astatus">
                                    <h3><%=testersteam.get(i).getStatus()%><h3>
                                </div>
                            </div>
                    </div>
            </div>
        <%}%>
</div>
<div class="acompany">
    <div class="asection"><spring:message code='head.company'/></div>
    <%for (int i=0; i<companyteam.size();i++){%>
            <div class="ahum-wrapper">
                    <div class="ainfo">
                            <div class="aphoto">
                                <img src="<%=companyteam.get(i).getPhotoUrl()%>"/>
                            </div>
                            <div class="atitle">
                                <h1>
                                <a href="<%=companyteam.get(i).getUrl()%>" target="_blank">
                                    <p><%=companyteam.get(i).getFirstName()%></p>
                                    <p><%=companyteam.get(i).getLastName()%></p>
                                </a>
                                <h1>
                                <div class="astatus">
                                    <h3><%=companyteam.get(i).getStatus()%><h3>
                                </div>
                            </div>
                    </div>
            </div>
        <%}%>
</div>
<div class="atnx">
    <div class="asection"><spring:message code='head.tnx'/></div>
    <%for (int i=0; i<tnxteam.size();i++){%>
            <div class="ahum-wrapper">
                    <div class="ainfo">
                            <div class="aphoto">
                                <img src="<%=tnxteam.get(i).getPhotoUrl()%>"/>
                            </div>
                            <div class="atitle">
                                <h1>
                                <a href="<%=tnxteam.get(i).getUrl()%>" target="_blank">
                                    <p><%=tnxteam.get(i).getFirstName()%></p>
                                    <p><%=tnxteam.get(i).getLastName()%></p>
                                </a>
                                <h1>
                                <div class="astatus">
                                    <h3><%=tnxteam.get(i).getStatus()%><h3>
                                </div>
                            </div>
                    </div>
            </div>
        <%}%>
</div>
</div>
</body>
</html>