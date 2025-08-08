<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">

<c:forEach items="${drops}" var="group">
	<table>
		<thead>
			<tr>
				<th colspan="2">Last Drop ${group.left}</th>
				<th>Prix (msrp)</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${group.right}" var="drop">
				<tr>
					<td>${drop.marque}</td>
					<td><a href="${drop.url}" target="_blank">${drop.metaModel} - ${drop.name}</a></td>
					<td>${drop.lastPrice}</td>
					<td><fmt:formatDate value="${drop.lastPriceDate}" pattern="dd MMMM à hh:mm"/> </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:forEach>

<table>
	<c:forEach items="${gpus}" var="group">
		<thead>
			<tr>
				<th>Toutes les ${group.left}</th>
				<th>Prix</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${group.right}" var="drop">
				<tr>
					<td>${drop.marque} ${drop.metaModel} ${drop.name}</td>
					<td>
						<c:forEach items="${drop.prices}" var="price">
							<a href="${price.url}" target="_blank" title="<fmt:formatDate value='${drop.lastPriceDate}' pattern='dd MMMM à hh:mm'/>">${price.lastPrice} €</a>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</c:forEach>
</table>




</jsp:attribute>
</t:template>
