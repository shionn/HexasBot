package shionn.hexas.mongo;

import java.net.UnknownHostException;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import shionn.hexas.configuration.Configuration;
import shionn.hexas.configuration.ConfigurationKey;

import com.mongodb.MongoClient;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a- C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class MongoClientFactory {

    @Inject
	@Configuration(ConfigurationKey.MongoHost)
    private String host;

    @Inject
	@Configuration(ConfigurationKey.MongoPort)
    private int port;

    @Produces
    @Singleton
    public MongoClient build() throws UnknownHostException {
        return new MongoClient(host, port);
    }

    public void close(@Disposes MongoClient client) {
        client.close();
    }

}
