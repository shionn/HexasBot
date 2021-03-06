package shionn.hexas.bot.handlers.adventure.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shionn.hexas.bot.handlers.adventure.manipulator.Event;
import shionn.hexas.bot.handlers.adventure.manipulator.Events;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.adventure.DropMo;
import shionn.hexas.mongo.mo.adventure.MonsterMo;

/**
 * Permet de traiter un combat
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Battle {
	private static final int LOOSE_FACTOR = 3;

	private static final Pattern INTERVAL = Pattern.compile("(\\d+)-(\\d+)");

	private Player player;
	private MonsterMo monster;
	private Random seed;
	private Message message;

	private Events events = new Events();
	private NextLvl nextLvl = new NextLvl();


	public Battle(Random seed, Player player) {
		this.seed = seed;
		this.player = player;
		this.message = new Message(player);
	}

	public Battle run() {
		int damage = damage();
		if (player.pv() > 0) {
			win(damage);
		} else {
			loose();
		}
		return this;
	}

	private void win(int damage) {
		Event battle = events.battle();
		int po = po();
		int xp = xp();
		player.xp(xp).po(po);
		DropMo drop = drop();
		message.battleWin().pvLoose().xpGain().poGain();
		if (nextLvl.lvlUp(player)) {
			message.lvlUp().gamer();
			events.lvlUp().message(new Message(player).lvlUp().gamer());
		}
		if (drop != null) {
			message.itemGain().drop(drop);
		}
		message.player(player).monster(monster).pv(damage).po(po).xp(xp);
		battle.monster(monster).win().message(message);
	}

	private void loose() {
		int po = poLoose();
		int xp = xpLoose();
		player.po(-po).xp(-xp).pv(player.maxPv() / 3);
		message.battleLoose().pvGain().xpLoose().poLoose().player(player).monster(monster).po(po)
				.xp(xp);
		events.battle().monster(monster).loose().message(message);
	}

	private int damage() {
		int damage = randomInterval(monster.getDamage());
		damage = Math.max(player.def().multiply(BigDecimal.valueOf(damage)).intValue(), 1);
		player.pv(-damage);
		return damage;
	}

	private int xp() {
		return new BigDecimal(monster.getXp()).multiply(player.xpRate()).intValue();
	}

	private int xpLoose() {
		return new BigDecimal(monster.getXp() * LOOSE_FACTOR).multiply(player.xpRate()).intValue();
	}

	private int po() {
		int po = randomInterval(monster.getPo());
		po = new BigDecimal(po).multiply(player.goldRate()).intValue();
		return po;
	}

	private int poLoose() {
		int po = randomInterval(monster.getPo()) * LOOSE_FACTOR;
		po = new BigDecimal(po).multiply(player.goldRate()).intValue();
		return po;
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

	private DropMo drop() {
		DropMo drop = findDrop();
		if (drop != null) {
			player.item(drop.getItem(), 1);
		}
		return drop;
	}

	private DropMo findDrop() {
		List<DropMo> drops = new ArrayList<>();
		for (DropMo drop : player.adventure().getDrops()) {
			if (drop.getMonster().equals(monster.getName()) && drop.getRate() > seed.nextInt(100)) {
				drops.add(drop);
			}
		}
		DropMo drop = null;
		if (!drops.isEmpty()) {
			drop = drops.get(seed.nextInt(drops.size()));
		}
		return drop;
	}

	public Battle player(Player player) {
		this.player = player;
		return this;
	}

	public Battle monster(MonsterMo monster) {
		this.monster = monster;
		return this;
	}

	public Message message() {
		return message;
	}

	public Events events() {
		return events;
	}
}
