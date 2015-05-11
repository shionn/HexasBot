package shionn.hexas.mongo.mo.adventure;

import java.util.List;

/**
 * Représente la configuration d'une aventure
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class AdventureMo {

	private boolean activated;
	private CommandMo commands;
	private MessageMo messages;
	private List<MonsterMo> monsters;
	private List<DropMo> drops;
	private List<UseMo> uses;
	private List<SchemaMo> schemes;
	private List<ItemShopMo> shops;
	private List<EquipementMo> equipements;

	private GamerMo gamer;

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public CommandMo getCommands() {
		return commands;
	}

	public void setCommands(CommandMo commands) {
		this.commands = commands;
	}

	public MessageMo getMessages() {
		return messages;
	}

	public void setMessages(MessageMo messages) {
		this.messages = messages;
	}

	public List<MonsterMo> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<MonsterMo> monsters) {
		this.monsters = monsters;
	}

	public GamerMo getGamer() {
		return gamer;
	}

	public void setGamer(GamerMo gamer) {
		this.gamer = gamer;
	}

	public List<DropMo> getDrops() {
		return drops;
	}

	public void setDrops(List<DropMo> drops) {
		this.drops = drops;
	}

	public List<UseMo> getUses() {
		return uses;
	}

	public void setUses(List<UseMo> uses) {
		this.uses = uses;
	}

	public List<SchemaMo> getSchemes() {
		return schemes;
	}

	public void setSchemes(List<SchemaMo> schemes) {
		this.schemes = schemes;
	}

	public List<ItemShopMo> getShops() {
		return shops;
	}

	public void setShops(List<ItemShopMo> shops) {
		this.shops = shops;
	}

	public List<EquipementMo> getEquipements() {
		return equipements;
	}

	public void setEquipements(List<EquipementMo> equipements) {
		this.equipements = equipements;
	}

}
