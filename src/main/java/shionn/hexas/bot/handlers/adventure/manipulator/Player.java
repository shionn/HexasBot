package shionn.hexas.bot.handlers.adventure.manipulator;

import shionn.hexas.mongo.mo.adventure.PlayerMo;

/**
 * Permet de manipuler un joueur
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Player {

	private PlayerMo mo;

	public Player(PlayerMo player) {
		this.mo = player;
	}

	public int lvl() {
		return mo.getLvl();
	}

	public int pv() {
		return mo.getPv();
	}
	
	public Player pv(int pv) {
		mo.setPv(Math.max(mo.getPv() + pv, 0));
		return this;
	}

	public int maxPv() {
		return mo.getMaxPv();
	}

	public Player xp(int xp) {
		mo.setXp(Math.max(mo.getXp() + xp, 0));
		return this;
	}

	public Player po(int po) {
		mo.setPo(Math.max(mo.getPo() + po, 0));
		return this;
	}

	public Player item(String item, int qty) {
		Integer current = mo.getItems().get(item);
		if (current == null) {
			current = 0;
		}
		current += qty;
		if (current == 0) {
			mo.getItems().remove(item);
		} else {
			mo.getItems().put(item, current);
		}
		return this;
	}

	public PlayerMo mo() {
		return mo;
	}

	public Player updateLastBattle() {
		mo.setLastBattle(System.currentTimeMillis());
		return this;
	}

}
