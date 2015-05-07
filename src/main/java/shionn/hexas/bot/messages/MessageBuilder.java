package shionn.hexas.bot.messages;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;

public class MessageBuilder {

	private String message;

	private Map<String, String> substitution = new HashMap<String, String>();


	public MessageBuilder(String message) {
		this.message = message;
	}

	public MessageBuilder event(MessageEvent<HexasBot> event) {
		substitution.put("user", event.getUser().getNick());
		substitution.put("channel", event.getChannel().getName());
		return this;
	}

	public String message() {
		return new StrSubstitutor(substitution).replace(message);
	}

}
