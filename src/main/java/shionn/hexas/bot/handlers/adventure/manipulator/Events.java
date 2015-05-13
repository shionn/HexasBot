package shionn.hexas.bot.handlers.adventure.manipulator;

import java.util.ArrayList;
import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.mongo.mo.adventure.EventMo;

/**
 * Permet la construction de plusieurs evenement
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Events {

	private List<Event> events = new ArrayList<Event>();

	public Event battle() {
		Event event = new Event();
		events.add(event);
		return event.battle();
	}

	public Event lvlUp() {
		Event event = new Event();
		events.add(event);
		return event.lvlUp();
	}

	public List<EventMo> mo(MessageEvent<HexasBot> event) {
		List<EventMo> mos = new ArrayList<EventMo>();
		for (Event e : events) {
			mos.add(e.event(event).mo());
		}
		return mos;
	}


}
