package shionn.hexas.bot.handlers.adventure.manipulator;

import java.math.BigDecimal;
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

	private static final BigDecimal _100 = BigDecimal.valueOf(100);
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
				player.def(neg(equip));
				break;
			case goldBoost:
				player.goldRate(pos(equip));
				break;
			case xpBoost:
				player.xpRate(pos(equip));
				break;

			default:
				break;
			}
		}
	}

	private BigDecimal neg(EquipementMo equip) {
		return _100.subtract(new BigDecimal(equip.getVar()))
				.divide(_100);
	}

	private BigDecimal pos(EquipementMo equip) {
		return _100.add(new BigDecimal(equip.getVar())).divide(_100);
	}

}
