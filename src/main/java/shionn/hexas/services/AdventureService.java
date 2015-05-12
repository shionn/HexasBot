package shionn.hexas.services;

import java.io.File;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.slf4j.Logger;

import shionn.hexas.auth.Session;
import shionn.hexas.mongo.mo.adventure.EventMo;

/**
 * Permet d'emettre les evenement de combat
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Path("adventure")
public class AdventureService {

	private static final Pattern SHARP = Pattern.compile("#");

	@Inject
	private Logger logger;

	@Inject
	private JacksonDBCollection<EventMo, String> events;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public EventMo get(@Context HttpServletRequest request) {
		EventMo event = events.findOne(DBQuery.is("channel", new Session(request).getChannel()));
		logger.info("Request event " + new Session(request).getChannel());
		if (event != null) {
			events.removeById(event.getId());
		}
		return event;
	}

	@Path("img/{id:[^/]*}")
	@GET
	@Produces(MediaType.MEDIA_TYPE_WILDCARD)
	public Object img(@PathParam("id") String id, @Context HttpServletRequest request) {
		String channel = SHARP.matcher(new Session(request).getChannel()).replaceAll("");
		return new File(".hexas/" + channel + "/" + id.toLowerCase());

	}
}
