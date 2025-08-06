package hexas.db.dbo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

	private int id;
	private Task parent;
	private TaskType type;
	private String url;
	private Product product;
	private String includePattern, excludePattern;
	private BigDecimal lastPrice;
	private Date lastPriceDate;

}
