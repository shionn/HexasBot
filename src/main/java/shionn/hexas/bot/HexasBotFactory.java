package shionn.hexas.bot;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;

import shionn.hexas.configuration.ConfigurationKey;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class HexasBotFactory {

	@Inject
	@shionn.hexas.configuration.Configuration(ConfigurationKey.BotName)
	private String name;
	@Inject
	@shionn.hexas.configuration.Configuration(ConfigurationKey.IrcHost)
	private String host;
	@Inject
	@shionn.hexas.configuration.Configuration(ConfigurationKey.IrcPort)
	private int port;
	@Inject
	@shionn.hexas.configuration.Configuration(ConfigurationKey.IrcPass)
	private String password;
	
	@Inject
	private HexasBotListener listener;

	@Produces
	@Singleton
	public HexasBot build() {
		Builder<HexasBot> builder = new Configuration.Builder<HexasBot>();
		Configuration<HexasBot> configuration = builder
				.setName(name)
				.setServer(host, port, password)
				.addListener(listener)
				.setAutoReconnect(true)
				.buildConfiguration();
		return new HexasBot(configuration);
	}

}
