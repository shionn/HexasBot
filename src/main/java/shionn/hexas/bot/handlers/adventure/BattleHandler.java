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
import shionn.hexas.bot.handlers.adventure.action.Battle;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.mongo.mo.adventure.EventMo;
import shionn.hexas.mongo.mo.adventure.MonsterMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

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
	private JacksonDBCollection<PlayerMo, String> players;
	@Inject
	private JacksonDBCollection<EventMo, String> events;

	private Random seed = new Random();

	public void run(Player player, MessageEvent<HexasBot> event) {
		MonsterMo monster = findMonster(player);
		Battle battle = new Battle(seed, player).monster(monster).run();
		battle.message().send(event);
		players.save(player.updateLastBattle().mo());
		events.insert(battle.event(event).mo());
	}

	private MonsterMo findMonster(Player player) {
		List<MonsterMo> monsters = new ArrayList<MonsterMo>();
		for (MonsterMo monster : player.adventure().getMonsters()) {
			if (isAssignable(monster, player)) {
				monsters.add(monster);
			}
		}
		if (monsters.isEmpty()) {
			monsters = player.adventure().getMonsters();
		}
		return monsters.get(seed.nextInt(monsters.size()));
	}

	private boolean isAssignable(MonsterMo monster, Player player) {
		Matcher m = INTERVAL.matcher(monster.getLvl());
		int min = 0;
		int max = 0;
		if (m.matches()) {
			min = Integer.parseInt(m.group(1));
			max = Integer.parseInt(m.group(2));
		}
		return min <= player.lvl() && player.lvl() <= max;
	}

}
