'use strict';

var Configuration = function() {

	this.tPage = new MTemplate("configuration");
	this.tSimpleCommand = new MTemplate("simple-commands");
	this.tTimer = new MTemplate("timer");
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
		$.ajax({
			url : 'channel',
			type : 'POST',
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(this.buildData()),
			success : $.proxy(this.display, this),
			error : $.proxy(this.error)
		});
	};

	this.buildData = function() {
		var data = {
			simpleCommands : new Array(),
			timers : new Array()
		};
		$.each($("[role=simple-command]"), function() {
			data.simpleCommands.push({
				command : $(this).find("[name=command]").val(),
				message : $(this).find("[name=message]").val()
			});
		});
		$.each($("[role=timer]"), function() {
			data.timers.push({
				delayTime : $(this).find("[name=delayTime]").val(),
				delayMessage : $(this).find("[name=delayMessage]").val(),
				message : $(this).find("[name=message]").val()
			});
		});
		return data;
	};

	this.addSimpleCommand = function(event) {
		var target = $(event.target).parents("[class=container]");
		this.tSimpleCommand.insertAfter({}, target);
	};

	this.addTimer = function(event) {
		var target = $(event.target).parents("[class=container]");
		this.tTimer.insertAfter({}, target);
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
	$("#main").on("click", "button[role=add-timer]", $.proxy(this.addTimer, this));
	$("#main").on("click", "button[role=delete]", $.proxy(this.remove, this));
	$("nav").on("click", "[href=#configuration]", $.proxy(this.load, this));

};

$(function() {
	new Configuration();
});
