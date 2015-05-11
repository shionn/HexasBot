package shionn.hexas.bot.handlers.adventure.action;

import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.mongo.mo.adventure.GamerMo;

/**
 * permet de calculer les chose relative au lvl suivant
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class NextLvl {

	public int xp(Player player) {
		GamerMo gamer = player.adventure().getGamer();
		return (int) (Math.pow(gamer.getXpFactor(), player.lvl() - 1) * gamer.getXpBase());
	}

	public boolean lvlUp(Player player) {
		boolean lvlUp = false;
		if (player.xp() >= xp(player)) {
			player.xp(-xp(player)).lvl(1).maxPv(maxPv(player)).maxMp(maxMp(player));
			lvlUp = true;
		}
		return lvlUp;
	}

	private int maxPv(Player player) {
		GamerMo gamer = player.adventure().getGamer();
		return (int) (gamer.getPvBase() + gamer.getPvFactor() * (player.lvl() - 1));
	}

	private int maxMp(Player player) {
		GamerMo gamer = player.adventure().getGamer();
		return (int) (gamer.getMpBase() + gamer.getMpFactor() * (player.lvl() - 1));
	}

}
