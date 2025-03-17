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
<form:form action="${url}" method="POST">
	<label for="marque">Marque</label>
	<input name="marque" type="text" placeholder="Asrock / Lego"/>
	<label for="metaModel">Metal Model</label>
	<input name="metaModel" type="text" placeholder="RX 5070"/>
	<label for="model">Model</label>
	<input name="model" type="text" placeholder="Steel Legend"/>
	<label for="url">Url</label>
	<input name="url" type="text" placeholder="https://www.amazon.fr/ASRock-Radeon-Steel-Legend-GDDR6/dp/B0DTTGMFK3"/>
	<p>Ne pas garder les parametres apres ?</p>
	<label for="msrp">MSRP</label>
	<input name="msrp" type="text" placeholder="920€"/>
	<label for="notifyChannel">Canal de notification</label>
	<input name="notifyChannel" type="text"/>
	<label for="scanner">Scanner</label>
	<input name="scanner" type="text" placeholder="jsoop / javafx"/>
	<p>javafx pour pccomponentes. jsoop sinon.</p>
	<input type="Submit" value="valider">
</form:form>

<p>Site :
	<ul>
		<li>Amazon.fr</li>
		<li>Pccomponentes.fr (marche pas fou)</li>
		<li>Cyberteck.fr</li>
	</ul> 
</p>

</jsp:attribute>
</t:template>
