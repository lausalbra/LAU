<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
    <acme:list-column code="inventor.huster.list.label.code" path="code" width="15%"/>
    <acme:list-column code="inventor.huster.list.label.themes" path="themes" width="15%"/>
    <acme:list-column code="inventor.huster.list.label.statement" path="statement" width="15%"/>
    <acme:list-column code="inventor.huster.list.label.starPeriod" path="starPeriod" width="15%"/>
    <acme:list-column code="inventor.huster.list.label.endPeriod" path="endPeriod" width="15%"/>
    <acme:list-column code="inventor.huster.list.label.provision" path="provision" width="15%"/>
</acme:list>
<acme:button test="${showCreate == true}" code="inventor.huster.list.button.create" action="/inventor/huster/create?masterId=${masterId}"/>


