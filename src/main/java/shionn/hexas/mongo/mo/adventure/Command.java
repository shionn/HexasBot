package shionn.hexas.mongo.mo.adventure;


/**
 * Les commande de {@link Adventure}
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Command {
	private String battle;
	private int battleColdDown;
	private String bag;
	private int bagColdDown;
	private String itemUse;
	private int itemUseColdDown;
	private String stat;
	private int statColdDown;
	private String craft;
	private int craftColdDown;
	private String shop;
	private int shopColdDown;

	public String getBattle() {
		return battle;
	}

	public void setBattle(String battle) {
		this.battle = battle;
	}

	public int getBattleColdDown() {
		return battleColdDown;
	}

	public void setBattleColdDown(int battleColdDown) {
		this.battleColdDown = battleColdDown;
	}

	public String getBag() {
		return bag;
	}

	public void setBag(String bag) {
		this.bag = bag;
	}


	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getItemUse() {
		return itemUse;
	}

	public void setItemUse(String itemUse) {
		this.itemUse = itemUse;
	}

	public String getCraft() {
		return craft;
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}

	public int getBagColdDown() {
		return bagColdDown;
	}

	public void setBagColdDown(int bagColdDown) {
		this.bagColdDown = bagColdDown;
	}

	public int getItemUseColdDown() {
		return itemUseColdDown;
	}

	public void setItemUseColdDown(int itemUseColdDown) {
		this.itemUseColdDown = itemUseColdDown;
	}

	public int getStatColdDown() {
		return statColdDown;
	}

	public void setStatColdDown(int statColdDown) {
		this.statColdDown = statColdDown;
	}

	public int getCraftColdDown() {
		return craftColdDown;
	}

	public void setCraftColdDown(int craftColdDown) {
		this.craftColdDown = craftColdDown;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public int getShopColdDown() {
		return shopColdDown;
	}

	public void setShopColdDown(int shopColdDown) {
		this.shopColdDown = shopColdDown;
	}

}
