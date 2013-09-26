Файл portal-ext.properties должен лежать в папке

{liferay-home}\{tomcat}\webapps\ROOT\WEB-INF\classes




ОТДАЧА СТАТИКИ ТОМКАТОМ


Сначала в корне томката создайте папку PROJECT_DATA

редактируем файл \{liferay-home}\{tomcat}\conf\server.xml

В элемент хост добавьте контекст, где в docBase - абсолютный путь к PROJECT_DATA

<Host .......>

<Context docBase="{drive}:{liferay-home}\{tomcat}\PROJECT_DATA" path="/mediastuff"/>
</Host......>