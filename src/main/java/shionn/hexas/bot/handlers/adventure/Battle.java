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
import shionn.hexas.bot.messages.MessageBuilder;
import shionn.hexas.mongo.mo.adventure.Adventure;
import shionn.hexas.mongo.mo.adventure.Drop;
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
public class Battle {

	private static final Pattern INTERVAL = Pattern.compile("(\\d+)-(\\d+)");
	@Inject
	private JacksonDBCollection<Player, String> players;

	private Random seed = new Random();

	@Inject
	private NextLvl nextLvl;

	public void run(Player player, Adventure adventure, MessageEvent<HexasBot> event) {
		Monster monster = findMonster(adventure, player);
		int damage = damage(player, monster);
		if (player.getPv() > 0) {
			win(player, monster, damage, adventure, event);
		} else {
			loose(player, monster, adventure, event);
		}
		player.setLastBattle(System.currentTimeMillis());
		players.save(player);
	}

	public void win(Player player, Monster monster, int damage, Adventure adventure,
			MessageEvent<HexasBot> event) {
		player.setXp(player.getXp() + monster.getXp());
		int po = randomInterval(monster.getPo());
		player.setPo(player.getPo() + po);
		Drop drop = drop(adventure, monster, player);
		MessageBuilder message = new MessageBuilder(adventure.getMessages().getBattleWin())
				.append(adventure.getMessages().getPvLoose())
				.append(adventure.getMessages().getXpGain())
				.append(adventure.getMessages().getPoGain());
		if (nextLvl.lvlUp(adventure, player)) {
			message.append(adventure.getMessages().getLvlUp());
		}
		if (drop != null) {
			message.append(adventure.getMessages().getItemGain()).drop(drop);
		}
		message.player(player).monster(monster).pv(damage).po(po).send(event);
	}

	public void loose(Player player, Monster monster, Adventure adventure,
			MessageEvent<HexasBot> event) {
		int po = randomInterval(monster.getPo());
		player.setPo(player.getPo() - po);
		player.setXp(player.getXp() - monster.getXp());
		player.setPv(player.getMaxPv() / 2);
		new MessageBuilder(adventure.getMessages().getBattleLoose())
				.append(adventure.getMessages().getPvGain())
				.append(adventure.getMessages().getXpLoose())
				.append(adventure.getMessages().getPoLoose()).player(player).monster(monster)
				.po(po).send(event);
	}

	private int damage(Player player, Monster monster) {
		int damage = randomInterval(monster.getDamage());
		player.setPv(player.getPv() - damage);
		return damage;
	}

	private Drop drop(Adventure adventure, Monster monster, Player player) {
		Drop drop = findDrop(adventure, monster);
		if (drop != null) {
			Integer qty = player.getItems().get(drop.getItem());
			if (qty == null) {
				qty = 0;
			}
			qty++;
			player.getItems().put(drop.getItem(), qty);
		}
		return drop;
	}

	private Drop findDrop(Adventure adventure, Monster monster) {
		List<Drop> drops = new ArrayList<>();
		for (Drop drop : adventure.getDrops()) {
			if (drop.getMonster().equals(monster.getName()) && drop.getRate() > seed.nextInt(100)) {
				drops.add(drop);
			}
		}
		Drop drop = null;
		if (!drops.isEmpty()) {
			drop = drops.get(seed.nextInt(drops.size()));
		}
		return drop;
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

	private int randomInterval(String inter) {
		Matcher m = INTERVAL.matcher(inter);
		int value = 0;
		if (m.find()) {
			int min = Integer.parseInt(m.group(1));
			int max = Integer.parseInt(m.group(2));
			value = seed.nextInt(max - min) + min;
		}
		return value;
	}

}
