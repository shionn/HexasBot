package test;

import org.junit.Test;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.events.MessageEvent;

public class Main implements Listener<PircBotX> {

	@Test
	public void testConnect() throws Exception {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Configuration<PircBotX> configuration = new Configuration.Builder().setName("HexasBot")
				.setServer("irc.twitch.tv", 6667, "oauth:jx01kaxzx6k8byanklpuq4muosz538")
				.addAutoJoinChannel("#ozuki99").addListener(this).buildConfiguration();

		// Create our bot with the configuration
		PircBotX bot = new PircBotX(configuration);
		// Connect to the server
		bot.startBot();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(Event event) throws Exception {
		if (event instanceof MessageEvent) {
			message((MessageEvent<PircBotX>) event);
		}
	}

	private void message(MessageEvent<PircBotX> event) {
		if ("!test".equals(event.getMessage())) {
			event.getChannel().send().message("Ton test marche : " + event.getUser().getNick());
		}
	}

}
