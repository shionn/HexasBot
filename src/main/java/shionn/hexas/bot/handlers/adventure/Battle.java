package shionn.hexas.bot.handlers.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shionn.hexas.bot.messages.MessageBuilder;
import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.DropMo;
import shionn.hexas.mongo.mo.adventure.MonsterMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

/**
 * Permet de traiter un combat
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Battle {
	private static final Pattern INTERVAL = Pattern.compile("(\\d+)-(\\d+)");

	private PlayerMo player;
	private MonsterMo monster;
	private Random seed;
	private AdventureMo adventure;
	private MessageBuilder message = new MessageBuilder("");

	private NextLvl nextLvl = new NextLvl();

	public Battle(Random seed, AdventureMo adventure) {
		this.seed = seed;
		this.adventure = adventure;
	}

	public Battle run() {
		int damage = damage();
		if (player.getPv() > 0) {
			win(damage);
		} else {
			loose();
		}
		return this;
	}

	private void win(int damage) {
		player.xp(monster.getXp());
		int po = randomInterval(monster.getPo());
		player.po(po);
		DropMo drop = drop();
		message.append(adventure.getMessages().getBattleWin())
				.append(adventure.getMessages().getPvLoose())
				.append(adventure.getMessages().getXpGain())
				.append(adventure.getMessages().getPoGain());
		if (nextLvl.lvlUp(adventure, player)) {
			message.append(adventure.getMessages().getLvlUp()).gamer(adventure.getGamer());
		}
		if (drop != null) {
			message.append(adventure.getMessages().getItemGain()).drop(drop);
		}
		message.player(player).monster(monster).pv(damage).po(po);
	}

	private void loose() {
		int po = randomInterval(monster.getPo());
		player.po(-po).xp(-monster.getXp()).pv(player.getMaxPv() / 2);
		message.append(adventure.getMessages().getBattleLoose())
				.append(adventure.getMessages().getPvGain())
				.append(adventure.getMessages().getXpLoose())
				.append(adventure.getMessages().getPoLoose()).player(player).monster(monster)
				.po(po);
	}

	private int damage() {
		int damage = randomInterval(monster.getDamage());
		player.pv(-damage);
		return damage;
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
		for (DropMo drop : adventure.getDrops()) {
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

	public Battle player(PlayerMo player) {
		this.player = player;
		return this;
	}

	public Battle monster(MonsterMo monster) {
		this.monster = monster;
		return this;
	}

	public MessageBuilder message() {
		return message;
	}

}
