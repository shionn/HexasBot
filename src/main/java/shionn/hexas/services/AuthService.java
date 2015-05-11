package shionn.hexas.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
@Path("auth")
public class AuthService {

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
