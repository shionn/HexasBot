package shionn.hexas.bot.handlers.adventure.action;

import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

/**
 * permet de calculer les chose relative au lvl suivant
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class NextLvl {

	public int xp(AdventureMo adventure, PlayerMo player) {
		return (int) (Math.pow(adventure.getGamer().getXpFactor(), player.getLvl() - 1) * adventure
				.getGamer().getXpBase());
	}

	public boolean lvlUp(AdventureMo adventure, PlayerMo player) {
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

	private int maxPv(AdventureMo adventure, PlayerMo player) {
		return (int) (adventure.getGamer().getPvBase() + adventure.getGamer().getPvFactor()
				* (player.getLvl() - 1));
	}

	private int maxMp(AdventureMo adventure, PlayerMo player) {
		return (int) (adventure.getGamer().getMpBase() + adventure.getGamer().getMpFactor()
				* (player.getLvl() - 1));
	}

}