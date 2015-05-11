package shionn.hexas.mongo.mo;

import java.util.ArrayList;
import java.util.List;

import org.mongojack.Id;

import shionn.hexas.mongo.mo.adventure.AdventureMo;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class ChannelConfigurationMo {

	@Id
	private String name;

	private List<SimpleCommandMo> simpleCommands = new ArrayList<>();

	private List<TimerMo> timers = new ArrayList<>();

	private AdventureMo adventure;

	public ChannelConfigurationMo() {
	}

	public ChannelConfigurationMo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SimpleCommandMo> getSimpleCommands() {
		return simpleCommands;
	}

	public void setSimpleCommands(List<SimpleCommandMo> simples) {
		this.simpleCommands = simples;
	}

	public List<TimerMo> getTimers() {
		return timers;
	}

	public void setTimers(List<TimerMo> timers) {
		this.timers = timers;
	}

	public AdventureMo getAdventure() {
		return adventure;
	}

	public void setAdventure(AdventureMo adventure) {
		this.adventure = adventure;
	}

}
