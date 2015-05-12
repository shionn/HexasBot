/**
 * 
 */
package shionn.hexas.mongo;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.mongojack.JacksonDBCollection;

import shionn.hexas.configuration.Configuration;
import shionn.hexas.configuration.ConfigurationKey;
import shionn.hexas.mongo.mo.ChannelConfigurationMo;
import shionn.hexas.mongo.mo.ChannelMo;
import shionn.hexas.mongo.mo.adventure.EventMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class JacksonDBCollectionFactory {

	@Inject
	private MongoClient client;

	@Inject
	@Configuration(ConfigurationKey.MongoDB)
	private String database;

	@Produces
	public JacksonDBCollection<ChannelMo, String> channels() {
		return collection(ChannelMo.class, String.class);
	}

	@Produces
	public JacksonDBCollection<ChannelConfigurationMo, String> configurations() {
		return collection(ChannelConfigurationMo.class, String.class);
	}

	@Produces
	public JacksonDBCollection<PlayerMo, String> datas() {
		return collection(PlayerMo.class, String.class);
	}

	@Produces
	public JacksonDBCollection<EventMo, String> events() {
		return collection(EventMo.class, String.class);
	}

	private <Type, Key> JacksonDBCollection<Type, Key> collection(Class<Type> type, Class<Key> key) {
		return JacksonDBCollection.wrap(collection(type), type, key);
	}

	private DBCollection collection(Class<?> type) {
		return collection(type.getSimpleName().replace("Mo", ""));
	}

	private DBCollection collection(String name) {
		return db().getCollection(name.toLowerCase());
	}

	private DB db() {
		return client.getDB(database);
	}

}
