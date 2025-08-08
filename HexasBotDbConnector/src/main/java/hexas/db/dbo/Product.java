package hexas.db.dbo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
	private String name;
	private String msrp;
	private BigDecimal notifyPrice;
	private String notifyChannel;
	private String url;
	private BigDecimal lastPrice;
	private Date lastPriceDate;
	private List<Product> prices;

	// à ne pas conservé
	@Deprecated
	private String model;
	@Deprecated
	private String vendor;
	@Deprecated
	private String scanner;
	@Deprecated
	private String includePattern, excludePattern;
	@Deprecated
	private boolean notify;

	@Deprecated
	public String getFormatedVendor() {
		if (vendor == null)
			return "--";
		return VENDOR.matcher(vendor).replaceAll("");
	}

	@Deprecated
	public boolean isGroup() {
		return scanner.startsWith("group-");
	}

	public String getDescription() {
		return marque + " " + (name == null ? model : name);
	}
}
