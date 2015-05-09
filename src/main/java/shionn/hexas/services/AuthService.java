package shionn.hexas.services;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.digest.DigestUtils;
import org.mongojack.JacksonDBCollection;

import shionn.hexas.auth.Authentification;
import shionn.hexas.auth.Session;
import shionn.hexas.mongo.mo.Channel;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
@Path("auth")
public class AuthService {
	@Inject
	private JacksonDBCollection<Channel, String> channels;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authentify(Authentification auth, @Context HttpServletRequest request) {
		Channel channel = channels.findOneById(auth.getChannel());
		String pass = DigestUtils.md5Hex(auth.getPassword());
		Session session = new Session(request);
		session.clearChannel();
		boolean valid = false;
		if (channel != null && channel.getPassword().equals(pass)) {
			session.setChannel(auth.getChannel());
			valid = true;
		}
		return Response.status(valid ? Response.Status.ACCEPTED : Response.Status.UNAUTHORIZED)
				.entity(true).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public boolean authentified(@Context HttpServletRequest request) {
		return new Session(request).hasChannel();
	}

	@Path("password/{password:[^/]*}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String generatePassword(@PathParam("password") String password) {
		return DigestUtils.md5Hex(password);
	}

	// @POST()
	// @Path("register")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public User register(Authentification auth) {
	// String pass = DigestUtils.md5Hex(auth.getPassword());
	// return users.insert(new User(auth.getUser(), pass)).getSavedObject();
	// }

}
