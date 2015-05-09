package shionn.hexas.mongo.mo.adventure;

/**
 * Représente une possibilité de craft
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Schema {

	private String item;
	private String requiereds;
	private int po;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getRequiereds() {
		return requiereds;
	}

	public void setRequiereds(String requiereds) {
		this.requiereds = requiereds;
	}

	public int getPo() {
		return po;
	}

	public void setPo(int po) {
		this.po = po;
	}

}
