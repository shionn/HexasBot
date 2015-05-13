package shionn.hexas.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.slf4j.Logger;

import shionn.hexas.mongo.mo.ChannelMo;

/**
 * Tache qui se charge de rejoindre automatiquement les channels
 * 
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class HexasBotJoinChannelTask implements Runnable {
	@Inject
	private Logger logger;

	@Inject
	private HexasBot bot;

	@Inject
	private JacksonDBCollection<ChannelMo, String> channels;

	@Override
	public void run() {
		if (!bot.isConnected()) {
			new Thread(new HexasBotConnectTask(bot, logger)).start();
		} else {
			DBCursor<ChannelMo> notConnectes = channels.find(
					DBQuery.notIn("_id", extractNames(bot.getUserChannelDao().getAllChannels())))
					.limit(10);
			for (ChannelMo notConnect : notConnectes) {
				logger.info("Auto join : " + notConnect.getName());
				bot.join(notConnect.getName());
			}
		}
	}

	private List<String> extractNames(Set<org.pircbotx.Channel> allChannels) {
		List<String> names = new ArrayList<>();
		for (org.pircbotx.Channel channel : allChannels) {
			names.add(channel.getName());
		}
		return names;
	}

}
