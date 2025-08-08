package hexas.db.dbo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceNotification {
	private int task;
	private Date date;
	private TaskType type;
	private String url;
	private String marque, name;
	private BigDecimal price;
	private String notifyChannel;
}
