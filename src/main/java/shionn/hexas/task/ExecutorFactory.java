package shionn.hexas.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import shionn.hexas.configuration.Configuration;
import shionn.hexas.configuration.ConfigurationKey;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class ExecutorFactory {

	@Inject
	@Configuration(ConfigurationKey.NbThreads)
	private int nbThreads;

	@Produces
	@Singleton
	public ScheduledExecutorService build() {
		return Executors.newScheduledThreadPool(nbThreads);
	}

}
