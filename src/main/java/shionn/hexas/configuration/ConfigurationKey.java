package shionn.hexas.configuration;

/**
 * Clef des configuration pour l'injection
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public enum ConfigurationKey {

	Debug("debug", "false"),

	BotAutoJoinDelai("bot.autojoindelai", "5"),

	BotName("irc.login"), IrcHost("irc.host"), IrcPort("irc.port"), IrcPass("irc.pass"),

	MongoHost("mongo.host"), MongoPort("mongo.port"), MongoDB("mongo.database"),

	Undifined(null), NbThreads("nbthread");

	private String key;
	private String defolt;

	private ConfigurationKey(String key) {
		this(key, key + "not found");
	}

	private ConfigurationKey(String key, String defolt) {
		this.key = key;
		this.defolt = defolt;
	}

	public String key() {
		return key;
	}

	public String defolt() {
		return defolt;
	}
}
