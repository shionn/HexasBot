'use strict';

var Adventure = function() {

	this.battle = new MTemplate("battle");

	this.pull = function() {
		$.ajax({
			url : 'adventure',
			type : 'GET',
			context : this,
			success : this.display,
		})
	};

	this.display = function(data) {
		if (data != undefined) {
			if (data.type == "battle") {
				this.battle.html(data, "#main");
			}
		}
	};

	this.startTask = function(delay) {
		this.task = setInterval($.proxy(this.pull, this), delay);
	};

};

$(function() {
	new Adventure().startTask(60000);
})
