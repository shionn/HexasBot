package shionn.hexas.bot.messages;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.mongo.mo.adventure.Drop;
import shionn.hexas.mongo.mo.adventure.Monster;
import shionn.hexas.mongo.mo.adventure.Player;

public class MessageBuilder {

	private String message;

	private Map<String, String> substitution = new HashMap<String, String>();

	public MessageBuilder(String message) {
		this.message = message;
	}

	public String message() {
		return new StrSubstitutor(substitution).replace(message);
	}

	public MessageBuilder append(String message) {
		this.message = this.message.concat(" ").concat(message);
		return this;
	}

	public MessageBuilder event(MessageEvent<HexasBot> event) {
		substitution.put("user", event.getUser().getNick());
		substitution.put("channel", event.getChannel().getName());
		return this;
	}

	public MessageBuilder player(Player player) {
		substitution.put("pv", Integer.toString(player.getPv()));
		substitution.put("maxPv", Integer.toString(player.getMaxPv()));
		substitution.put("lvl", Integer.toString(player.getLvl()));
		substitution.put("xp", Integer.toString(player.getXp()));
		substitution.put("po", Integer.toString(player.getPo()));
		return this;
	}

	public MessageBuilder monster(Monster monster) {
		substitution.put("monster", monster.getName());
		substitution.put("xp", Integer.toString(monster.getXp()));
		return this;
	}

	public void send(MessageEvent<HexasBot> event) {
		event.getChannel().send().message(event(event).message());
	}

	public MessageBuilder drop(Drop drop) {
		substitution.put("item", drop.getItem());
		return this;
	}

	public MessageBuilder po(int po) {
		substitution.put("po", Integer.toString(po));
		return this;
	}

	public MessageBuilder coldDown(int coldDown) {
		substitution.put("coldDown", Integer.toString(coldDown));
		return this;
	}

	public MessageBuilder pv(int damage) {
		substitution.put("pv", Integer.toString(damage));
		return this;
	}

	public MessageBuilder nextXp(int xp) {
		substitution.put("nextXp", Integer.toString(xp));
		return this;
	}

}
