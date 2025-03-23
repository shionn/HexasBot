package hexas.db.dbo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private static final Pattern VENDOR = Pattern.compile("(Braunecker Commerce )");
	private int id;
	private String marque;
	private String metaModel;
	private String model;
	private String url;
	private String msrp;
	private BigDecimal lastPrice;
	private BigDecimal notifyPrice;
	private Date lastPriceDate;
	private String vendor;
	private String notifyChannel;
	private String scanner;
	private boolean notify;

	public String getFormatedVendor() {
		if (vendor == null)
			return "--";
		return VENDOR.matcher(vendor).replaceAll("");
	}
}
