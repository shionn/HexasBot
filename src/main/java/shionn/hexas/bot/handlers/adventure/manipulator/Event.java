package shionn.hexas.bot.handlers.adventure.manipulator;


import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.mongo.mo.adventure.EventMo;
import shionn.hexas.mongo.mo.adventure.EventMo.Type;
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

	public Event battle() {
		mo.setType(Type.battle);
		return this;
	}

	public EventMo mo() {
		return mo;
	}

	public Event event(MessageEvent<HexasBot> event) {
		mo.setChannel(event.getChannel().getName());
		mo.setPlayer(event.getUser().getNick());
		return this;
	}

	public Event monster(MonsterMo monster) {
		mo.setMonster(monster.getName());
		return this;
	}

	public Event message(String message) {
		mo.setMessage(message);
		return this;
	}

}
