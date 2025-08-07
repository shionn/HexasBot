<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">

<table>
<thead>
	<tr>
		<th>#</th>
		<th>Type</th>
		<th colspan="2">Produit</th>
		<th>Url</th>
	</tr>
</thead>
<tbody>
	<c:set var="marque" value="NULL"/>
	<c:forEach items="${tasks}" var="task">
		<c:if test="${task.product.marque != marque}">
			<c:set var="marque" value="${task.product.marque}"/>
			<tr>
				<th colspan="5">${task.product.marque}</th>
			</tr>
		</c:if>
		<tr id="${task.id}">
			<td><a href="<spring:url value='/tasks/edit/${task.id}'/>" style="text-decoration: none;">✎</a></td>
			<td>${task.type}</td>
			<td>${task.product.marque}</td>
			<td>${task.product.metaModel} - ${task.product.name}</td>
			<td><a href="${task.url}" target="_blank">${task.url}</a></td>
		</tr>
	</c:forEach>
</tbody>
</table>
<p>
<a href="<spring:url value="/tasks/add"/>">Ajouter une tache</a>
</p>


</jsp:attribute>
</t:template>
