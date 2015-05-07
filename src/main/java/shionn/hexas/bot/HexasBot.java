package shionn.hexas.bot;

import java.io.IOException;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class HexasBot extends PircBotX {

	public HexasBot(Configuration<HexasBot> configuration) {
		super(configuration);
	}

	public void stop() {
		super.shutdown(true);
	}

	public void start() throws IOException, IrcException {
		super.startBot();
	}

	public void join(String channel) {
		super.sendIRC().joinChannel(channel);
	}



}
