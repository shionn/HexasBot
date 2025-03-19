<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">

<spring:url  value="/drops/add" var="url"/>
<c:if test="${not empty product}">
	<spring:url  value="/drops/edit/${product.id}" var="url"/>
</c:if>
<form:form action="${url}" method="POST">
	<label for="marque">Marque</label>
	<input name="marque" type="text" placeholder="Asrock / Lego" value="${product.marque}"/>
	<label for="metaModel">Metal Model</label>
	<input name="metaModel" type="text" placeholder="RX 5070" value="${product.metaModel}"/>
	<label for="model">Model</label>
	<input name="model" type="text" placeholder="Steel Legend" value="${product.model}"/>
	<label for="url">Url</label>
	<input name="url" type="text" placeholder="https://www.amazon.fr/ASRock-Radeon-Steel-Legend-GDDR6/dp/B0DTTGMFK3" value="${product.url}"/>
	<p>Ne pas garder les parametres apres ?</p>
	<label for="msrp">MSRP</label>
	<input name="msrp" type="text" placeholder="920€" value="${product.msrp}"/>
	<label for="notifyChannel">Canal de notification</label>
	<input name="notifyChannel" type="text" value="${product.notifyChannel}"/>
	<label for="scanner">Scanner</label>
	<input name="scanner" type="text" placeholder="selenium / jsoop" value="${product.scanner}"/>
	<input type="Submit" value="valider">
</form:form>

<p>Site :
	<ul>
		<li>Amazon.fr (peu marché en jsoup)</li>
		<li>Pccomponentes.fr (selenium)</li>
		<li>Cyberteck.fr (selenium)</li>
		<li>CaseKing.de (selenium)</li>
		<li>Compumsa.eu (selenium)</li>
		<li>Cdiscount.fr (selenium)</li>
	</ul> 
</p>

</jsp:attribute>
</t:template>
