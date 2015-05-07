package shionn.hexas.services;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.mongojack.JacksonDBCollection;

import shionn.hexas.auth.Session;
import shionn.hexas.mongo.mo.ChannelConfiguration;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
@Path("channel")
public class ChannelService {
	@Inject
	private JacksonDBCollection<ChannelConfiguration, String> channels;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ChannelConfiguration save(ChannelConfiguration channel,
			@Context HttpServletRequest request) {
		String id = new Session(request).getChannel();
		channels.updateById(id, channel);
		return channel;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ChannelConfiguration load(@Context HttpServletRequest request) {
		String id = new Session(request).getChannel();
		ChannelConfiguration configuration = channels.findOneById(id);
		if (configuration == null) {
			configuration = new ChannelConfiguration(id);
			channels.insert(configuration);
		}
		return configuration;
	}

}
