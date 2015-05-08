package shionn.hexas.mongo.mo.adventure;

import java.util.HashMap;
import java.util.Map;

import org.mongojack.Id;

/**
 * un joueur de l'aventure
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Player {

	@Id
	private String key;

	private int lvl = 1;

	private int xp = 0;

	private int po = 0;

	private int pv = 0;

	private int maxPv = 0;

	private Map<String, Integer> items = new HashMap<>();

	private long lastBattle;

	public Player() {
	}

	public Player(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getPo() {
		return po;
	}

	public void setPo(int po) {
		this.po = po;
	}

	public Map<String, Integer> getItems() {
		return items;
	}

	public void setItems(Map<String, Integer> items) {
		this.items = items;
	}

	public long getLastBattle() {
		return lastBattle;
	}

	public void setLastBattle(long lastBattle) {
		this.lastBattle = lastBattle;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public int getMaxPv() {
		return maxPv;
	}

	public void setMaxPv(int maxPv) {
		this.maxPv = maxPv;
	}

}
