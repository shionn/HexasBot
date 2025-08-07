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
		<th colspan="2">Produit</th>
		<th>Prix (msrp)</th>
		<th>Date</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${lastDrops}" var="drop">
		<tr>
			<td>${drop.marque}</td>
			<td><a href="${drop.url}" target="_blank">${drop.name}</a></td>
			<td>${drop.lastPrice}</td>
			<td>${drop.lastPriceDate}</td>
		</tr>
	</c:forEach>
</tbody>
</table>


</jsp:attribute>
</t:template>
