package shionn.hexas.bot.handlers.adventure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

import com.mongodb.BasicDBObject;

/**
 * Traite les commande de top
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class TopHandler {

	@Inject
	private JacksonDBCollection<PlayerMo, String> players;

	public void runTopLvl(Player player, MessageEvent<HexasBot> event) {
		Iterator<PlayerMo> iterator = players
				.find(DBQuery.regex("_id", Pattern.compile(".*" + event.getChannel().getName())))
				.sort(new BasicDBObject("lvl", -1)).sort(new BasicDBObject("xp", -1)).iterator();
		List<String> lvls = new ArrayList<>();
		while (iterator.hasNext()) {
			PlayerMo mo = iterator.next();
			lvls.add(mo.getKey().replace(event.getChannel().getName(), "") + ' ' + mo.getLvl());
		}
		new Message(player).topLvl().top(lvls).send(event);
	}

}
