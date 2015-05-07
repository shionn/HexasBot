package shionn.hexas.mongo.mo;

import java.util.ArrayList;
import java.util.List;

import org.mongojack.Id;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Channel {

	@Id
	private String name;

	private List<SimpleCommand> simpleCommands = new ArrayList<>();

	private List<Timer> timers = new ArrayList<>();

	private String password = "undifined";

	public Channel() {
	}

	public Channel(String name, String password) {
		this.name = name;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Timer> getTimers() {
		return timers;
	}

	public void setTimers(List<Timer> timers) {
		this.timers = timers;
	}

}
