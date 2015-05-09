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
public class Adventure {

	private boolean activated;
	private Command commands;
	private Message messages;
	private List<Monster> monsters;
	private List<Drop> drops;
	private List<Use> uses;
	private List<Schema> schemes;

	private Gamer gamer;

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public Command getCommands() {
		return commands;
	}

	public void setCommands(Command commands) {
		this.commands = commands;
	}

	public Message getMessages() {
		return messages;
	}

	public void setMessages(Message messages) {
		this.messages = messages;
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public void setGamer(Gamer gamer) {
		this.gamer = gamer;
	}

	public List<Drop> getDrops() {
		return drops;
	}

	public void setDrops(List<Drop> drops) {
		this.drops = drops;
	}

	public List<Use> getUses() {
		return uses;
	}

	public void setUses(List<Use> uses) {
		this.uses = uses;
	}

	public List<Schema> getSchemes() {
		return schemes;
	}

	public void setSchemes(List<Schema> schemes) {
		this.schemes = schemes;
	}

}
