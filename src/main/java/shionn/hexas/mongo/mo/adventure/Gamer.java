package shionn.hexas.mongo.mo.adventure;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

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
	@JsonSerialize(using = ToStringSerializer.class)
	// @JsonDeserialize(using = BigDecimalDeserializer.class)
	private BigDecimal xpFactor;

	public int getXpBase() {
		return xpBase;
	}

	public void setXpBase(int xpBase) {
		this.xpBase = xpBase;
	}

	public BigDecimal getXpFactor() {
		return xpFactor;
	}

	public void setXpFactor(BigDecimal xpFactor) {
		this.xpFactor = xpFactor;
	}

}
