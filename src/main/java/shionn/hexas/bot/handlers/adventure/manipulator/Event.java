package shionn.hexas.bot.handlers.adventure.manipulator;


import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.adventure.EventMo;
import shionn.hexas.mongo.mo.adventure.EventMo.Type;
import shionn.hexas.mongo.mo.adventure.ItemShopMo;
import shionn.hexas.mongo.mo.adventure.MonsterMo;

/**
 * Manipulateur d'evenement
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Event {

	private EventMo mo = new EventMo();
	private Message message;

	public Event battle() {
		mo.setType(Type.battle);
		return this;
	}

	public Event shop() {
		mo.setType(Type.shop);
		return this;
	}

	public Event lvlUp() {
		mo.setType(Type.lvlup);
		return this;
	}


	public EventMo mo() {
		mo.setMessage(message.message());
		return mo;
	}

	public Event event(MessageEvent<HexasBot> event) {
		mo.setChannel(event.getChannel().getName());
		mo.setPlayer(event.getUser().getNick());
		if (this.message != null) {
			this.message.event(event);
		}
		return this;
	}

	public Event monster(MonsterMo monster) {
		mo.setMonster(monster.getName());
		return this;
	}

	public Event message(Message message) {
		this.message = message;
		return this;
	}

	public Event win() {
		mo.setWin(true);
		return this;
	}

	public Event loose() {
		mo.setWin(false);
		return this;
	}

	public Event item(ItemShopMo item) {
		mo.setItem(item.getItem());
		mo.setPo(item.getSellPrice());
		return this;
	}

}
