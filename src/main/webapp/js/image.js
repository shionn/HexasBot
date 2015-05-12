'use strict';

var Image = function() {

	this.tPage = new MTemplate("image");

	this.open = function(event) {
		event.preventDefault();
		this.tPage.html({}, "#main");
	};

	 $("nav").on("click", "[href=#image]", $.proxy(this.open, this));

};

$(function() {
	new Image();
});
