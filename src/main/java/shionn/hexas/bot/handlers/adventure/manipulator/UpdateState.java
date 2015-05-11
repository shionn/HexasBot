package shionn.hexas.bot.handlers.adventure.manipulator;

import java.util.List;

import shionn.hexas.mongo.mo.adventure.EquipementMo;

/**
 * Permet de mettre à jour les statistiques en fonction de l'equipement
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class UpdateState {

	private Player player;

	public UpdateState(Player player) {
		this.player = player;
	}

	public void update() {
		List<EquipementMo> equips = player.equips();
		player.resetStat();
		for (EquipementMo equip : equips) {
			switch (equip.getEffect()) {
			case armor:
				player.def(rate(equip));
				break;

			default:
				break;
			}
		}
	}

	private float rate(EquipementMo equip) {
		return (100 - Integer.parseInt(equip.getVar())) / 100f;
	}

}
