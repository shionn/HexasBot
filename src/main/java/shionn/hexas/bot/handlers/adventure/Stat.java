package shionn.hexas.bot.handlers.adventure;

import javax.inject.Named;

import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.messages.MessageBuilder;
import shionn.hexas.mongo.mo.adventure.Adventure;
import shionn.hexas.mongo.mo.adventure.Player;

/**
 * gere une commande de stat
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class Stat {

	private NextLvl nextLvl = new NextLvl();

	public void run(Player player, Adventure adventure, MessageEvent<HexasBot> event) {
		new MessageBuilder(adventure.getMessages().getStat()).player(player)
				.nextXp(nextLvl.xp(adventure, player)).send(event);
	}

}