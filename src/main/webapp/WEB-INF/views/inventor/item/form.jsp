<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="inventor.item.form.label.name" path="name"/>
	<acme:input-textbox code="inventor.item.form.label.itemType" path="itemType"/>
	<acme:input-textbox code="inventor.item.form.label.code" path="code"/>
	<acme:input-textbox code="inventor.item.form.label.technology" path="technology"/>
	<acme:input-textarea code="inventor.item.form.label.description" path="description"/>
	<acme:input-money code="inventor.item.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="inventor.item.form.label.optionalLink" path="optionalLink"/>
	<acme:input-textbox code="inventor.item.form.label.inventor" path="fullname"/>
	
	<jstl:if test="${command != 'create' && itemType == 'TOOL'}">
		<acme:button code="inventor.xx1.form.button.create" action="/inventor/xx1/list?masterId=${id}"/>
	</jstl:if>
	
</acme:form>

