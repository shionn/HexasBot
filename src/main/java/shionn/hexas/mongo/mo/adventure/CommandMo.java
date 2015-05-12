package shionn.hexas.mongo.mo.adventure;

/**
 * Les commande de {@link AdventureMo}
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class CommandMo {
	private String battle;
	private String bag;
	private String itemUse;
	private String stat;
	private String craft;
	private String shop;
	private boolean autoBattleColdDown;
	private float battleColdDown;
	private float bagColdDown;
	private float itemUseColdDown;
	private float statColdDown;
	private float craftColdDown;
	private float shopColdDown;


	public String getBattle() {
		return battle;
	}

	public void setBattle(String battle) {
		this.battle = battle;
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

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public float getBattleColdDown() {
		return battleColdDown;
	}

	public void setBattleColdDown(float battleColdDown) {
		this.battleColdDown = battleColdDown;
	}

	public float getBagColdDown() {
		return bagColdDown;
	}

	public void setBagColdDown(float bagColdDown) {
		this.bagColdDown = bagColdDown;
	}

	public float getItemUseColdDown() {
		return itemUseColdDown;
	}

	public void setItemUseColdDown(float itemUseColdDown) {
		this.itemUseColdDown = itemUseColdDown;
	}

	public float getStatColdDown() {
		return statColdDown;
	}

	public void setStatColdDown(float statColdDown) {
		this.statColdDown = statColdDown;
	}

	public float getCraftColdDown() {
		return craftColdDown;
	}

	public void setCraftColdDown(float craftColdDown) {
		this.craftColdDown = craftColdDown;
	}

	public float getShopColdDown() {
		return shopColdDown;
	}

	public void setShopColdDown(float shopColdDown) {
		this.shopColdDown = shopColdDown;
	}

	public boolean isAutoBattleColdDown() {
		return autoBattleColdDown;
	}

	public void setAutoBattleColdDown(boolean autoBattleColdDown) {
		this.autoBattleColdDown = autoBattleColdDown;
	}

}
