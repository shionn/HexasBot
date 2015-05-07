'use strict';

var Configuration = function() {

	this.tPage = new MTemplate("configuration");
	this.tSimpleCommand = new MTemplate("simple-commands");
	this.tError = new MTemplate("error");

	this.load = function(event) {
		event.preventDefault();
		$.ajax({
			url : 'channel',
			type : 'GET',
			success : $.proxy(this.display, this),
			error : $.proxy(this.error)
		});
	};

	this.save = function(event) {
		var data = {
			simpleCommands : new Array()
		};
		$.each($("[role=simple-command]"), function() {
			data.simpleCommands.push({
				command : $(this).find("[name=command]").val(),
				message : $(this).find("[name=message]").val()
			});
		});
		$.ajax({
			url : 'channel',
			type : 'POST',
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(data),
			success : $.proxy(this.display, this),
			error : $.proxy(this.error)
		});
	};

	this.addSimpleCommand = function(event) {
		var target = $(event.target).parents("[class=container]");
		this.tSimpleCommand.insertAfter({}, target);
	};

	this.remove = function(event) {
		$(event.target).parents("[class=container]").remove();
	};

	this.display = function(data) {
		this.tPage.html(data, "#main");
	};

	this.error = function(data) {
		alert(data.status + " " + data.statusText);
	};

	$("#main").on("click", "button[role=save]", $.proxy(this.save, this));
	$("#main").on("click", "button[role=add-simple-command]", $.proxy(this.addSimpleCommand, this));
	$("#main").on("click", "button[role=delete]", $.proxy(this.remove, this));
	$("nav").on("click", "[href=#configuration]", $.proxy(this.load, this));

};

$(function() {
	new Configuration();
});
