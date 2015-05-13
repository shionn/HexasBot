'use strict';

var Adventure = function() {

	this.battle = new MTemplate("adventure/battle");
	this.lvlup = new MTemplate("adventure/lvlup");
	this.shop = new MTemplate("adventure/shop");

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
			} else if (data.type == "shop") {
				this.shop.html(data, "#main");
			} else if (data.type == "lvlup") {
				this.lvlup.html(data, "#main");
			}
		}
	};

	this.startTask = function(delay) {
		this.task = setInterval($.proxy(this.pull, this), delay);
	};

};

$(function() {
	new Adventure().startTask(10000);
})
