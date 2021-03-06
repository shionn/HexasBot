package shionn.hexas.mongo.mo.adventure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Les messages emis dans une aventure
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageMo {
	private String battleWin;
	private String battleLoose;
	private String coldDown;
	private String xpGain;
	private String xpLoose;
	private String pvGain;
	private String pvLoose;
	private String itemGain;
	private String itemLoose;
	private String poGain;
	private String poLoose;
	private String lvlUp;
	private String stat;
	private String bag;
	private String bagEmpty;
	private String craft;
	private String noItem;
	private String noUse;
	private String noSchema;
	private String needItem;
	private String helpCraft;
	private String helpUse;
	private String helpShop;
	private String itemShopDesc;
	private String shopNoItem;
	private String shopNotEnouthMoney;
	private String shopBuy;
	private String topLvl;
	private String topPo;

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

	public String getPvGain() {
		return pvGain;
	}

	public void setPvGain(String pvGain) {
		this.pvGain = pvGain;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getBag() {
		return bag;
	}

	public void setBag(String bag) {
		this.bag = bag;
	}

	public String getNoItem() {
		return noItem;
	}

	public void setNoItem(String noItem) {
		this.noItem = noItem;
	}

	public String getNoUse() {
		return noUse;
	}

	public void setNoUse(String noUse) {
		this.noUse = noUse;
	}

	public String getNoSchema() {
		return noSchema;
	}

	public void setNoSchema(String noSchema) {
		this.noSchema = noSchema;
	}

	public String getNeedItem() {
		return needItem;
	}

	public void setNeedItem(String needItem) {
		this.needItem = needItem;
	}

	public String getCraft() {
		return craft;
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}

	public String getHelpUse() {
		return helpUse;
	}

	public void setHelpUse(String helpUse) {
		this.helpUse = helpUse;
	}

	public String getHelpCraft() {
		return helpCraft;
	}

	public void setHelpCraft(String helpCraft) {
		this.helpCraft = helpCraft;
	}

	public String getColdDown() {
		return coldDown;
	}

	public void setColdDown(String coldDown) {
		this.coldDown = coldDown;
	}

	public String getHelpShop() {
		return helpShop;
	}

	public void setHelpShop(String helpShop) {
		this.helpShop = helpShop;
	}

	public String getItemShopDesc() {
		return itemShopDesc;
	}

	public void setItemShopDesc(String itemShopDesc) {
		this.itemShopDesc = itemShopDesc;
	}

	public String getShopNoItem() {
		return shopNoItem;
	}

	public void setShopNoItem(String shopNoItem) {
		this.shopNoItem = shopNoItem;
	}

	public String getShopNotEnouthMoney() {
		return shopNotEnouthMoney;
	}

	public void setShopNotEnouthMoney(String shopNotEnouthMoney) {
		this.shopNotEnouthMoney = shopNotEnouthMoney;
	}

	public String getShopBuy() {
		return shopBuy;
	}

	public void setShopBuy(String shopBuy) {
		this.shopBuy = shopBuy;
	}

	public String getBagEmpty() {
		return bagEmpty;
	}

	public void setBagEmpty(String bagEmpty) {
		this.bagEmpty = bagEmpty;
	}

	public String getTopLvl() {
		return topLvl;
	}

	public void setTopLvl(String topLvl) {
		this.topLvl = topLvl;
	}

	public String getTopPo() {
		return topPo;
	}

	public void setTopPo(String topPo) {
		this.topPo = topPo;
	}

}
