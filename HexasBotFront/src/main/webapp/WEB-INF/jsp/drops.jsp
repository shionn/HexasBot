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
		<th colspan="3">Model</th>
		<th>prix</th>
		<th>Vendeur</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${drops}" var="d">
		<tr>
			<td>${d.metaModel}</td>
			<td>${d.marque}</td>
			<td><a href="${d.url}" target="_blank">${d.model}</a></td>
			<c:if test ="${not empty d.lastPrice}">
				<td>${d.lastPrice}</td>
			</c:if>
			<c:if test ="${empty d.lastPrice}">
				<td>--</td>
			</c:if>
			<td>${d.vendor}</td>
		</tr>
	</c:forEach>
</tbody>
</table>

</jsp:attribute>
</t:template>
