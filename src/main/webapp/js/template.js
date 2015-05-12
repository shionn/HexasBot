'use strict';

var MTemplate = function(template) {
	var html = null;

	this.appendTo = function(values, target) {
		var data = this.decorate(values);
		if (html == null) {
			$.get("template/" + template + ".html", function(template) {
				html = template;
				$(Mustache.render(html, data)).appendTo(target);
			}, "html");
		} else {
			$(Mustache.render(html, data)).appendTo(target);
		}
	};

	this.insertAfter = function(values, target) {
		var data = this.decorate(values);
		if (html == null) {
			$.get("template/" + template + ".html", function(template) {
				html = template;
				$(Mustache.render(html, data)).insertAfter(target);
			}, "html");
		} else {
			$(Mustache.render(html, data)).insertAfter(target);
		}
	};

	this.prependTo = function(values, target) {
		var data = this.decorate(values);
		if (html == null) {
			$.get("template/" + template + ".html", function(template) {
				html = template;
				$(Mustache.render(html, data)).prependTo(target);
			}, "html");
		} else {
			$(Mustache.render(html, data)).prependTo(target);
		}
	};

	this.html = function(values, target) {
		var data = this.decorate(values);
		if (html == null) {
			$.get("template/" + template + ".html", function(template) {
				html = template;
				$(target).html(Mustache.render(html, data));
			}, "html");
		} else {
			$(target).html(Mustache.render(html, data));
		}
	};

	this.decorate = function(values) {
		return {
			data : values,
			onOffButtonClass : function() {
				return function(text, render) {
					return 'true' == render(text) ? "btn-success" : "btn-danger";
				}
			},
			toCheckBox : function() {
				return function(text, render) {
					return 'true' == render(text) ? "checked" : "";
				}
			}
		}
	};
	
	
	this.popup = function(data) {
		var myWindow = window.open("", "Adventrue", "width=320, height=200");
		if (html == null) {
			$.get("template/" + template + ".html", function(template) {
				html = template;
				myWindow.document.write(Mustache.render(html, data));
			}, "html");
		} else {
			myWindow.document.write(Mustache.render(html, data));
		}
	};
};
