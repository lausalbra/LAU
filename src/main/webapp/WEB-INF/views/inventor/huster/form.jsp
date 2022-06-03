<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${command == 'create'}">
			<acme:input-textbox code="inventor.huster.form.label.code" path="code"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:input-moment code="inventor.huster.form.label.creationMoment" path="creationMoment" readonly="true"/>
			<acme:input-textbox code="inventor.huster.form.label.code" path="code"/>
		</jstl:when>
	</jstl:choose>
    <acme:input-textbox code="inventor.huster.form.label.themes" path="themes"/>
    <acme:input-textarea code="inventor.huster.form.label.statement" path="statement"/>
    <acme:input-moment code="inventor.huster.form.label.starPeriod" path="starPeriod"/>
    <acme:input-moment code="inventor.huster.form.label.endPeriod" path="endPeriod"/>
    <acme:input-money code="inventor.huster.form.label.provision" path="provision"/>
    <acme:input-url code="inventor.huster.form.label.additionalInfo" path="additionalInfo"/>
    
    	<jstl:choose>
			<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
				<acme:submit code="inventor.huster.form.button.update" action="/inventor/huster/update"/>
				<acme:submit code="inventor.huster.form.button.delete" action="/inventor/huster/delete"/>
			</jstl:when>	
		
			<jstl:when test="${command == 'create'}">
				<acme:submit code="inventor.huster.form.button.create" action="/inventor/huster/create?masterId=${masterId}"/>
			</jstl:when>
		</jstl:choose>
    
</acme:form>