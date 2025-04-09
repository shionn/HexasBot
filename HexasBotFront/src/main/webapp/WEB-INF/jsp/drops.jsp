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
		<th colspan="3">Produit</th>
		<th>Prix (msrp)</th>
		<th>Vendeur</th>
		<th>Scanner</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${drops}" var="d">
		<c:if test="${not d.group}">
			<tr id="${d.id}">
				<td><a href="<spring:url value='/drops/edit/${d.id}'/>" style="text-decoration: none;">✎</a></td>
				<td>${d.metaModel}</td>
				<td>${d.marque}</td>
				<td><a href="${d.url}" target="_blank">${d.model}</a></td>
				<c:if test ="${not empty d.lastPrice}">
					<td>
						${d.lastPrice}
						<c:if test ="${not empty d.msrp}">
							<small><strike>(${d.msrp})</strike></small>
						</c:if>
					</td>
				</c:if>
				<c:if test ="${empty d.lastPrice}">
					<td>--</td>
				</c:if>
				<td>${d.formatedVendor}</td>
				<td>${d.scanner}</td>
			</tr>
		</c:if>
	</c:forEach>
</tbody>

<thead>
	<tr>
		<th colspan="5">group result</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${drops}" var="d">
		<c:if test="${d.group}">
			<tr id="${d.id}">
				<td><a href="<spring:url value='/drops/edit/${d.id}'/>" style="text-decoration: none;">✎</a></td>
				<td>${d.metaModel}</td>
				<td>${d.marque}</td>
				<td><a href="${d.url}" target="_blank">${d.model}</a></td>
				<c:if test ="${not empty d.lastPrice}">
					<td>
						${d.lastPrice}
						<c:if test ="${not empty d.msrp}">
							<small><strike>(${d.msrp})</strike></small>
						</c:if>
					</td>
				</c:if>
				<c:if test ="${empty d.lastPrice}">
					<td>--</td>
				</c:if>
				<td>${d.formatedVendor}</td>
				<td>${d.scanner}</td>
			</tr>
		</c:if>
	</c:forEach>
</tbody>
</table>
<p>
<a href="<spring:url value="/drops/add"/>">Ajouter un produit</a>
<a href="<spring:url value="/drops/all"/>">Voir tous les Produits</a>
</p>


</jsp:attribute>
</t:template>
