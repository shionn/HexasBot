package shionn.hexas.mongo.mo;


/**
 * Represente une commande survenant reguliere au bout d'un certain temps.
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Timer {

	private int delayTime;

	private int delayMessage;

	private String message;

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	public int getDelayMessage() {
		return delayMessage;
	}

	public void setDelayMessage(int delayMessage) {
		this.delayMessage = delayMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
