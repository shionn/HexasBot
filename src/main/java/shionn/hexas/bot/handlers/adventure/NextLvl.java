package shionn.hexas.bot.handlers.adventure;

import shionn.hexas.mongo.mo.adventure.Adventure;
import shionn.hexas.mongo.mo.adventure.Player;

/**
 * permet de calculer les chose relative au lvl suivant
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class NextLvl {

	public int xp(Adventure adventure, Player player) {
		return (int) (Math.pow(adventure.getGamer().getXpFactor(), player.getLvl() - 1) * adventure
				.getGamer().getXpBase());
	}

	public boolean lvlUp(Adventure adventure, Player player) {
		boolean lvlUp = false;
		if (player.getXp() >= xp(adventure, player)) {
			player.setXp(player.getXp() - xp(adventure, player));
			player.setLvl(player.getLvl() + 1);
			player.setMaxPv(maxPv(adventure, player));
			player.setMaxMp(maxMp(adventure, player));
			lvlUp = true;
		}
		return lvlUp;
	}

	private int maxPv(Adventure adventure, Player player) {
		return (int) (adventure.getGamer().getPvBase() + adventure.getGamer().getPvFactor()
				* (player.getLvl() - 1));
	}

	private int maxMp(Adventure adventure, Player player) {
		return (int) (adventure.getGamer().getMpBase() + adventure.getGamer().getMpFactor()
				* (player.getLvl() - 1));
	}

}
