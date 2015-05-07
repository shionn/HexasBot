'use strict';

var Auth = function() {

	this.auth = new MTemplate("auth");
	this.error = new MTemplate("error");

	this.authentify = function(event) {
		event.preventDefault();
		this.requestAuthentity(event.target.login.value, event.target.password.value);
	};

	this.requestAuthentity = function(login, pass) {
		$.ajax({
			url : 'auth',
			type : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({
				user : login,
				password : pass
			}),
			success : $.proxy(this.refreshAuthentified, this),
			error : $.proxy(this.error)
		});
	};

	this.requestAuthentified = function() {
		$.ajax({
			url : 'auth',
			type : 'GET',
			success : $.proxy(this.refreshAuthentified, this),
			error : $.proxy(this.error)
		});
	};

	this.error = function(data) {
		alert(data.status + " "+ data.statusText);
	};

	this.refreshAuthentified = function(auth) {
		if (!auth) {
			this.auth.html(auth, "#main");
		}
	};

	this.requestAuthentified();
	$("#main").on("submit", "form[role=authentification]", $.proxy(this.authentify, this));

};

$(function() {
	new Auth();
});
