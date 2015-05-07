'use strict';

var MTemplate = function(template) {
	var html = null;

	this.appendTo = function(values, target) {
		if (html == null) {
			$.get("template/"+template+".html", function(template) {
				html = template;
				$(Mustache.render(html, this.decorate(values))).appendTo(target);
			}, "html");
		} else {
			$(Mustache.render(html, this.decorate(values))).appendTo(target);
		}
	};

	this.html = function(values, target) {
		var data = this.decorate(values);
		if (html == null) {
			$.get("template/"+template+".html", function(template) {
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
			f : function() {
				return function(text, render) {
					return render(text);
				}
			}
		}
	};
};

