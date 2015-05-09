'use strict';

var Configuration = function() {

	this.tPage = new MTemplate("configuration");
	this.tSimpleCommand = new MTemplate("simple-commands");
	this.tTimer = new MTemplate("timer");
	this.tMonster = new MTemplate("monster");
	this.tItemDrop = new MTemplate("item-drop");
	this.tItemUse = new MTemplate("item-use");
	this.tCraft = new MTemplate("craft");
	this.tError = new MTemplate("error");

	this.load = function(event) {
		event.preventDefault();
		$.ajax({
			url : 'channel',
			type : 'GET',
			context : this,
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
			uses : this.extractArrayObject("[role=item-use]"),
			schemes : this.extractArrayObject("[role=craft]"),
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

	this.addCraft = function(event) {
		event.preventDefault();
		var target = $("#main").find("[role=craft-title]");
		this.tCraft.insertAfter({}, target);
	};

	this.addItemUse = function(event) {
		event.preventDefault();
		var target = $("#main").find("[role=item-use-title]");
		this.tItemUse.insertAfter({}, target);
	};

	this.remove = function(event) {
		$(event.target).parents("[class=container]").remove();
	};

	this.display = function(data) {
		this.tPage.html(data, "#main");
		this.updatePvCurve();
		this.updateXpCurve();
	};

	this.error = function(data) {
		alert(data.status + " " + data.statusText);
	};
	
	this.updateXpCurve = function() {
		var base = +$("#main [name=xpBase]").val();
		var fact = +$("#main [name=xpFactor]").val();
		var xpPerLvl = function(lvl) {
			return Math.floor(Math.pow(fact, lvl) * base);
		};
		$("span[role=xp1]").text(xpPerLvl(0));
		$("span[role=xp2]").text(xpPerLvl(1));
		$("span[role=xp3]").text(xpPerLvl(2));
		$("span[role=xp4]").text(xpPerLvl(3));
		$("span[role=xp5]").text(xpPerLvl(4));
		$("span[role=xp10]").text(xpPerLvl(9));
		$("span[role=xp20]").text(xpPerLvl(19));
	};

	this.updatePvCurve = function() {
		var base = +$("#main [name=pvBase]").val();
		var fact = +$("#main [name=pvFactor]").val();
		var perLvl = function(lvl) {
			return Math.floor(base+fact*(lvl-1));
		};
		$("span[role=pv1]").text(perLvl(1));
		$("span[role=pv2]").text(perLvl(2));
		$("span[role=pv3]").text(perLvl(3));
		$("span[role=pv4]").text(perLvl(4));
		$("span[role=pv5]").text(perLvl(5));
		$("span[role=pv10]").text(perLvl(10));
		$("span[role=pv20]").text(perLvl(20));
	};

	$("#main").on("change", "[name=xpBase]", this.updateXpCurve);
	$("#main").on("change", "[name=xpFactor]", this.updateXpCurve);
	$("#main").on("change", "[name=pvBase]", this.updatePvCurve);
	$("#main").on("change", "[name=pvFactor]", this.updatePvCurve);

	$("#main").on("click", "button[role=save]", $.proxy(this.save, this));
	$("#main").on("click", "button[role=add-simple-command]", $.proxy(this.addSimpleCommand, this));
	$("#main").on("click", "button[role=add-timer]", $.proxy(this.addTimer, this));
	$("#main").on("click", "a[role=add-monster]", $.proxy(this.addMonster, this));
	$("#main").on("click", "a[role=add-item-drop]", $.proxy(this.addItem, this));
	$("#main").on("click", "a[role=add-item-use]", $.proxy(this.addItemUse, this));
	$("#main").on("click", "a[role=add-craft]", $.proxy(this.addCraft, this));
	$("#main").on("click", "button[role=delete]", $.proxy(this.remove, this));
	$("nav").on("click", "[href=#configuration]", $.proxy(this.load, this));

};

$(function() {
	new Configuration();

	$("#main").on("click", "button.onoff", function() {
		$(this).toggleClass("btn-danger");
		$(this).toggleClass("btn-success");
	});
	
	$("#main").on("click", ".auto-drop-down a", function(event){
		event.preventDefault();
		var value = $(this).attr("role");
		$(this).parents(".auto-drop-down").parent().find("input[type=hidden]").val(value);
		$(this).parents(".auto-drop-down").find("button").text(value);
	});

});
