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
		<th>Marque</th>
		<th>Nom</th>
		<th>MSRP</th>
		<th>Prix d'alerte</th>
		<th>Canal de notification</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${products}" var="product">
		<tr id="${product.id}">
			<td><a href="<spring:url value='/products/edit/${product.id}'/>" style="text-decoration: none;">✎</a></td>
			<td>${product.marque}</td>
			<td>
				<c:if test="${empty product.name}">
					<em>${product.metaModel} - ${product.model}</em>
				</c:if>
				${product.name}
			</td>
			<td>${product.msrp}</td>
			<td>${product.notifyPrice}</td>
			<td>${product.notifyChannel}</td>
		</tr>
	</c:forEach>
</tbody>
</table>
<p>
<a href="<spring:url value="/products/add"/>">Ajouter un Produit</a>
</p>


</jsp:attribute>
</t:template>
