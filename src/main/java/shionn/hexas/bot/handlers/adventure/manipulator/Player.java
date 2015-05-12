package shionn.hexas.bot.handlers.adventure.manipulator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.EquipementMo;
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

	private static final BigDecimal _100 = BigDecimal.valueOf(100);
	private PlayerMo mo;
	private AdventureMo adventure;

	public Player(PlayerMo player, AdventureMo adventure) {
		this.mo = player;
		this.adventure = adventure;
	}

	public int lvl() {
		return mo.getLvl();
	}

	public Player lvl(int lvl) {
		mo.setLvl(lvl() + lvl);
		return this;
	}

	public int pv() {
		return mo.getPv();
	}

	public Player pv(int pv) {
		mo.setPv(Math.min(Math.max(pv() + pv, 0), maxPv()));
		return this;
	}

	public int maxPv() {
		return mo.getMaxPv();
	}

	public Player maxPv(int maxPv) {
		mo.setMaxPv(maxPv);
		return this;
	}

	public int mp() {
		return mo.getMp();
	}

	public int maxMp() {
		return mo.getMaxMp();
	}

	public Player maxMp(int maxMp) {
		mo.setMaxMp(maxMp);
		return this;
	}

	public int xp() {
		return mo.getXp();
	}

	public Player xp(int xp) {
		mo.setXp(Math.max(mo.getXp() + xp, 0));
		return this;
	}

	public int po() {
		return mo.getPo();
	}

	public Player po(int po) {
		mo.setPo(Math.max(mo.getPo() + po, 0));
		return this;
	}

	public BigDecimal def() {
		return mo.getDefRate();
	}

	public Player def(BigDecimal def) {
		mo.setDefRate(def().multiply(def));
		return this;
	}

	public int defPc() {
		return BigDecimal.ONE.subtract(def()).multiply(_100).intValue();
	}

	public BigDecimal goldRate() {
		return mo.getGoldRate();
	}

	public Player goldRate(BigDecimal rate) {
		mo.setGoldRate(goldRate().multiply(rate));
		return this;
	}

	public BigDecimal xpRate() {
		return mo.getXpRate();
	}

	public Player xpRate(BigDecimal rate) {
		mo.setXpRate(xpRate().multiply(rate));
		return this;
	}

	public Map<String, Integer> items() {
		return mo.getItems();
	}

	public int item(String name) {
		int qty = 0;
		Iterator<Entry<String, Integer>> ite = items().entrySet().iterator();
		while (qty == 0 && ite.hasNext()) {
			Entry<String, Integer> item = ite.next();
			if (item.getKey().equalsIgnoreCase(name)) {
				qty = item.getValue();
			}
		}
		return qty;
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
		new UpdateState(this).update();
		return this;
	}

	public Player resetStat() {
		mo.setDefRate(BigDecimal.ONE);
		mo.setGoldRate(BigDecimal.ONE);
		mo.setXpRate(BigDecimal.ONE);
		return this;
	}

	public List<EquipementMo> equips() {
		List<EquipementMo> equips = new ArrayList<EquipementMo>();
		for (EquipementMo equip : adventure.getEquipements()) {
			if (item(equip.getItem()) > 0) {
				equips.add(equip);
			}
		}
		return equips;
	}

	/**
	 * Renvoi l'objet mongo pour le sauvegarder
	 */
	public PlayerMo mo() {
		return mo;
	}

	public AdventureMo adventure() {
		return adventure;
	}

	/*
	 * Mise a jour des last
	 */
	public Player updateLastBattle() {
		mo.setLastBattle(System.currentTimeMillis());
		return this;
	}

	public Player updateLastBag() {
		mo.setLastBag(System.currentTimeMillis());
		return this;
	}

	public Player updateLastCraft() {
		mo.setLastCraft(System.currentTimeMillis());
		return this;
	}

	public Player updateLastItemUse() {
		mo.setLastItemUse(System.currentTimeMillis());
		return this;
	}

	public Player updateLastShop() {
		mo.setLastShop(System.currentTimeMillis());
		return this;
	}

	public Player updateLastStat() {
		mo.setLastStat(System.currentTimeMillis());
		return this;
	}

}
