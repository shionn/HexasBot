package shionn.hexas.mongo.mo.adventure;


/**
 * Profile d'evolution des joueur dans {@link Adventure}
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Gamer {
	private int xpBase;
	private float xpFactor;

	private int pvBase;
	private int pvFactor;

	public int getXpBase() {
		return xpBase;
	}

	public void setXpBase(int xpBase) {
		this.xpBase = xpBase;
	}

	public float getXpFactor() {
		return xpFactor;
	}

	public void setXpFactor(float xpFactor) {
		this.xpFactor = xpFactor;
	}

	public int getPvBase() {
		return pvBase;
	}

	public void setPvBase(int pvBase) {
		this.pvBase = pvBase;
	}

	public int getPvFactor() {
		return pvFactor;
	}

	public void setPvFactor(int pvFactor) {
		this.pvFactor = pvFactor;
	}

}
