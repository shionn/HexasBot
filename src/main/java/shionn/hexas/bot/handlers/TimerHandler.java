package shionn.hexas.bot.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.timer.TimerState;
import shionn.hexas.bot.messages.MessageBuilderFactoy;
import shionn.hexas.mongo.mo.Timer;

/**
 * Permet de traiter les {@link Timer}
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class TimerHandler {

	private static final int MILLIS_IN_MIN = 60 * 1000;

	@Inject
	private MessageBuilderFactoy messages;

	private Map<String, TimerState> states = new HashMap<>();

	public void handle(Timer timer, MessageEvent<HexasBot> event) {
		TimerState state = getOrCreate(timer, event);
		if (isTime(timer, state)) {
			event.getChannel().send().message(buildMessage(timer, event));
			state.setLastTime(now());
			state.setCountMessage(timer.getDelayMessage());
		} else {
			state.setCountMessage(state.getCountMessage() - 1);
		}
	}

	public String buildMessage(Timer timer, MessageEvent<HexasBot> event) {
		return messages.build(timer.getMessage()).event(event).message();
	}

	private boolean isTime(Timer timer, TimerState state) {
		return state.getCountMessage() <= 0 && futurExec(timer, state) < now();
	}

	public long futurExec(Timer timer, TimerState state) {
		return state.getLastTime() + timer.getDelayMessage() * MILLIS_IN_MIN;
	}

	public long now() {
		return System.currentTimeMillis();
	}

	private TimerState getOrCreate(Timer timer, MessageEvent<HexasBot> event) {
		String key = timer.getMessage() + event.getChannel().getName();
		TimerState state = states.get(key);
		if (state == null) {
			state = new TimerState();
			states.put(key, state);
		}
		return state;
	}


}
