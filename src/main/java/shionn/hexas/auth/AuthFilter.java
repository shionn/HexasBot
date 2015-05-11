package shionn.hexas.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.jboss.weld.exceptions.IllegalStateException;
import org.mongojack.JacksonDBCollection;

import shionn.hexas.mongo.mo.ChannelMo;

/**
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class AuthFilter implements Filter {
	private static final Pattern SPLIT = Pattern.compile(":");

	@Inject
	private JacksonDBCollection<ChannelMo, String> channels;

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		Session session = new Session(request);
		if (session.hasChannel()) {
			chain.doFilter(request, response);
		} else {
			String auth = request.getHeader("Authorization");
			session.clearChannel();
			if (validAuth(auth)) {
				String channel = extractLogin(auth);
				String pass = extractPass(auth);
				if (isValidUser(channel, pass)) {
					session.setChannel(channel);
					chain.doFilter(req, response);
				} else {
					refuse(response);
				}
			} else {
				refuse(response);
			}
		}
	}

	private String extractLogin(String auth) {
		return SPLIT.split(decode(auth))[0];
	}

	private String extractPass(String auth) {
		try {
			return md5encode(SPLIT.split(decode(auth))[1]);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private void refuse(ServletResponse response) throws IOException {
		HttpServletResponse httpRes = (HttpServletResponse) response;
		httpRes.setHeader("WWW-Authenticate", "BASIC realm=\"Hexas\"");
		httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	private boolean isValidUser(String login, String pass) {
		ChannelMo channel = channels.findOneById(login);
		return channel != null && channel.getPassword().equals(pass);
	}

	private String md5encode(String pass) throws NoSuchAlgorithmException {
		return DigestUtils.md5Hex(pass);
	}

	private boolean validAuth(String auth) {
		return auth != null && auth.toUpperCase().startsWith("BASIC ");
	}

	private String decode(String auth) {
		String userpassEncoded = auth.substring(6);
		return new String(new Base64().decode(userpassEncoded));
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
