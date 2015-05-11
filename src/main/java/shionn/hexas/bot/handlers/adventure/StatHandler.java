package shionn.hexas.bot.handlers.adventure;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.action.NextLvl;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

/**
 * gere une commande de stat
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class StatHandler {
	@Inject
	private JacksonDBCollection<PlayerMo, String> players;

	private NextLvl nextLvl = new NextLvl();

	public void run(Player player, MessageEvent<HexasBot> event) {
		new Message(player).stat().nextXp(nextLvl.xp(player)).send(event);
		players.save(player.updateLastStat().mo());
	}

}
