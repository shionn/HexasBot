'use strict';

var Configuration = function() {

	this.tPage = new MTemplate("configuration");
	this.tSimpleCommand = new MTemplate("simple-commands");
	this.tTimer = new MTemplate("timer");
	this.tMonster = new MTemplate("monster");
	this.tItemDrop = new MTemplate("item-drop");
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
			timers : new Array(),
			adventure : this.buildAdventure()
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

	this.buildAdventure = function() {
		return {
			activated : $(".btn-success[role=adventure-activated]").length == 1,
			commands : this.extractObject("[role=adventure-commands]"),
			messages : this.extractObject("[role=adventure-messages]"),
			monsters : this.extractArrayObject("[role=monster]"),
			drops : this.extractArrayObject("[role=item-drop]"),
			gamer : this.extractObject("[role=gamer]")
		};
	};

	this.extractArrayObject = function(name) {
		var tab = new Array();
		$.each($(name), $.proxy(function(i, item) {
			tab.push(this.extractObject(item));
		}, this));
		return tab;
	};

	this.extractObject = function(name) {
		var object = new Object();
		$.each($(name).find("input"), function() {
			object[$(this).attr("name")] = $(this).val();
		});
		return object;
	};

	this.addSimpleCommand = function(event) {
		var target = $(event.target).parents("[class=container]");
		this.tSimpleCommand.insertAfter({}, target);
	};

	this.addTimer = function(event) {
		var target = $(event.target).parents("[class=container]");
		this.tTimer.insertAfter({}, target);
	};

	this.addMonster = function(event) {
		event.preventDefault();
		var target = $("#main").find("[role=monster-title]");
		this.tMonster.insertAfter({}, target);
	};

	this.addItem = function(event) {
		event.preventDefault();
		var target = $("#main").find("[role=item-drop-title]");
		this.tItemDrop.insertAfter({}, target);
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
	$("#main").on("click", "a[role=add-monster]", $.proxy(this.addMonster, this));
	$("#main").on("click", "a[role=add-item-drop]", $.proxy(this.addItem, this));
	$("#main").on("click", "button[role=delete]", $.proxy(this.remove, this));
	$("nav").on("click", "[href=#configuration]", $.proxy(this.load, this));

};

$(function() {
	new Configuration();

	$("#main").on("click", "button.onoff", function() {
		$(this).toggleClass("btn-danger");
		$(this).toggleClass("btn-success");
	});

	var updateLvlCurve = function() {
		var base = $("#main [name=xpBase]").val();
		var fact = $("#main [name=xpFactor]").val();
		var xpPerLvl = function(lvl) {
			return Math.floor(Math.pow(fact, lvl) * base);
		};
		$("span[role=lvl1]").text(xpPerLvl(0));
		$("span[role=lvl2]").text(xpPerLvl(1));
		$("span[role=lvl3]").text(xpPerLvl(2));
		$("span[role=lvl4]").text(xpPerLvl(3));
		$("span[role=lvl5]").text(xpPerLvl(4));
		$("span[role=lvl10]").text(xpPerLvl(9));
		$("span[role=lvl20]").text(xpPerLvl(19));
	};

	$("#main").on("change", "[name=xpBase]", updateLvlCurve);
	$("#main").on("change", "[name=xpFactor]", updateLvlCurve);
});