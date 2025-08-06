<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">

<spring:url  value="/products/add" var="url"/>
<c:if test="${not empty product}">
	<spring:url  value="/products/edit/${product.id}" var="url"/>
</c:if>
<form:form action="${url}" method="POST">
	<label for="marque">Marque</label>
	<input name="marque" type="text" value="${product.marque}"/>
	<label for="name">Nom</label>
	<input name="name" type="text" value="<c:if test="${empty product.name}">${product.metaModel} ${product.model}</c:if>${product.name}"/>
	<label for="msrp">MSRP</label>
	<input name="msrp" type="text" value="${product.msrp}"/>
	<label for="notifyPrice">Prix d'alerte</label>
	<input name="notifyPrice" type="text" value="${product.notifyPrice}"/>
	<label for="notifyChannel">Canal de notification</label>
	<input name="notifyChannel" type="text" value="${product.notifyChannel}"/>
	<input type="Submit" value="Valider">
</form:form>

<p>
<c:if test="${not empty product}">
	<a href="<spring:url value="/products/delete/${product.id}"/>">🗑 Supprimer</a>
</c:if>
</p>

</jsp:attribute>
</t:template>
