package shionn.hexas.bot.handlers.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.mongo.mo.adventure.Adventure;
import shionn.hexas.mongo.mo.adventure.Monster;
import shionn.hexas.mongo.mo.adventure.Player;

/**
 * Taite une commande de combat
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class BattleHandler {

	private static final Pattern INTERVAL = Pattern.compile("(\\d+)-(\\d+)");
	@Inject
	private JacksonDBCollection<Player, String> players;

	private Random seed = new Random();

	public void run(Player player, Adventure adventure, MessageEvent<HexasBot> event) {
		Monster monster = findMonster(adventure, player);
		new Battle(seed, adventure).player(player).monster(monster).run().message().send(event);
		player.setLastBattle(System.currentTimeMillis());
		players.save(player);
	}

	private Monster findMonster(Adventure adventure, Player player) {
		List<Monster> monsters = new ArrayList<Monster>();
		for (Monster monster : adventure.getMonsters()) {
			if (isAssignable(monster, player)) {
				monsters.add(monster);
			}
		}
		if (monsters.isEmpty()) {
			monsters = adventure.getMonsters();
		}
		return monsters.get(seed.nextInt(monsters.size()));
	}

	private boolean isAssignable(Monster monster, Player player) {
		Matcher m = INTERVAL.matcher(monster.getLvl());
		int min = 0;
		int max = 0;
		if (m.matches()) {
			min = Integer.parseInt(m.group(1));
			max = Integer.parseInt(m.group(2));
		}
		return min <= player.getLvl() && player.getLvl() <= max;
	}

}
