package shionn.hexas.mongo.mo.adventure;

/**
 * Les messages emis dans une aventure
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Message {
	private String battleWin;
	private String battleLoose;
	private String xpGain;
	private String xpLoose;
	private String pvLoose;
	private String itemGain;
	private String itemLoose;
	private String poGain;
	private String poLoose;
	private String lvlUp;

	public String getBattleWin() {
		return battleWin;
	}

	public void setBattleWin(String battleWin) {
		this.battleWin = battleWin;
	}

	public String getBattleLoose() {
		return battleLoose;
	}

	public void setBattleLoose(String battleLoose) {
		this.battleLoose = battleLoose;
	}

	public String getXpGain() {
		return xpGain;
	}

	public void setXpGain(String xpGain) {
		this.xpGain = xpGain;
	}

	public String getXpLoose() {
		return xpLoose;
	}

	public void setXpLoose(String xpLoose) {
		this.xpLoose = xpLoose;
	}

	public String getPvLoose() {
		return pvLoose;
	}

	public void setPvLoose(String pvLoose) {
		this.pvLoose = pvLoose;
	}

	public String getItemGain() {
		return itemGain;
	}

	public void setItemGain(String itemGain) {
		this.itemGain = itemGain;
	}

	public String getItemLoose() {
		return itemLoose;
	}

	public void setItemLoose(String itemLoose) {
		this.itemLoose = itemLoose;
	}

	public String getPoGain() {
		return poGain;
	}

	public void setPoGain(String poGain) {
		this.poGain = poGain;
	}

	public String getPoLoose() {
		return poLoose;
	}

	public void setPoLoose(String poLoose) {
		this.poLoose = poLoose;
	}
	public String getLvlUp() {
		return lvlUp;
	}
	public void setLvlUp(String lvlUp) {
		this.lvlUp = lvlUp;
	}
}