<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">

<spring:url  value="/tasks/add" var="url"/>
<c:if test="${not empty task}">
	<spring:url  value="/tasks/edit/${task.id}" var="url"/>
</c:if>
<form:form action="${url}" method="POST">
	<label for="type">Type</label>
	<select name="type">
		<c:forEach items="${types}" var="type">
			<option value="${type}" <c:if test="${task.type==type}">selected="selected"</c:if>>${type}
		</c:forEach>
	</select>
	<label for="url">Url <a href="${task.url}" target="_blank">👁</a></label>
	<input name="url" type="text" placeholder="https://www.amazon.fr/ASRock-Radeon-Steel-Legend-GDDR6/dp/B0DTTGMFK3" value="${task.url}"/>
	<label for="product.id">Product</label>
	<select name="product.id">
		<c:forEach items="${products}" var="product">
			<option value="${product.id}" <c:if test="${product.id==task.product.id}">selected="selected"</c:if>>${product.marque} - ${product.metaModel} - ${product.name}
		</c:forEach>
	</select>
	<label for="includePattern">Include pattern</label>
	<input name="includePattern" type="text" value="${task.includePattern}"/>
	<label for="excludePattern">Exclude pattern</label>
	<input name="excludePattern" type="text" value="${task.excludePattern}"/>
	<input type="Submit" value="valider">
</form:form>

<p>Site :
	<ul>
		<li>Amazon.fr : group / price</li>
		<li>Pccomponentes.fr : TODO</li>
		<li>Cyberteck.fr : TODO</li>
		<li>CaseKing.de : group / price</li>
		<li>Compumsa.eu : TODO</li>
		<li>Cdiscount.fr : price</li>
		<li>LDLC.com : group / price</li>
		<li>marketplace.nvidia.com : TODO</li>
	</ul> 
</p>

<p>
	<c:if test="${not empty task}">
		<a href="<spring:url value='/tasks/delete/${task.id}'/>">🗑 Supprimer</a>
	</c:if>
</p>

</jsp:attribute>
</t:template>
