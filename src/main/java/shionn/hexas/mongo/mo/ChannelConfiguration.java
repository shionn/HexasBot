package shionn.hexas.mongo.mo;

import java.util.ArrayList;
import java.util.List;

import org.mongojack.Id;

import shionn.hexas.mongo.mo.adventure.Adventure;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class ChannelConfiguration {

	@Id
	private String name;

	private List<SimpleCommand> simpleCommands = new ArrayList<>();

	private List<Timer> timers = new ArrayList<>();

	private Adventure adventure;

	public ChannelConfiguration() {
	}

	public ChannelConfiguration(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SimpleCommand> getSimpleCommands() {
		return simpleCommands;
	}

	public void setSimpleCommands(List<SimpleCommand> simples) {
		this.simpleCommands = simples;
	}

	public List<Timer> getTimers() {
		return timers;
	}

	public void setTimers(List<Timer> timers) {
		this.timers = timers;
	}

	public Adventure getAdventure() {
		return adventure;
	}

	public void setAdventure(Adventure adventure) {
		this.adventure = adventure;
	}

}
