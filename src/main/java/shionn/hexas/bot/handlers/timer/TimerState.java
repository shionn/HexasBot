package shionn.hexas.bot.handlers.timer;

/**
 * permet de connaitre l'etat d'un timer
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class TimerState {
	private long lastTime;
	private int countMessage;

	public TimerState() {
		lastTime = 0;
		countMessage = 0;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public int getCountMessage() {
		return countMessage;
	}

	public void setCountMessage(int countMessage) {
		this.countMessage = countMessage;
	}

}
