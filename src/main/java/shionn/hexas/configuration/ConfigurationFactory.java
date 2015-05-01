package shionn.hexas.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Permet d'injecter les configurations
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class ConfigurationFactory {

	private static final String FILE = "configuration.properties";

    @Produces
	@Configuration()
    public String string(InjectionPoint point) throws IOException {
		ConfigurationKey key = point.getAnnotated().getAnnotation(Configuration.class).value();
        Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE));
		return props.getProperty(key.key(), key.defolt());
    }

    @Produces
	@Configuration()
    public int integer(InjectionPoint point) throws IOException {
        return Integer.parseInt(string(point));
    }

}
