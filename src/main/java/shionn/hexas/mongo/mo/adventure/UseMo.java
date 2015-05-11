package shionn.hexas.mongo.mo.adventure;

/**
 * Une utilisation d'objet
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class UseMo {
	public enum ItemUsage {
		pvGain, xpGain
	}

	private String item;
	private String message;
	private ItemUsage usage;
	private String var;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ItemUsage getUsage() {
		return usage;
	}

	public void setUsage(ItemUsage usage) {
		this.usage = usage;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

}
