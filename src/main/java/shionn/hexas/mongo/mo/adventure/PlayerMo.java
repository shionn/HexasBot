package shionn.hexas.mongo.mo.adventure;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.mongojack.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * un joueur de l'aventure
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class PlayerMo {

	@Id
	private String key;

	private int lvl = 1;
	private int xp = 0;
	private int po = 0;
	private int pv = 0;
	private int maxPv = 0;
	private int mp = 0;
	private int maxMp = 0;

	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal defRate = BigDecimal.ONE;
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal goldRate = BigDecimal.ONE;
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal xpRate = BigDecimal.ONE;

	private Map<String, Integer> items = new HashMap<String, Integer>();

	private long lastBattle;
	private long lastStat;
	private long lastBag;
	private long lastItemUse;
	private long lastCraft;
	private long lastShop;

	public PlayerMo() {
	}

	public PlayerMo(String key) {
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
		this.xp = Math.max(xp, 0);
	}

	public int getPo() {
		return po;
	}

	public void setPo(int po) {
		this.po = Math.max(po, 0);
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
		this.pv = Math.max(pv, 0);
	}

	public int getMaxPv() {
		return maxPv;
	}

	public void setMaxPv(int maxPv) {
		this.maxPv = maxPv;
	}

	public long getLastCraft() {
		return lastCraft;
	}

	public void setLastCraft(long lastCraft) {
		this.lastCraft = lastCraft;
	}

	public long getLastStat() {
		return lastStat;
	}

	public void setLastStat(long lastStat) {
		this.lastStat = lastStat;
	}

	public long getLastBag() {
		return lastBag;
	}

	public void setLastBag(long lastBag) {
		this.lastBag = lastBag;
	}

	public long getLastItemUse() {
		return lastItemUse;
	}

	public void setLastItemUse(long lastItemUse) {
		this.lastItemUse = lastItemUse;
	}

	public long getLastShop() {
		return lastShop;
	}

	public void setLastShop(long lastShop) {
		this.lastShop = lastShop;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public int getMaxMp() {
		return maxMp;
	}

	public void setMaxMp(int maxMp) {
		this.maxMp = maxMp;
	}

	public BigDecimal getDefRate() {
		return defRate;
	}

	public void setDefRate(BigDecimal defRate) {
		this.defRate = defRate;
	}

	public BigDecimal getGoldRate() {
		return goldRate;
	}

	public void setGoldRate(BigDecimal goldRate) {
		this.goldRate = goldRate;
	}

	public BigDecimal getXpRate() {
		return xpRate;
	}

	public void setXpRate(BigDecimal xpRate) {
		this.xpRate = xpRate;
	}


}
