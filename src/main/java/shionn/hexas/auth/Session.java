package shionn.hexas.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class Session {

	private static final String CHANNEL = "channel";

    private HttpSession session;

    public Session(HttpServletRequest request) {
        session = request.getSession();
    }

    public void clearChannel() {
		session.removeAttribute(CHANNEL);
    }

    public void setChannel(String user) {
		session.setAttribute(CHANNEL, user);
    }

    public boolean hasChannel() {
		return session.getAttribute(CHANNEL) != null;
    }

	public String getChannel() {
		return (String) session.getAttribute(CHANNEL);
    }

}
